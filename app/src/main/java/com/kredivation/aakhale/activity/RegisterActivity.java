package com.kredivation.aakhale.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.database.AakhelDBHelper;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Player_roles;
import com.kredivation.aakhale.model.Sports;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ASTEditText fullName, email, contactNo, password, experience;
    ASTTextView dobEdt;
    ImageView dateIcon;
    Spinner gender, sportsSpinner, roleSpinner;
    DatePickerDialog todatepicker;
    Calendar myCalendar;
    ArrayList<Long> sportIdList;
    ArrayList<Long> roleIdList;
    Button registerBtn;
    String strfullName, stremail, strcontactNo, strdobEdt, strgender, strexperience,
            strsportsSpinner, strroleSpinner, strpassword, role;

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
        registerBtn = findViewById(R.id.registerBtn);
        setDate();
        registerBtn.setOnClickListener(this);
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
            case R.id.registerBtn:
                if (isvalidateSignup()) {
                    callSignup();
                }
                break;


        }
    }

    public boolean isvalidateSignup() {
        strfullName = fullName.getText().toString();
        stremail = email.getText().toString();
        strcontactNo = contactNo.getText().toString();
        strdobEdt = dobEdt.getText().toString();
        strgender = gender.getSelectedItem().toString();
        strexperience = experience.getText().toString();
        strsportsSpinner = sportsSpinner.getSelectedItem().toString();
        strroleSpinner = roleSpinner.getSelectedItem().toString();
        strpassword = password.getText().toString();

        if (strfullName.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Username", Toast.LENGTH_SHORT).show();
            return false;
        } else if (stremail.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strpassword.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strcontactNo.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Contact no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strdobEdt.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide DOB", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strgender.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strexperience.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Experience", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strsportsSpinner.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Sports ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strsportsSpinner.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Provide Role ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void callSignup() {
        if (ASTUIUtil.isOnline(this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(RegisterActivity.this);
            dotDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            final String url = Contants.BASE_URL + Contants.Registration;
            JSONObject object = new JSONObject();
            try {

                object.put("role", "Player");
                object.put("full_name", strfullName);
                object.put("email", stremail);
                object.put("mobile", strcontactNo);
                object.put("password", strpassword);
                object.put("date_of_birth", strdobEdt);
                object.put("gender", strgender);
                object.put("experience", strexperience);
                object.put("users_sports", strsportsSpinner);
                object.put("player_role", strroleSpinner);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            serviceCaller.CallCommanServiceMethod(url, object, "Signup", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ContentData data = new Gson().fromJson(result, ContentData.class);
                        if (data != null) {
                            //   if (data.isStatus()) {
                            ASTUIUtil.setUserId(RegisterActivity.this, stremail, strpassword, null, null);
                            Toast.makeText(RegisterActivity.this, "Signup Successfully.", Toast.LENGTH_LONG).show();
                            Intent intentLoggedIn = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intentLoggedIn);
                            // } else {
                            Toast.makeText(RegisterActivity.this, "Signup not Successfully!", Toast.LENGTH_LONG).show();
                            // }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Signup not Successfully!", Toast.LENGTH_LONG).show();
                        }
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
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
