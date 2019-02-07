package com.kredivation.aakhale.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UmpireDetailActivity extends AppCompatActivity {
    TextView nametxt, ratingTxt, uniqueIdTxt, ageTxt, ExperienceTxt, OdieTxt, taskId, Roletxt, usersport, phone, email, fee, address;
    LinearLayout umpireInfoLayout;
    ImageView imageView, fab;
    private Toolbar toolbar;

    public UmpireDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_umpire_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        fee = findViewById(R.id.fee);
        phone = findViewById(R.id.phone);
        usersport = findViewById(R.id.usersport);
        imageView = findViewById(R.id.imageView);
        nametxt = findViewById(R.id.name);
        ratingTxt = findViewById(R.id.ratingTxt);
        uniqueIdTxt = findViewById(R.id.uniqueIdTxt);
        ageTxt = findViewById(R.id.ageTxt);
        ExperienceTxt = findViewById(R.id.ExperienceTxt);
        OdieTxt = findViewById(R.id.OdieTxt);
        taskId = findViewById(R.id.taskId);
        Roletxt = findViewById(R.id.Roletxt);
        fab = findViewById(R.id.fab);
        umpireInfoLayout = findViewById(R.id.umpireInfoLayout);
        getUmpireDetails();
    }


    ASTProgressBar astProgressBar;

    //get getUmpireDetails
    private void getUmpireDetails() {
        long id = getIntent().getLongExtra("id", 0);
        if (Utility.isOnline(UmpireDetailActivity.this)) {
            astProgressBar = new ASTProgressBar(UmpireDetailActivity.this);
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.UsersApi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(UmpireDetailActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getUmpireDetails", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                JSONObject jsonObject = mainObj.optJSONObject("data");
                                if (jsonObject != null) {
                                    astProgressBar.dismiss();
                                    JSONObject basic_info = jsonObject.optJSONObject("basic_info");
                                    if (basic_info != null) {
                                        int id = basic_info.optInt("id");
                                        String emailStr = basic_info.optString("email");
                                        String unique_id = basic_info.optString("unique_id");
                                        String created_at = basic_info.optString("created_at");
                                        String updated_at = basic_info.optString("updated_at");
                                        String role = basic_info.optString("role");
                                        int is_active = basic_info.optInt("is_active");
                                        String full_name = basic_info.optString("full_name");
                                        String mobile = basic_info.optString("mobile");
                                        String date_of_birth = basic_info.optString("date_of_birth");
                                        int gender = basic_info.optInt("gender");
                                        String addressstr = basic_info.optString("address");
                                        String city = basic_info.optString("city");
                                        String state = basic_info.optString("state");
                                        String country = basic_info.optString("country");
                                        String zipcode = basic_info.optString("zipcode");
                                        String users_sports = basic_info.optString("users_sports");
                                        String profile = basic_info.optString("profile");
                                        String experience = basic_info.optString("experience");
                                        String player_role = basic_info.optString("player_role");
                                        String profile_picture = basic_info.optString("profile_picture");
                                        String fee_per_match_day = basic_info.optString("fee_per_match_day");
                                        if (profile_picture != null && !profile_picture.equals("")) {
                                            Picasso.with(UmpireDetailActivity.this).load(Contants.BASE_URL + profile_picture)
                                                    .placeholder(R.drawable.ic_uuuser).into(imageView);
                                        }
                                        nametxt.setText(full_name);
                                        //ratingTxt.setText("");
                                        uniqueIdTxt.setText(unique_id);
                                        ageTxt.setText(date_of_birth);
                                        ExperienceTxt.setText(experience);
                                        OdieTxt.setText("");
                                        taskId.setText("");
                                        Roletxt.setText(role);
                                        usersport.setText(users_sports + "");
                                        phone.setText(mobile);
                                        email.setText(emailStr);
                                        fee.setText(fee_per_match_day + "");
                                        address.setText(addressstr + "," + city + "," + state + "," + country + "," + zipcode);
                                        if (gender == 1) {
                                            // ge.setText("Open");
                                        }


                                        JSONArray umpire_matchArray = jsonObject.optJSONArray("umpire_match");
                                        if (umpire_matchArray != null) {
                                            for (int i = 0; i < umpire_matchArray.length(); i++) {
                                                try {
                                                    JSONObject object = umpire_matchArray.getJSONObject(i);
                                                    String pname = object.optString("name");
                                                    String statusp = object.optString("status");
                                                    addUmpireInfo(pname, statusp);
                                                } catch (JSONException e) {
                                                    //e.printStackTrace();
                                                }
                                            }

                                        }


                                    }
                                }

                            }
                            astProgressBar.dismiss();
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(UmpireDetailActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                        astProgressBar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, UmpireDetailActivity.this);//off line msg....
        }
    }


    //add free service layout in runtime
    public void addUmpireInfo(String name, String status) {
        LayoutInflater inflater = LayoutInflater.from(UmpireDetailActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.umpirelist_item_row, null);
        TextView matchType = inflatedLayout.findViewById(R.id.matchType);
        TextView odiesInfo = inflatedLayout.findViewById(R.id.odiesInfo);
        matchType.setText(name);
        odiesInfo.setText(name);
        umpireInfoLayout.addView(inflatedLayout);

    }


}
