package com.kredivation.aakhale.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.UmpireAdapter;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.database.AakhelDBHelper;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ContentDataAsArray;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.Sports;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UmpireListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    ASTFontTextIconView sortBy;
    RecyclerView rvList;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    ArrayList<Data> umpireList;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private UmpireAdapter adapter;
    private Toolbar toolbar;
    boolean selectFlag = false;
    int total_pages = 1;

    public UmpireListActivity() {
        // Required empty public constructor
    }

    ArrayList<Sports> sportList;
    AlertDialog.Builder builderSingle;
    int selectedSportPosition = 0;
    long selectSportId;
    ArrayAdapter<String> arrayAdapter;
    String serviceURL;
    int sortFlag = 1;
    EditText searchedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_umpire_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        selectFlag = getIntent().getBooleanExtra("CreateMatch", false);
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
        sortBy = findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        rvList = findViewById(R.id.rvList);
        LinearLayout sortByLayout = findViewById(R.id.sortByLayout);
        sortByLayout.setOnClickListener(this);
        ImageView searchIcon = findViewById(R.id.searchIcon);
        searchIcon.setOnClickListener(this);
        searchedit = findViewById(R.id.searchedit);
        mLayoutManager = new LinearLayoutManager(UmpireListActivity.this);
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = findViewById(R.id.loaddataProgress);
        umpireList = new ArrayList<>();
        adapter = new UmpireAdapter(UmpireListActivity.this, umpireList, selectFlag);
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
                                getUmireList();
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
                getUmireList();
            }
        });
        setSportValue();
    }

    private void getUmireList() {
        if (Utility.isOnline(UmpireListActivity.this)) {
            loaddataProgress.setVisibility(View.VISIBLE);
            ASTProgressBar dotDialog = new ASTProgressBar(UmpireListActivity.this);
            // dotDialog.show();
            if (sortFlag == 1) {
                serviceURL = Contants.BASE_URL + Contants.UserList + "umpire&page=" + currentPage;
            }
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(UmpireListActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getUmireList", new IAsyncWorkCompletedCallback() {
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
                                        JSONArray umpire_tournament = dataArray.getJSONArray("umpire_tournament");
                                        if (umpire_tournament != null) {
                                            data.setUmpire_tournament(umpire_tournament.toString());
                                        }
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
                                        String fee_per_match_day = jsonObject.optString("fee_per_match_day");
                                        data.setFee_per_match_day(fee_per_match_day);


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
                                        umpireList.add(data);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                loading = true;
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            } else {
                                Toast.makeText(UmpireListActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                            loaddataProgress.setVisibility(View.GONE);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        Toast.makeText(UmpireListActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                        loaddataProgress.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, UmpireListActivity.this);//off line msg....
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortByLayout:
                builderSingle.setSingleChoiceItems(arrayAdapter, selectedSportPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedSportPosition = which;
                        selectSportId = sportList.get(which).getId();
                        serviceURL = Contants.BASE_URL + Contants.UserList + "umpire&page=" + currentPage + "&sports=" + selectSportId;
                        sortFlag = 2;
                        currentPage = 1;
                        mSwipeRefreshLayout.setRefreshing(true);
                        umpireList.clear();
                        getUmireList();
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
                break;
            case R.id.searchIcon:
                String userId = searchedit.getText().toString().trim();
                if (userId != null && userId.length() != 0) {
                    serviceURL = Contants.BASE_URL + "users?filter[unique_id]=" + userId;//users?filter[unique_id][like]=
                    sortFlag = 3;
                    currentPage = 1;
                    mSwipeRefreshLayout.setRefreshing(true);
                    umpireList.clear();
                    getUmireList();
                } else {
                    Toast.makeText(this, "Please enter user Id!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        sortFlag = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        umpireList.clear();
        getUmireList();
    }

    private void setSportValue() {
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        AakhelDBHelper switchDBHelper = new AakhelDBHelper(this);
        ContentData contentData = switchDBHelper.getMasterDataById(1);
        if (contentData != null && contentData.getData() != null) {
            sportList = new ArrayList<>();
            if (contentData.getData().getSports() != null) {
                sportList = contentData.getData().getSports();
                for (Sports sports : contentData.getData().getSports()) {
                    arrayAdapter.add(sports.getSports_name());
                }
            }
        }
        builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Select Sport");
    }

    @Override
    public void onBackPressed() {
        getUmpireValue();
    }

    private void getUmpireValue() {
        ArrayList<Data> selectedumpireList = new ArrayList<>();
        String IdsStr = "";
        if (umpireList != null && umpireList.size() > 0) {
            String separatorComm = ",";
            StringBuilder stringBuilders = new StringBuilder();
            for (int i = 0; i < umpireList.size(); i++) {
                if (umpireList.get(i).isSelectValue()) {
                    selectedumpireList.add(umpireList.get(i));
                    stringBuilders.append(String.valueOf(umpireList.get(i).getId()));
                    stringBuilders.append(",");
                }
            }
            IdsStr = stringBuilders.toString();
            if (IdsStr != null && !IdsStr.equals("")) {
                IdsStr = IdsStr.substring(0, IdsStr.length() - separatorComm.length());
            }
        }
        Intent output = new Intent();
        output.putExtra("selectUmpireId", IdsStr);
        output.putExtra("selectedUmpire", new Gson().toJson(selectedumpireList));
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
