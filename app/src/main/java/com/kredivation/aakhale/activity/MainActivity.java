package com.kredivation.aakhale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AreaAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ASTTextView signup, login;
    private ASTButton continuebtn;
    RecyclerView araelist;
    ArrayList<String> areaList;
    AreaAdapter areaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadview();
    }

    public void loadview() {
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        continuebtn = findViewById(R.id.continuebtn);

        araelist = this.findViewById(R.id.araelist);
        araelist.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        datatoView();

    }

    public void datatoView() {
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        continuebtn.setOnClickListener(this);
        areaList = new ArrayList<>();
        areaList.add("Player");
        areaList.add("RWA");
        areaList.add("Academy");
        areaList.add("Player");
        areaList.add("RWA");
        areaList.add("Player");
        areaList.add("RWA");
        areaList.add("Academy");
        areaList.add("Player");
        areaList.add("RWA");
        areaList.add("Player");
        areaList.add("RWA");
        areaList.add("Academy");
        areaList.add("Player");
        areaList.add("RWA");

        areaAdapter = new AreaAdapter(MainActivity.this, areaList);
        araelist.setAdapter(areaAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
