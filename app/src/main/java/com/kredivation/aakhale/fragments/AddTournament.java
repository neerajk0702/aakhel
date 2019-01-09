package com.kredivation.aakhale.fragments;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.RecycleViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTournament extends Fragment implements View.OnClickListener {

    View view;
    ASTEditText tournamentName, venueAddress, zipCode, noofOver, enteryFee, aboutTournament;

    Spinner stateSpinner, citySpinner, formateSpinner, startdateSpinner, enddateSpinner, statusSpinner;
    LinearLayout container_add_facilities, container_add_rule_regulation, container_add_rule_price;
    TextView addsisplayPicture, addMoreprice, addMoreFacilites, addMorerule_regulation;
    RecyclerView addImageView;
    private RecycleViewAdapter imageAdapater;
    private ArrayList<String> stateIdList;
    private ArrayList<String> groundList;
    private ArrayList<String> cityIdList;
    ASTEditText addviewedtxt;
    private String stateId, cityId, groundId;
    List<City> cityInfoList;
ASTButton continuebtn;

    String name, tournament_address, tournament_state, tournament_city, tournament_zipcode, overs,
            format, start_date, end_date, entry_fees, status, about_tournament, facilities, rules_regulations,
            prizes, display_picture, teams;

    public AddTournament() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_tournament, container, false);

        getActivity().setTitle("Add Tournament");
        init();
        return view;
    }

    public void init() {
        tournamentName = view.findViewById(R.id.tournamentName);
        venueAddress = view.findViewById(R.id.venueAddress);
        zipCode = view.findViewById(R.id.zipCode);
        noofOver = view.findViewById(R.id.noofOver);
        enteryFee = view.findViewById(R.id.enteryFee);
        aboutTournament = view.findViewById(R.id.aboutTournament);
        addMoreFacilites = view.findViewById(R.id.addMoreFacilites);
        addMorerule_regulation = view.findViewById(R.id.addMorerule_regulation);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        formateSpinner = view.findViewById(R.id.formateSpinner);
        startdateSpinner = view.findViewById(R.id.startdateSpinner);
        enddateSpinner = view.findViewById(R.id.enddateSpinner);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        container_add_facilities = view.findViewById(R.id.container_add_facilities);
        container_add_rule_regulation = view.findViewById(R.id.container_add_rule_regulation);
        container_add_rule_price = view.findViewById(R.id.container_add_rule_price);
        addsisplayPicture = view.findViewById(R.id.addsisplayPicture);
        addMoreprice = view.findViewById(R.id.addMoreprice);
        addMoreFacilites = view.findViewById(R.id.addMoreFacilites);
        addImageView = view.findViewById(R.id.addImageView);
        imageAdapater = new RecycleViewAdapter(getContext(), R.layout.image_item_layout, getData());
        addImageView.setAdapter(imageAdapater);
        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);
        addsisplayPicture.setOnClickListener(this);
        addMoreprice.setOnClickListener(this);
        addMoreFacilites.setOnClickListener(this);
        addMorerule_regulation.setOnClickListener(this);
        continuebtn = view.findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(this);
        addMoreFacilites("Facilites Name");
        addMorerule_regulation("Rule Regulation Name");
        addMoreprice("Price");
        getAddTournamentData();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreFacilites:
                addMoreFacilites("Facilites Name");
                break;
            case R.id.addMorerule_regulation:
                addMorerule_regulation("Rule Regulation Name");
                break;
            case R.id.addMoreprice:
                addMoreprice("Price");
                break;
            case R.id.continuebtn:
                if (isvalidateSignup()) {
                    addTournamentServer();
                }


        }
    }


    public void addMoreFacilites(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_facilities.addView(inflatedLayout);
    }


    public void addMorerule_regulation(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_rule_regulation.addView(inflatedLayout);
    }


    public void addMoreprice(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_rule_price.addView(inflatedLayout);
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }


    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void getAddTournamentData() {
        if (Utility.isOnline(getContext())) {
            ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.tournamentForm;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getAddTournamentData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setStateSpinnerValue(serviceData);
                            }
                        } else {
                            Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (dotDialog.isShowing()) {
                dotDialog.dismiss();
            }
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }

    private void setStateSpinnerValue(ContentData serviceData) {
        if (serviceData.getState() != null) {
            ArrayList<String> stateList = new ArrayList<>();
            stateIdList = new ArrayList<String>();
            stateList.add("Select State");
            stateIdList.add("0");
            cityInfoList = new ArrayList<City>();
            for (State state : serviceData.getState()) {
                stateList.add(state.getState_name());
                stateIdList.add(state.getId());
                for (City city : state.getCity()) {
                    cityInfoList.add(city);
                }
            }
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, stateList);
            stateSpinner.setAdapter(statedapter);
            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    stateId = stateIdList.get(pos);
                    setCitySpinnerValue();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void setCitySpinnerValue() {
        if (cityInfoList != null) {
            final ArrayList<String> cityList = new ArrayList<>();
            cityIdList = new ArrayList<String>();
            cityList.add("Select City");
            cityIdList.add("0");
            for (int i = 0; i < cityInfoList.size(); i++) {
                if (stateId.equals(cityInfoList.get(i).getState_id())) {
                    cityList.add(cityInfoList.get(i).getCity_name());
                    cityIdList.add(cityInfoList.get(i).getId());
                }
            }
            ArrayAdapter<String> citydapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, cityList);
            citySpinner.setAdapter(citydapter);
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    cityId = cityIdList.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void addTournamentServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            final String url = Contants.BASE_URL + Contants.tournamentAPi;
            JSONObject object = new JSONObject();
            try {
                object.put("name", name);
                object.put("tournament_address", tournament_address);
                object.put("tournament_state", tournament_state);
                object.put("tournament_city", tournament_city);
                object.put("tournament_zipcode", tournament_zipcode);
                object.put("overs", overs);
                object.put("format", format);
                object.put("start_date", start_date);
                object.put("end_date", end_date);
                object.put("entry_fees", entry_fees);
                object.put("status", status);
                object.put("about_tournament", about_tournament);
                object.put("facilities", facilities);
                object.put("rules_regulations", rules_regulations);
                object.put("prizes", prizes);
                object.put("display_picture", display_picture);
                object.put("teams", teams);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            serviceCaller.CallCommanServiceMethod(url, object, "addTournament", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseTournamentData(result);
                    } else {
                        showToast(Contants.Error);
                    }
                    if (dotDialog.isShowing()) {
                        dotDialog.dismiss();
                    }
                }
            });
        } else {
            showToast(Contants.OFFLINE_MESSAGE);
        }

    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    public boolean isvalidateSignup() {
        name = tournamentName.getText().toString();
        tournament_address = venueAddress.getText().toString();
        tournament_state = stateSpinner.getSelectedItem().toString();
        tournament_city = citySpinner.getSelectedItem().toString();
        tournament_zipcode = zipCode.getText().toString();
        overs = noofOver.getText().toString();
       format = formateSpinner.getSelectedItem().toString();
        start_date = startdateSpinner.getSelectedItem().toString();
        end_date = enddateSpinner.getSelectedItem().toString();
        entry_fees = enteryFee.getText().toString();
        status = statusSpinner.getSelectedItem().toString();
        about_tournament = aboutTournament.getText().toString();
        //    facilities = timeTxt.getText().toString();
        //   rules_regulations = dateTxt.getText().toString();
        //   prizes = dateTxt.getText().toString();
        //  display_picture = dateTxt.getText().toString();
        //  teams = dateTxt.getText().toString();


        if (name.equals("")) {
            Toast.makeText(getContext(), "Please Enter Tournament Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_address.equals("")) {
            Toast.makeText(getContext(), "Please Enter Tournament Venue", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_state.equals("") || tournament_state.equalsIgnoreCase("Select State")) {
            Toast.makeText(getContext(), "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_city.equals("") || tournament_city.equalsIgnoreCase("Select City")) {
            Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_zipcode.equals("")) {
            Toast.makeText(getContext(), "Please Enter Zip Code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (overs.equals("")) {
            Toast.makeText(getContext(), "Please Enter Over", Toast.LENGTH_SHORT).show();
            return false;
        } else if (format.equals("")) {
            Toast.makeText(getContext(), "Please Enter Match Formate", Toast.LENGTH_SHORT).show();
            return false;
        } else if (start_date.equals("")) {
            Toast.makeText(getContext(), "Please Select Start Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (end_date.equals("")) {
            Toast.makeText(getContext(), "Please Select End Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (entry_fees.equals("")) {
            Toast.makeText(getContext(), "Please Enter Entry Fees", Toast.LENGTH_SHORT).show();
            return false;
        } else if (about_tournament.equals("")) {
            Toast.makeText(getContext(), "Please Enter About Tournament", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    /*
     *
     * Parse and Validate Teame Service Data
     */
    private void parseTournamentData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("success")) {
                    JSONObject object = jsonRootObject.optJSONObject("data");
                    String userName = object.optString("name").toString();
                    Toast.makeText(getContext(), "Tournament added successfully", Toast.LENGTH_LONG).show();
                } else {
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }
}