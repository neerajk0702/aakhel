package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.kredivation.aakhale.adapter.PlayerAdapter;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentDataAsArray;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddPlayersActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Toolbar toolbar;
    boolean selectFlag = false;
    RecyclerView rvList;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    ArrayList<Data> playerList;
    private PlayerAdapter playerAdapter;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    long total_pages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
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
        selectFlag = getIntent().getBooleanExtra("CreateTeam", false);

        rvList = findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(AddPlayersActivity.this);

        loaddataProgress = findViewById(R.id.loaddataProgress);
        playerList = new ArrayList<>();
        rvList.setLayoutManager(mLayoutManager);
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
                                getPlayerListData();
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

                // Fetching data from server
                getPlayerListData();
            }
        });
        playerAdapter = new PlayerAdapter(AddPlayersActivity.this, playerList, true);
        rvList.setAdapter(playerAdapter);
    }

    //get players list
    private void getPlayerListData() {
        if (Utility.isOnline(AddPlayersActivity.this)) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.UserList + "player&page=" + currentPage;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(AddPlayersActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getPlayerListData", new IAsyncWorkCompletedCallback() {
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
                                        Data data = new Data();
                                        JSONArray umpire_match = dataArray.getJSONArray("umpire_match");
                                        JSONArray player_team = dataArray.getJSONArray("player_team");
                                        if (umpire_match != null) {
                                            data.setUmpireMatchArray(umpire_match.toString());
                                        }
                                        if (player_team != null) {
                                            data.setPlayerTeamArray(player_team.toString());
                                        }
                                        JSONObject jsonObject = dataArray.getJSONObject("basic_info");
                                        int id = jsonObject.optInt("id");
                                        String unique_id = jsonObject.optString("unique_id");
                                        String email = jsonObject.optString("email");
                                        String created_at = jsonObject.optString("created_at");
                                        String updated_at = jsonObject.optString("updated_at");
                                        JSONObject role = jsonObject.optJSONObject("role");
                                        int is_active = jsonObject.optInt("is_active");
                                        String full_name = jsonObject.optString("full_name");
                                        String mobile = jsonObject.optString("mobile");
                                        String date_of_birth = jsonObject.optString("date_of_birth");
                                        int gender = jsonObject.optInt("gender");
                                        String address = jsonObject.optString("address");
                                        JSONObject city = jsonObject.optJSONObject("city");
                                        JSONObject state = jsonObject.optJSONObject("state");
                                        JSONObject country = jsonObject.optJSONObject("country");
                                        String zipcode = jsonObject.optString("zipcode");
                                        JSONArray users_sports = jsonObject.optJSONArray("users_sports");
                                        String about = jsonObject.optString("about");
                                        String experience = jsonObject.optString("experience");
                                        String profile_picture = jsonObject.optString("profile_picture");
                                        String auth_token = jsonObject.optString("auth_token");
                                        JSONObject player_role = jsonObject.optJSONObject("player_role");
                                        String cityStr = "";
                                        String countryNameStr = "";
                                        String stateNameStr = "";
                                        if (city != null) {
                                            cityStr = city.optString("city_name");
                                            data.setCityObj(city.toString());
                                        }
                                        if (state != null) {
                                            stateNameStr = state.optString("state_name");
                                            data.setStateObj(state.toString());
                                        }
                                        if (country != null) {
                                            countryNameStr = country.optString("country_name");
                                            data.setCountry(country.toString());
                                        }
                                        data.setId(id);
                                        data.setUnique_id(unique_id);
                                        data.setEmail(email);
                                        data.setCreated_at(created_at);
                                        data.setUpdated_at(updated_at);
                                        data.setRoleObj(role.toString());
                                        data.setIs_active(is_active);
                                        data.setFull_name(full_name);
                                        data.setMobile(mobile);
                                        data.setDate_of_birth(date_of_birth);
                                        data.setGender(gender);
                                        data.setAddress(address);
                                        String completeAdd = address + "," + cityStr + "," + stateNameStr + "," + countryNameStr + "," + zipcode;
                                        data.setComplateAddress(completeAdd);
                                        data.setZipcode(zipcode);
                                        if (users_sports != null) {
                                            data.setUsersSportArray(users_sports.toString());
                                        }
                                        data.setAbout(about);
                                        data.setExperience(experience);
                                        data.setProfile_picture(profile_picture);
                                        data.setAuth_token(auth_token);
                                        if (player_role != null) {
                                            data.setPlayerRoleObj(player_role.toString());
                                        }
                                        playerList.add(data);
                                    }
                                }
                                playerAdapter.notifyDataSetChanged();
                                loading = true;
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            } else {
                                Toast.makeText(AddPlayersActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(AddPlayersActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                        loading = true;
                        loaddataProgress.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, AddPlayersActivity.this);//off line msg....
        }
    }

    @Override
    public void onRefresh() {
         currentPage = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        playerList.clear();
        getPlayerListData();
    }

    @Override
    public void onBackPressed() {
        getValue();
    }

    private void getValue() {
        ArrayList<Data> teamList = new ArrayList<>();
        String IdsStr = "";
        if (playerList != null && playerList.size() > 0) {
            String separatorComm = ",";
            StringBuilder stringBuilders = new StringBuilder();
            for (int i = 0; i < playerList.size(); i++) {
                if (playerList.get(i).isSelectValue()) {
                    teamList.add(playerList.get(i));
                    stringBuilders.append(String.valueOf(playerList.get(i).getUnique_id()));
                    stringBuilders.append(",");
                }
            }
            IdsStr = stringBuilders.toString();
            if (IdsStr != null && !IdsStr.equals("")) {
                IdsStr = IdsStr.substring(0, IdsStr.length() - separatorComm.length());
            }
        }
        Intent output = new Intent();
        output.putExtra("selectId", IdsStr);
        output.putExtra("selectedData", new Gson().toJson(teamList));
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

    @Override
    public void onClick(View view) {

    }
}
