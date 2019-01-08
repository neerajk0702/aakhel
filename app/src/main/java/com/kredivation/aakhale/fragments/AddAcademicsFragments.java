package com.kredivation.aakhale.fragments;


import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.LoginActivity;
import com.kredivation.aakhale.activity.RegisterActivity;
import com.kredivation.aakhale.activity.ResetPasswordActivity;
import com.kredivation.aakhale.adapter.GridViewAdapter;
import com.kredivation.aakhale.adapter.RecycleViewAdapter;
import com.kredivation.aakhale.adapter.SportsServiceGridViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Sports;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAcademicsFragments extends Fragment implements View.OnClickListener {

    View view;
    private RecyclerView addImageView;
    private RecycleViewAdapter imageAdapater;
    LinearLayout container_moreadd;
    ImageView sortiMG, acadmiciMG;
    ASTEditText acdName, accAddress, zipcode, description, managerfullName, manageremail, mamangercontactno;
    ASTEditText coachfullName, coachemail, coachcontactno, OwnerfullName, Owneremail, Ownercontactno;
    Spinner stateSpinner, sitySpinner;
    TextView addMoreViewmember, addMoreViewImage;
    LinearLayout academiesMemberLayout;
    ASTEditText memberfullName, memberemail, membercontactno;
    ASTButton continuebtn;
    TextView nooftemMember;
    int count = 0;
    LinearLayout acadmicViewinfoLayout, acadmicinfoLayout, acadmicmemberinfoLayout;
    int numberclick = 1;
    int academiesMemberLayoutclick = 1;

    GridView sportsgridView;
    SportsServiceGridViewAdapter sportsServiceGridViewAdapter;
    private ArrayList<String> stateIdList;
    private ArrayList<String> cityIdList;
    private String stateId, cityId;
    List<City> cityInfoList;
    private String acdNameStr, accAddressStr, zipcodeStr, descriptionStr, managerfullNameStr, manageremailStr, mamangercontactnoStr, coachfullNameStr, coachemailStr, coachcontactnoStr, OwnerfullNameStr, OwneremailStr, OwnercontactnoStr;

    public AddAcademicsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_academics_vfragments, container, false);
        init();
        getActivity().setTitle("Add Academics");
        return view;
    }

    public void init() {

        addImageView = view.findViewById(R.id.addImageView);
        imageAdapater = new RecycleViewAdapter(getContext(), R.layout.image_item_layout, getData());
        addImageView.setAdapter(imageAdapater);

        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);


        container_moreadd = view.findViewById(R.id.container_moreadd);
        addMoreViewmember = view.findViewById(R.id.addMoreViewmember);

        sortiMG = view.findViewById(R.id.sortiMG);
        acadmiciMG = view.findViewById(R.id.acadmiciMG);
        acdName = view.findViewById(R.id.acdName);
        accAddress = view.findViewById(R.id.accAddress);
        zipcode = view.findViewById(R.id.zipcode);
        description = view.findViewById(R.id.description);
        managerfullName = view.findViewById(R.id.managerfullName);
        manageremail = view.findViewById(R.id.manageremail);
        mamangercontactno = view.findViewById(R.id.mamangercontactno);
        coachfullName = view.findViewById(R.id.coachfullName);
        coachemail = view.findViewById(R.id.coachemail);
        coachcontactno = view.findViewById(R.id.coachcontactno);
        OwnerfullName = view.findViewById(R.id.OwnerfullName);
        Owneremail = view.findViewById(R.id.Owneremail);
        Ownercontactno = view.findViewById(R.id.Ownercontactno);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        sitySpinner = view.findViewById(R.id.sitySpinner);
        addMoreViewmember = view.findViewById(R.id.addMoreViewmember);
        addMoreViewImage = view.findViewById(R.id.addMoreViewImage);
        academiesMemberLayout = view.findViewById(R.id.academiesMemberLayout);
        continuebtn = view.findViewById(R.id.continuebtn);
        addMoreViewmember.setOnClickListener(this);
        acadmicViewinfoLayout = view.findViewById(R.id.acadmicViewinfoLayout);
        acadmicinfoLayout = view.findViewById(R.id.acadmicinfoLayout);
        acadmicViewinfoLayout.setOnClickListener(this);
        academiesMemberLayout.setOnClickListener(this);
        acadmicmemberinfoLayout = view.findViewById(R.id.acadmicmemberinfoLayout);
        addMoreMember();
        sportsgridView = view.findViewById(R.id.sportsgridView);
        getacademyFormData();
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
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }

    private ArrayList<ImageItem> getSportsData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        ImageItem imageItem = new ImageItem();
        for (int i = 1; i <= 8; i++) {
            imageItem.setTitle("Cricket");
            imageItem.setTitle("Hocky");
            imageItem.setTitle("FootBall");
            imageItem.setTitle("VollyBall");
            imageItem.setTitle("Swimming");
            imageItem.setTitle("Chess");
            imageItem.setTitle("FootBall");
            imageItem.setTitle("Swimming");
            imageItem.setTitle("Cricket");
            imageItems.add(imageItem);
        }


        return imageItems;
    }


    public void addMoreMember() {
        count++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_more_row, null);
        memberfullName = inflatedLayout.findViewById(R.id.memberfullName);
        memberemail = inflatedLayout.findViewById(R.id.memberemail);
        membercontactno = inflatedLayout.findViewById(R.id.membercontactno);
        nooftemMember = inflatedLayout.findViewById(R.id.nooftemMember);
        nooftemMember.setText("Team Member\t" + count);
        container_moreadd.addView(inflatedLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreViewmember:
                addMoreMember();
                break;
            case R.id.acadmicViewinfoLayout:
                if ((numberclick % 2) == 0) {
                    sortiMG.setImageResource(R.drawable.ic_up_arrow);
                    acadmicinfoLayout.setVisibility(View.VISIBLE);
                } else {
                    sortiMG.setImageResource(R.drawable.ic_angle_arrow_down);
                    acadmicinfoLayout.setVisibility(View.GONE);
                }
                numberclick++;
                break;
            case R.id.academiesMemberLayout:
                if ((academiesMemberLayoutclick % 2) == 0) {
                    acadmiciMG.setImageResource(R.drawable.ic_up_arrow);
                    acadmicmemberinfoLayout.setVisibility(View.VISIBLE);
                } else {
                    acadmiciMG.setImageResource(R.drawable.ic_angle_arrow_down);
                    acadmicmemberinfoLayout.setVisibility(View.GONE);
                }
                academiesMemberLayoutclick++;
                break;
        }
    }

    private void getacademyFormData() {
        if (Utility.isOnline(getContext())) {
            ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.academyForm;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getacademyFormData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setSpinnerValue(serviceData);
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

    private void setSpinnerValue(ContentData serviceData) {
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
            if(serviceData.getSports()!=null) {
                sportsServiceGridViewAdapter = new SportsServiceGridViewAdapter(getContext(), R.layout.sports_row, serviceData.getSports());
                sportsgridView.setAdapter(sportsServiceGridViewAdapter);
                sportsgridView.setFocusable(true);
            }
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
            sitySpinner.setAdapter(citydapter);
            sitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public boolean isValidate() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        acdNameStr = acdName.getText().toString();
        accAddressStr = accAddress.getText().toString();
        zipcodeStr = zipcode.getText().toString();
        descriptionStr = description.getText().toString();
        managerfullNameStr = managerfullName.getText().toString();
        manageremailStr = manageremail.getText().toString();
        mamangercontactnoStr = mamangercontactno.getText().toString();
        coachfullNameStr = coachfullName.getText().toString();
        coachemailStr = coachemail.getText().toString();
        coachcontactnoStr = coachcontactno.getText().toString();
        OwnerfullNameStr = OwnerfullName.getText().toString();
        OwneremailStr = Owneremail.getText().toString();
        OwnercontactnoStr = Ownercontactno.getText().toString();

        if (acdNameStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Academic Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (accAddressStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Academic Address!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (zipcodeStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Zipcode!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (descriptionStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Academic Description!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (managerfullNameStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Manager Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (manageremailStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Manager Email Id!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!manageremailStr.matches(emailRegex)) {
            Toast.makeText(getContext(), "Please Enter valid Manager Email ID!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mamangercontactnoStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Manager Phone No!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (coachfullNameStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Coach Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (coachemailStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Coach Email Id!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!coachemailStr.matches(emailRegex)) {
            Toast.makeText(getContext(), "Please Enter valid Coach Email ID!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (OwnerfullNameStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Owner Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (OwneremailStr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Owner Email Id!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!OwneremailStr.matches(emailRegex)) {
            Toast.makeText(getContext(), "Please Enter valid Owner Email ID!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}
