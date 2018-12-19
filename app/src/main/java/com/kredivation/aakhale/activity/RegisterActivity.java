package com.kredivation.aakhale.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTEditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ASTEditText fullName, email, contactNo, dobEdt;
    ImageView dateIcon;
    Spinner gender, experience, sportsSpinner, roleSpinner;

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
        dateIcon = findViewById(R.id.dateIcon);


    }


    @Override
    public void onClick(View v) {

    }
}
