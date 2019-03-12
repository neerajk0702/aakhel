package com.kredivation.aakhale.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.MyTournamnetAdapter;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Tournament;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyTournamentActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rvList;
    int total_pages = 1;
    private Toolbar toolbar;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MyTournamnetAdapter adapter;
    ArrayList<Tournament> tournamentArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tournament_fragment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    public void init() {
        Typeface materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/materialdesignicons-webfont.otf");
        TextView back = toolbar.findViewById(R.id.back);
        back.setTypeface(materialdesignicons_font);
        back.setText(Html.fromHtml("&#xf30d;"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rvList = findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(MyTournamentActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = findViewById(R.id.loaddataProgress);
        tournamentArrayList = new ArrayList<>();
        adapter = new MyTournamnetAdapter(MyTournamentActivity.this, tournamentArrayList);
        rvList.setAdapter(adapter);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            currentPage += 1;
                            if (currentPage <= total_pages) {
                                getTournamentMatch();
                            }
                        }
                    }
                }
            }


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING || newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // playerAdapter.onScrolled(recyclerView);
                }
            }

        });

        // SwipeRefreshLayout
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server first time
                getTournamentMatch();
            }
        });

        /*rvList.addOnItemTouchListener(
                new RecyclerItemClickListener(TeamListActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        teamArrayList.get(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/
    }

    //get GgetTournamentMatch
    private void getTournamentMatch() {
        if (Utility.isOnline(MyTournamentActivity.this)) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.tournamentAPi + "?page=" + currentPage + "&" + "my_tournament=1";
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(MyTournamentActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getTournamentMatch", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                total_pages = mainObj.optInt("total_pages");
                                JSONArray dataArray = mainObj.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        Tournament tournament = new Tournament();
                                        JSONObject mainData = dataArray.getJSONObject(i);
                                        JSONObject innerDataObject = mainData.optJSONObject("data");
                                        JSONObject jsonObject = innerDataObject.optJSONObject("basic_info");
                                        int id = jsonObject.optInt("id");
                                        String name = jsonObject.optString("name");
                                        String unique_id = jsonObject.optString("unique_id");
                                        String created_at = jsonObject.optString("created_at");
                                        String updated_at = jsonObject.optString("updated_at");
                                        int is_active = jsonObject.optInt("is_active");
                                        String about_tournament = jsonObject.optString("about_tournament");
                                        String tournament_address = jsonObject.optString("tournament_address");
                                        String format = jsonObject.optString("format");
                                        String overs = jsonObject.optString("overs");
                                        String start_date = jsonObject.optString("start_date");
                                        String end_date = jsonObject.optString("end_date");
                                        String entry_fees = jsonObject.optString("entry_fees");
                                        String tournament_zipcode = jsonObject.optString("tournament_zipcode");
                                        int statusValue = jsonObject.optInt("status");
                                        String display_picture = jsonObject.optString("display_picture");
                                        String rules_regulations = jsonObject.optString("rules_regulations");
                                        JSONObject tournament_country = jsonObject.optJSONObject("tournament_country");
                                        JSONObject tournament_city = jsonObject.optJSONObject("tournament_city");
                                        JSONObject tournament_state = jsonObject.optJSONObject("tournament_state");
                                        String user_id = jsonObject.optString("user_id");
                                        String prizes = jsonObject.optString("prizes");
                                        String facilities = jsonObject.optString("facilities");
                                        JSONArray tournament_teamArray = innerDataObject.optJSONArray("tournament_team");
                                        JSONArray tournament_umpire = innerDataObject.optJSONArray("tournament_umpire");

                                        tournament.setId(id);
                                        tournament.setEnd_date(end_date);
                                        tournament.setIs_active(is_active);
                                        tournament.setUnique_id(unique_id);
                                        tournament.setUser_id(user_id);
                                        tournament.setCreated_at(created_at);
                                        tournament.setUpdated_at(updated_at);
                                        tournament.setRules_regulations(rules_regulations);
                                        tournament.setTournament_zipcode(tournament_zipcode);
                                        tournament.setFormat(format);
                                        tournament.setTournament_country(tournament_country.toString());
                                        tournament.setOvers(overs);
                                        tournament.setTournament_city(tournament_city.toString());
                                        tournament.setEntry_fees(entry_fees);
                                        tournament.setAbout_tournament(about_tournament);
                                        tournament.setTournament_state(tournament_state.toString());
                                        tournament.setTournament_address(tournament_address);
                                        tournament.setName(name);
                                        tournament.setPrizes(prizes);
                                        tournament.setPrizes(facilities);
                                        tournament.setStart_date(start_date);
                                        tournament.setDisplay_picture(display_picture);
                                        tournament.setStatus(statusValue);
                                        String completeAdd = tournament_address + "," + tournament_city.optString("city_name") + "," + tournament_state.optString("state_name") + "," + tournament_country.optString("country_name") + "," + tournament_zipcode;
                                        tournament.setCompleteAddress(completeAdd);
                                        tournament.setTournamentTeam(tournament_teamArray);
                                        tournament.setTournament_umpire(tournament_umpire);
                                        tournamentArrayList.add(tournament);
                                    }
                                    adapter.notifyDataSetChanged();
                                    loading = true;
                                    loaddataProgress.setVisibility(View.GONE);
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MyTournamentActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                        loaddataProgress.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, MyTournamentActivity.this);//off line msg....
        }
    }

    @Override
    public void onRefresh() {
        currentPage=1;
        mSwipeRefreshLayout.setRefreshing(true);
        tournamentArrayList.clear();
        getTournamentMatch();
    }

    @Override
    public void onClick(View view) {

    }
}
