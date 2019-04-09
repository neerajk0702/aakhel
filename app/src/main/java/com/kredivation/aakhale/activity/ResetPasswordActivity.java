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

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ASTEditText registerEmailEdt, newPassword, reapeatPassword;
    private ASTButton submitBtn;
    private String reapeatPasswordStr, newPasswordStr, emailStr;
    ;

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
                if (isvalidateForgot()) {
                    resetPassword();
                }
                break;

        }
    }

    public boolean isvalidateForgot() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        emailStr = registerEmailEdt.getText().toString();
        newPasswordStr = newPassword.getText().toString();
        reapeatPasswordStr = reapeatPassword.getText().toString();
        if (emailStr.equals("")) {
            Toast.makeText(ResetPasswordActivity.this, "Please Enter Register Email Id!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!emailStr.matches(emailRegex)) {
            Toast.makeText(ResetPasswordActivity.this, "Please Enter valid Email ID!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (newPasswordStr.length() == 0) {
            Toast.makeText(ResetPasswordActivity.this, "Please Enter new Password!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (reapeatPasswordStr.length() == 0) {
            Toast.makeText(ResetPasswordActivity.this, "Please Enter Repeat Password!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!newPasswordStr.equals(reapeatPasswordStr)) {
            Toast.makeText(ResetPasswordActivity.this, "Repeat Password does not Match with new Password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void resetPassword() {
        if (ASTUIUtil.isOnline(this)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(ResetPasswordActivity.this);
            dotDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            final String url = Contants.BASE_URL + Contants.ResetPassword;
            JSONObject object = new JSONObject();
            try {
                object.put("email", emailStr);
                object.put("password", newPasswordStr);
                object.put("Confirm_password", reapeatPasswordStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            serviceCaller.CallCommanServiceMethod(url, object, "resetPassword", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ContentData data = new Gson().fromJson(result, ContentData.class);
                        if (data != null) {
                            Toast.makeText(ResetPasswordActivity.this, data.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Password has not been reset successfully!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, Contants.Error, Toast.LENGTH_LONG).show();
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
