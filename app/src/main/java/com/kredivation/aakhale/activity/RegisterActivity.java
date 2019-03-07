package com.kredivation.aakhale.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.database.AakhelDBHelper;
import com.kredivation.aakhale.framework.FileUploaderHelperWithProgress;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Player_roles;
import com.kredivation.aakhale.model.Sports;
import com.kredivation.aakhale.runtimepermission.PermissionResultCallback;
import com.kredivation.aakhale.runtimepermission.PermissionUtils;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {
    ASTEditText fullName, email, contactNo, password, experience;
    ASTTextView dobEdt, selectSports,login;
    ImageView dateIcon;
    Spinner gender, sportsSpinner, roleSpinner;
    DatePickerDialog todatepicker;
    Calendar myCalendar;
    ArrayList<Long> sportIdList;
    ArrayList<String> sportList;
    ArrayList<Long> roleIdList;
    ArrayList<Sports> selectedSport;
    Button registerBtn;
    String strfullName, stremail, strcontactNo, strdobEdt, strgender, strexperience, strpassword, role, feepermatchStr;
    long selectRoleId;
    private long areaId = 0;
    ASTEditText feepermatch;
    ImageView profileImg;
    public final int SELECT_PHOTO = 102;
    public final int REQUEST_CAMERA = 101;
    private String userChoosenTask;
    File imgFile;
    private LinearLayout roleLayout, sportLayout, genderLayout, yearofexpLayout, doblayouut, genderMainLayout;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private int REQUEST_CODE_GPS_PERMISSIONS = 2;
    protected ArrayList<CharSequence> selectedSportItem;
    String[] sportArry;
    String sportIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        lodView();
        runTimePermission();
    }

    public void lodView() {
        areaId = getIntent().getLongExtra("AreaId", 0);
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        contactNo = findViewById(R.id.contactNo);
        dobEdt = findViewById(R.id.dobEdt);
        gender = findViewById(R.id.gender);
        experience = findViewById(R.id.experience);
        sportsSpinner = findViewById(R.id.sportsSpinner);
        roleSpinner = findViewById(R.id.roleSpinner);
        password = findViewById(R.id.password);
        dateIcon = findViewById(R.id.dateIcon);
        profileImg = findViewById(R.id.profileImg);
        profileImg.setOnClickListener(this);
        roleLayout = findViewById(R.id.roleLayout);
        sportLayout = findViewById(R.id.sportLayout);
        genderLayout = findViewById(R.id.genderLayout);
        yearofexpLayout = findViewById(R.id.yearofexpLayout);
        doblayouut = findViewById(R.id.doblayouut);
        genderMainLayout = findViewById(R.id.genderMainLayout);
        selectSports = findViewById(R.id.selectSports);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        feepermatch = findViewById(R.id.feepermatch);
        if (areaId == 1) {//Player
            roleLayout.setVisibility(View.VISIBLE);
            genderLayout.setVisibility(View.VISIBLE);
            yearofexpLayout.setVisibility(View.VISIBLE);
            doblayouut.setVisibility(View.VISIBLE);
            dobEdt.setText("DATE OF BIRTH");
        }
        if (areaId == 2 || areaId == 3) {//coach and Umpire
            feepermatch.setVisibility(View.VISIBLE);
            genderLayout.setVisibility(View.VISIBLE);
            yearofexpLayout.setVisibility(View.VISIBLE);
            doblayouut.setVisibility(View.VISIBLE);
            dobEdt.setText("DATE OF BIRTH");
        }
        if (areaId == 6 || areaId == 7 || areaId == 8 || areaId == 9 || areaId == 10) {
            doblayouut.setVisibility(View.VISIBLE);
            dobEdt.setText("Establishment Date");
            genderMainLayout.setVisibility(View.GONE);
        } else if (areaId == 4 || areaId == 5) {//Academy ground
            doblayouut.setVisibility(View.GONE);
            genderMainLayout.setVisibility(View.GONE);
        }


        dateIcon.setOnClickListener(this);
        registerBtn = findViewById(R.id.registerBtn);
        setDate();
        registerBtn.setOnClickListener(this);
        AakhelDBHelper switchDBHelper = new AakhelDBHelper(RegisterActivity.this);
        ContentData contentData = switchDBHelper.getMasterDataById(1);
        if (contentData != null && contentData.getData() != null) {
            selectedSport = new ArrayList<>();
            sportList = new ArrayList<>();
            sportIdList = new ArrayList<>();
            // sportList.add("SELECT SPORTS");
            //  sportIdList.add((long) 0);
            if (contentData.getData().getSports() != null) {
                sportArry = new String[contentData.getData().getSports().size()];
                for (Sports sports : contentData.getData().getSports()) {
                    sportList.add(sports.getSports_name());
                    sportIdList.add(sports.getId());
                }
                sportArry = sportList.toArray(sportArry);
            }
            selectedSportItem = new ArrayList<CharSequence>();
            selectSports.setOnClickListener(this);
           /* ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_row, sportList);
            sportsSpinner.setAdapter(homeadapter);
            sportsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    selectSportId = sportIdList.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/

            ArrayList<String> roleList = new ArrayList<>();
            roleIdList = new ArrayList<>();
            roleList.add("SELECT ROLE");
            roleIdList.add((long) 0);
            if (contentData.getData().getPlayer_roles() != null) {
                for (Player_roles roles : contentData.getData().getPlayer_roles()) {
                    roleList.add(roles.getRole_name());
                    roleIdList.add(roles.getId());
                }
            }
            ArrayAdapter<String> roleadapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_row, roleList);
            roleSpinner.setAdapter(roleadapter);

            roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    selectRoleId = roleIdList.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void setDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dobEdt.setText(sdf.format(myCalendar.getTime()));
            }
        };
        todatepicker = new DatePickerDialog(RegisterActivity.this, todate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateIcon:
                todatepicker.show();
                break;
            case R.id.registerBtn:
                if (isvalidateSignup()) {
                    callSignup();
                }
                break;
            case R.id.profileImg:
                selectImage();
                break;
            case R.id.selectSports:
                showSelectRCASubDialog();
                break;
            case R.id.login:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }

    protected void showSelectRCASubDialog() {
        if (sportList != null && sportList.size() > 0) {
            boolean[] checkedItems = new boolean[sportList.size()];
            for (int i = 0; i < sportList.size(); i++)
                checkedItems[i] = selectedSportItem.contains(sportList.get(i).toString());
            DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        selectedSportItem.add(sportList.get(which).toString());

                        Sports sports = new Sports();
                        sports.setSports_name(sportList.get(which).toString());
                        sports.setId(sportIdList.get(which));
                        selectedSport.add(sports);
                    } else {
                        removeselectvalue(sportIdList.get(which), sportList.get(which));
                    }
                    onChangeSelectedItem();
                }

            };

            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("Select Sports");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setMultiChoiceItems(sportArry, checkedItems, coloursDialogListener);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    //remove selected value
    private void removeselectvalue(long id, String sportStr) {
        for (int i = 0; i < selectedSport.size(); i++) {
            if (selectedSport.get(i).getId() == id) {
                selectedSport.remove(i);
                break;
            }
        }
        for (int i = 0; i < selectedSportItem.size(); i++) {
            if (selectedSportItem.get(i).equals(sportStr)) {
                selectedSportItem.remove(i);
                break;
            }
        }
    }

    //on select or deselect sub rca
    protected void onChangeSelectedItem() {
        StringBuilder stringBuilder = new StringBuilder();
        String separatorComm = ",";
        for (CharSequence selectitem : selectedSportItem) {
            stringBuilder.append(selectitem);
            stringBuilder.append(separatorComm);
        }
        selectSports.setText(stringBuilder.toString());

        StringBuilder stringBuildersubRca = new StringBuilder();
        for (int i = 0; i < selectedSport.size(); i++) {
            stringBuildersubRca.append(String.valueOf(selectedSport.get(i).getId()));
            stringBuildersubRca.append(separatorComm);
        }
        sportIdStr = stringBuildersubRca.toString();
        if (sportIdStr != null && !sportIdStr.equals("")) {
            sportIdStr = sportIdStr.substring(0, sportIdStr.length() - separatorComm.length());
        }
    }

    public boolean isvalidateSignup() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        strfullName = fullName.getText().toString();
        stremail = email.getText().toString();
        strcontactNo = contactNo.getText().toString();
        strdobEdt = dobEdt.getText().toString();
        strgender = gender.getSelectedItem().toString();
        strexperience = experience.getText().toString();
        strpassword = password.getText().toString();
        feepermatchStr = feepermatch.getText().toString();

        if (strfullName.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Enter Full Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (stremail.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!stremail.matches(emailRegex)) {
            showToast("Please Enter valid Email ID");
            return false;
        } else if (strpassword.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strcontactNo.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sportIdStr == null || sportIdStr.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please Select Sport", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void callSignup() {
        HashMap<String, String> payloadList = new HashMap<String, String>();
        int genId = 0;
        if (strgender.equals("Male")) {
            genId = 1;
        } else if (strgender.equals("Female")) {
            genId = 2;
        }

        if (areaId == 1) {//player validation
            if (selectRoleId == 0) {
                Toast.makeText(RegisterActivity.this, "Please Select Role", Toast.LENGTH_SHORT).show();
                return;
            } else if (strgender.equals("") || strgender.equals("GENDER")) {
                Toast.makeText(RegisterActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                return;
            } else if (strdobEdt.equals("") || strdobEdt.equals("DATE OF BIRTH")) {
                Toast.makeText(RegisterActivity.this, "Please Select Date Of Birth", Toast.LENGTH_SHORT).show();
                return;
            } else if (strexperience.equals("")) {
                Toast.makeText(RegisterActivity.this, "Please Enter Year of Experience", Toast.LENGTH_SHORT).show();
                return;
            }
            payloadList.put("date_of_birth", strdobEdt);
            payloadList.put("gender", String.valueOf(genId));
            payloadList.put("player_role", String.valueOf(selectRoleId));
        } else if (areaId == 2 || areaId == 3) {//coach and umpire validation
            if (strgender.equals("") || strgender.equals("GENDER")) {
                Toast.makeText(RegisterActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                return;
            } else if (strdobEdt.equals("") || strdobEdt.equals("DATE OF BIRTH")) {
                Toast.makeText(RegisterActivity.this, "Please Select Date of Birth", Toast.LENGTH_SHORT).show();
                return;
            } else if (strexperience.equals("")) {
                Toast.makeText(RegisterActivity.this, "Please Enter Year of Experience", Toast.LENGTH_SHORT).show();
                return;
            } else if (feepermatchStr.equals("")) {
                Toast.makeText(RegisterActivity.this, "Please Enter Fee Per Match", Toast.LENGTH_SHORT).show();
                return;
            }
            payloadList.put("experience", strexperience);
            payloadList.put("fee_per_match_day", feepermatchStr);
        } else if (areaId == 6 || areaId == 7 || areaId == 8 || areaId == 9 || areaId == 10) {
            if (strdobEdt.equals("") || strdobEdt.equals("Establishment Date")) {
                Toast.makeText(RegisterActivity.this, "Please Select Establishment Date", Toast.LENGTH_SHORT).show();
                return;
            }
            payloadList.put("estd", strdobEdt);
        }
        if (ASTUIUtil.isOnline(this)) {
            final String url = Contants.BASE_URL + Contants.Registration;

            payloadList.put("role", String.valueOf(areaId));
            payloadList.put("full_name", strfullName);
            payloadList.put("email", stremail);
            payloadList.put("mobile", strcontactNo);
            payloadList.put("password", strpassword);
            payloadList.put("users_sports", sportIdStr);

            String device_token = Utility.getDeviceIDFromSharedPreferences(RegisterActivity.this);
            payloadList.put("device_token", device_token);

            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelperWithProgress fileUploaderHelper = new FileUploaderHelperWithProgress(RegisterActivity.this, payloadList, multipartBody, url) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null && data.isStatus()) {
                        ASTUIUtil.setUserId(RegisterActivity.this, stremail, strpassword, null, null);
                        Toast.makeText(RegisterActivity.this, data.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intentLoggedIn = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intentLoggedIn);
                    } else {
                        Toast.makeText(RegisterActivity.this, data.getError().getEmail(), Toast.LENGTH_LONG).show();
                    }

                }
            };
            fileUploaderHelper.execute();
        } else {
            showToast(Contants.OFFLINE_MESSAGE);
        }
    }

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE = MediaType.parse("image/png");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (imgFile != null && imgFile.exists()) {
            multipartBody.addFormDataPart("profile_picture", imgFile.getName(), RequestBody.create(MEDIA_TYPE, imgFile));
        }
        return multipartBody;
    }

    private void showToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Select File!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    //OpenCameraIntent("academic.png");
                    cameraIntent();
                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //open camera
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    //select image from android.widget.Gallery
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PHOTO) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            permissionUtils.check_permission(permissions, "Location, Phone and Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
        if (requestCode == REQUEST_CODE_GPS_PERMISSIONS) {
            checkGpsEnable();
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        Uri uri = Utility.getImageUri(RegisterActivity.this, thumbnail);

        if (uri != null) {
            setImageName(uri, thumbnail);
        }
    }

    private void setImageName(Uri uri, Bitmap imageBitmap) {
        String homeStr = "academicPic" + System.currentTimeMillis() + ".png";
        addBitmapAsFile(imageBitmap, homeStr);
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri uri = null;
        if (data != null) {
            try {
                uri = data.getData();
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(RegisterActivity.this.getContentResolver(), data.getData());
                if (uri != null) {
                    setImageName(uri, imageBitmap);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    //add bitmap into list
    private Boolean addBitmapAsFile(final Bitmap bitmap, final String fileName) {

        new AsyncTask<Void, Void, Boolean>() {
            ASTProgressBar astProgressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                astProgressBar = new ASTProgressBar(RegisterActivity.this);
                astProgressBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                Boolean flag = false;
                File sdcardPath = Utility.getExternalStorageFilePath(RegisterActivity.this);
                sdcardPath.mkdirs();
                //File imgFile = new File(sdcardPath, System.currentTimeMillis() + ".png");
                imgFile = new File(sdcardPath, fileName);

                try {
                    FileOutputStream fOut = new FileOutputStream(imgFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut);
                    fOut.flush();
                    fOut.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                MediaScannerConnection.scanFile(RegisterActivity.this, new String[]{imgFile.toString()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                if (Contants.IS_DEBUG_LOG) {
                                    if (Contants.IS_DEBUG_LOG) {
                                        Log.d(Contants.LOG_TAG, "Scanned " + path + ":");
                                        Log.d(Contants.LOG_TAG, "-> uri=" + uri);
                                    }
                                }
                            }
                        });
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                Picasso.with(RegisterActivity.this).load(imgFile).into(profileImg);
                // setImageIntoList(imgFile);

                astProgressBar.dismiss();
            }
        }.execute();

        return true;
    }

    private void runTimePermission() {
        permissionUtils = new PermissionUtils(RegisterActivity.this);

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.CAMERA);
        permissions.add(android.Manifest.permission.WAKE_LOCK);
        permissions.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY);


        permissionUtils.check_permission(permissions, "Location,Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
    }

    @Override
    public void PermissionGranted(int request_code) {
        checkGpsEnable();
        //startLocationAlarmService();
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
        finish();
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
        finish();
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
        neverAskAgainAlert();
    }

    private void neverAskAgainAlert() {
        //Previously Permission Request was cancelled with 'Dont Ask Again',
        // Redirect to Settings after showing Information about why you need the permission
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Need Multiple Permissions");
        builder.setCancelable(false);
        builder.setMessage("Location, Phone and Storage Services Permissions are required for this App.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }

    private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_CODE_GPS_PERMISSIONS);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        checkGpsEnable();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    //    check gps enable in device or not
    private void checkGpsEnable() {
        try {
            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;
            final LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                buildAlertMessageNoGps();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
