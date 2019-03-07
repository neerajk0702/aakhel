package com.kredivation.aakhale.activity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.components.CircleImageView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Match;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileActivity extends AppCompatActivity {


    public MyProfileActivity() {
        // Required empty public constructor
    }

    CircleImageView profileImg;
    private Toolbar toolbar;
    private TextView name, unique_id, emailid, Mobile, role, fee_per_match_day, experience, player_role, address;
    LinearLayout teamInfoLayoutView, userSsportsLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_profile);
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
        profileImg = findViewById(R.id.profileImg);
        name = findViewById(R.id.name);
        unique_id = findViewById(R.id.unique_id);
        emailid = findViewById(R.id.emailid);
        Mobile = findViewById(R.id.Mobile);
        role = findViewById(R.id.role);
        fee_per_match_day = findViewById(R.id.fee_per_match_day);
        experience = findViewById(R.id.experience);
        player_role = findViewById(R.id.player_role);
        address = findViewById(R.id.address);
        teamInfoLayoutView = findViewById(R.id.teamInfoLayoutView);
        userSsportsLayoutView = findViewById(R.id.userSsportsLayoutView);
        getProfileDetail();
    }

    //add free service layout in runtime
    public void addTeameInfoView(JSONObject object) {
        String pname = object.optString("name");
        String image = object.optString("image");
        String unique_id = object.optString("unique_id");
        String city = object.optString("city");
        String state = object.optString("state");
        String country = object.optString("country");
        String zipcode = object.optString("zipcode");
        String status = object.optString("status");

        LayoutInflater inflater = LayoutInflater.from(MyProfileActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.playerlist_item_row, null);
        ImageView imageView = inflatedLayout.findViewById(R.id.image);
        TextView nameView = inflatedLayout.findViewById(R.id.name);
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        TextView address = inflatedLayout.findViewById(R.id.address);
        TextView statusview = inflatedLayout.findViewById(R.id.status);
        nameView.setText(pname);
        userId.setText(unique_id);
        String completeAdd = city + "," + state + "," + country + "," + zipcode;
        address.setText(completeAdd);
        userId.setText(unique_id);
        if (status.equals("0")) {
            statusview.setText("Panding");
        } else {
            statusview.setText("Approved");
        }
        if (image != null && !image.equals("")) {
            Picasso.with(MyProfileActivity.this).load(Contants.BASE_URL + image)
                    .placeholder(R.drawable.noimage).into(imageView);
        }
        teamInfoLayoutView.addView(inflatedLayout);
    }

    //add user sports
    public void addUserSports(String name) {
        LayoutInflater inflater = LayoutInflater.from(MyProfileActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.area_row, null);
        TextView nameView = inflatedLayout.findViewById(R.id.namearea);
        nameView.setText(name);
        userSsportsLayoutView.addView(inflatedLayout);

    }

    //get getMatchList
    private void getProfileDetail() {
        SharedPreferences UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
        long id = UserInfo.getLong("id", 0);
        if (Utility.isOnline(MyProfileActivity.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(MyProfileActivity.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.UsersApi + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(MyProfileActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getProfileDetail", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                JSONObject mainDataArray = mainObj.optJSONObject("data");
                                if (mainDataArray != null) {
                                    JSONArray player_team = mainDataArray.optJSONArray("player_team");
                                    JSONArray challenge_given = mainDataArray.optJSONArray("challenge_given");
                                    JSONArray challenge_received = mainDataArray.optJSONArray("challenge_received");
                                    JSONArray coach_team = mainDataArray.optJSONArray("coach_team");
                                    JSONObject jsonObject = mainDataArray.optJSONObject("basic_info");

                                    JSONObject roleobj = jsonObject.optJSONObject("role");
                                    JSONObject city = jsonObject.optJSONObject("city");
                                    JSONObject state = jsonObject.optJSONObject("state");
                                    JSONObject country = jsonObject.optJSONObject("country");
                                    JSONArray users_sportsobj = jsonObject.optJSONArray("users_sports");
                                    JSONObject player_roleobj = jsonObject.optJSONObject("player_role");
                                    String emailstr = jsonObject.optString("email");
                                    String full_name = jsonObject.optString("full_name");
                                    String mobilestr = jsonObject.optString("mobile");
                                    int id = jsonObject.optInt("id");
                                    String unique_idsrt = jsonObject.optString("unique_id");
                                    String date_of_birth = jsonObject.optString("date_of_birth");
                                    int gender = jsonObject.optInt("gender");
                                    String addressstr = jsonObject.optString("address");
                                    String zipcode = jsonObject.optString("zipcode");
                                    String experiencestr = jsonObject.optString("experience");
                                    String profile_picture = jsonObject.optString("profile_picture");
                                    String about = jsonObject.optString("about");
                                    String fee_per_match_daystr = jsonObject.optString("fee_per_match_day");

                                    String city_name = "";
                                    String country_name = "";
                                    String state_name = "";
                                    if (city != null) {
                                        city_name = city.optString("city_name");
                                    }
                                    if (state != null) {
                                        state_name = state.optString("state_name");
                                    }
                                    if (country != null) {
                                        country_name = country.optString("country_name");
                                    }
                                    String completeAdd = addressstr + "," + city_name + "," + state_name + "," + country_name + "," + zipcode;

                                    name.setText(full_name);
                                    unique_id.setText(unique_idsrt);
                                    Mobile.setText(mobilestr);
                                    String user_type = roleobj.optString("user_type");
                                    role.setText(user_type);
                                    fee_per_match_day.setText(fee_per_match_daystr);
                                    experience.setText(experiencestr);
                                    String role_name = player_roleobj.optString("role_name");
                                    player_role.setText(role_name);
                                    address.setText(completeAdd);
                                    if (profile_picture != null && !profile_picture.equals("")) {
                                        Picasso.with(MyProfileActivity.this).load(Contants.BASE_URL + profile_picture)
                                                .placeholder(R.drawable.ic_user).into(profileImg);
                                    }
                                    for (int i = 0; i < users_sportsobj.length(); i++) {
                                        String sports_name = users_sportsobj.optJSONObject(i).optString("sports_name");
                                        addUserSports(sports_name);
                                    }
                                    for (int i = 0; i < player_team.length(); i++) {
                                        addTeameInfoView(player_team.optJSONObject(i));
                                    }
                                }

                            } else {
                                Toast.makeText(MyProfileActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MyProfileActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();

                    }
                    dotDialog.dismiss();
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, MyProfileActivity.this);//off line msg....
        }
    }
}
