package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddTournamnetPAdapter;
import com.kredivation.aakhale.adapter.GroundAdapter;
import com.kredivation.aakhale.adapter.TournamentUpcoomingAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.GroundData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Tournament;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class UpcommingMatchFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    View view;
    ASTFontTextIconView sortBy;
    RecyclerView rvList;

    ArrayList<Academics> dataModels;
    private static GroundAdapter adapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    ArrayList<Tournament> tournamentArrayList;
    private TournamentUpcoomingAdapter tournamentUpcoomingAdapter;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int total_pages = 1;
    public UpcommingMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcomming_match, container, false);
        init();
        return view;
    }

    private void init() {
        rvList = view.findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = view.findViewById(R.id.loaddataProgress);
        tournamentArrayList = new ArrayList<>();
        tournamentUpcoomingAdapter = new TournamentUpcoomingAdapter(getContext(), tournamentArrayList);
        rvList.setAdapter(tournamentUpcoomingAdapter);

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
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
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
    }
    //get GgetTournamentMatch
    private void getTournamentMatch() {
        if (Utility.isOnline(getContext())) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.tournamentAPi + "?page=" + currentPage + "&" + "list=upcoming";
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
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
                                        tournamentArrayList.add(tournament);
                                    }
                                    tournamentUpcoomingAdapter.notifyDataSetChanged();
                                    loading = true;
                                    loaddataProgress.setVisibility(View.GONE);
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                        loaddataProgress.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortBy:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        tournamentArrayList.clear();
        getTournamentMatch();
    }
}
