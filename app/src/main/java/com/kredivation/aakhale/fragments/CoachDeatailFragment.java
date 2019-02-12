package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class CoachDeatailFragment extends Fragment {

    View view;
    ImageView imageView, fab;
    TextView nametxt, ratingTxt, uniqueIdTxt, ageTxt, ExperienceTxt, OdieTxt, fee, coachRole, phone, email, address;
    LinearLayout coachInformation;
    Bundle bundle;

    public CoachDeatailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coach_deatail, container, false);
        getActivity().setTitle("Coaches Details");
        init();
        return view;
    }

    private void init() {
        imageView = view.findViewById(R.id.imageView);
        nametxt = view.findViewById(R.id.nametxt);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        uniqueIdTxt = view.findViewById(R.id.uniqueIdTxt);
        ageTxt = view.findViewById(R.id.ageTxt);
        ExperienceTxt = view.findViewById(R.id.ExperienceTxt);
        OdieTxt = view.findViewById(R.id.OdieTxt);
        fee = view.findViewById(R.id.fee);
        coachRole = view.findViewById(R.id.coachRole);
        fab = view.findViewById(R.id.fab);
        coachInformation = view.findViewById(R.id.coachInformation);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        address = view.findViewById(R.id.address);
        getUmpireDetails();
    }


    ASTProgressBar astProgressBar;

    //get getUmpireDetails
    private void getUmpireDetails() {
        bundle = getArguments();
        long id = getArguments().getLong("id");
        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.UsersApi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
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
                                        String emailstr = basic_info.optString("email");
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
                                        String fee_per = basic_info.optString("fee_per_match_day");


                                        if (profile_picture != null && !profile_picture.equals("")) {
                                            Picasso.with(getContext()).load(Contants.BASE_URL + profile_picture)
                                                    .placeholder(R.drawable.noimage).into(imageView);
                                        }

                                        nametxt.setText(full_name);
                                        // ratingTxt.setText("");
                                        uniqueIdTxt.setText(unique_id);
                                        ageTxt.setText(date_of_birth);
                                        ExperienceTxt.setText(experience + " Years");
                                        OdieTxt.setText(users_sports + "");
                                        fee.setText(fee_per + "");
                                        coachRole.setText(role);
                                        phone.setText(mobile);
                                        email.setText(emailstr);
                                        address.setText(addressstr + "," + city + "," + state + "," + country + "," + zipcode);

                                        if (gender == 1) {
                                            // ge.setText("Open");
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
}
