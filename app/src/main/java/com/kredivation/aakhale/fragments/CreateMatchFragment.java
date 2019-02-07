package com.kredivation.aakhale.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.DashboardActivity;
import com.kredivation.aakhale.activity.ForgetPasswordActivity;
import com.kredivation.aakhale.activity.LoginActivity;
import com.kredivation.aakhale.activity.RegisterActivity;
import com.kredivation.aakhale.activity.TeamListActivity;
import com.kredivation.aakhale.activity.UmpireListActivity;
import com.kredivation.aakhale.adapter.GridViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.AddViewDynamically;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.Ground;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMatchFragment extends Fragment implements View.OnClickListener {
    View view;
    ASTEditText matchName, venueAddress, zipCode, timeTxt, overtxt, formate, dateTxt;
    ImageView timeImage, dateIcon;
    Spinner stateSpinner, citySpinner, groundSpinner, matchtypeSpinner;
    ASTButton canclebtn, continuebtn;
    private ArrayList<String> stateIdList;
    private ArrayList<String> groundList;
    private ArrayList<String> cityIdList;
    private String stateId, cityId, groundId;
    List<City> cityInfoList;
    DatePickerDialog todatepicker;
    Calendar myCalendar;
    String name, match_address, match_state, match_city, match_zipcode, match_type, format, tournament_id, over, ground_id, time, date;
    TextView addTeamView, addUmpireView;
    LinearLayout addTeamLayout, addUmpireLayout;
    String selectTeamId, selectUmpireId;

    public CreateMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_match, container, false);
        getActivity().setTitle("Create Match");
        init();
        return view;
    }


    public void init() {
        matchName = view.findViewById(R.id.matchName);
        venueAddress = view.findViewById(R.id.venueAddress);
        zipCode = view.findViewById(R.id.zipCode);
        timeTxt = view.findViewById(R.id.timeTxt);
        dateTxt = view.findViewById(R.id.dateTxt);
        overtxt = view.findViewById(R.id.overtxt);
        formate = view.findViewById(R.id.formate);
        timeImage = view.findViewById(R.id.timeImage);
        dateIcon = view.findViewById(R.id.dateIcon);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        groundSpinner = view.findViewById(R.id.groundSpinner);
        matchtypeSpinner = view.findViewById(R.id.matchtype);
        canclebtn = view.findViewById(R.id.canclebtn);
        continuebtn = view.findViewById(R.id.continuebtn);
        getMatchFormData();
        setDate();
        dateIcon.setOnClickListener(this);
        timeImage.setOnClickListener(this);
        continuebtn.setOnClickListener(this);

        addTeamView = view.findViewById(R.id.addTeamView);
        addUmpireView = view.findViewById(R.id.addUmpireView);
        addTeamLayout = view.findViewById(R.id.addTeamLayout);
        addUmpireLayout = view.findViewById(R.id.addUmpireLayout);
        addUmpireView.setOnClickListener(this);
        addTeamView.setOnClickListener(this);

    }


    private void getMatchFormData() {
        if (Utility.isOnline(getContext())) {
            ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.matchFormApi;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getMatchForm", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setStateSpinnerValue(serviceData);
                                setGroundpinnerValue(serviceData);
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

    private void setGroundpinnerValue(ContentData serviceData) {
        if (serviceData.getGround() != null) {
            ArrayList<String> groundListt = new ArrayList<>();
            groundList = new ArrayList<String>();
            groundListt.add("Select Ground");
            groundList.add("0");
            for (Ground ground : serviceData.getGround()) {
                groundListt.add(ground.getName());
                groundList.add(ground.getId());
            }
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, groundListt);
            groundSpinner.setAdapter(statedapter);

            groundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    groundId = groundList.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }


    private void setDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateTxt.setText(sdf.format(myCalendar.getTime()));
            }
        };
        todatepicker = new DatePickerDialog(getContext(), todate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateIcon:
                todatepicker.show();
                break;
            case R.id.timeImage:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTxt.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.continuebtn:
                if (isvalidateSignup()) {
                    saveCreateMatchData();
                }
                break;
            case R.id.addTeamView:
                Intent intent1 = new Intent(getContext(), TeamListActivity.class);
                intent1.putExtra("CreateMatch", true);
                startActivityForResult(intent1, Contants.REQ_PAGE_COMMUNICATOR);
                break;
            case R.id.addUmpireView:
                Intent umintent = new Intent(getContext(), UmpireListActivity.class);
                umintent.putExtra("CreateMatch", true);
                startActivityForResult(umintent, Contants.REQ_PAGE_COMMUNICATOR);
                break;
        }
    }

    private void saveCreateMatchData() {

        String matchtype = "0";
        if (match_type.equals("Test")) {
            matchtype = "1";
        } else if (match_type.equals("ODI")) {
            matchtype = "2";
        } else if (match_type.equals("T-20")) {
            matchtype = "3";
        }

        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            final String url = Contants.BASE_URL + Contants.creatematchApi;
            JSONObject object = new JSONObject();
            try {
                object.put("name", name);
                object.put("match_address", match_address);
                object.put("match_state", stateId);
                object.put("match_city", cityId);
                object.put("match_zipcode", match_zipcode);
                object.put("ground_id", groundId);
                object.put("match_type", matchtype);
                object.put("format", format);
                object.put("tournament_id", tournament_id);
                object.put("match_team", selectTeamId);
                object.put("match_umpire", selectUmpireId);
                object.put("over", over);
                object.put("time", time);
                object.put("date", date);


            } catch (JSONException e) {
                //e.printStackTrace();
            }
            serviceCaller.CallCommanServiceMethod(url, object, "saveCreateMatchData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseCrateTeameData(result);
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
        name = matchName.getText().toString();
        match_address = venueAddress.getText().toString();
        match_state = stateSpinner.getSelectedItem().toString();
        match_city = citySpinner.getSelectedItem().toString();
        match_zipcode = zipCode.getText().toString();
        match_type = matchtypeSpinner.getSelectedItem().toString();
        format = formate.getText().toString();
        ground_id = groundSpinner.getSelectedItem().toString();
        tournament_id = "";
        over = overtxt.getText().toString();
        time = timeTxt.getText().toString();
        date = dateTxt.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getContext(), "Please Enter Match Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (match_address.equals("")) {
            Toast.makeText(getContext(), "Please Enter Match Venue", Toast.LENGTH_SHORT).show();
            return false;
        } else if (match_state.equals("") || match_state.equalsIgnoreCase("Select State")) {
            Toast.makeText(getContext(), "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        } else if (match_city.equals("") || match_city.equalsIgnoreCase("Select City")) {
            Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_SHORT).show();
            return false;
        } else if (match_zipcode.equals("")) {
            Toast.makeText(getContext(), "Please Enter Zip Code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ground_id.equals("") || ground_id.equalsIgnoreCase("Select Ground")) {
            Toast.makeText(getContext(), "Please Select Ground", Toast.LENGTH_SHORT).show();
            return false;
        } else if (match_type.equals("")) {
            Toast.makeText(getContext(), "Please Select Match Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (over.equals("")) {
            Toast.makeText(getContext(), "Please Enter Over", Toast.LENGTH_SHORT).show();
            return false;
        } else if (format.equals("")) {
            Toast.makeText(getContext(), "Please Enter Match Formate", Toast.LENGTH_SHORT).show();
            return false;
        } else if (time.equals("")) {
            Toast.makeText(getContext(), "Please Select Match Time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (date.equals("")) {
            Toast.makeText(getContext(), "Please Select Match Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    /*
     *
     * Parse and Validate Teame Service Data
     */
    private void parseCrateTeameData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                boolean jsonStatus = jsonRootObject.optBoolean("status");
                String message = jsonRootObject.optString("message");
                if (jsonStatus) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String selectedTeam = data.getExtras().getString("selectedTeam");
            if (selectedTeam != null && !selectedTeam.equals("")) {
                selectTeamId = data.getExtras().getString("selectTeamId");
                ArrayList<Team> teamList = new Gson().fromJson(selectedTeam, new TypeToken<ArrayList<Team>>() {
                }.getType());
                for (Team team : teamList) {
                    showSelectedTeam(team);
                }
            }
            String selectedUmpire = data.getExtras().getString("selectedUmpire");
            if (selectedUmpire != null && !selectedUmpire.equals("")) {
                selectUmpireId = data.getExtras().getString("selectUmpireId");
                ArrayList<Data> teamList = new Gson().fromJson(selectedUmpire, new TypeToken<ArrayList<Data>>() {
                }.getType());
                for (Data umpire : teamList) {
                    showSelectedUmpire(umpire);
                }
            }
        }
    }

    public void showSelectedTeam(Team team) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.create_match_team_layout, null);
        ImageView imageView = inflatedLayout.findViewById(R.id.imageView);
        TextView name = inflatedLayout.findViewById(R.id.name);
        name.setText(team.getName());
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        userId.setText(team.getUnique_id());
        Picasso.with(getContext()).load(Contants.BASE_URL + team.getTeam_thumbnail()).placeholder(R.drawable.ic_uuuser).into(imageView);
        addTeamLayout.addView(inflatedLayout);
    }

    public void showSelectedUmpire(Data data) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.create_match_team_layout, null);
        ImageView imageView = inflatedLayout.findViewById(R.id.imageView);
        TextView name = inflatedLayout.findViewById(R.id.name);
        name.setText(data.getFull_name());
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        userId.setText(data.getUnique_id());
        Picasso.with(getContext()).load(Contants.BASE_URL + data.getProfile_picture()).placeholder(R.drawable.ic_uuuser).into(imageView);
        addUmpireLayout.addView(inflatedLayout);
    }
}
