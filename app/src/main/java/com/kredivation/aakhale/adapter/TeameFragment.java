package com.kredivation.aakhale.adapter;


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

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.ContentDataAsArray;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Match;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeameFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    View view;
    RecyclerView rvList;
    ASTButton saveBtn;
    ASTFontTextIconView sortBy;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Team> teamArrayList;
    private TeamsAdapter teamsAdapter;

    public TeameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teame, container, false);
        getActivity().setTitle("Teams");
        init();
        return view;

    }

    public void init() {
        sortBy = view.findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        rvList = view.findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = view.findViewById(R.id.loaddataProgress);
        teamArrayList = new ArrayList<>();
        teamsAdapter = new TeamsAdapter(getContext(), teamArrayList);
        rvList.setAdapter(teamsAdapter);

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
                            getTeamList();
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
                getTeamList();
            }
        });
    }


    //get getTeamList
    private void getTeamList() {
        if (Utility.isOnline(getContext())) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.teamCreate + "?page=" + currentPage;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getTeamList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                int total_pages = mainObj.optInt("total_pages");
                                JSONArray dataArray = mainObj.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        Team teamdata = new Team();
                                        JSONObject jsonObject = dataArray.getJSONObject(i);
                                        int is_active = jsonObject.optInt("is_active");
                                        String team_zipcode = jsonObject.optString("team_zipcode");
                                        String team_country = jsonObject.optString("team_country");
                                        String team_thumbnail = jsonObject.optString("team_thumbnail");
                                        String user_id = jsonObject.optString("user_id");
                                        String created_at = jsonObject.optString("created_at");
                                        String name = jsonObject.optString("name");
                                        String team_city = jsonObject.optString("team_city");
                                        int id = jsonObject.optInt("id");
                                        String updated_at = jsonObject.optString("updated_at");
                                        String unique_id = jsonObject.optString("unique_id");
                                        String team_state = jsonObject.optString("team_state");
                                        teamdata.setId(id);
                                        teamdata.setIs_active(is_active);
                                        teamdata.setTeam_zipcode(team_zipcode);
                                        teamdata.setTeam_country(team_country);
                                        teamdata.setTeam_thumbnail(team_thumbnail);
                                        teamdata.setUnique_id(unique_id);
                                        teamdata.setCreated_at(created_at);
                                        teamdata.setName(name);
                                        teamdata.setTeam_city(team_city);
                                        teamdata.setUpdated_at(updated_at);
                                        teamdata.setTeam_state(team_state);
                                        teamdata.setUser_id(user_id);
                                        teamArrayList.add(teamdata);
                                    }
                                    teamsAdapter.notifyDataSetChanged();
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
        teamArrayList.clear();
        getTeamList();
    }

}
