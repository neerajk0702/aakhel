package com.kredivation.aakhale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.SignInButton;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ASTEditText registerEmailEdt, password;
    private ASTButton loginText;
    private ASTTextView forgotPassword;
    private SignInButton btn_gsign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lodView();
    }

    public void lodView() {
        registerEmailEdt = findViewById(R.id.registerEmailEdt);
        password = findViewById(R.id.password);
        loginText = findViewById(R.id.loginText);
        forgotPassword = findViewById(R.id.forgotPassword);
        btn_gsign_in = findViewById(R.id.btn_gsign_in);

        forgotPassword.setOnClickListener(this);
        btn_gsign_in.setOnClickListener(this);
        loginText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginText:
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                break;
            case R.id.forgotPassword:
                Intent intent1 = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
        }
    }
}


