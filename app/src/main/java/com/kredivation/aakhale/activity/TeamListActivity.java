package com.kredivation.aakhale.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.TeamsAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.RecyclerItemClickListener;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
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
    int total_pages = 1;
    private Toolbar toolbar;

    public TeamListActivity() {
        // Required empty public constructor
    }

    boolean selectFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_teame);
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
        selectFlag = getIntent().getBooleanExtra("CreateMatch", false);
        sortBy = findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        rvList = findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(TeamListActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = findViewById(R.id.loaddataProgress);
        teamArrayList = new ArrayList<>();
        teamsAdapter = new TeamsAdapter(TeamListActivity.this, teamArrayList, selectFlag);
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
                            if (currentPage <= total_pages) {
                                getTeamList();
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
                getTeamList();
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


    //get getTeamList
    private void getTeamList() {
        if (Utility.isOnline(TeamListActivity.this)) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.teamCreate + "?page=" + currentPage;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(TeamListActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getTeamList", new IAsyncWorkCompletedCallback() {
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
                                        Team teamdata = new Team();
                                        JSONObject obj = dataArray.getJSONObject(i);
                                        JSONObject jsonObject = obj.getJSONObject("basic_info");
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
                                        String about_team = jsonObject.optString("about_team");

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
                        Toast.makeText(TeamListActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                        loaddataProgress.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, TeamListActivity.this);//off line msg....
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
        currentPage=1;
        mSwipeRefreshLayout.setRefreshing(true);
        teamArrayList.clear();
        getTeamList();
    }

    @Override
    public void onBackPressed() {
        getTeamValue();
    }

    private void getTeamValue() {
        ArrayList<Team> teamList = new ArrayList<>();
        String teamIdsStr = "";
        if (teamArrayList != null && teamArrayList.size() > 0) {
            String separatorComm = ",";
            StringBuilder stringBuilders = new StringBuilder();
            for (int i = 0; i < teamArrayList.size(); i++) {
                if (teamArrayList.get(i).isSelectValue()) {
                    teamList.add(teamArrayList.get(i));
                    stringBuilders.append(String.valueOf(teamArrayList.get(i).getId()));
                    stringBuilders.append(",");
                }
            }
            teamIdsStr = stringBuilders.toString();
            if (teamIdsStr != null && !teamIdsStr.equals("")) {
                teamIdsStr = teamIdsStr.substring(0, teamIdsStr.length() - separatorComm.length());
            }
        }
        Intent output = new Intent();
        output.putExtra("selectTeamId", teamIdsStr);
        output.putExtra("selectedTeam", new Gson().toJson(teamList));
        setResult(RESULT_OK, output);
        finish();
    }

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
