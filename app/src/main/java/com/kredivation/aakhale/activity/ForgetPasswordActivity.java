package com.kredivation.aakhale.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ASTEditText registerEmailEdt, otpEditTxt;
    private ASTButton submitBtn;
    private ASTTextView resend;
    private SignInButton btn_gsign_in;
    String emailStr;
    long token = 0;
    private boolean verifyOtp = false;

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
        resend = findViewById(R.id.resend);
        resend.setOnClickListener(this);
        submitBtn.setText("Send OTP");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                if (verifyOtp) {
                    String otpStr = otpEditTxt.getText().toString();
                    if (otpStr.length() == 0) {
                        Toast.makeText(ForgetPasswordActivity.this, "Please Enter OTP!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (otpStr.equals(String.valueOf(token))) {
                            Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "OTP did not Match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (isvalidateForgot()) {
                        sendOtp();
                    }
                }
                break;
            case R.id.resend:
                if (isvalidateForgot()) {
                    sendOtp();
                }
                break;
        }
    }

    public boolean isvalidateForgot() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        emailStr = registerEmailEdt.getText().toString();
        if (emailStr.equals("")) {
            Toast.makeText(ForgetPasswordActivity.this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!emailStr.matches(emailRegex)) {
            Toast.makeText(ForgetPasswordActivity.this, "Please Enter valid Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void sendOtp() {
        if (ASTUIUtil.isOnline(this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(ForgetPasswordActivity.this);
            dotDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            final String url = Contants.BASE_URL + Contants.forgotPassword;
            JSONObject object = new JSONObject();
            try {
                object.put("email", emailStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            serviceCaller.CallCommanServiceMethod(url, object, "sendOtp", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ContentData data = new Gson().fromJson(result, ContentData.class);
                        if (data != null) {
                            Toast.makeText(ForgetPasswordActivity.this, data.getMessage(), Toast.LENGTH_LONG).show();
                            token = data.getToken();
                            verifyOtp = true;
                            submitBtn.setText("Verify OTP");
                            otpEditTxt.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "OTP not send Successfully!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, Contants.Error, Toast.LENGTH_LONG).show();
                    }
                    if (dotDialog.isShowing()) {
                        dotDialog.dismiss();
                    }
                }
            });
        }

    }

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
