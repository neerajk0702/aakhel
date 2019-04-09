package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.TeamListActivity;
import com.kredivation.aakhale.adapter.AcadamicAdapter;
import com.kredivation.aakhale.adapter.GroundAdapter;
import com.kredivation.aakhale.adapter.PlayerAdapter;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.ContentDataAsArray;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.GroundData;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroundFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    View view;
    ASTFontTextIconView sortBy;
    RecyclerView rvList;

    ArrayList<Academics> dataModels;
    private static GroundAdapter adapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    ArrayList<GroundData> groundList;
    private GroundAdapter groundAdapter;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int total_pages = 1;
    EditText searchedit;
    String serviceURL;
    int sortFlag = 1;
    public GroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ground, container, false);
        init();
        getActivity().setTitle("Ground");
        return view;
    }

    private void init() {
        sortBy = view.findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        rvList = view.findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = view.findViewById(R.id.loaddataProgress);
        groundList = new ArrayList<>();
        groundAdapter = new GroundAdapter(getContext(), groundList);
        rvList.setAdapter(groundAdapter);
        searchedit = view.findViewById(R.id.searchedit);
        ImageView searchIcon = view.findViewById(R.id.searchIcon);
        searchIcon.setOnClickListener(this);
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
                                getGroundlist();
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
                getGroundlist();
            }
        });
    }

    //get Ground list
    private void getGroundlist() {
        if (Utility.isOnline(getContext())) {
            loaddataProgress.setVisibility(View.VISIBLE);
            if (sortFlag == 1) {
                 serviceURL = Contants.BASE_URL + Contants.SaveGround + "page=" + currentPage;
            }
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getGroundlist", new IAsyncWorkCompletedCallback() {
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
                                        GroundData groundData = new GroundData();
                                        JSONObject obj = dataArray.getJSONObject(i);
                                        JSONObject jsonObject = obj.optJSONObject("data");
                                        int id = jsonObject.optInt("id");
                                        String name = jsonObject.optString("name");
                                        String unique_id = jsonObject.optString("unique_id");
                                        String user_id = jsonObject.optString("user_id");
                                        String created_at = jsonObject.optString("created_at");
                                        String updated_at = jsonObject.optString("updated_at");
                                        int is_active = jsonObject.optInt("is_active");
                                        int available = jsonObject.optInt("available");
                                        String ground_address = jsonObject.optString("ground_address");
                                        JSONObject city = jsonObject.optJSONObject("ground_city");
                                        JSONObject state = jsonObject.optJSONObject("ground_state");
                                        JSONObject country = jsonObject.optJSONObject("ground_country");
                                        String ground_zipcode = jsonObject.optString("ground_zipcode");
                                        int floodlight = jsonObject.optInt("floodlight");
                                        String capacity = jsonObject.optString("capacity");
                                        String dimension = jsonObject.optString("dimension");
                                        JSONArray timezone = jsonObject.optJSONArray("timezone");
                                        String match_per_day = jsonObject.optString("match_per_day");
                                        int day_or_night = jsonObject.optInt("day_or_night");
                                        String surface = jsonObject.optString("surface");
                                        String free_services = jsonObject.optString("free_services");
                                        String terms_conditions = jsonObject.optString("terms_conditions");
                                        String cost = jsonObject.optString("cost");
                                        String display_picture = jsonObject.optString("display_picture");
                                        JSONArray sports = jsonObject.optJSONArray("sports");
                                        String achievements = jsonObject.optString("achievements");
                                        String staff = jsonObject.optString("staff");
                                        groundData.setId(id);
                                        groundData.setName(name);
                                        groundData.setUnique_id(unique_id);
                                        groundData.setUser_id(user_id);
                                        groundData.setCreated_at(created_at);
                                        groundData.setUpdated_at(updated_at);
                                        groundData.setIs_active(is_active);
                                        groundData.setAvailable(available);
                                        groundData.setGround_address(ground_address);
                                        String cityStr = "";
                                        String countryNameStr = "";
                                        String stateNameStr = "";
                                        if (city != null) {
                                            groundData.setGround_city(city.toString());
                                            cityStr = city.optString("city_name");
                                        }
                                        if (state != null) {
                                            groundData.setGround_state(state.toString());
                                            stateNameStr = state.optString("state_name");
                                        }
                                        if (country != null) {
                                            groundData.setGround_country(country.toString());
                                            countryNameStr = country.optString("country_name");
                                        }
                                        String completeAdd = ground_address + "," + cityStr + "," + stateNameStr + "," + countryNameStr + "," + ground_zipcode;
                                        groundData.setCompleteAddress(completeAdd);
                                        groundData.setGround_zipcode(ground_zipcode);
                                        groundData.setFloodlight(floodlight);
                                        groundData.setCapacity(capacity);
                                        groundData.setDimension(dimension);
                                        if (timezone != null) {
                                            groundData.setTimezone(timezone.toString());
                                        }
                                        groundData.setMatch_per_day(match_per_day);
                                        groundData.setDay_or_night(day_or_night);
                                        groundData.setSurface(surface);
                                        groundData.setFree_services(free_services);
                                        groundData.setTerms_conditions(terms_conditions);
                                        groundData.setCost(cost);
                                        groundData.setDisplay_picture(display_picture);
                                        if (sports != null) {
                                            groundData.setSports(sports.toString());
                                        }
                                        groundData.setStaff(staff);
                                        groundData.setAchievements(achievements);
                                        groundList.add(groundData);
                                    }
                                    groundAdapter.notifyDataSetChanged();
                                    loading = true;
                                    loaddataProgress.setVisibility(View.GONE);
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }else{
                                    loading = true;
                                    loaddataProgress.setVisibility(View.GONE);
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            }else{
                                loading = true;
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                        loading = true;
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
            case R.id.searchIcon:
                String userId = searchedit.getText().toString().trim();
                if (userId != null && userId.length() != 0) {
                    serviceURL = Contants.BASE_URL + "ground?filter[unique_id]=" + userId;//users?filter[unique_id][like]=
                    sortFlag = 2;
                    currentPage = 1;
                    mSwipeRefreshLayout.setRefreshing(true);
                    groundList.clear();
                    getGroundlist();
                } else {
                    Toast.makeText(getContext(), "Please enter Ground Id!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        currentPage=1;
        sortFlag=1;
        mSwipeRefreshLayout.setRefreshing(true);
        groundList.clear();
        getGroundlist();
    }
}
