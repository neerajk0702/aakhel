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
import com.kredivation.aakhale.adapter.MyMatchAdapter;
import com.kredivation.aakhale.adapter.MyTournamnetAdapter;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Match;
import com.kredivation.aakhale.model.Tournament;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyMatchActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    public MyMatchActivity() {
        // Required empty public constructor
    }

    RecyclerView rvList;
    int total_pages = 1;
    private Toolbar toolbar;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MyMatchAdapter adapter;
    ArrayList<Match> arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_match);
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
        mLayoutManager = new LinearLayoutManager(MyMatchActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = findViewById(R.id.loaddataProgress);
        arrayList = new ArrayList<>();
        adapter = new MyMatchAdapter(MyMatchActivity.this, arrayList);
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
                                getMatchList();
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
                getMatchList();
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

    //get getMatchList
    private void getMatchList() {
        if (Utility.isOnline(MyMatchActivity.this)) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.creatematchApi + "?page=" + currentPage + "&" + "my_match=1";
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(MyMatchActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getMatchList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            total_pages = mainObj.optInt("total_pages");
                            if (status) {
                                JSONArray mainDataArray = mainObj.getJSONArray("data");
                                if (mainDataArray != null) {
                                    for (int i = 0; i < mainDataArray.length(); i++) {
                                        JSONObject obj = mainDataArray.optJSONObject(i);
                                        JSONObject dataArray = obj.optJSONObject("data");
                                        Match matchdata = new Match();
                                        JSONArray umpireArray = dataArray.getJSONArray("match_umpire");
                                        JSONArray teamArray = dataArray.getJSONArray("match_team");
                                        JSONObject ground_data = dataArray.getJSONObject("ground_data");
                                        matchdata.setMatchUmpire(umpireArray.toString());
                                        matchdata.setMatchteam(teamArray.toString());
                                        matchdata.setMatchGround(ground_data.toString());

                                        JSONObject jsonObject = dataArray.getJSONObject("basic_info");

                                        int match_type = jsonObject.optInt("match_type");
                                        int is_active = jsonObject.optInt("is_active");
                                        JSONObject match_city = jsonObject.getJSONObject("match_city");
                                        JSONObject match_state = jsonObject.getJSONObject("match_state");
                                        JSONObject match_country = jsonObject.getJSONObject("match_country");
                                        matchdata.setMatchCity(match_city.toString());
                                        matchdata.setMatchState(match_state.toString());
                                        matchdata.setMatchCountry(match_country.toString());

                                        String format = jsonObject.optString("format");
                                        String ground_id = jsonObject.optString("ground_id");
                                        String date = jsonObject.optString("date");
                                        int id = jsonObject.optInt("id");
                                        String unique_id = jsonObject.optString("unique_id");
                                        String match_zipcode = jsonObject.optString("match_zipcode");
                                        String over = jsonObject.optString("over");
                                        String time = jsonObject.optString("time");
                                        String tournament_id = jsonObject.optString("tournament_id");
                                        String name = jsonObject.optString("name");
                                        String created_at = jsonObject.optString("created_at");
                                        String user_id = jsonObject.optString("user_id");
                                        String match_address = jsonObject.optString("match_address");
                                        matchdata.setIs_active(String.valueOf(is_active));
                                        matchdata.setId(id);
                                        matchdata.setMatch_type(String.valueOf(match_type));
                                        matchdata.setFormat(format);
                                        matchdata.setGround_id(ground_id);
                                        matchdata.setDate(date);
                                        matchdata.setUnique_id(unique_id);
                                        matchdata.setMatch_zipcode(match_zipcode);
                                        matchdata.setOver(over);
                                        matchdata.setTime(time);
                                        matchdata.setTournament_id(tournament_id);
                                        matchdata.setName(name);
                                        matchdata.setCreated_at(created_at);
                                        matchdata.setUser_id(user_id);
                                        matchdata.setMatch_address(match_address);
                                        arrayList.add(matchdata);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                loading = true;
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);

                            } else {
                                Toast.makeText(MyMatchActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MyMatchActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                        loaddataProgress.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, MyMatchActivity.this);//off line msg....
        }
    }

    @Override
    public void onRefresh() {
        currentPage=1;
        mSwipeRefreshLayout.setRefreshing(true);
        arrayList.clear();
        getMatchList();
    }

    @Override
    public void onClick(View view) {

    }
}
