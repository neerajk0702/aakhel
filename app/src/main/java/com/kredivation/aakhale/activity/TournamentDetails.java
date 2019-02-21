package com.kredivation.aakhale.activity;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class TournamentDetails extends AppCompatActivity implements View.OnClickListener {


    ImageView displayImage;
    TextView nametxt, statusttxt, uniqeIdtxt, dateTime,
            aboutTournament, teamneName, over, teametxt, formatetxt,
            venueAddress, enteryFee;
    LinearLayout facilitiesesView, priceView, UmpireView, ruleRegulationView, teamesView;
    private Toolbar toolbar;
    int tournamentId, teamId;
    long userIdValue;
    int userRoleId;

    public TournamentDetails() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tournament_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        SharedPreferences UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
        userIdValue = UserInfo.getLong("id", 0);
        userRoleId = UserInfo.getInt("role", 0);
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
        displayImage = findViewById(R.id.displayImage);
        nametxt = findViewById(R.id.name);
        statusttxt = findViewById(R.id.status);
        uniqeIdtxt = findViewById(R.id.uniqeId);
        dateTime = findViewById(R.id.dateTime);
        aboutTournament = findViewById(R.id.aboutTournament);
        over = findViewById(R.id.over);
        teametxt = findViewById(R.id.teame);
        formatetxt = findViewById(R.id.formate);
        facilitiesesView = findViewById(R.id.facilitiesesView);
        enteryFee = findViewById(R.id.enteryFee);
        venueAddress = findViewById(R.id.venueAddress);
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        ruleRegulationView = findViewById(R.id.ruleRegulationView);
        facilitiesesView = findViewById(R.id.facilitiesesView);
        priceView = findViewById(R.id.priceView);
        UmpireView = findViewById(R.id.UmpireView);
        teamesView = findViewById(R.id.teamesView);

        getTournamentDetailMatch();
    }

    ASTProgressBar astProgressBar;

    //get GgetTournamentMatch
    private void getTournamentDetailMatch() {

        int id = getIntent().getIntExtra("id", 0);
        if (Utility.isOnline(TournamentDetails.this)) {
            astProgressBar = new ASTProgressBar(TournamentDetails.this);
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.tournamentAPi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(TournamentDetails.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getTournamentDetailMatch", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                JSONObject datajsonObject = mainObj.optJSONObject("data");
                                if (datajsonObject != null) {
                                    JSONObject jsonObject = datajsonObject.optJSONObject("basic_info");
                                    if (jsonObject != null) {
                                        tournamentId = jsonObject.optInt("id");
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
                                        JSONArray tournament_teamArray = datajsonObject.optJSONArray("tournament_team");
                                        JSONArray tournament_umpire = datajsonObject.optJSONArray("tournament_umpire");
                                        if (display_picture != null && !display_picture.equals("")) {
                                            Picasso.with(TournamentDetails.this).load(Contants.BASE_URL + display_picture)
                                                    .placeholder(R.drawable.noimage).into(displayImage);
                                        }
                                        nametxt.setText(name);
                                        if (statusValue == 1) {
                                            statusttxt.setText("Open");
                                        } else {
                                            statusttxt.setText("Close");
                                        }
                                        uniqeIdtxt.setText(unique_id);
                                        dateTime.setText(start_date + " - " + end_date);
                                        aboutTournament.setText(about_tournament);
                                        over.setText(overs);
                                        teametxt.setText(tournament_teamArray.length() + "");
                                        if (format.equals("1")) {
                                            formatetxt.setText("Test");
                                        } else if (format.equals("2")) {
                                            formatetxt.setText("ODI");
                                        } else if (format.equals("3")) {
                                            formatetxt.setText("T-20");
                                        }
                                        enteryFee.setText(entry_fees);
                                        String completeAdd = tournament_address + "," + tournament_city.optString("city_name") + "," + tournament_state.optString("state_name") + "," + tournament_country.optString("country_name") + "," + tournament_zipcode;
                                        venueAddress.setText(completeAdd);
                                        if (tournament_umpire != null) {
                                            for (int i = 0; i < tournament_umpire.length(); i++) {
                                                try {
                                                    JSONObject object = tournament_umpire.getJSONObject(i);
                                                    String teamname = object.optString("name");
                                                    String teamunique_id = object.optString("unique_id");
                                                    String statusp = object.optString("status");
                                                    String team_thumbnail = object.optString("team_thumbnail");
                                                    addTeamView(teamname, teamunique_id, team_thumbnail, statusp, false);
                                                } catch (JSONException e) {
                                                }
                                            }
                                        }
                                        if (tournament_teamArray != null) {
                                            for (int i = 0; i < tournament_teamArray.length(); i++) {
                                                try {
                                                    JSONObject object = tournament_teamArray.getJSONObject(i);
                                                    String teamname = object.optString("name");
                                                    String teamunique_id = object.optString("unique_id");
                                                    String statusp = object.optString("status");
                                                    String team_thumbnail = object.optString("team_thumbnail");
                                                    addTeamView(teamname, teamunique_id, team_thumbnail, statusp, true);
                                                } catch (JSONException e) {
                                                }
                                            }
                                        }
                                        JSONArray free_servicesjsonArray = new JSONArray(facilities);
                                        for (int i = 0; i < free_servicesjsonArray.length(); i++) {
                                            addFreeService(free_servicesjsonArray.get(i).toString());
                                        }

                                        JSONArray prizesarstr_servicesjsonArray = new JSONArray(prizes);
                                        for (int i = 0; i < prizesarstr_servicesjsonArray.length(); i++) {
                                            addPrice(prizesarstr_servicesjsonArray.get(i).toString());
                                        }
                                        JSONArray strrules_regulations_jsonArray = new JSONArray(rules_regulations);
                                        for (int i = 0; i < strrules_regulations_jsonArray.length(); i++) {
                                            ruleRegulation(strrules_regulations_jsonArray.get(i).toString());
                                        }
                                    }
                                }
                            }
                            astProgressBar.dismiss();
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(TournamentDetails.this, Contants.Error, Toast.LENGTH_SHORT).show();
                        astProgressBar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, TournamentDetails.this);//off line msg....
        }
    }


    //add team layout in runtime
    public void addTeamView(String teamname, String teamunique_id, String team_thumbnail, String statusp, boolean flage) {
        LayoutInflater inflater = LayoutInflater.from(TournamentDetails.this);
        View inflatedLayout = inflater.inflate(R.layout.sports_item_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        ImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        teamneName.setText(teamname);
        userId.setText(teamunique_id);
        Picasso.with(TournamentDetails.this).load(Contants.BASE_URL + team_thumbnail)
                .placeholder(R.drawable.noimage).into(matchPerview);
        if (flage) {
            teamesView.addView(inflatedLayout);
        } else {
            UmpireView.addView(inflatedLayout);
        }

    }

    int serviceCount;

    //add free service layout in runtime
    public void addFreeService(String name) {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(TournamentDetails.this);
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "-");
        servicesName.setText(name);
        facilitiesesView.addView(inflatedLayout);

    }

    int priceCount;

    //add free service layout in runtime
    public void addPrice(String name) {
        priceCount++;
        LayoutInflater inflater = LayoutInflater.from(TournamentDetails.this);
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(priceCount + "-");
        servicesName.setText(name);
        priceView.addView(inflatedLayout);

    }

    int ruleCount;

    //add free service layout in runtime
    public void ruleRegulation(String name) {
        ruleCount++;
        LayoutInflater inflater = LayoutInflater.from(TournamentDetails.this);
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(ruleCount + "-");
        servicesName.setText(name);
        ruleRegulationView.addView(inflatedLayout);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (userRoleId == 3) {//umpire
                    umriprerequestTournament();
                } else {
                    if (isValidate()) {
                        ParticipateTournament();
                    }
                }
                break;
        }
    }

    public boolean isValidate() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        if (teamId == 0) {
            Toast.makeText(TournamentDetails.this, "Please select Team first!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void ParticipateTournament() {
        if (Utility.isOnline(TournamentDetails.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(TournamentDetails.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.tournament_participate;
            JSONObject object = new JSONObject();
            try {
                object.put("tournament_id", tournamentId);
                object.put("team_id", teamId);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(TournamentDetails.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "ParticipateTournament", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(TournamentDetails.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TournamentDetails.this, message, Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, TournamentDetails.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, TournamentDetails.this);//off line msg....
        }
    }

    private void umriprerequestTournament() {
        if (Utility.isOnline(TournamentDetails.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(TournamentDetails.this);
            dotDialog.show();
            String serviceURL = "";
            if (userRoleId == 3) {//Umpire
                serviceURL = Contants.BASE_URL + Contants.tournament_umpire_request;
            }

            JSONObject object = new JSONObject();
            try {
                object.put("team_id", teamId);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(TournamentDetails.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "umriprerequestTournament", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(TournamentDetails.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TournamentDetails.this, message, Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, TournamentDetails.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, TournamentDetails.this);//off line msg....
        }
    }
}
