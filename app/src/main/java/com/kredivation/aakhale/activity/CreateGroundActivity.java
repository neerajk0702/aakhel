package com.kredivation.aakhale.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddAcademicsImageAdapter;
import com.kredivation.aakhale.adapter.SportsServiceGridViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.FileUploaderHelperWithProgress;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.AddViewDynamically;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Sports;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.model.Timezone;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroundActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView addImageView;
    private ArrayList<String> stateIdList;
    private ArrayList<String> timeZoneList;
    private ArrayList<Sports> sportsList;
    private ArrayList<String> cityIdList;
    private String stateId, cityId, timeZoneId;
    List<City> cityInfoList;
    int serviceCount = 0;
    int termsCount = 0;
    int AchievementsCount = 0;
    ASTEditText gName, venueAddress, zipCode, capacitytxt, dimesionTxt, noofMatchtxt, surfaceTxt, costtext;
    Spinner stateSpinner, citySpinner, floodlightSpinner, TimeZoneSpinner, dayorNightSpiner;
    ASTButton continuebtn, canclebtn;
    String name, ground_address, ground_zipcode, capacity, Dimension, noofMatchStr,
            match_per_day, surface, free_services, terms_conditions, coststr;
    int floodlight = -1;
    int day_or_night = -1;
    LinearLayout container_add_freeservices, container_add_term_condtion;
    List<AddViewDynamically> allFreeSertvice = new ArrayList<AddViewDynamically>();
    List<AddViewDynamically> allTermCondication = new ArrayList<AddViewDynamically>();
    List<AddViewDynamically> allAchievements = new ArrayList<AddViewDynamically>();
    private TextView addMoretermcondtion, addmorfreeServices, addPicture;
    public final int SELECT_PHOTO = 102;
    public final int REQUEST_CAMERA = 101;
    private String userChoosenTask;
    File imgFile;
    ASTProgressBar astProgressBar;
    ArrayList<ImageItem> acImgList;
    AddAcademicsImageAdapter imageAdapater;
    GridView sportsgridView;
    private Toolbar toolbar;
    TextView addStaffView, addMoreAchievements;
    LinearLayout addStaffLayout, AchievementsLayout;

    public CreateGroundActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_ground);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    public void init() {
        addStaffView = findViewById(R.id.addStaffView);
        addStaffView.setOnClickListener(this);
        addMoreAchievements = findViewById(R.id.addMoreAchievements);
        addMoreAchievements.setOnClickListener(this);
        addStaffLayout = findViewById(R.id.addStaffLayout);
        AchievementsLayout = findViewById(R.id.AchievementsLayout);

        gName = findViewById(R.id.gName);
        costtext = findViewById(R.id.cost);
        venueAddress = findViewById(R.id.venueAddress);
        zipCode = findViewById(R.id.zipCode);
        capacitytxt = findViewById(R.id.capacity);
        dimesionTxt = findViewById(R.id.dimesionTxt);
        noofMatchtxt = findViewById(R.id.noofMatchtxt);
        surfaceTxt = findViewById(R.id.surfaceTxt);
        stateSpinner = findViewById(R.id.stateSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        floodlightSpinner = findViewById(R.id.floodlightSpinner);
        TimeZoneSpinner = findViewById(R.id.TimeZoneSpinner);
        dayorNightSpiner = findViewById(R.id.dayorNightSpiner);
        continuebtn = findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(this);
        canclebtn = findViewById(R.id.canclebtn);
        canclebtn.setOnClickListener(this);
        addImageView = findViewById(R.id.addImageView);
        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);
        sportsgridView = findViewById(R.id.sportsgridView);
        container_add_freeservices = findViewById(R.id.container_add_freeservices);
        container_add_term_condtion = findViewById(R.id.container_add_term_condtion);
        addmorfreeServices = findViewById(R.id.addmorfreeServices);
        addMoretermcondtion = findViewById(R.id.addMoretermcondtion);
        addmorfreeServices.setOnClickListener(this);
        addMoretermcondtion.setOnClickListener(this);
        addPicture = findViewById(R.id.addPicture);
        addPicture.setOnClickListener(this);
        acImgList = new ArrayList<>();
        imageAdapater = new AddAcademicsImageAdapter(CreateGroundActivity.this, R.layout.image_item_layout, acImgList);
        addImageView.setAdapter(imageAdapater);
        getGroundData();
        addTermsAndCondition();
        addFreeService();
        addAchievements();
    }

    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(CreateGroundActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getGroundData() {
        if (Utility.isOnline(CreateGroundActivity.this)) {
            ASTProgressBar dotDialog = new ASTProgressBar(CreateGroundActivity.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.groundFormApi;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(CreateGroundActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getGroundData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setStateSpinnerValue(serviceData);
                                setTimeZoneSpinner(serviceData);
                                setSportsSpinner(serviceData);
                            }
                        } else {
                            Toast.makeText(CreateGroundActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CreateGroundActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (dotDialog.isShowing()) {
                dotDialog.dismiss();
            }
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, CreateGroundActivity.this);//off line msg....
        }
    }

    private void setStateSpinnerValue(ContentData serviceData) {
        if (serviceData.getState() != null) {
            ArrayList<String> stateList = new ArrayList<>();
            stateIdList = new ArrayList<String>();
            stateList.add("Select State");
            stateIdList.add("0");
            cityInfoList = new ArrayList<City>();
            for (State state : serviceData.getState()) {
                stateList.add(state.getState_name());
                stateIdList.add(state.getId());
                for (City city : state.getCity()) {
                    cityInfoList.add(city);
                }
            }
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(CreateGroundActivity.this, R.layout.spinner_row, stateList);
            stateSpinner.setAdapter(statedapter);
            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    stateId = stateIdList.get(pos);
                    setCitySpinnerValue();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void setCitySpinnerValue() {
        if (cityInfoList != null) {
            final ArrayList<String> cityList = new ArrayList<>();
            cityIdList = new ArrayList<String>();
            cityList.add("Select City");
            cityIdList.add("0");
            for (int i = 0; i < cityInfoList.size(); i++) {
                if (stateId.equals(cityInfoList.get(i).getState_id())) {
                    cityList.add(cityInfoList.get(i).getCity_name());
                    cityIdList.add(cityInfoList.get(i).getId());
                }
            }
            ArrayAdapter<String> citydapter = new ArrayAdapter<String>(CreateGroundActivity.this, R.layout.spinner_row, cityList);
            citySpinner.setAdapter(citydapter);
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    cityId = cityIdList.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void setTimeZoneSpinner(ContentData serviceData) {
        if (serviceData.getTimezone() != null) {
            ArrayList<String> timeZoneListt = new ArrayList<>();
            timeZoneList = new ArrayList<String>();
            timeZoneListt.add("Select Time Zone");
            timeZoneList.add("0");
            for (Timezone timezone : serviceData.getTimezone()) {
                timeZoneListt.add(timezone.getTimezone_name());
                timeZoneList.add(timezone.getId());
            }
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(CreateGroundActivity.this, R.layout.spinner_row, timeZoneListt);
            TimeZoneSpinner.setAdapter(statedapter);

            TimeZoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                    timeZoneId = timeZoneList.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void setSportsSpinner(ContentData serviceData) {
        if (serviceData.getSports() != null) {
            sportsList = new ArrayList<>();
            sportsList.addAll(serviceData.getSports());
            SportsServiceGridViewAdapter sportsServiceGridViewAdapter = new SportsServiceGridViewAdapter(CreateGroundActivity.this, R.layout.sports_row, sportsList);
            sportsgridView.setAdapter(sportsServiceGridViewAdapter);
            sportsgridView.setFocusable(true);
        }
    }

    //add free service layout in runtime
    public void addFreeService() {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(CreateGroundActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.add_editbox, null);
        ASTEditText edittext = inflatedLayout.findViewById(R.id.edittext);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "-");
        container_add_freeservices.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setAddEditText(edittext);
        allFreeSertvice.add(addViewDynamically);
    }

    //add term and condition in run time
    public void addTermsAndCondition() {
        termsCount++;
        LayoutInflater inflater = LayoutInflater.from(CreateGroundActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.add_editbox, null);
        ASTEditText edittext = inflatedLayout.findViewById(R.id.edittext);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(termsCount + "-");
        container_add_term_condtion.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setAddEditText(edittext);
        allTermCondication.add(addViewDynamically);
    }

    //add Achievements in run time
    public void addAchievements() {
        AchievementsCount++;
        LayoutInflater inflater = LayoutInflater.from(CreateGroundActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.add_editbox, null);
        ASTEditText edittext = inflatedLayout.findViewById(R.id.edittext);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(AchievementsCount + "-");
        AchievementsLayout.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setAddEditText(edittext);
        allAchievements.add(addViewDynamically);
    }

    public boolean isValidate() {
        name = gName.getText().toString();
        ground_address = venueAddress.getText().toString();
        ground_zipcode = zipCode.getText().toString();
        capacity = capacitytxt.getText().toString();
        Dimension = dimesionTxt.getText().toString();
        noofMatchStr = noofMatchtxt.getText().toString();
        surface = surfaceTxt.getText().toString();
        String flood = floodlightSpinner.getSelectedItem().toString();
        if (flood.equals("Yes")) {
            floodlight = 1;
        } else if (flood.equals("No")) {
            floodlight = 0;
        }
        String dayNight = dayorNightSpiner.getSelectedItem().toString();
        if (dayNight.equals("Day")) {
            day_or_night = 1;
        } else if (dayNight.equals("Night")) {
            day_or_night = 2;
        } else if (dayNight.equals("Day and Night")) {
            day_or_night = 3;
        }
        coststr = costtext.getText().toString();
        if (name.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Ground Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ground_address.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Ground Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ground_zipcode.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Ground Zipcode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (capacity.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Ground Capacity", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Dimension.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Ground Dimension", Toast.LENGTH_SHORT).show();
            return false;
        } else if (noofMatchStr.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter No of Match", Toast.LENGTH_SHORT).show();
            return false;
        } else if (surface.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Ground surface", Toast.LENGTH_SHORT).show();
            return false;
        } else if (coststr.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Cost", Toast.LENGTH_SHORT).show();
            return false;
        } else if (noofMatchStr.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter No of Match", Toast.LENGTH_SHORT).show();
            return false;
        } else if (stateId == null || stateId.equals("") || stateId.equals("0")) {
            Toast.makeText(CreateGroundActivity.this, "Please Select State!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (cityId == null || cityId.equals("") || cityId.equals("0")) {
            Toast.makeText(CreateGroundActivity.this, "Please Select City!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (floodlight == -1) {
            Toast.makeText(CreateGroundActivity.this, "Please Select Flood Light!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (timeZoneId == null || timeZoneId.equals("") || timeZoneId.equals("0")) {
            Toast.makeText(CreateGroundActivity.this, "Please Select Time Zone!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (day_or_night == -1) {
            Toast.makeText(CreateGroundActivity.this, "Please Select Day / Night!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (coststr.equals("")) {
            Toast.makeText(CreateGroundActivity.this, "Please Enter Ground Cost!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }


    private void saveGroundData() {
        if (ASTUIUtil.isOnline(CreateGroundActivity.this)) {
            String serviceURL = Contants.BASE_URL + Contants.SaveGround;
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("name", name);
            payloadList.put("ground_address", ground_address);
            payloadList.put("ground_state", stateId);
            payloadList.put("ground_city", cityId);
            payloadList.put("ground_zipcode", ground_zipcode);
            payloadList.put("floodlight", String.valueOf(floodlight));
            payloadList.put("capacity", capacity);
            payloadList.put("Dimension", Dimension);
            payloadList.put("timezone", String.valueOf(timeZoneId));
            payloadList.put("match_per_day", match_per_day);
            payloadList.put("day_or_night", String.valueOf(day_or_night));
            payloadList.put("surface", surface);
            payloadList.put("cost", coststr);
            JSONArray jsonArraFreeSertvice = new JSONArray();
            if (allFreeSertvice != null && allFreeSertvice.size() > 0) {
                for (AddViewDynamically viewDynamically : allFreeSertvice) {
                    jsonArraFreeSertvice.put(viewDynamically.getAddEditText().getText().toString());
                }
            }
            payloadList.put("free_services", jsonArraFreeSertvice.toString());
            JSONArray jsonArraTermCondication = new JSONArray();
            if (allTermCondication != null && allTermCondication.size() > 0) {
                for (AddViewDynamically viewDynamically : allTermCondication) {
                    jsonArraTermCondication.put(viewDynamically.getAddEditText().getText().toString());
                }
            }
            payloadList.put("terms_conditions", jsonArraTermCondication.toString());
            JSONArray jsonArraAchievement = new JSONArray();
            if (allAchievements != null && allAchievements.size() > 0) {
                for (AddViewDynamically viewDynamically : allAchievements) {
                    jsonArraAchievement.put(viewDynamically.getAddEditText().getText().toString());
                }
            }
            payloadList.put("achievements", jsonArraAchievement.toString());
            String sportsIdsStr = "";
            if (sportsList != null && sportsList.size() > 0) {
                String separatorComm = ",";
                StringBuilder stringBuilders = new StringBuilder();
                for (int i = 0; i < sportsList.size(); i++) {
                    stringBuilders.append(String.valueOf(sportsList.get(i).getId()));
                    stringBuilders.append(",");
                }
                sportsIdsStr = stringBuilders.toString();
                if (sportsIdsStr != null && !sportsIdsStr.equals("")) {
                    sportsIdsStr = sportsIdsStr.substring(0, sportsIdsStr.length() - separatorComm.length());
                }
            }
            payloadList.put("sports", sportsIdsStr);
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelperWithProgress fileUploaderHelper = new FileUploaderHelperWithProgress(CreateGroundActivity.this, payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    // ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (result != null) {
                        ASTUIUtil.showToast(CreateGroundActivity.this, "Ground added successfully");
                    } else {
                        ASTUIUtil.showToast(CreateGroundActivity.this, "Ground not added successfully!");
                    }

                }
            };
            fileUploaderHelper.execute();
        } else {
            ASTUIUtil.alertForErrorMessage(Contants.OFFLINE_MESSAGE, CreateGroundActivity.this);//off line msg....
        }


    }

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE = MediaType.parse("image/png");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (ImageItem imageItem : acImgList) {
            if (imageItem.getImagFile() != null && imageItem.getImagFile().exists()) {
                multipartBody.addFormDataPart("display_picture[]", imageItem.getImagFile().getName(), RequestBody.create(MEDIA_TYPE, imageItem.getImagFile()));
            }
        }
        return multipartBody;
    }

    private void showToast(String message) {
        Toast.makeText(CreateGroundActivity.this, message, Toast.LENGTH_LONG).show();
    }


    /*
     *
     * Parse and Validate Teame Service Data
     */
    private void parseGroundData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("success")) {
                    JSONObject object = jsonRootObject.optJSONObject("data");
                    String userName = object.optString("name").toString();
                    Toast.makeText(CreateGroundActivity.this, "Ground added successfully", Toast.LENGTH_LONG).show();
                } else {
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continuebtn:
                if (isValidate()) {
                    saveGroundData();
                }
                break;
            case R.id.canclebtn:
                break;
            case R.id.addmorfreeServices:
                addFreeService();
                ASTUIUtil.showToast(CreateGroundActivity.this, "One More Free Services Added.");
                break;
            case R.id.addMoretermcondtion:
                addTermsAndCondition();
                ASTUIUtil.showToast(CreateGroundActivity.this, "One More Term and Condition Added.");
                break;
            case R.id.addPicture:
                selectImage();
                break;
            case R.id.addMoreAchievements:
                addAchievements();
                ASTUIUtil.showToast(CreateGroundActivity.this, "One More Achievement Added.");
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroundActivity.this);
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
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        Uri uri = Utility.getImageUri(CreateGroundActivity.this, thumbnail);

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
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(CreateGroundActivity.this.getContentResolver(), data.getData());
                if (uri != null) {
                    setImageName(uri, imageBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //add bitmap into list
    private Boolean addBitmapAsFile(final Bitmap bitmap, final String fileName) {

        new AsyncTask<Void, Void, Boolean>() {
            File imgFile;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                astProgressBar = new ASTProgressBar(CreateGroundActivity.this);
                astProgressBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {

                Boolean flag = false;
                File sdcardPath = Utility.getExternalStorageFilePath(CreateGroundActivity.this);
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
                MediaScannerConnection.scanFile(CreateGroundActivity.this, new String[]{imgFile.toString()}, null,
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
                // Picasso.with(context).load(imgFile).into(faultImage);
                // setImageIntoList(imgFile);
                ImageItem imageItem = new ImageItem();
                imageItem.setImagFile(imgFile);
                imageItem.setImageStr(imgFile.getAbsolutePath());
                acImgList.add(imageItem);
                imageAdapater.notifyDataSetChanged();
                astProgressBar.dismiss();
            }
        }.execute();

        return true;
    }
}
