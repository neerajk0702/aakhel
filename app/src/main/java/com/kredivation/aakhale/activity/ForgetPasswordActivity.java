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

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ASTEditText registerEmailEdt, otpEditTxt;
    private ASTButton submitBtn;
    private ASTTextView valid5min;
    private SignInButton btn_gsign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        lodView();
    }

    public void lodView() {
        registerEmailEdt = findViewById(R.id.registerEmailEdt);
        otpEditTxt = findViewById(R.id.otpEditTxt);
        submitBtn = findViewById(R.id.submitBtn);
        btn_gsign_in = findViewById(R.id.btn_gsign_in);
        btn_gsign_in.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;

        }
    }
}
