package com.kredivation.aakhale.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PlayerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nametxt, ratingTxt, agetxt, ExperienceTxt, OdieTxt, roleTxt, challange, REQUEST, INVITE, gender, phoneno, mail, userId;
    LinearLayout teamInfoLayoutView, galleryView, userSsportsLayoutView;
    MetalRecyclerViewPager topperformanceviewPager;
    ImageView fab, imageView;
    FloatingActionButton chat;

    public PlayerDetailsActivity() {
        // Required empty public constructor
    }


    SharedPreferences UserInfo;
    long userIdValue;
    long playerId;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_player_details);
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

        UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
        userIdValue = UserInfo.getLong("id", 0);
        userId = findViewById(R.id.userId);
        phoneno = findViewById(R.id.phoneno);
        mail = findViewById(R.id.mail);
        nametxt = findViewById(R.id.name);
        ratingTxt = findViewById(R.id.ratingTxt);
        agetxt = findViewById(R.id.age);
        ExperienceTxt = findViewById(R.id.ExperienceTxt);
        OdieTxt = findViewById(R.id.OdieTxt);
        gender = findViewById(R.id.gender);
        roleTxt = findViewById(R.id.roleTxt);
        challange = findViewById(R.id.challange);
        challange.setOnClickListener(this);
        REQUEST = findViewById(R.id.REQUEST);
        REQUEST.setOnClickListener(this);
        INVITE = findViewById(R.id.INVITE);
        INVITE.setOnClickListener(this);
        fab = findViewById(R.id.fab);
        teamInfoLayoutView = findViewById(R.id.teamInfoLayoutView);
        galleryView = findViewById(R.id.galleryView);
        imageView = findViewById(R.id.imageView);
        userSsportsLayoutView = findViewById(R.id.userSsportsLayoutView);
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(this);
        String Detail = getIntent().getStringExtra("Detail");
        if (Detail != null) {
            setValue(Detail);
        }
        //getPlayerList();
    }

    private void setValue(String detail) {
        Data data = new Gson().fromJson(detail, Data.class);
        if (data != null) {
            if (data.getProfile_picture() != null && !data.getProfile_picture().equals("")) {
                Picasso.with(this).load(Contants.BASE_URL + data.getProfile_picture())
                        .placeholder(R.drawable.ic_cricket_player).into(imageView);
            }
            playerId = data.getId();
            userId.setText(data.getUnique_id());
            phoneno.setText(data.getMobile() + "");
            mail.setText(data.getEmail());
            nametxt.setText(data.getFull_name());
            //  ratingTxt.setText("");
            agetxt.setText(data.getDate_of_birth());
            ExperienceTxt.setText(data.getExperience() + " Years");
            try {
                if (data.getPlayerRoleObj() != null) {
                    JSONObject jsonObject = new JSONObject(data.getPlayerRoleObj());
                    OdieTxt.setText(jsonObject.optString("role_name"));
                }
                if (data.getRoleObj() != null) {
                    JSONObject jsonObject = new JSONObject(data.getRoleObj());
                    roleTxt.setText(jsonObject.optString("user_type"));
                }
                if (data.getPlayerTeamArray() != null) {
                    JSONArray playerTeamArray = new JSONArray(data.getPlayerTeamArray());
                    if (playerTeamArray != null) {
                        for (int i = 0; i < playerTeamArray.length(); i++) {
                            try {
                                JSONObject object = playerTeamArray.getJSONObject(i);
                                addTeameInfoView(object);
                            } catch (JSONException e) {
                            }
                        }
                    }
                }
                if (data.getUsersSportArray() != null) {
                    JSONArray dataArray = new JSONArray(data.getUsersSportArray());
                    if (dataArray != null) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            try {
                                JSONObject object = dataArray.getJSONObject(i);
                                String sports_name = object.optString("sports_name");
                                addUserSports(sports_name);
                            } catch (JSONException e) {
                            }
                        }
                    }
                }
            } catch (JSONException e) {
            }
            if (data.getGender() == 1) {
                gender.setText("Male");
            } else {
                gender.setText("Female");
            }
        }
    }

    ASTProgressBar astProgressBar;

   /* //get getPlayerList
    private void getPlayerList() {

        long id = getArguments().getLong("id");
        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.UsersApi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getPlayerDetailList", new IAsyncWorkCompletedCallback() {
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
                                        String email = basic_info.optString("email");
                                        String unique_id = basic_info.optString("unique_id");
                                        String created_at = basic_info.optString("created_at");
                                        String updated_at = basic_info.optString("updated_at");
                                        String role = basic_info.optString("role");
                                        int is_active = basic_info.optInt("is_active");
                                        String full_name = basic_info.optString("full_name");
                                        String mobile = basic_info.optString("mobile");
                                        String date_of_birth = basic_info.optString("date_of_birth");
                                        int genderValue = basic_info.optInt("gender");
                                        String address = basic_info.optString("address");
                                        String city = basic_info.optString("city");
                                        String state = basic_info.optString("state");
                                        String country = basic_info.optString("country");
                                        String zipcode = basic_info.optString("zipcode");
                                        String users_sports = basic_info.optString("users_sports");
                                        String profile = basic_info.optString("profile");
                                        String experience = basic_info.optString("experience");
                                        String player_role = basic_info.optString("player_role");
                                        String profile_picture = basic_info.optString("profile_picture");

                                        if (profile_picture != null && !profile_picture.equals("")) {
                                            Picasso.with(getContext()).load(Contants.BASE_URL + profile_picture)
                                                    .placeholder(R.drawable.ic_cricket_player).into(imageView);
                                        }
                                        userId.setText(unique_id);
                                        phoneno.setText(mobile + "");
                                        mail.setText(email);
                                        nametxt.setText(full_name);
                                        //  ratingTxt.setText("");
                                        agetxt.setText(date_of_birth);
                                        ExperienceTxt.setText(experience + " Years");
                                        OdieTxt.setText(player_role);
                                        roleTxt.setText(role);
                                        if (genderValue == 1) {
                                            gender.setText("Male");
                                        } else {
                                            gender.setText("Female");
                                        }
                                        JSONArray playerTeamArray = jsonObject.optJSONArray("player_team");
                                        if (playerTeamArray != null) {
                                            for (int i = 0; i < playerTeamArray.length(); i++) {
                                                try {
                                                    JSONObject object = playerTeamArray.getJSONObject(i);
                                                    String pname = object.optString("name");
                                                    String statusp = object.optString("status");
                                                    String datestr = object.optString("date");
                                                    addTeameInfoView(pname, statusp, datestr);
                                                } catch (JSONException e) {
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
    public void addTeameInfoView(JSONObject object) {
        String pname = object.optString("name");
        String image = object.optString("image");
        String unique_id = object.optString("unique_id");
        String city = object.optString("city");
        String state = object.optString("state");
        String country = object.optString("country");
        String zipcode = object.optString("zipcode");
        String status = object.optString("status");

        LayoutInflater inflater = LayoutInflater.from(this);
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
            Picasso.with(this).load(Contants.BASE_URL + image)
                    .placeholder(R.drawable.noimage).into(imageView);
        }
        teamInfoLayoutView.addView(inflatedLayout);
    }

    //add user sports
    public void addUserSports(String name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View inflatedLayout = inflater.inflate(R.layout.area_row, null);
        TextView nameView = inflatedLayout.findViewById(R.id.namearea);
        nameView.setText(name);
        userSsportsLayoutView.addView(inflatedLayout);

    }

    //add free service layout in runtime
    public void addGaleryView(String name, String statuss, String date) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View inflatedLayout = inflater.inflate(R.layout.toperformancer_item, null);
        ImageView bgimage = inflatedLayout.findViewById(R.id.bgimage);
        TextView title = inflatedLayout.findViewById(R.id.title);
        title.setText(name);
        galleryView.addView(inflatedLayout);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.challange:
                openActionDilaog();
                break;
            case R.id.REQUEST:
                teamrequest();
                break;
            case R.id.INVITE:
                openActionDilaog();
                break;
            case R.id.chat:
                Intent intent = new Intent(PlayerDetailsActivity.this, ChatDetailActivity.class);
                intent.putExtra("id", playerId);
                startActivity(intent);
                break;
        }


    }

    //show action taken popup
    public void openActionDilaog() {
        final View myview = LayoutInflater.from(this).inflate(R.layout.actiontaken_dilaog, null);
        final EditText messageedit = myview.findViewById(R.id.messageedit);
        Button btnSubmit = myview.findViewById(R.id.btnSubmit);
        Button btncancel = myview.findViewById(R.id.btncancel);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setCancelable(false)
                .setView(myview).create();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgStr = messageedit.getText().toString();
                if (msgStr.length() == 0) {
                    Toast.makeText(PlayerDetailsActivity.this, "Please enter Your Message!", Toast.LENGTH_LONG).show();
                } else {
                    alertDialog.dismiss();
                    player_challenge(msgStr);
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();


    }

    private void player_challenge(String message) {
        if (Utility.isOnline(this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.player_challenge;
            JSONObject object = new JSONObject();
            try {
                object.put("acceptor_player", playerId);
                object.put("comment", message);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "player_challenge", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(PlayerDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PlayerDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, PlayerDetailsActivity.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, PlayerDetailsActivity.this);//off line msg....
        }
    }

    //team request for add player in our team
    private void teamrequest() {
        if (Utility.isOnline(PlayerDetailsActivity.this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(PlayerDetailsActivity.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.team_player_request;
            JSONObject object = new JSONObject();
            try {
                object.put("team_id", 0);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(PlayerDetailsActivity.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "teamrequest", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(PlayerDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PlayerDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, PlayerDetailsActivity.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, PlayerDetailsActivity.this);//off line msg....
        }
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
