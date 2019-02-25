package com.kredivation.aakhale.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.database.AakhelDBHelper;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ASTEditText registerEmailEdt, etpassword;
    private ASTButton loginText;
    private ASTTextView forgotPassword, signup;
    private SignInButton btn_gsign_in;
    ASTProgressBar dotDialog;
    SharedPreferences UserInfo;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lodView();
        dotDialog = new ASTProgressBar(LoginActivity.this);
    }

    public void lodView() {
        registerEmailEdt = findViewById(R.id.registerEmailEdt);
        etpassword = findViewById(R.id.password);
        loginText = findViewById(R.id.loginText);
        forgotPassword = findViewById(R.id.forgotPassword);
        signup = findViewById(R.id.signup);
        btn_gsign_in = findViewById(R.id.btn_gsign_in);
        signup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        btn_gsign_in.setOnClickListener(this);
        loginText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginText:
                String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
                String userName = registerEmailEdt.getText().toString();
                String password = etpassword.getText().toString();
                if (userName.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
                } else if (!userName.matches(emailRegex)) {
                    Toast.makeText(LoginActivity.this, "Please Enter valid Email ID", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    loginData(userName, password);
                }


                break;
            case R.id.forgotPassword:
                Intent intent1 = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.signup:
                Intent intentsignup = new Intent(LoginActivity.this, SelectUserTypeActivity.class);
                startActivity(intentsignup);
                break;
        }
    }


    private void loginData(String userName, String password) {
        if (Utility.isOnline(LoginActivity.this)) {
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.LOGIN_URL;
            JSONObject object = new JSONObject();
            try {
                object.put("email", userName);
                object.put("password", password);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(LoginActivity.this);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "LoginData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseLoginServiceData(result);
                    } else {
                        if (dotDialog.isShowing()) {
                            dotDialog.dismiss();
                        }
                        Utility.alertForErrorMessage(Contants.Error, LoginActivity.this);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, LoginActivity.this);//off line msg....
        }
    }


    /*
     *
     * Parse and Validate Login Service Data
     */
    private void parseLoginServiceData(String result) {
        if (result != null) {
            try {
                UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
                ContentData contentData = new Gson().fromJson(result, ContentData.class);
                if (contentData.isStatus()) {
                    Data data = contentData.getData();
                    SharedPreferences.Editor editor = UserInfo.edit();
                    editor.putLong("id", data.getId());
                    editor.putString("unique_id", data.getUnique_id());
                    editor.putString("email", data.getEmail());
                    editor.putInt("role", data.getRole());
                    editor.putInt("is_active", data.getIs_active());
                    editor.putString("auth_token", data.getAuth_token());
                    editor.putString("full_name", data.getFull_name());
                    editor.putString("mobile", data.getMobile());
                    editor.putString("users_sports", String.valueOf(data.getUsers_sports()));
                    editor.putString("profile_picture", data.getProfile_picture());
                    editor.commit();
                    Contants.auth_token = data.getAuth_token();
                    Toast.makeText(this, contentData.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Provide Correct Credentials", Toast.LENGTH_SHORT).show();
                }
                if (dotDialog.isShowing()) {
                    dotDialog.dismiss();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }
}


