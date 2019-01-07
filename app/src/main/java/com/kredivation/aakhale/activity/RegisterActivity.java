package com.kredivation.aakhale.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.database.AakhelDBHelper;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Player_roles;
import com.kredivation.aakhale.model.Sports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ASTEditText fullName, email, contactNo,password,experience;
    ASTTextView dobEdt;
    ImageView dateIcon;
    Spinner gender, sportsSpinner, roleSpinner;
    DatePickerDialog todatepicker;
    Calendar myCalendar;
    ArrayList<Long> sportIdList;
    ArrayList<Long> roleIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        lodView();
    }

    public void lodView() {
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        contactNo = findViewById(R.id.contactNo);
        dobEdt = findViewById(R.id.dobEdt);
        gender = findViewById(R.id.gender);
        experience = findViewById(R.id.experience);
        sportsSpinner = findViewById(R.id.sportsSpinner);
        roleSpinner = findViewById(R.id.roleSpinner);
        password = findViewById(R.id.password);
        dateIcon = findViewById(R.id.dateIcon);
        dateIcon.setOnClickListener(this);
        setDate();

        AakhelDBHelper switchDBHelper = new AakhelDBHelper(RegisterActivity.this);
        ContentData contentData = switchDBHelper.getMasterDataById(1);
        if (contentData != null && contentData.getData() != null) {
            ArrayList<String> sportList = new ArrayList<>();
            sportIdList = new ArrayList<>();
            sportList.add("SELECT SPORTS");
            sportIdList.add((long) 0);
            if (contentData.getData().getSports() != null) {
                for (Sports sports : contentData.getData().getSports()) {
                    sportList.add(sports.getSports_name());
                    sportIdList.add(sports.getId());
                }
            }
            ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_row, sportList);
            sportsSpinner.setAdapter(homeadapter);

            ArrayList<String> roleList = new ArrayList<>();
            roleIdList = new ArrayList<>();
            roleList.add("SELECT ROLE");
            roleIdList.add((long) 0);
            if (contentData.getData().getPlayer_roles() != null) {
                for (Player_roles roles : contentData.getData().getPlayer_roles()) {
                    roleList.add(roles.getRole_name());
                    roleIdList.add(roles.getId());
                }
            }
            ArrayAdapter<String> roleadapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_row, roleList);
            roleSpinner.setAdapter(roleadapter);
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
                dobEdt.setText(sdf.format(myCalendar.getTime()));
            }
        };
        todatepicker = new DatePickerDialog(RegisterActivity.this, todate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateIcon:
                todatepicker.show();
                break;
        }
    }
}
