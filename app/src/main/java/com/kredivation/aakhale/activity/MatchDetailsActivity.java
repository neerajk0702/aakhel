package com.kredivation.aakhale.activity;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.components.CircleImageView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Match;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MatchDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nametxt, datetime, uniqueId, statustxt, overtxt, teams, formateMatch, venueAddress, venueAddress1;
    LinearLayout umpireView, teamsView;

    public MatchDetailsActivity() {
        // Required empty public constructor
    }

    Match MatchDetail;
    ASTProgressBar astProgressBar;
    long userIdValue;
    int userRoleId;
    int matchId;
    private Toolbar toolbar;
    boolean MyMatchFlag;
    Button matchStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_match_details);
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

        SharedPreferences UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
        userIdValue = UserInfo.getLong("id", 0);
        userRoleId = UserInfo.getInt("role", 0);
        String MatchDetailStr = getIntent().getStringExtra("MatchDetail");
        MatchDetail = new Gson().fromJson(MatchDetailStr, new TypeToken<Match>() {
        }.getType());
        MyMatchFlag = getIntent().getBooleanExtra("MyMatchFlag", false);
        nametxt = findViewById(R.id.name);
        datetime = findViewById(R.id.datetime);
        uniqueId = findViewById(R.id.uniqueId);
        statustxt = findViewById(R.id.status);
        overtxt = findViewById(R.id.over);
        teams = findViewById(R.id.teams);
        formateMatch = findViewById(R.id.formateMatch);
        venueAddress = findViewById(R.id.venueAddress);
        venueAddress1 = findViewById(R.id.venueAddress1);
        umpireView = findViewById(R.id.umpireView);
        teamsView = findViewById(R.id.teamsView);
        Button button = findViewById(R.id.submit);
        button.setOnClickListener(this);
        if (userRoleId == 3) {
            button.setVisibility(View.VISIBLE);
        }
        matchStart = findViewById(R.id.matchStart);
        matchStart.setOnClickListener(this);

        dataToView();
    }

    public void dataToView() {
        if (MatchDetail != null) {
            try {
                matchId = MatchDetail.getId();
                nametxt.setText(MatchDetail.getName());
                datetime.setText(MatchDetail.getDate() + ", " + MatchDetail.getTime());
                uniqueId.setText(MatchDetail.getUnique_id());
                if (MatchDetail.getIs_active().equals("1")) {
                    statustxt.setText("OPEN");
                } else {
                    statustxt.setText("CLOSE");
                }
                overtxt.setText(MatchDetail.getOver() + "");
                formateMatch.setText(MatchDetail.getFormat());

                JSONObject cityObj = new JSONObject(MatchDetail.getMatchCity());
                JSONObject stateObj = new JSONObject(MatchDetail.getMatchState());
                JSONObject matchGroundObj = new JSONObject(MatchDetail.getMatchGround());
                venueAddress.setText(matchGroundObj.optString("name") + "");
                String completeAddress = matchGroundObj.optString("address") + "," + matchGroundObj.optString("city") + "," + matchGroundObj.optString("state") + "," + matchGroundObj.optString("zipcode");
                venueAddress1.setText(completeAddress);
                String matchTeam = MatchDetail.getMatchteam();
                JSONArray teamArray = new JSONArray(matchTeam);
                if (teamArray != null) {
                    teams.setText(teamArray.length() + "");
                    for (int i = 0; i < teamArray.length(); i++) {
                        JSONObject jsonObject = teamArray.getJSONObject(i);
                        String uname = jsonObject.optString("name");
                        String unique_id = jsonObject.optString("unique_id");
                        String team_thumbnail = jsonObject.optString("team_thumbnail");
                        String status = jsonObject.optString("status");
                        addTeamView(uname, unique_id, team_thumbnail, teamsView);
                    }
                }
                String matchUmpire = MatchDetail.getMatchUmpire();
                JSONArray umpireArray = new JSONArray(matchUmpire);
                if (umpireArray != null) {
                    for (int i = 0; i < umpireArray.length(); i++) {
                        JSONObject jsonObject = umpireArray.getJSONObject(i);
                        String uname = jsonObject.optString("name");
                        String profile_picture = jsonObject.optString("profile_picture");
                        String unique_id = jsonObject.optString("unique_id");
                        String status = jsonObject.optString("status");
                        addTeamView(uname, unique_id, profile_picture, umpireView);
                    }
                }
                if (MyMatchFlag) {
                    //if match date then show button for start match
                    if (ASTUIUtil.compareDate(MatchDetail.getDate())) {
                        matchStart.setVisibility(View.VISIBLE);
                    }
                }

            } catch (JSONException e) {
                // e.printStackTrace();
            }
        }

    }

  /*  // getMatchDetails
    private void getMatchDetails() {
        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.creatematchApi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getMatchDetails", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                astProgressBar.dismiss();
                                JSONObject jsondata = mainObj.optJSONObject("data");
                                JSONObject basic_infodata = jsondata.optJSONObject("basic_info");

                                String name = basic_infodata.optString("name");
                                String unique_id = basic_infodata.optString("unique_id");
                                String user_id = basic_infodata.optString("user_id");
                                String created_at = basic_infodata.optString("created_at");
                                String updated_at = basic_infodata.optString("updated_at");
                                int is_active = basic_infodata.optInt("is_active");
                                String match_city = basic_infodata.optString("match_city");
                                String match_state = basic_infodata.optString("match_state");
                                String match_country = basic_infodata.optString("match_country");
                                String match_zipcode = basic_infodata.optString("match_zipcode");
                                String ground_id = basic_infodata.optString("ground_id");
                                String time = basic_infodata.optString("time");
                                String date = basic_infodata.optString("date");
                                String match_type = basic_infodata.optString("match_type");
                                String format = basic_infodata.optString("format");
                                String over = basic_infodata.optString("over");

                                nametxt.setText(name);
                                datetime.setText(date);
                                uniqueId.setText(unique_id);


                                if (is_active == 1) {
                                    statustxt.setText("Avilable");

                                } else {
                                    statustxt.setText("Not Avilable");
                                }


                                overtxt.setText(over);
                                // teams.setText(teams);
                                formateMatch.setText(format);
                                //venueAddress.setText(venueAddress);
                                //venueAddress1.setText(name);

                                JSONArray dataArray = jsondata.optJSONArray("match_team");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject jsonObject = dataArray.getJSONObject(i);
                                        String uname = jsonObject.optString("name");
                                        String ustatus = jsonObject.optString("status");
                                        addTeamView(uname);
                                    }
                                }


                                JSONArray udataArray = jsondata.optJSONArray("match_umpire");
                                if (dataArray != null) {
                                    for (int i = 0; i < udataArray.length(); i++) {
                                        JSONObject jsonObject = udataArray.getJSONObject(i);
                                        String uname = jsonObject.optString("name");
                                        String country = jsonObject.optString("country");
                                        String state = jsonObject.optString("state");
                                        String ustatus = jsonObject.optString("status");
                                        addUmpire(uname, country, state);
                                    }
                                }

                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                        astProgressBar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }*/


    //add free service layout in runtime
    public void addTeamView(String name, String unique_id, String team_thumbnail, LinearLayout mainView) {
        LayoutInflater inflater = LayoutInflater.from(MatchDetailsActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.sports_item_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        ImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        teamneName.setText(name);
        userId.setText(unique_id);
        Picasso.with(MatchDetailsActivity.this).load(Contants.BASE_URL + team_thumbnail).placeholder(R.drawable.ic_cricket_player).into(matchPerview);
        mainView.addView(inflatedLayout);
    }


    //add free service layout in runtime
    public void addUmpire(String name, String country, String state) {
        LayoutInflater inflater = LayoutInflater.from(MatchDetailsActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.umpire_itemm_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        CircleImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        TextView address = inflatedLayout.findViewById(R.id.address);
        address.setText(country + "," + state);
        teamneName.setText(name);
        umpireView.addView(inflatedLayout);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                request();
                break;
            case R.id.matchStart:
                matchStart();
                break;
        }
    }

    private void request() {
        if (Utility.isOnline(MatchDetailsActivity.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(MatchDetailsActivity.this);
            dotDialog.show();
            String serviceURL = "";
            if (userRoleId == 1) {//Player
                serviceURL = Contants.BASE_URL + Contants.match_umpire_request;
            } else if (userRoleId == 3) {//Umpire
                serviceURL = Contants.BASE_URL + Contants.match_umpire_request;
            }

            JSONObject object = new JSONObject();
            try {
                object.put("match_id", matchId);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(MatchDetailsActivity.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "MatchRequestumpire", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(MatchDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MatchDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            if (dotDialog.isShowing()) {
                                dotDialog.dismiss();
                            }
                        } catch (JSONException e) {
                        }
                    } else {
                        if (dotDialog.isShowing()) {
                            dotDialog.dismiss();
                        }
                        Utility.alertForErrorMessage(Contants.Error, MatchDetailsActivity.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, MatchDetailsActivity.this);//off line msg....
        }
    }
    private void matchStart() {
        if (Utility.isOnline(MatchDetailsActivity.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(MatchDetailsActivity.this);
            dotDialog.show();
            String serviceURL = "";
            if (userRoleId == 1) {//Player
                serviceURL = Contants.BASE_URL + Contants.match_umpire_request;
            } else if (userRoleId == 3) {//Umpire
                serviceURL = Contants.BASE_URL + Contants.match_umpire_request;
            }

            JSONObject object = new JSONObject();
            try {
                object.put("match_id", matchId);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(MatchDetailsActivity.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "MatchRequestumpire", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(MatchDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MatchDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            if (dotDialog.isShowing()) {
                                dotDialog.dismiss();
                            }
                        } catch (JSONException e) {
                        }
                    } else {
                        if (dotDialog.isShowing()) {
                            dotDialog.dismiss();
                        }
                        Utility.alertForErrorMessage(Contants.Error, MatchDetailsActivity.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, MatchDetailsActivity.this);//off line msg....
        }
    }
}
