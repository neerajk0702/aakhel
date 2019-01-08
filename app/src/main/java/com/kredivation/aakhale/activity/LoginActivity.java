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
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ASTEditText registerEmailEdt, etpassword;
    private ASTButton loginText;
    private ASTTextView forgotPassword;
    private SignInButton btn_gsign_in;
    ASTProgressBar dotDialog;
    SharedPreferences pref;
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
        btn_gsign_in = findViewById(R.id.btn_gsign_in);

        forgotPassword.setOnClickListener(this);
        btn_gsign_in.setOnClickListener(this);
        loginText.setOnClickListener(this);
        pref = getApplicationContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        userId = pref.getString("USER_ID", "");
        if (!userId.equals("")) {
            Intent intentHome = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intentHome);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginText:
                String userName = registerEmailEdt.getText().toString();
                String password = etpassword.getText().toString();
                if (userName.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Provide Username", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Provide Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                  //  loginData(userName, password);
                }


                break;
            case R.id.forgotPassword:
                Intent intent1 = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent1);
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
                e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(LoginActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "LoginData", new IAsyncWorkCompletedCallback() {
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
                pref = getSharedPreferences("SharedPref", MODE_PRIVATE);
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("1")) {
                    JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                    // String userName = jsonObject.optString("UserName").toString();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("USER_NAME", "");
                    editor.putString("USER_ID", userId);
                    editor.putString("EMP_NAME", "");
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Provide Correct Credentials", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }
}


