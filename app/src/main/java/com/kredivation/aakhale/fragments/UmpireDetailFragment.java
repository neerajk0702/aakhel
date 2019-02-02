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
public class UmpireDetailFragment extends Fragment {
    View view;
    Bundle bundle;
    TextView nametxt, ratingTxt, uniqueIdTxt, ageTxt, ExperienceTxt, OdieTxt, taskId, Roletxt;
    LinearLayout umpireInfoLayout;
    ImageView imageView, fab;

    public UmpireDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_umpire_detail, container, false);
        getActivity().setTitle("Umpires Details");
        init();
        return view;
    }

    private void init() {
        imageView = view.findViewById(R.id.imageView);
        nametxt = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        uniqueIdTxt = view.findViewById(R.id.uniqueIdTxt);
        ageTxt = view.findViewById(R.id.ageTxt);
        ExperienceTxt = view.findViewById(R.id.ExperienceTxt);
        OdieTxt = view.findViewById(R.id.OdieTxt);
        taskId = view.findViewById(R.id.taskId);
        Roletxt = view.findViewById(R.id.Roletxt);
        fab = view.findViewById(R.id.fab);
        umpireInfoLayout = view.findViewById(R.id.umpireInfoLayout);

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
                                        uniqueIdTxt.setText(unique_id);
                                        ageTxt.setText("");
                                        ExperienceTxt.setText(experience);
                                        OdieTxt.setText("");
                                        taskId.setText("");
                                        Roletxt.setText(role);

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
    public void addUmpireInfo(String name, String status) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.umpirelist_item_row, null);
        TextView matchType = inflatedLayout.findViewById(R.id.matchType);
        TextView odiesInfo = inflatedLayout.findViewById(R.id.odiesInfo);
        matchType.setText(name);
        odiesInfo.setText(name);
        umpireInfoLayout.addView(inflatedLayout);

    }


}
