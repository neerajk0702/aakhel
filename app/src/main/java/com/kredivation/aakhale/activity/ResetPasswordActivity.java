package com.kredivation.aakhale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.SignInButton;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTTextView;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ASTEditText registerEmailEdt, newPassword, reapeatPassword;
    private ASTButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        lodView();
    }

    public void lodView() {
        registerEmailEdt = findViewById(R.id.registerEmailEdt);
        newPassword = findViewById(R.id.newPassword);
        submitBtn = findViewById(R.id.submitBtn);
        reapeatPassword = findViewById(R.id.reapeatPassword);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

        }
    }
}
