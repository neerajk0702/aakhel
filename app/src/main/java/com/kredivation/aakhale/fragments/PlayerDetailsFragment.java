package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.TopperformanceAdapter;
import com.kredivation.aakhale.adapter.UpcommingMatchAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    TextView nametxt, ratingTxt, agetxt, ExperienceTxt, OdieTxt, boloingTxt, roleTxt, challange, REQUEST, INVITE;
    LinearLayout teamInfoLayoutView, galleryView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player_details, container, false);
        getActivity().setTitle("Players Details");
        init();
        return view;
    }

    public void init() {
        nametxt = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        agetxt = view.findViewById(R.id.age);
        ExperienceTxt = view.findViewById(R.id.ExperienceTxt);
        OdieTxt = view.findViewById(R.id.OdieTxt);
        boloingTxt = view.findViewById(R.id.boloingTxt);
        roleTxt = view.findViewById(R.id.roleTxt);
        challange = view.findViewById(R.id.challange);
        REQUEST = view.findViewById(R.id.REQUEST);
        INVITE = view.findViewById(R.id.INVITE);
        fab = view.findViewById(R.id.fab);
        teamInfoLayoutView = view.findViewById(R.id.teamInfoLayoutView);
        galleryView = view.findViewById(R.id.galleryView);
        imageView = view.findViewById(R.id.imageView);
        getPlayerList();
    }


    ASTProgressBar astProgressBar;

    //get getPlayerList
    private void getPlayerList() {
        bundle = getArguments();
        long id = getArguments().getLong("id");
        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.UsersApi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getPlayerList", new IAsyncWorkCompletedCallback() {
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
                                        int gender = basic_info.optInt("gender");
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
                                            Picasso.with(getContext()).load("http://cricket.vikaskumar.info/" + profile_picture)
                                                    .placeholder(R.drawable.noimage).into(imageView);
                                        }

                                        nametxt.setText(full_name);
                                        ratingTxt.setText("");
                                        agetxt.setText("");
                                        ExperienceTxt.setText(experience);
                                        OdieTxt.setText("");
                                        boloingTxt.setText("");
                                        roleTxt.setText(role);


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
                                                    String datestr = object.optString("date");
                                                    addTeameInfoView(pname, statusp, datestr);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
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
    }


    //add free service layout in runtime
    public void addTeameInfoView(String name, String statuss, String date) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.playerlist_item_row, null);
        TextView nameTameInfo = inflatedLayout.findViewById(R.id.nameTameInfo);
        TextView dateInfo = inflatedLayout.findViewById(R.id.dateInfo);
        TextView status = inflatedLayout.findViewById(R.id.status);
        nameTameInfo.setText(name);
        dateInfo.setText(date);
        status.setText(statuss);
        teamInfoLayoutView.addView(inflatedLayout);

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

}
