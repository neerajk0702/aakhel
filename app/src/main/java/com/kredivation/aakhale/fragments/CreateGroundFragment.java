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
import com.kredivation.aakhale.model.AddViewDynamically;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.model.Timezone;
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
public class CreateGroundFragment extends Fragment implements View.OnClickListener {

    View view;

    RecyclerView addImageView;
    private RecycleViewAdapter imageAdapater;
    private ArrayList<String> stateIdList;
    private ArrayList<String> timeZoneList;
    private ArrayList<String> cityIdList;
    private String stateId, cityId, timeZoneId;
    List<City> cityInfoList;
    int serviceCount = 0;
    int termsCount = 0;
    ASTEditText gName, venueAddress, zipCode, capacitytxt, dimesionTxt, noofMatchtxt, surfaceTxt;
    Spinner stateSpinner, citySpinner, floodlightSpinner, TimeZoneSpinner, dayorNightSpiner;
    ASTButton continuebtn, canclebtn;
    String name, ground_address, ground_state, ground_city, ground_zipcode, floodlight, capacity, Dimension, timezone,
            match_per_day, day_or_night, surface, free_services, terms_conditions, cost, sports;
    LinearLayout container_add_freeservices, container_add_term_condtion;
    List<AddViewDynamically> allFreeSertvice = new ArrayList<AddViewDynamically>();
    List<AddViewDynamically> allTermCondication = new ArrayList<AddViewDynamically>();
    private TextView addMoretermcondtion, addmorfreeServices, addPicture;

    public CreateGroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_ground, container, false);
        init();
        getActivity().setTitle("Create Ground");
        return view;
    }

    public void init() {
        gName = view.findViewById(R.id.gName);
        venueAddress = view.findViewById(R.id.venueAddress);
        zipCode = view.findViewById(R.id.zipCode);
        capacitytxt = view.findViewById(R.id.capacity);
        dimesionTxt = view.findViewById(R.id.dimesionTxt);
        noofMatchtxt = view.findViewById(R.id.noofMatchtxt);
        surfaceTxt = view.findViewById(R.id.surfaceTxt);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        floodlightSpinner = view.findViewById(R.id.floodlightSpinner);
        TimeZoneSpinner = view.findViewById(R.id.TimeZoneSpinner);
        dayorNightSpiner = view.findViewById(R.id.dayorNightSpiner);
        continuebtn = view.findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(this);
        canclebtn = view.findViewById(R.id.canclebtn);
        canclebtn.setOnClickListener(this);
        addImageView = view.findViewById(R.id.addImageView);
        imageAdapater = new RecycleViewAdapter(getContext(), R.layout.image_item_layout, getData());
        addImageView.setAdapter(imageAdapater);
        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);
        container_add_freeservices = view.findViewById(R.id.container_add_freeservices);
        container_add_term_condtion = view.findViewById(R.id.container_add_term_condtion);
        addmorfreeServices = view.findViewById(R.id.addmorfreeServices);
        addMoretermcondtion = view.findViewById(R.id.addMoretermcondtion);
        addmorfreeServices.setOnClickListener(this);
        addMoretermcondtion.setOnClickListener(this);
        addPicture = view.findViewById(R.id.addPicture);
        addPicture.setOnClickListener(this);

        getGroundData();
        addTermsAndCondition();
        addFreeService();
    }

    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            //imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }


    private void getGroundData() {
        if (Utility.isOnline(getContext())) {
            ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.groundFormApi;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getGroundData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setStateSpinnerValue(serviceData);
                                setTimeZoneSpinner(serviceData);
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


    private void setTimeZoneSpinner(ContentData serviceData) {
        if (serviceData.getTimezone() != null) {
            ArrayList<String> timeZoneListt = new ArrayList<>();
            timeZoneList = new ArrayList<String>();
            timeZoneListt.add("Select Time Zone");
            timeZoneList.add("0");
            for (Timezone timezone : serviceData.getTimezone()) {
                timeZoneListt.add(timezone.getTimezone_name());
                timeZoneList.add(timezone.getId());
            }
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, timeZoneListt);
            TimeZoneSpinner.setAdapter(statedapter);

            TimeZoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    timeZoneId = timeZoneList.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    //add free service layout in runtime
    public void addFreeService() {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_editbox, null);
        ASTEditText edittext = inflatedLayout.findViewById(R.id.edittext);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(count + "-");
        container_add_freeservices.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setAddEditText(edittext);
        allFreeSertvice.add(addViewDynamically);
    }

    //add term and condition in run time
    public void addTermsAndCondition() {
        termsCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_editbox, null);
        ASTEditText edittext = inflatedLayout.findViewById(R.id.edittext);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(count + "-");
        container_add_term_condtion.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setAddEditText(edittext);
        allTermCondication.add(addViewDynamically);
    }

    public boolean isValidate() {
        name = gName.getText().toString();
        ground_address = venueAddress.getText().toString();
        ground_zipcode = zipCode.getText().toString();
        capacity = capacitytxt.getText().toString();
        Dimension = dimesionTxt.getText().toString();
        surface = surfaceTxt.getText().toString();
        ground_state = stateSpinner.getSelectedItem().toString();
        floodlight = floodlightSpinner.getSelectedItem().toString();
        timezone = TimeZoneSpinner.getSelectedItem().toString();
        day_or_night = dayorNightSpiner.getSelectedItem().toString();
        if (name.equals("")) {
            Toast.makeText(getContext(), "Please Enter Ground Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ground_address.equals("")) {
            Toast.makeText(getContext(), "Please Enter Ground Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }


    private void saveGroundData() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            final String url = Contants.BASE_URL + Contants.creatematchApi;
            JSONObject object = new JSONObject();
            try {
                object.put("name", name);
                object.put("ground_address", ground_address);
                object.put("ground_state", ground_state);
                object.put("ground_city", ground_city);
                object.put("ground_zipcode", ground_zipcode);
                object.put("floodlight", floodlight);
                object.put("capacity", capacity);
                object.put("Dimension", Dimension);
                object.put("timezone", timezone);
                object.put("match_per_day", match_per_day);
                object.put("day_or_night", day_or_night);
                object.put("surface", surface);
                object.put("free_services", free_services);
                object.put("terms_conditions", terms_conditions);
                object.put("cost", cost);
                object.put("sports", sports);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            serviceCaller.CallCommanServiceMethod(url, object, "saveGroundData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseGroundData(result);
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


    /*
     *
     * Parse and Validate Teame Service Data
     */
    private void parseGroundData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("success")) {
                    JSONObject object = jsonRootObject.optJSONObject("data");
                    String userName = object.optString("name").toString();
                    Toast.makeText(getContext(), "Ground added successfully", Toast.LENGTH_LONG).show();
                } else {
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continuebtn:
                if (isValidate()) {

                }
                break;
            case R.id.canclebtn:
                break;
            case R.id.addmorfreeServices:
                addFreeService();
                ASTUIUtil.showToast(getContext(), "One More Free Services Added.");
                break;
            case R.id.addMoretermcondtion:
                addTermsAndCondition();
                ASTUIUtil.showToast(getContext(), "One More Term and Condition Added.");
                break;
            case R.id.addPicture:
                break;
        }
    }
}
