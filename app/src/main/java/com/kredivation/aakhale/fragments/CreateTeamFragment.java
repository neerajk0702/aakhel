package com.kredivation.aakhale.fragments;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.GridViewAdapter;
import com.kredivation.aakhale.adapter.SportsServiceGridViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.dialog.SearchDialog;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment implements View.OnClickListener {
    View view;
    ASTEditText teameName, aboutTeam, zipCode;
    Spinner stateSpinner, citySpinner;
    TextView addMoreView, addMoreViewImage, addplayelbl;
    ListView AddPlayerLIst;
    ASTButton continuebtn;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private ArrayList<String> stateIdList;
    private ArrayList<String> cityIdList;
    private String stateId, cityId;
    List<City> cityInfoList;

    public CreateTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);
        init();
        getActivity().setTitle("Create Team");
        return view;
    }

    public void init() {
        teameName = view.findViewById(R.id.teameName);
        aboutTeam = view.findViewById(R.id.aboutTeam);
        zipCode = view.findViewById(R.id.zipCode);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        addMoreView = view.findViewById(R.id.addMoreView);
        addMoreViewImage = view.findViewById(R.id.addMoreViewImage);
        AddPlayerLIst = view.findViewById(R.id.AddPlayerLIst);
        continuebtn = view.findViewById(R.id.continuebtn);
        addplayelbl = view.findViewById(R.id.addplayelbl);
        addMoreView.setOnClickListener(this);

        gridView = view.findViewById(R.id.gridView);


        String s = "Add Player(Enter Unique Id)";
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1f), 0, 10, 0); // set size
        //   ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        addplayelbl.setText(ss1);
        getTeamFormData();
    }


    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            //.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }


    SearchDialog fnNewDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreView:
                fnNewDialog = new SearchDialog(getContext()) {
                    @Override
                    public void actionPerform() {

                    }
                };

                fnNewDialog.show();

                break;

            case R.id.addMoreViewImage:
                break;
        }
    }


    private void getTeamFormData() {
        if (Utility.isOnline(getContext())) {
            ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.teamForm;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getTeamFormData", new IAsyncWorkCompletedCallback() {
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
            if (serviceData.getSports() != null) {
                gridAdapter = new GridViewAdapter(getContext(), R.layout.image_item_layout, getData());
                gridView.setAdapter(gridAdapter);
                gridView.setFocusable(true);
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
}
