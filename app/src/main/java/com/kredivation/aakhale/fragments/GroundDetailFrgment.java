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
import com.kredivation.aakhale.adapter.MatchPlayedAdapter;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.AddViewDynamically;
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
            statustxt, uniqeId, capacityTxt, Dimensionstxt, dayNightTxt, fllodLightTxt, capacitymatchPerDayTxt, timeZoneTxt,
            surfaceTxt, venueTxt, costTxt;
    LinearLayout spoetsLayoyView, freServiceesLayoutView, termCondtionLayoutView;
    int serviceCount, termsCount;

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
        timeZoneTxt = view.findViewById(R.id.timeZoneTxt);
        surfaceTxt = view.findViewById(R.id.surfaceTxt);
        venueTxt = view.findViewById(R.id.venueTxt);
        costTxt = view.findViewById(R.id.costTxt);
        spoetsLayoyView = view.findViewById(R.id.spoetsLayoyView);
        freServiceesLayoutView = view.findViewById(R.id.freServiceesLayoutView);
        termCondtionLayoutView = view.findViewById(R.id.termCondtionLayoutView);
        dataToView();

    }

    public void dataToView() {
        getGroundDetail();
    }

    ASTProgressBar astProgressBar;

    // getGroundDetail
    private void getGroundDetail() {
        bundle = getArguments();
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

                                    nametxt.setText(name);
                                    if (available == 1) {
                                        statustxt.setText("Avilable");

                                    } else {
                                        statustxt.setText("Not Avilable");
                                    }


                                    uniqeId.setText(unique_id);
                                    capacityTxt.setText(capacity);
                                    Dimensionstxt.setText(dimension);

                                    if (day_or_night == 1) {
                                        dayNightTxt.setText("Day");

                                    } else if (day_or_night == 2) {
                                        dayNightTxt.setText("Night");
                                    } else {
                                        dayNightTxt.setText("Day or Night");
                                    }


                                    fllodLightTxt.setText(floodlight + "");
                                    capacitymatchPerDayTxt.setText(match_per_day);
                                    timeZoneTxt.setText(timezone);
                                    surfaceTxt.setText(surface);
                                    venueTxt.setText(ground_address + "," + ground_city + "," + ground_zipcode);
                                    costTxt.setText(cost);

                                    Picasso.with(getContext()).load("http://cricket.vikaskumar.info/" + display_picture)
                                            .placeholder(R.drawable.noimage).into(displAyImage);

                                    JSONArray sportsArray = jsonObject.optJSONArray("sports");
                                    if (sportsArray != null) {
                                        for (int i = 0; i < sportsArray.length(); i++) {
                                            try {
                                                JSONObject object = sportsArray.getJSONObject(i);
                                                String pname = object.optString("name");
                                                int idp = object.optInt("id");
                                                addSportmatchView(pname, idp);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }

                                    String terms_conditions = jsonObject.optString("terms_conditions");
                                    String newterms_conditions = terms_conditions.substring(1, terms_conditions.length() - 1);


                                    JSONArray jsonArray = new JSONArray(newterms_conditions);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        addTermsAndCondition(jsonArray.get(i).toString());
                                    }
                                /*    String[] terms_conditionsList = terms_conditions.split(",");
                                    List<String> academy_photosArayList = Arrays.asList(terms_conditionsList);
                                    if (academy_photosArayList != null) {
                                        for (int i = 0; i < academy_photosArayList.size(); i++) {
                                            addTermsAndCondition(academy_photosArayList.get(i));
                                        }*/

                                    //}


                                    String free_services = jsonObject.optString("free_services");
                                    String newfree_services = free_services.substring(1, free_services.length() - 1);


                                    JSONArray free_servicesjsonArray = new JSONArray(newfree_services);
                                    for (int i = 0; i < free_servicesjsonArray.length(); i++) {
                                        addFreeService(free_servicesjsonArray.get(i).toString());
                                    }

                                   /* String free_services = jsonObject.optString("free_services");
                                    String[] free_servicesList = free_services.split(",");
                                    List<String> free_servicesLis = Arrays.asList(free_servicesList);
                                    if (free_servicesLis != null) {
                                        for (int i = 0; i < free_servicesLis.size(); i++) {
                                            addFreeService(free_servicesLis.get(i));
                                        }
                                    }
*/

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
    }


    //add free service layout in runtime
    public void addSportmatchView(String name, int id) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.sports_item_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        ImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        teamneName.setText(name);
        spoetsLayoyView.addView(inflatedLayout);

    }

    //add free service layout in runtime
    public void addFreeService(String name) {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "-");
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

}
