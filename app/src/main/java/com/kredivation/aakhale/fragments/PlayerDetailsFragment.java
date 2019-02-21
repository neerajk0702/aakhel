package com.kredivation.aakhale.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.TournamentDetails;
import com.kredivation.aakhale.adapter.TopperformanceAdapter;
import com.kredivation.aakhale.adapter.UpcommingMatchAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentDataAsArray;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerDetailsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    TextView nametxt, ratingTxt, agetxt, ExperienceTxt, OdieTxt, roleTxt, challange, REQUEST, INVITE, gender, phoneno, mail, userId;
    LinearLayout teamInfoLayoutView, galleryView, userSsportsLayoutView;
    Bundle bundle;
    MetalRecyclerViewPager topperformanceviewPager;
    ImageView fab, imageView;

    public PlayerDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerDetailsFragment newInstance(String param1, String param2) {
        PlayerDetailsFragment fragment = new PlayerDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    SharedPreferences UserInfo;
    long userIdValue;
    long playerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player_details, container, false);
        getActivity().setTitle("Players Details");
        init();
        return view;
    }

    public void init() {
        UserInfo = getActivity().getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
        userIdValue = UserInfo.getLong("id", 0);
        userId = view.findViewById(R.id.userId);
        phoneno = view.findViewById(R.id.phoneno);
        mail = view.findViewById(R.id.mail);
        nametxt = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        agetxt = view.findViewById(R.id.age);
        ExperienceTxt = view.findViewById(R.id.ExperienceTxt);
        OdieTxt = view.findViewById(R.id.OdieTxt);
        gender = view.findViewById(R.id.gender);
        roleTxt = view.findViewById(R.id.roleTxt);
        challange = view.findViewById(R.id.challange);
        challange.setOnClickListener(this);
        REQUEST = view.findViewById(R.id.REQUEST);
        REQUEST.setOnClickListener(this);
        INVITE = view.findViewById(R.id.INVITE);
        INVITE.setOnClickListener(this);
        fab = view.findViewById(R.id.fab);
        teamInfoLayoutView = view.findViewById(R.id.teamInfoLayoutView);
        galleryView = view.findViewById(R.id.galleryView);
        imageView = view.findViewById(R.id.imageView);
        userSsportsLayoutView = view.findViewById(R.id.userSsportsLayoutView);
        bundle = getArguments();
        String Detail = bundle.getString("Detail");
        if (Detail != null) {
            setValue(Detail);
        }
        //getPlayerList();
    }

    private void setValue(String detail) {
        Data data = new Gson().fromJson(detail, Data.class);
        if (data != null) {
            if (data.getProfile_picture() != null && !data.getProfile_picture().equals("")) {
                Picasso.with(getContext()).load(Contants.BASE_URL + data.getProfile_picture())
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

        LayoutInflater inflater = LayoutInflater.from(getContext());
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
            Picasso.with(getActivity()).load(Contants.BASE_URL + image)
                    .placeholder(R.drawable.noimage).into(imageView);
        }
        teamInfoLayoutView.addView(inflatedLayout);
    }

    //add user sports
    public void addUserSports(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.area_row, null);
        TextView nameView = inflatedLayout.findViewById(R.id.namearea);
        nameView.setText(name);
        userSsportsLayoutView.addView(inflatedLayout);

    }

    //add free service layout in runtime
    public void addGaleryView(String name, String statuss, String date) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
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
        }
    }

    //show action taken popup
    public void openActionDilaog() {
        final View myview = LayoutInflater.from(getActivity()).inflate(R.layout.actiontaken_dilaog, null);
        final EditText messageedit = myview.findViewById(R.id.messageedit);
        Button btnSubmit = myview.findViewById(R.id.btnSubmit);
        Button btncancel = myview.findViewById(R.id.btncancel);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher).setCancelable(false)
                .setView(myview).create();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgStr = messageedit.getText().toString();
                if (msgStr.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter Your Message!", Toast.LENGTH_LONG).show();
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
        if (Utility.isOnline(getActivity())) {
            final ASTProgressBar dotDialog = new ASTProgressBar(getActivity());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.player_challenge;
            JSONObject object = new JSONObject();
            try {
                object.put("acceptor_player", playerId);
                object.put("comment", message);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(getActivity());
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "player_challenge", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, getActivity());
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getActivity());//off line msg....
        }
    }
    private void teamrequest() {
        if (Utility.isOnline(getActivity())) {
            final ASTProgressBar dotDialog = new ASTProgressBar(getActivity());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.team_player_request;
            JSONObject object = new JSONObject();
            try {
                object.put("team_id", 0);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(getActivity());
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "teamrequest", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                        Utility.alertForErrorMessage(Contants.Error, getActivity());
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getActivity());//off line msg....
        }
    }
}
