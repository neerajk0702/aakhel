package com.kredivation.aakhale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.UserTypeAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.database.AakhelDBHelper;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Users_roles;

import java.util.ArrayList;

public class SelectUserTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private ASTTextView signup, login;
    private ASTButton continuebtn;
    RecyclerView araelist;
    ArrayList<Users_roles> areaList;
    UserTypeAdapter areaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_user_type);
        loadview();
    }

    public void loadview() {
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        continuebtn = findViewById(R.id.continuebtn);

        araelist = this.findViewById(R.id.araelist);
        araelist.setLayoutManager(new LinearLayoutManager(SelectUserTypeActivity.this));
        datatoView();

    }

    public void datatoView() {
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        continuebtn.setOnClickListener(this);
        areaList = new ArrayList<>();
        AakhelDBHelper switchDBHelper = new AakhelDBHelper(SelectUserTypeActivity.this);
        ContentData contentData = switchDBHelper.getMasterDataById(1);
        if (contentData != null && contentData.getData() != null && contentData.getData().getUsers_roles() != null) {
            areaList = contentData.getData().getUsers_roles();
            areaAdapter = new UserTypeAdapter(SelectUserTypeActivity.this, areaList);
            araelist.setAdapter(areaAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                Intent intent = new Intent(SelectUserTypeActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                Intent intent1 = new Intent(SelectUserTypeActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
