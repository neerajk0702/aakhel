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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.UmpireDetailActivity;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.AddViewDynamically;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.GroundData;
import com.kredivation.aakhale.model.Match_played;
import com.kredivation.aakhale.model.Team_player;
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
 */
public class GroundDetailFrgment extends Fragment {
    Bundle bundle;
    View view;
    ImageView displAyImage;
    TextView nametxt,
            statustxt, uniqeId, capacityTxt, Dimensionstxt, dayNightTxt, fllodLightTxt, capacitymatchPerDayTxt,
            surfaceTxt, venueTxt, costTxt;
    LinearLayout spoetsLayoyView, freServiceesLayoutView, termCondtionLayoutView, achievementsLayoutView, staffLayoutView, timeZoneLayoutView;
    int serviceCount, termsCount, achivement;
    ASTProgressBar astProgressBar;

    public GroundDetailFrgment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ground_detail_frgment, container, false);
        init();
        return view;
    }

    public void init() {
        getActivity().setTitle("Ground Details");
        displAyImage = view.findViewById(R.id.displayImage);
        nametxt = view.findViewById(R.id.name);
        statustxt = view.findViewById(R.id.status);
        uniqeId = view.findViewById(R.id.uniqeId);
        capacityTxt = view.findViewById(R.id.capacityTxt);
        Dimensionstxt = view.findViewById(R.id.Dimensionstxt);
        dayNightTxt = view.findViewById(R.id.dayNightTxt);
        fllodLightTxt = view.findViewById(R.id.fllodLightTxt);
        capacitymatchPerDayTxt = view.findViewById(R.id.capacitymatchPerDayTxt);
        timeZoneLayoutView = view.findViewById(R.id.timeZoneLayoutView);
        surfaceTxt = view.findViewById(R.id.surfaceTxt);
        venueTxt = view.findViewById(R.id.venueTxt);
        costTxt = view.findViewById(R.id.costTxt);
        spoetsLayoyView = view.findViewById(R.id.spoetsLayoyView);
        freServiceesLayoutView = view.findViewById(R.id.freServiceesLayoutView);
        termCondtionLayoutView = view.findViewById(R.id.termCondtionLayoutView);
        achievementsLayoutView = view.findViewById(R.id.achievementsLayoutView);
        staffLayoutView = view.findViewById(R.id.staffLayoutView);
        dataToView();

    }

    public void dataToView() {
        bundle = getArguments();
        String Detail = bundle.getString("Detail");
        GroundData data = new Gson().fromJson(Detail, GroundData.class);
        if (data != null) {
            nametxt.setText(data.getName() + "");
            if (data.getAvailable() == 1) {
                statustxt.setText("Avilable");
            } else {
                statustxt.setText("Not Avilable");
            }
            uniqeId.setText(data.getUnique_id());
            capacityTxt.setText(data.getCapacity() + "");
            Dimensionstxt.setText(data.getDimension() + "");

            if (data.getDay_or_night() == 1) {
                dayNightTxt.setText("Day");
            } else if (data.getDay_or_night() == 2) {
                dayNightTxt.setText("Night");
            } else {
                dayNightTxt.setText("Day or Night");
            }
            if (data.getFloodlight() == 1) {
                fllodLightTxt.setText("Yes");
            } else {
                fllodLightTxt.setText("No");
            }

            capacitymatchPerDayTxt.setText(data.getMatch_per_day() + "");

            surfaceTxt.setText(data.getSurface());
            venueTxt.setText(data.getCompleteAddress());
            costTxt.setText(data.getCost() + " Per Match");
            try {
                JSONArray timeArray = new JSONArray(data.getTimezone());
                for (int i = 0; i < timeArray.length(); i++) {
                    JSONObject obj = timeArray.optJSONObject(i);
                    addTimeZome(obj.optString("name"));
                }
                JSONArray jsonArray = new JSONArray(data.getDisplay_picture());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Picasso.with(getContext()).load(Contants.BASE_URL + jsonArray.get(i).toString()).placeholder(R.drawable.noimage).into(displAyImage);
                    break;
                }

                JSONArray sportsArray = new JSONArray(data.getSports());
                if (sportsArray != null) {
                    for (int i = 0; i < sportsArray.length(); i++) {
                        JSONObject object = sportsArray.getJSONObject(i);
                        String pname = object.optString("name");
                        int idp = object.optInt("id");
                        addSportmatchView(pname, idp);
                    }
                }
                JSONArray terjsonArray = new JSONArray(data.getTerms_conditions());
                for (int i = 0; i < terjsonArray.length(); i++) {
                    addTermsAndCondition(terjsonArray.get(i).toString());
                }
                JSONArray free_servicesjsonArray = new JSONArray(data.getFree_services());
                for (int i = 0; i < free_servicesjsonArray.length(); i++) {
                    addFreeService(free_servicesjsonArray.get(i).toString());
                }
                JSONArray achievementsjsonArray = new JSONArray(data.getAchievements());
                for (int i = 0; i < achievementsjsonArray.length(); i++) {
                    achievements(achievementsjsonArray.get(i).toString());
                }
                JSONArray staffArray = new JSONArray(data.getStaff());
                if (staffArray != null) {
                    for (int i = 0; i < staffArray.length(); i++) {
                        try {
                            JSONObject object = staffArray.getJSONObject(i);
                            String pname = object.optString("name");
                            String designation = object.optString("designation");
                            String photo = object.optString("photo");
                            addStaffinfo(pname, designation, photo);
                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {

            }
        }
        //getGroundDetail();
    }


    /*// getGroundDetail
    private void getGroundDetail() {

        int id = getArguments().getInt("id");

        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.Ground + "/" + id;
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, "getGroundDetail", new IAsyncWorkCompletedCallback() {
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
                                    int id = jsonObject.optInt("id");
                                    String match_per_day = jsonObject.optString("match_per_day");
                                    String timezone = jsonObject.optString("timezone");
                                    int floodlight = jsonObject.optInt("floodlight");
                                    int available = jsonObject.optInt("available");
                                    String display_picture = jsonObject.optString("display_picture");
                                    String created_at = jsonObject.optString("created_at");
                                    String ground_country = jsonObject.optString("ground_country");
                                    String capacity = jsonObject.optString("capacity");
                                    String updated_at = jsonObject.optString("updated_at");
                                    String dimension = jsonObject.optString("dimension");
                                    String ground_state = jsonObject.optString("ground_state");
                                    String unique_id = jsonObject.optString("unique_id");
                                    int is_active = jsonObject.optInt("is_active");
                                    int day_or_night = jsonObject.optInt("day_or_night");
                                    String cost = jsonObject.optString("cost");
                                    String surface = jsonObject.optString("surface");
                                    String ground_city = jsonObject.optString("ground_city");
                                    String ground_address = jsonObject.optString("ground_address");
                                    String ground_zipcode = jsonObject.optString("ground_zipcode");
                                    String user_id = jsonObject.optString("user_id");
                                    String name = jsonObject.optString("name");

                                    nametxt.setText(name + "");
                                    if (available == 1) {
                                        statustxt.setText("Avilable");

                                    } else {
                                        statustxt.setText("Not Avilable");
                                    }
                                    uniqeId.setText(unique_id);
                                    capacityTxt.setText(capacity + "");
                                    Dimensionstxt.setText(dimension + "");

                                    if (day_or_night == 1) {
                                        dayNightTxt.setText("Day");
                                    } else if (day_or_night == 2) {
                                        dayNightTxt.setText("Night");
                                    } else {
                                        dayNightTxt.setText("Day or Night");
                                    }
                                    if (floodlight == 1) {
                                        fllodLightTxt.setText("Yes");
                                    } else {
                                        fllodLightTxt.setText("No");
                                    }

                                    capacitymatchPerDayTxt.setText(match_per_day + "");
                                    timeZoneTxt.setText(timezone);
                                    surfaceTxt.setText(surface);
                                    venueTxt.setText(ground_address + "," + ground_city + "," + ground_state + "," + ground_country + "," + ground_zipcode);
                                    costTxt.setText(cost + " Per Match");
                                    try {
                                        JSONArray jsonArray = new JSONArray(display_picture);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            Picasso.with(getContext()).load(Contants.BASE_URL + jsonArray.get(i).toString()).placeholder(R.drawable.noimage).into(displAyImage);
                                            break;
                                        }
                                    } catch (JSONException e) {

                                    }

                                    JSONArray sportsArray = jsonObject.optJSONArray("sports");
                                    if (sportsArray != null) {
                                        for (int i = 0; i < sportsArray.length(); i++) {
                                            try {
                                                JSONObject object = sportsArray.getJSONObject(i);
                                                String pname = object.optString("name");
                                                int idp = object.optInt("id");
                                                addSportmatchView(pname, idp);

                                            } catch (JSONException e) {
                                            }
                                        }

                                    }

                                    String terms_conditions = jsonObject.optString("terms_conditions");
                                    JSONArray jsonArray = new JSONArray(terms_conditions);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        addTermsAndCondition(jsonArray.get(i).toString());
                                    }
                                    String free_services = jsonObject.optString("free_services");
                                    JSONArray free_servicesjsonArray = new JSONArray(free_services);
                                    for (int i = 0; i < free_servicesjsonArray.length(); i++) {
                                        addFreeService(free_servicesjsonArray.get(i).toString());
                                    }
                                    String achievements = jsonObject.optString("achievements");
                                    JSONArray achievementsjsonArray = new JSONArray(achievements);
                                    for (int i = 0; i < achievementsjsonArray.length(); i++) {
                                        achievements(achievementsjsonArray.get(i).toString());
                                    }

                                    String staffstr = jsonObject.optString("staff");
                                    JSONArray staffArray = new JSONArray(staffstr);
                                    if (staffArray != null) {
                                        for (int i = 0; i < staffArray.length(); i++) {
                                            try {
                                                JSONObject object = staffArray.getJSONObject(i);
                                                String pname = object.optString("name");
                                                String designation = object.optString("designation");
                                                String photo = object.optString("photo");
                                                addStaffinfo(pname, designation, photo);
                                            } catch (JSONException e) {
                                                //e.printStackTrace();
                                            }
                                        }
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

    // addTimeZome
    public void addTimeZome(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.area_row, null);
        TextView nameView = inflatedLayout.findViewById(R.id.namearea);
        nameView.setText(name);
        timeZoneLayoutView.addView(inflatedLayout);

    }

    //add free service layout in runtime
    public void addSportmatchView(String name, int id) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.sportsnew, null);
        TextView sportsName = inflatedLayout.findViewById(R.id.sportsName);
        sportsName.setText(name);
        spoetsLayoyView.addView(inflatedLayout);

    }

    //add free service layout in runtime
    public void addFreeService(String name) {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "");
        servicesName.setText(name);
        freServiceesLayoutView.addView(inflatedLayout);

    }


    //add term and condition in run time
    public void addTermsAndCondition(String name) {
        termsCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(termsCount + "-");
        servicesName.setText(name);
        termCondtionLayoutView.addView(inflatedLayout);
    }

    public void achievements(String name) {
        achivement++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(achivement + "");
        servicesName.setText(name);
        achievementsLayoutView.addView(inflatedLayout);

    }

    public void addStaffinfo(String name, String designation, String photo) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.sports_item_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        ImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        teamneName.setText(name);
        userId.setText(designation);
        Picasso.with(getActivity()).load(Contants.BASE_URL + photo).placeholder(R.drawable.ic_cricket_player).into(matchPerview);
        staffLayoutView.addView(inflatedLayout);

    }

}
