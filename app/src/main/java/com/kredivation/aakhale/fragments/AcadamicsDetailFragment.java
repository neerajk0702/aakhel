package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.TournamentDetails;
import com.kredivation.aakhale.adapter.GalleryAdapter;
import com.kredivation.aakhale.adapter.PlayerAdapter;
import com.kredivation.aakhale.adapter.SportsServiceGridViewAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcadamicsDetailFragment extends Fragment {
    View view;
    TextView name, ratingTxt, address, uniqueId, establishText, description;
    ImageView fab;
    ASTProgressBar astProgressBar;
    Bundle bundle;
    int id;
    LinearLayout galeeryImageView, managerLayout, teamLiastView, sportsView, ownerLayout, coachLayout, achievementsLayout;

    public AcadamicsDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_acadamics_detail, container, false);
        init();
        return view;
    }

    public void init() {
        name = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        address = view.findViewById(R.id.address);
        uniqueId = view.findViewById(R.id.uniqueId);
        establishText = view.findViewById(R.id.establishText);
        fab = view.findViewById(R.id.fab);
        galeeryImageView = view.findViewById(R.id.galeeryImageView);
        managerLayout = view.findViewById(R.id.managerLayout);
        teamLiastView = view.findViewById(R.id.teamLiastView);
        sportsView = view.findViewById(R.id.sportsView);
        ownerLayout = view.findViewById(R.id.ownerLayout);
        coachLayout = view.findViewById(R.id.coachLayout);
        description = view.findViewById(R.id.description);
        achievementsLayout = view.findViewById(R.id.achievementsLayout);
        getActivity().setTitle("Academics Details");
        dataToView();

    }

    public void dataToView() {
        getAcadmicsDetails();
    }


    // getMatchDetails
    private void getAcadmicsDetails() {
        bundle = getArguments();
        int id = getArguments().getInt("id");

        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.addAcademy + "/" + id;
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, "getAcadmicsDetails", new IAsyncWorkCompletedCallback() {
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
                                    String academy_name = jsonObject.optString("academy_name");
                                    String unique_id = jsonObject.optString("unique_id");
                                    String user_id = jsonObject.optString("user_id");
                                    String created_at = jsonObject.optString("created_at");
                                    String updated_at = jsonObject.optString("updated_at");
                                    int is_active = jsonObject.optInt("is_active");
                                    String street_address = jsonObject.optString("street_address");
                                    String academy_city = jsonObject.optString("academy_city");
                                    String academy_state = jsonObject.optString("academy_state");
                                    String academy_country = jsonObject.optString("academy_country");
                                    String academy_zipcode = jsonObject.optString("academy_zipcode");
                                    String academy_description = jsonObject.optString("academy_description");
                                    String estd = jsonObject.optString("estd");
                                    String achievements = jsonObject.optString("achievements");
                                    name.setText(academy_name);
                                    // ratingTxt.setText(academy_name);
                                    String completeAdd = street_address + "," + academy_city + "," + academy_state + "," + academy_country + "," + academy_zipcode;
                                    address.setText(completeAdd);
                                    uniqueId.setText("Unique ID : " + unique_id);
                                    establishText.setText("Date of Establishment: " + estd);
                                    description.setText("Academy Description: " + academy_description);

                                    JSONArray achievementsObj = new JSONArray(achievements);
                                    for (int i = 0; i < achievementsObj.length(); i++) {
                                        addachievements(achievementsObj.optString(i));
                                    }

                                    String academymanager = jsonObject.optString("academy_manager");
                                    JSONObject academy_manager = new JSONObject(academymanager);
                                    String full_name = academy_manager.optString("full_name");
                                    String email = academy_manager.optString("email");
                                    String contact_number = academy_manager.optString("contact_number");
                                    addManager(email, contact_number, full_name);

                                    String academy_owner = jsonObject.optString("academy_owner");
                                    JSONObject academy_ownerobj = new JSONObject(academy_owner);
                                    String ownerfull_name = academy_ownerobj.optString("full_name");
                                    String owneremail = academy_ownerobj.optString("email");
                                    String ownercontact_number = academy_ownerobj.optString("contact_number");
                                    addOwner(owneremail, ownercontact_number, ownerfull_name);


                                    String team_member = jsonObject.optString("team_member");
                                    JSONArray academy_teamobj = new JSONArray(team_member);
                                    for (int i = 0; i < academy_teamobj.length(); i++) {
                                        JSONObject object = academy_teamobj.optJSONObject(i);
                                        String teamfull_name = object.optString("full_name");
                                        String teamemail = object.optString("email");
                                        String teamcontact_number = object.optString("contact_number");
                                        addTeamsView(teamemail, teamcontact_number, teamfull_name, i + 1);
                                    }
                                    String academy_coach = jsonObject.optString("academy_coach");

                                    JSONObject academy_tacademy_coach = new JSONObject(academy_coach);
                                    String caochfull_name = academy_tacademy_coach.optString("full_name");
                                    String caochemail = academy_tacademy_coach.optString("email");
                                    String caochcontact_number = academy_tacademy_coach.optString("contact_number");
                                    addCoachView(caochcontact_number, caochemail, caochfull_name);

                                    String academy_photos = jsonObject.optString("academy_photos");
                                    JSONArray photojsonArray = new JSONArray(academy_photos);
                                    if (photojsonArray != null) {
                                        for (int i = 0; i < photojsonArray.length(); i++) {
                                            addGalleryImage(photojsonArray.get(i).toString());
                                        }
                                    }
                                    try {
                                        String academy_servicesStr = jsonObject.optString("academy_services");
                                        JSONArray jsonArray =new JSONArray(academy_servicesStr);
                                        if (jsonArray != null) {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                String jsonStr = jsonArray.optString(i);
                                                if (jsonStr != null) {
                                                    JSONObject sportJsonObject = new JSONObject(jsonStr);
                                                    JSONObject sportObj = sportJsonObject.optJSONObject("sports");
                                                    String sname = sportObj.optString("name");
                                                    JSONObject trainerObj = sportJsonObject.optJSONObject("trainer");
                                                    String trainername = trainerObj.optString("name");
                                                    String trainergender = trainerObj.optString("gender");
                                                    String trainerage = trainerObj.optString("age");
                                                    String trainerexperience = trainerObj.optString("experience");
                                                    String timing = sportJsonObject.optString("timing");
                                                    String fees = sportJsonObject.optString("fees");
                                                    addSportS(sname, trainername, trainergender, trainerage, trainerexperience, timing, fees);
                                                }
                                            }
                                        }

                                    } catch (JSONException e) {
                                        // e.printStackTrace();
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
    }


    //add free service layout in runtime
    public void addGalleryImage(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.gallery_row, null);
        ImageView image = inflatedLayout.findViewById(R.id.image);

        Picasso.with(getContext()).load(Contants.BASE_URL + name)
                .placeholder(R.drawable.noimage).into(image);
        galeeryImageView.addView(inflatedLayout);

    }

    int achievementsCount;

    //add free service layout in runtime
    public void addachievements(String name) {
        achievementsCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(achievementsCount + "-");
        servicesName.setText(name);
        achievementsLayout.addView(inflatedLayout);

    }

    //addSportS
    public void addSportS(String sname, String trainername, String trainergender, String trainerage, String trainerexperience, String timing, String fees) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.academic_sports_service_layout, null);
        TextView sportsName = inflatedLayout.findViewById(R.id.sportsName);
        sportsName.setText(sname);
        TextView trainernametext = inflatedLayout.findViewById(R.id.trainername);
        trainernametext.setText(trainername);
        TextView trainergendertext = inflatedLayout.findViewById(R.id.trainergender);
        if (trainergender.equals("1")) {
            trainergendertext.setText("Male");
        } else if (trainergender.equals("2")) {
            trainergendertext.setText("Female");
        }
        TextView traineragetext = inflatedLayout.findViewById(R.id.trainerage);
        traineragetext.setText(trainerage);
        TextView exp = inflatedLayout.findViewById(R.id.exp);
        exp.setText(trainerexperience);
        TextView timingtext = inflatedLayout.findViewById(R.id.timing);
        timingtext.setText(timing);
        TextView feestext = inflatedLayout.findViewById(R.id.fees);
        feestext.setText(fees);
        sportsView.addView(inflatedLayout);
    }

    //addManager
    public void addManager(String emailstr, String strcontactNo, String strfullName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.memebr_item_row, null);
        TextView teammemberlabel = inflatedLayout.findViewById(R.id.teammemberlabel);
        teammemberlabel.setVisibility(View.GONE);
        AppCompatAutoCompleteTextView contactNo = inflatedLayout.findViewById(R.id.contactNo);
        AppCompatAutoCompleteTextView emailId = inflatedLayout.findViewById(R.id.emailId);
        AppCompatAutoCompleteTextView fullName = inflatedLayout.findViewById(R.id.fullName);
        contactNo.setText(strcontactNo);
        emailId.setText(emailstr);
        fullName.setText(strfullName);
        managerLayout.addView(inflatedLayout);

    }


    //addOwner
    public void addOwner(String emailstr, String strcontactNo, String strfullName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.memebr_item_row, null);
        AppCompatAutoCompleteTextView contactNo = inflatedLayout.findViewById(R.id.contactNo);
        AppCompatAutoCompleteTextView emailId = inflatedLayout.findViewById(R.id.emailId);
        AppCompatAutoCompleteTextView fullName = inflatedLayout.findViewById(R.id.fullName);
        contactNo.setText(strcontactNo);
        emailId.setText(emailstr);
        fullName.setText(strfullName);
        TextView teammemberlabel = inflatedLayout.findViewById(R.id.teammemberlabel);
        teammemberlabel.setVisibility(View.GONE);
        ownerLayout.addView(inflatedLayout);

    }


    //addCoachView
    public void addCoachView(String emailstr, String strcontactNo, String strfullName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.memebr_item_row, null);
        AppCompatAutoCompleteTextView contactNo = inflatedLayout.findViewById(R.id.contactNo);
        AppCompatAutoCompleteTextView emailId = inflatedLayout.findViewById(R.id.emailId);
        AppCompatAutoCompleteTextView fullName = inflatedLayout.findViewById(R.id.fullName);
        contactNo.setText(strcontactNo);
        emailId.setText(emailstr);
        fullName.setText(strfullName);
        TextView teammemberlabel = inflatedLayout.findViewById(R.id.teammemberlabel);
        teammemberlabel.setVisibility(View.GONE);
        coachLayout.addView(inflatedLayout);

    }

    //addSportS
    public void addTeamsView(String emailstr, String strcontactNo, String strfullName, int no) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.memebr_item_row, null);
        AppCompatAutoCompleteTextView contactNo = inflatedLayout.findViewById(R.id.contactNo);
        AppCompatAutoCompleteTextView emailId = inflatedLayout.findViewById(R.id.emailId);
        AppCompatAutoCompleteTextView fullName = inflatedLayout.findViewById(R.id.fullName);
        TextView teammemberlabel = inflatedLayout.findViewById(R.id.teammemberlabel);
        teammemberlabel.setText("Teams Member " + no);
        contactNo.setText(strcontactNo);
        emailId.setText(emailstr);
        fullName.setText(strfullName);
        teamLiastView.addView(inflatedLayout);

    }


}
