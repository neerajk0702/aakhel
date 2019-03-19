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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
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
 */
public class CreateScoreCardActivity extends AppCompatActivity implements View.OnClickListener {

    ASTEditText teame1, teame2;
    ASTButton continuebtn;

    public CreateScoreCardActivity() {
        // Required empty public constructor
    }

    private Toolbar toolbar;
    ImageView teameImage;
    TextView teamName;
    private Spinner batSpinner, bowlerspinner, bat2Spinner;
    SharedPreferences infopre;
    ArrayList<String> matchTeamIdList;
    ArrayList<String> myTeamIdList;
    ArrayList<String> myTeamPlayerNameList;
    ArrayList<Integer> myTeamPlayerIdList;
    int myTeamId, secondTeamId;
    int selectBat1Postion, selectBat2Postion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_score_card);
        toolbar = findViewById(R.id.toolbar);
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
        teameImage = findViewById(R.id.teameImage);
        teamName = findViewById(R.id.teamName);
        continuebtn = findViewById(R.id.continuebtn);
        batSpinner = findViewById(R.id.batSpinner);
        bowlerspinner = findViewById(R.id.bowlerspinner);
        ImageView run1 = findViewById(R.id.run1);
        run1.setOnClickListener(this);
        ImageView run2 = findViewById(R.id.run2);
        run2.setOnClickListener(this);
        ImageView run3 = findViewById(R.id.run3);
        run3.setOnClickListener(this);
        ImageView run4 = findViewById(R.id.run4);
        run4.setOnClickListener(this);
        ImageView run5 = findViewById(R.id.run5);
        run5.setOnClickListener(this);
        ImageView run6 = findViewById(R.id.run6);
        run6.setOnClickListener(this);
        ImageView wcket1 = findViewById(R.id.wcket1);
        wcket1.setOnClickListener(this);
        ImageView wcket2 = findViewById(R.id.wcket2);
        wcket2.setOnClickListener(this);
        ImageView wcket3 = findViewById(R.id.wcket3);
        wcket3.setOnClickListener(this);
        ImageView wcket4 = findViewById(R.id.wcket4);
        wcket4.setOnClickListener(this);
        ImageView wcket5 = findViewById(R.id.wcket5);
        wcket5.setOnClickListener(this);
        ImageView wcket6 = findViewById(R.id.wcket6);
        wcket6.setOnClickListener(this);

        bat2Spinner = findViewById(R.id.bat2Spinner);
        ImageView bat2run1 = findViewById(R.id.bat2run1);
        bat2run1.setOnClickListener(this);
        ImageView bat2run2 = findViewById(R.id.bat2run2);
        bat2run2.setOnClickListener(this);
        ImageView bat2run3 = findViewById(R.id.bat2run3);
        bat2run3.setOnClickListener(this);
        ImageView bat2run4 = findViewById(R.id.bat2run4);
        bat2run4.setOnClickListener(this);
        ImageView bat2run6 = findViewById(R.id.bat2run6);
        bat2run6.setOnClickListener(this);

        infopre = getSharedPreferences("MatchInfoSharedPref", MODE_PRIVATE);
        String TeamList = infopre.getString("TeamList", "");
        selectBat1Postion = infopre.getInt("Bastman1Postion", 0);
        selectBat2Postion = infopre.getInt("Bastman2Postion", 0);
        matchTeamIdList = new ArrayList<>();
        try {
            JSONArray teamArray = new JSONArray(TeamList);
            if (teamArray != null) {
                for (int i = 0; i < teamArray.length(); i++) {
                    JSONObject jsonObject = teamArray.getJSONObject(i);
                    String uname = jsonObject.optString("name");
                    String unique_id = jsonObject.optString("unique_id");
                    String team_thumbnail = jsonObject.optString("team_thumbnail");
                    String id = jsonObject.optString("id");
                    matchTeamIdList.add(id);
                }
            }
        } catch (JSONException e) {
        }
        getMyTeamList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.run1:
                // request();
                break;
            case R.id.run2:
                // request();
                break;
            case R.id.run3:
                // request();
                break;
            case R.id.run4:
                // request();
                break;
            case R.id.run5:
                // request();
                break;
            case R.id.run6:
                // request();
                break;
            case R.id.wcket1:
                // request();
                break;
            case R.id.wcket2:
                // request();
                break;
            case R.id.wcket3:
                // request();
                break;
            case R.id.wcket4:
                // request();
                break;
            case R.id.wcket5:
                // request();
                break;
            case R.id.wcket6:
                // request();
                break;
            case R.id.continuebtn:
                //  matchStart();
                break;
        }
    }

    //add runs
    private void addRun() {
        if (Utility.isOnline(CreateScoreCardActivity.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(CreateScoreCardActivity.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.score_add;

            JSONObject object = new JSONObject();
            try {
                /*object.put("match_id", matchId);
                object.put("batting_team_id", matchId);
                object.put("bowling_team_id", matchId);
                object.put("batsman_id", matchId);
                object.put("runs", matchId);
                object.put("batsman_status", matchId);*/
            } catch (Exception e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(CreateScoreCardActivity.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "addRun", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(CreateScoreCardActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CreateScoreCardActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, CreateScoreCardActivity.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, CreateScoreCardActivity.this);//off line msg....
        }
    }

    //get Team Player
    private void getTeamPlayer(int teamId) {
        if (Utility.isOnline(CreateScoreCardActivity.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(CreateScoreCardActivity.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.team_players + 2;

            JSONObject object = new JSONObject();
            ServiceCaller serviceCaller = new ServiceCaller(CreateScoreCardActivity.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "getTeamPlayer", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("success");
                            if (status) {
                                myTeamPlayerNameList = new ArrayList<>();
                                myTeamPlayerIdList = new ArrayList<>();
                                JSONArray dataArray = jsonObject.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject obj = dataArray.optJSONObject(i);
                                        myTeamPlayerNameList.add(obj.optString("full_name"));
                                        myTeamPlayerIdList.add(Integer.parseInt(obj.optString("id")));
                                    }
                                    setPlayersSpinner();
                                }
                            } else {
                                Toast.makeText(CreateScoreCardActivity.this, "No Player Found for this team!", Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, CreateScoreCardActivity.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, CreateScoreCardActivity.this);//off line msg....
        }
    }

    //show bastman spinner value
    private void setPlayersSpinner() {
        ArrayAdapter<String> bat1Adapater = new ArrayAdapter<String>(this, R.layout.spinner_row, myTeamPlayerNameList);
        batSpinner.setAdapter(bat1Adapater);
        batSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = infopre.edit();
                editor.putInt("Bastman1Postion", position);
                editor.putInt("Bastman1Id", myTeamPlayerIdList.get(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        batSpinner.setSelection(selectBat1Postion);

        ArrayAdapter<String> bat2Adapater = new ArrayAdapter<String>(this, R.layout.spinner_row, myTeamPlayerNameList);
        bat2Spinner.setAdapter(bat2Adapater);
        bat2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = infopre.edit();
                editor.putInt("Bastman2Postion", position);
                editor.putInt("Bastman2Id", myTeamPlayerIdList.get(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bat2Spinner.setSelection(selectBat2Postion);
    }

    //get getTeamList
    private void getMyTeamList() {
        if (Utility.isOnline(CreateScoreCardActivity.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(CreateScoreCardActivity.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.teamCreate + "?page=" + 1 + "&" + "my_team=1";
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(CreateScoreCardActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getMyTeamList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {

                                int total_pages = mainObj.optInt("total_pages");
                                JSONArray dataArray = mainObj.optJSONArray("data");
                                myTeamIdList = new ArrayList<>();
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject obj = dataArray.getJSONObject(i);
                                        JSONObject jsonObject = obj.getJSONObject("basic_info");
                                        int is_active = jsonObject.optInt("is_active");
                                        int id = jsonObject.optInt("id");
                                        myTeamIdList.add(String.valueOf(id));
                                    }
                                    //getting myteam id and second team id
                                    for (int j = 0; j < matchTeamIdList.size(); j++) {
                                        for (int k = 0; k < myTeamIdList.size(); k++) {
                                            if (myTeamIdList.get(k).equals(matchTeamIdList.get(j))) {
                                                myTeamId = Integer.parseInt(matchTeamIdList.get(j));
                                            } else {
                                                secondTeamId = Integer.parseInt(matchTeamIdList.get(j));
                                            }
                                        }
                                    }
                                    getTeamPlayer(myTeamId);
                                }
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(CreateScoreCardActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                    }
                    if (dotDialog.isShowing()) {
                        dotDialog.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, CreateScoreCardActivity.this);//off line msg....
        }
    }
}
