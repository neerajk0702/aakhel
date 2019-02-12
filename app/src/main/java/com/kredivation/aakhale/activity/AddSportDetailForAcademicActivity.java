package com.kredivation.aakhale.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.utility.FontManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddSportDetailForAcademicActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Button save;
    ASTEditText fee, time, exp, age, name;
    String feeStr, timeStr, expStr, ageStr, nameStr;
    Spinner gender;
    String strgender;
    int genId;
    long sportId;
    String SportName;
    boolean SporSelected;
    boolean saveFlag=false;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sport_detail_for_academic);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    public void init() {
        Typeface materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/materialdesignicons-webfont.otf");
        TextView back = toolbar.findViewById(R.id.back);
        back.setTypeface(materialdesignicons_font);
        back.setText(Html.fromHtml("&#xf30d;"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sportId = getIntent().getLongExtra("Id", 0);
        SportName = getIntent().getStringExtra("SportName");
        SporSelected = getIntent().getBooleanExtra("SporSelected", false);
        position = getIntent().getIntExtra("position", 0);
        gender = findViewById(R.id.gender);
        save = findViewById(R.id.save);
        fee = findViewById(R.id.fee);
        time = findViewById(R.id.time);
        exp = findViewById(R.id.exp);
        age = findViewById(R.id.age);
        name = findViewById(R.id.name);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    saveFlag=true;
                    onBackPressed();
                }
            }
        });
    }

    public boolean isValidate() {
        feeStr = fee.getText().toString();
        timeStr = time.getText().toString();
        expStr = exp.getText().toString();
        ageStr = age.getText().toString();
        nameStr = name.getText().toString();
        strgender = gender.getSelectedItem().toString();
        genId = 0;
        if (strgender.equals("Male")) {
            genId = 1;
        } else if (strgender.equals("Female")) {
            genId = 2;
        }
        if (nameStr.equals("")) {
            Toast.makeText(AddSportDetailForAcademicActivity.this, "Please Enter Trainer Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ageStr.equals("")) {
            Toast.makeText(AddSportDetailForAcademicActivity.this, "Please Enter Age!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (expStr.equals("")) {
            Toast.makeText(AddSportDetailForAcademicActivity.this, "Please Enter Experience!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (timeStr.equals("")) {
            Toast.makeText(AddSportDetailForAcademicActivity.this, "Please Enter Time!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (feeStr.equals("")) {
            Toast.makeText(AddSportDetailForAcademicActivity.this, "Please Enter Fee!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ageStr.equals("")) {
            Toast.makeText(AddSportDetailForAcademicActivity.this, "Please Select Establishment Date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        JSONObject mainObj = new JSONObject();
        JSONObject sportsData = new JSONObject();
        try {
            sportsData.put("name", SportName);
            sportsData.put("id", sportId);
            mainObj.put("sports", sportsData);
            mainObj.put("timing", timeStr);
            JSONObject trainerData = new JSONObject();
            trainerData.put("name", nameStr);
            trainerData.put("gender", genId);
            trainerData.put("age", ageStr);
            trainerData.put("experience", expStr);
            mainObj.put("trainer", trainerData);
            mainObj.put("fees", feeStr);
        } catch (JSONException e) {

        }
        Intent output = new Intent();
        output.putExtra("SportServiceDetail", mainObj.toString());
        output.putExtra("position", position);
        output.putExtra("saveFlag", saveFlag);
        setResult(RESULT_OK, output);
        finish();
    }
}
