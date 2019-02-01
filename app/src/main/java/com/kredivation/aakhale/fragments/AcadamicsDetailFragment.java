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
import com.kredivation.aakhale.R;
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
    TextView name, ratingTxt, address, uniqueId, establishText;
    ImageView fab;
    ASTProgressBar astProgressBar;
    Bundle bundle;
    int id;
    LinearLayout galeeryImageView, managerLayout, teamLiastView, sportsView, ownerLayout, coachLayout;
    int serviceCount;


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
                                    name.setText(academy_name);
                                    ratingTxt.setText(academy_name);
                                    address.setText(street_address);
                                    uniqueId.setText(user_id);
                                    establishText.setText(academy_description);

                                    String academymanager = jsonObject.optString("academy_manager");
                                    String newacademymanager = academymanager.substring(1, academymanager.length() - 1);

                                    JSONObject academy_manager = new JSONObject(newacademymanager);
                                    String full_name = academy_manager.optString("full_name");
                                    String email = academy_manager.optString("email");
                                    String contact_number = academy_manager.optString("contact_number");
                                    addManager(email, contact_number, full_name);


                                    String academy_owner = jsonObject.optString("academy_owner");
                                    String newacademy_owner = academy_owner.substring(1, academy_owner.length() - 1);


                                    JSONObject academy_ownerobj = new JSONObject(newacademy_owner);
                                    String ownerfull_name = academy_ownerobj.optString("full_name");
                                    String owneremail = academy_ownerobj.optString("email");
                                    String ownercontact_number = academy_ownerobj.optString("contact_number");
                                    addOwner(owneremail, ownercontact_number, ownerfull_name);


                                    String team_member = jsonObject.optString("team_member");

                                    String newteam_member = team_member.substring(1, team_member.length() - 1);

                                    JSONObject academy_teamobj = new JSONObject(newteam_member);
                                    String teamfull_name = academy_teamobj.optString("full_name");
                                    String teamemail = academy_teamobj.optString("email");
                                    String teamcontact_number = academy_teamobj.optString("contact_number");
                                    addTeamsView(teamemail, teamcontact_number, teamfull_name);


                                    String academy_coach = jsonObject.optString("academy_coach");
                                    String newacademy_coach = academy_coach.substring(1, academy_coach.length() - 1);

                                    JSONObject academy_tacademy_coach = new JSONObject(newacademy_coach);
                                    String caochfull_name = academy_tacademy_coach.optString("full_name");
                                    String caochemail = academy_tacademy_coach.optString("email");
                                    String caochcontact_number = academy_tacademy_coach.optString("contact_number");
                                    addCoachView(caochcontact_number, caochemail, caochfull_name);

                                    String academy_photos = jsonObject.optString("academy_photos");
                                    String[] academy_photosList = academy_photos.split(",");
                                    List<String> academy_photosArayList = Arrays.asList(academy_photosList);
                                    if (academy_photosArayList != null) {
                                        for (int i = 0; i < academy_photosArayList.size(); i++) {

                                            addGalleryImage(academy_photosArayList.get(i));
                                        }

                                    }

                                    JSONArray jsonArray = jsonObject.optJSONArray("academy_sports");
                                    if (jsonArray != null) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String pname = object.optString("name");
                                            addSportS(pname);
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
    }


    //add free service layout in runtime
    public void addGalleryImage(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.gallery_row, null);
        ImageView image = inflatedLayout.findViewById(R.id.image);

        Picasso.with(getContext()).load("http://cricket.vikaskumar.info/" + name)
                .placeholder(R.drawable.noimage).into(image);
        galeeryImageView.addView(inflatedLayout);

    }


    //addSportS
    public void addSportS(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.sports_row, null);
        TextView sportsName = inflatedLayout.findViewById(R.id.sportsName);
        sportsName.setText(name);
        sportsView.addView(inflatedLayout);
    }

    //addManager
    public void addManager(String emailstr, String strcontactNo, String strfullName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.memebr_item_row, null);
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
        coachLayout.addView(inflatedLayout);

    }

    //addSportS
    public void addTeamsView(String emailstr, String strcontactNo, String strfullName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.memebr_item_row, null);
        AppCompatAutoCompleteTextView contactNo = inflatedLayout.findViewById(R.id.contactNo);
        AppCompatAutoCompleteTextView emailId = inflatedLayout.findViewById(R.id.emailId);
        AppCompatAutoCompleteTextView fullName = inflatedLayout.findViewById(R.id.fullName);
        contactNo.setText(strcontactNo);
        emailId.setText(emailstr);
        fullName.setText(strfullName);
        teamLiastView.addView(inflatedLayout);

    }


}
