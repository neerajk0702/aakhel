package com.kredivation.aakhale.activity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.LoginActivity;
import com.kredivation.aakhale.activity.RegisterActivity;
import com.kredivation.aakhale.activity.ResetPasswordActivity;
import com.kredivation.aakhale.adapter.AddAcademicsImageAdapter;
import com.kredivation.aakhale.adapter.GridViewAdapter;
import com.kredivation.aakhale.adapter.RecycleViewAdapter;
import com.kredivation.aakhale.adapter.SportsServiceGridViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.framework.FileUploaderHelperWithProgress;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.AddViewDynamically;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Sports;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FilePickerHelper;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAcademicsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView addImageView;
    private AddAcademicsImageAdapter imageAdapater;
    LinearLayout container_moreadd, AchievementsLayout;
    ImageView sortiMG, acadmiciMG;
    ASTEditText acdName, accAddress, zipcode, description, managerfullName, manageremail, mamangercontactno;
    ASTEditText coachfullName, coachemail, coachcontactno, OwnerfullName, Owneremail, Ownercontactno;
    Spinner stateSpinner, citySpinner;
    TextView addMoreViewmember, addMoreViewImage;
    LinearLayout academiesMemberLayout;
    ASTEditText memberfullName, memberemail, membercontactno;
    ASTButton continuebtn;
    TextView nooftemMember;
    int count = 0;
    LinearLayout acadmicViewinfoLayout, acadmicinfoLayout, acadmicmemberinfoLayout;
    boolean numberclick = false;
    boolean academiesMemberLayoutclick = false;

    GridView sportsgridView;
    SportsServiceGridViewAdapter sportsServiceGridViewAdapter;
    private ArrayList<String> stateIdList;
    private ArrayList<String> cityIdList;
    private String stateId, cityId;
    List<City> cityInfoList;
    String strdobEdt;
    private String acdNameStr, accAddressStr, zipcodeStr, descriptionStr, managerfullNameStr, manageremailStr, mamangercontactnoStr, coachfullNameStr, coachemailStr, coachcontactnoStr, OwnerfullNameStr, OwneremailStr, OwnercontactnoStr;
    public final int SELECT_PHOTO = 102;
    public final int REQUEST_CAMERA = 101;
    private String userChoosenTask;
    File imgFile;
    ASTProgressBar astProgressBar;
    ArrayList<ImageItem> acImgList;
    List<AddViewDynamically> allmember = new ArrayList<AddViewDynamically>();
    ArrayList<Sports> sportsList;
    private Toolbar toolbar;
    List<AddViewDynamically> allAchievements = new ArrayList<AddViewDynamically>();
    Calendar myCalendar;
    ASTTextView dobEdt;
    DatePickerDialog todatepicker;
    TextView addMoreAchievements;
    ImageView dateIcon;

    public AddAcademicsActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_academics_vfragments);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    public void init() {
        Typeface materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/materialdesignicons-webfont.otf");
        TextView back = toolbar.findViewById(R.id.back);
        back.setTypeface(materialdesignicons_font);
        back.setText(Html.fromHtml("&#xf30d;"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        acImgList = new ArrayList<>();
        addImageView = findViewById(R.id.addImageView);
        imageAdapater = new AddAcademicsImageAdapter(AddAcademicsActivity.this, R.layout.image_item_layout, acImgList);
        addImageView.setAdapter(imageAdapater);
        dobEdt = findViewById(R.id.dobEdt);
        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);
        AchievementsLayout = findViewById(R.id.AchievementsLayout);
        container_moreadd = findViewById(R.id.container_moreadd);
        addMoreViewmember = findViewById(R.id.addMoreViewmember);
        sortiMG = findViewById(R.id.sortiMG);
        acadmiciMG = findViewById(R.id.acadmiciMG);
        acdName = findViewById(R.id.acdName);
        accAddress = findViewById(R.id.accAddress);
        zipcode = findViewById(R.id.zipcode);
        description = findViewById(R.id.description);
        managerfullName = findViewById(R.id.managerfullName);
        manageremail = findViewById(R.id.manageremail);
        mamangercontactno = findViewById(R.id.mamangercontactno);
        coachfullName = findViewById(R.id.coachfullName);
        coachemail = findViewById(R.id.coachemail);
        coachcontactno = findViewById(R.id.coachcontactno);
        OwnerfullName = findViewById(R.id.OwnerfullName);
        Owneremail = findViewById(R.id.Owneremail);
        Ownercontactno = findViewById(R.id.Ownercontactno);
        stateSpinner = findViewById(R.id.stateSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        addMoreViewmember = findViewById(R.id.addMoreViewmember);
        addMoreViewImage = findViewById(R.id.addMoreViewImage);
        addMoreViewImage.setOnClickListener(this);
        academiesMemberLayout = findViewById(R.id.academiesMemberLayout);
        continuebtn = findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(this);
        addMoreViewmember.setOnClickListener(this);
        acadmicViewinfoLayout = findViewById(R.id.acadmicViewinfoLayout);
        acadmicinfoLayout = findViewById(R.id.acadmicinfoLayout);
        acadmicViewinfoLayout.setOnClickListener(this);
        academiesMemberLayout.setOnClickListener(this);
        acadmicmemberinfoLayout = findViewById(R.id.acadmicmemberinfoLayout);
        addMoreAchievements = findViewById(R.id.addMoreAchievements);
        addMoreAchievements.setOnClickListener(this);
        sportsgridView = findViewById(R.id.sportsgridView);
        dateIcon = findViewById(R.id.dateIcon);
        dateIcon.setOnClickListener(this);
        addMoreMember();
        getacademyFormData();
        setDate();
        addAchievements();
        setValue();
    }

    private void setValue() {
        SharedPreferences UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
        Contants.auth_token = UserInfo.getString("auth_token", "");
        int role = UserInfo.getInt("role", 0);
        String email = UserInfo.getString("email", "");
        String full_name = UserInfo.getString("full_name", "");
        String mobile = UserInfo.getString("mobile", "");
        String profile_picture = UserInfo.getString("profile_picture", "");
        String users_sports = UserInfo.getString("users_sports", "");
        String unique_id = UserInfo.getString("unique_id", "");
        acdName.setText(full_name + "");
    }

    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(AddAcademicsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void addMoreMember() {
        count++;
        LayoutInflater inflater = LayoutInflater.from(AddAcademicsActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.add_more_row, null);
        memberfullName = inflatedLayout.findViewById(R.id.memberfullName);
        memberemail = inflatedLayout.findViewById(R.id.memberemail);
        membercontactno = inflatedLayout.findViewById(R.id.membercontactno);
        nooftemMember = inflatedLayout.findViewById(R.id.nooftemMember);
        nooftemMember.setText("Team Member\t" + count);
        container_moreadd.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setFullname(memberfullName);
        addViewDynamically.setEmail(memberemail);
        addViewDynamically.setPhoneNo(membercontactno);
        allmember.add(addViewDynamically);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreViewmember:
                addMoreMember();
                ASTUIUtil.showToast(AddAcademicsActivity.this, "One More Member Added");
                break;
            case R.id.acadmicViewinfoLayout:
                if (numberclick) {
                    numberclick = false;
                    sortiMG.setImageResource(R.drawable.ic_up_arrow);
                    acadmicinfoLayout.setVisibility(View.VISIBLE);
                } else {
                    numberclick = true;
                    sortiMG.setImageResource(R.drawable.ic_angle_arrow_down);
                    acadmicinfoLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.academiesMemberLayout:
                if (academiesMemberLayoutclick) {
                    academiesMemberLayoutclick = false;
                    acadmiciMG.setImageResource(R.drawable.ic_up_arrow);
                    acadmicmemberinfoLayout.setVisibility(View.VISIBLE);
                } else {
                    academiesMemberLayoutclick = true;
                    acadmiciMG.setImageResource(R.drawable.ic_angle_arrow_down);
                    acadmicmemberinfoLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.addMoreViewImage:
                selectImage();
                break;
            case R.id.continuebtn:
                if (isValidate()) {
                    uploadData();
                }
                break;
            case R.id.addMoreAchievements:
                addAchievements();
                ASTUIUtil.showToast(AddAcademicsActivity.this, "One More Achievement Added.");
                break;
            case R.id.dateIcon:
                todatepicker.show();
                break;
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
        todatepicker = new DatePickerDialog(AddAcademicsActivity.this, todate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    int AchievementsCount;

    //add Achievements in run time
    public void addAchievements() {
        AchievementsCount++;
        LayoutInflater inflater = LayoutInflater.from(AddAcademicsActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.add_editbox, null);
        ASTEditText edittext = inflatedLayout.findViewById(R.id.edittext);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(AchievementsCount + "-");
        AchievementsLayout.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setAddEditText(edittext);
        allAchievements.add(addViewDynamically);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddAcademicsActivity.this);
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
    public void OpenCameraIntent(String fileName) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Utility.getExternalStorageFilePathCreateAppDirectory(AddAcademicsActivity.this) + File.separator + fileName);
        String providerName = String.format(Locale.ENGLISH, "%s%s", AddAcademicsActivity.this.getPackageName(), ".imagepicker.provider");
        Uri uri = FileProvider.getUriForFile(AddAcademicsActivity.this, providerName, file);
        FilePickerHelper.grantAppPermission(AddAcademicsActivity.this, intent, uri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, FilePickerHelper.CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }

    private void setImageView() {
        String academicPic = "academicPic" + System.currentTimeMillis() + ".png";
        File file = new File(Utility.getExternalStorageFilePathCreateAppDirectory(AddAcademicsActivity.this) + File.separator + "academic.png");
        if (file.exists()) {
            //compresImage(file, academicPic);
        }
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
            } else if (requestCode == Contants.REQ_PAGE_COMMUNICATOR) {
                String SportServiceDetail = data.getExtras().getString("SportServiceDetail");
                int position = data.getExtras().getInt("position");
                boolean saveFlag = data.getExtras().getBoolean("saveFlag");
                if (saveFlag) {//check save button click or not in AddSportDetailForAcademicActivity
                    sportsList.get(position).setSportServiceDetail(SportServiceDetail);
                } else {
                    sportsList.get(position).setSportServiceDetail("");
                    sportsList.get(position).setSelected(false);
                    sportsServiceGridViewAdapter.notifyDataSetChanged();
                }

            }
            /* else if (requestCode == FilePickerHelper.CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
                setImageView();
            }*/
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        Uri uri = Utility.getImageUri(AddAcademicsActivity.this, thumbnail);

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
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(AddAcademicsActivity.this.getContentResolver(), data.getData());
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
                astProgressBar = new ASTProgressBar(AddAcademicsActivity.this);
                astProgressBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {

                Boolean flag = false;
                File sdcardPath = Utility.getExternalStorageFilePath(AddAcademicsActivity.this);
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
                MediaScannerConnection.scanFile(AddAcademicsActivity.this, new String[]{imgFile.toString()}, null,
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
    //compres image
   /* private void compresImage(final File file, final String fileName) {

        new AsyncTask<Void, Void, Boolean>() {
            ProgressDialog progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = ProgressDialog.show(AddAcademicsActivity.this, "",
                        "Please wait Image Loading..", true);
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
//compress file
                Boolean flag = false;
                int ot = FilePickerHelper.getExifRotation(file);
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f);
                if (bitmap != null) {
                    Uri uri = FilePickerHelper.getImageUri(AddAcademicsActivity.this, bitmap);
//save compresed file into location
                    imgFile = new File(Utility.getExternalStorageFilePathCreateAppDirectory(AddAcademicsActivity.this()) + File.separator, fileName);
                    try {
                        InputStream iStream = AddAcademicsActivity.this.getContentResolver().openInputStream(uri);
                        byte[] inputData = FilePickerHelper.getBytes(iStream);

                        FileOutputStream fOut = new FileOutputStream(imgFile);
                        fOut.write(inputData);
                        //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        iStream.close();
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                saveImageDetails();
            }
        }.execute();

    }*/

    private void getacademyFormData() {
        if (Utility.isOnline(AddAcademicsActivity.this)) {
            ASTProgressBar dotDialog = new ASTProgressBar(AddAcademicsActivity.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.academyForm;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(AddAcademicsActivity.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getacademyFormData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setSpinnerValue(serviceData);
                            }
                        } else {
                            Toast.makeText(AddAcademicsActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddAcademicsActivity.this, Contants.Error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (dotDialog.isShowing()) {
                dotDialog.dismiss();
            }
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, AddAcademicsActivity.this);//off line msg....
        }
    }

    private void setSpinnerValue(ContentData serviceData) {
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
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(AddAcademicsActivity.this, R.layout.spinner_row, stateList);
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
            if (serviceData.getSports() != null) {
                sportsList = serviceData.getSports();
                sportsServiceGridViewAdapter = new SportsServiceGridViewAdapter(AddAcademicsActivity.this, R.layout.sports_row, sportsList);
                sportsgridView.setAdapter(sportsServiceGridViewAdapter);
                sportsgridView.setFocusable(true);
            }
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
            ArrayAdapter<String> citydapter = new ArrayAdapter<String>(AddAcademicsActivity.this, R.layout.spinner_row, cityList);
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

    public boolean isValidate() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        acdNameStr = acdName.getText().toString();
        accAddressStr = accAddress.getText().toString();
        zipcodeStr = zipcode.getText().toString();
        descriptionStr = description.getText().toString();
        managerfullNameStr = managerfullName.getText().toString();
        manageremailStr = manageremail.getText().toString();
        mamangercontactnoStr = mamangercontactno.getText().toString();
        coachfullNameStr = coachfullName.getText().toString();
        coachemailStr = coachemail.getText().toString();
        coachcontactnoStr = coachcontactno.getText().toString();
        OwnerfullNameStr = OwnerfullName.getText().toString();
        OwneremailStr = Owneremail.getText().toString();
        OwnercontactnoStr = Ownercontactno.getText().toString();
        strdobEdt = dobEdt.getText().toString();
        if (acdNameStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Academic Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strdobEdt.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Select Establishment Date!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (accAddressStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Academic Address!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (zipcodeStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Zipcode!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (descriptionStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Academic Description!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (managerfullNameStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Manager Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (manageremailStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Manager Email Id!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!manageremailStr.matches(emailRegex)) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter valid Manager Email ID!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mamangercontactnoStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Manager Phone No!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (coachfullNameStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Coach Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (coachemailStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Coach Email Id!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!coachemailStr.matches(emailRegex)) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter valid Coach Email ID!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (coachcontactnoStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Coach Phone No!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (OwnerfullNameStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Owner Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (OwneremailStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Owner Email Id!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!OwneremailStr.matches(emailRegex)) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter valid Owner Email ID!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (OwnercontactnoStr.equals("")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Enter Owner Phone No!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (stateId == null || stateId.equals("") || stateId.equals("0")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Select State!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (cityId == null || cityId.equals("") || cityId.equals("0")) {
            Toast.makeText(AddAcademicsActivity.this, "Please Select City!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sportsList == null || sportsList.size() == 0) {
            Toast.makeText(AddAcademicsActivity.this, "Please Select at least one Sport!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    public void uploadData() {
        if (ASTUIUtil.isOnline(AddAcademicsActivity.this)) {
            String serviceURL = Contants.BASE_URL + Contants.addAcademy;
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("academy_name", acdNameStr);
            payloadList.put("street_address", accAddressStr);
            payloadList.put("academy_state", stateId);
            payloadList.put("academy_city", cityId);
            payloadList.put("academy_zipcode", zipcodeStr);
            payloadList.put("academy_description", descriptionStr);
            String sportsIdsStr = "";
           /* if (sportsList != null && sportsList.size() > 0) {
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
            payloadList.put("academy_sports", sportsIdsStr);*/

            OwnerfullNameStr = OwnerfullName.getText().toString();
            OwneremailStr = Owneremail.getText().toString();
            OwnercontactnoStr = Ownercontactno.getText().toString();

            JSONObject manager = new JSONObject();
            JSONObject coach = new JSONObject();
            JSONObject owner = new JSONObject();
            try {
                manager.put("full_name", managerfullNameStr);
                manager.put("email", manageremailStr);
                manager.put("contact_number", mamangercontactnoStr);
                coach.put("full_name", coachfullNameStr);
                coach.put("email", coachemailStr);
                coach.put("contact_number", coachcontactnoStr);
                owner.put("full_name", OwnerfullNameStr);
                owner.put("email", OwneremailStr);
                owner.put("contact_number", OwnercontactnoStr);
            } catch (JSONException e) {
                //e.printStackTrace();
            }

            payloadList.put("academy_manager", manager.toString());
            payloadList.put("academy_coach", coach.toString());
            payloadList.put("academy_owner", owner.toString());
            JSONArray jsonArrayTeam = new JSONArray();
            JSONArray academy_servicesjsonArray = new JSONArray();
            try {
                if (allmember != null && allmember.size() > 0) {
                    for (AddViewDynamically viewDynamically : allmember) {
                        JSONObject member = new JSONObject();
                        if (viewDynamically.getFullname() != null) {
                            member.put("full_name", viewDynamically.getFullname().getText().toString());
                        }
                        if (viewDynamically.getEmail() != null) {
                            member.put("email", viewDynamically.getEmail().getText().toString());
                        }
                        if (viewDynamically.getPhoneNo() != null) {
                            member.put("contact_number", viewDynamically.getPhoneNo().getText().toString());
                        }
                        jsonArrayTeam.put(member);
                    }
                }

                if (sportsList != null && sportsList.size() > 0) {
                    for (Sports sports : sportsList) {
                       /* JSONObject mainObj = new JSONObject();
                        JSONObject sportsData = new JSONObject();
                        sportsData.put("name", sports.getSports_name());
                        sportsData.put("id", String.valueOf(sports.getId()));
                        mainObj.put("sports", sportsData);
                        mainObj.put("timing", "9 am");
                        JSONObject trainerData = new JSONObject();
                        trainerData.put("name", sportsData);
                        trainerData.put("gender", sportsData);
                        trainerData.put("age", sportsData);
                        trainerData.put("experience", sportsData);
                        mainObj.put("trainer", trainerData);
                        mainObj.put("fees", "500");*/
                        if (sports.isSelected() && sports.getSportServiceDetail() != null && !sports.getSportServiceDetail().equals("")) {
                            academy_servicesjsonArray.put(sports.getSportServiceDetail());
                        }
                    }
                }
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            payloadList.put("academy_services", academy_servicesjsonArray.toString());
            payloadList.put("team_member", jsonArrayTeam.toString());
            JSONArray jsonArraAchievement = new JSONArray();
            if (allAchievements != null && allAchievements.size() > 0) {
                for (AddViewDynamically viewDynamically : allAchievements) {
                    jsonArraAchievement.put(viewDynamically.getAddEditText().getText().toString());
                }
            }
            payloadList.put("achievements", jsonArraAchievement.toString());
            payloadList.put("estd", strdobEdt);
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelperWithProgress fileUploaderHelper = new FileUploaderHelperWithProgress(AddAcademicsActivity.this, payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    if (result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            if (status) {
                                ASTUIUtil.showToast(AddAcademicsActivity.this, "Academic added successfully");
                            } else {
                                ASTUIUtil.showToast(AddAcademicsActivity.this, "Academic not added!");
                            }
                        } catch (JSONException e) {
                        }
                    } else {
                        ASTUIUtil.showToast(AddAcademicsActivity.this, "Academic not added!");
                    }

                }
            };
            fileUploaderHelper.execute();
        } else {
            ASTUIUtil.alertForErrorMessage(Contants.OFFLINE_MESSAGE, AddAcademicsActivity.this);//off line msg....
        }

    }


    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE = MediaType.parse("image/png");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (ImageItem imageItem : acImgList) {
            if (imageItem.getImagFile() != null && imageItem.getImagFile().exists()) {
                multipartBody.addFormDataPart("academy_photos[]", imageItem.getImagFile().getName(), RequestBody.create(MEDIA_TYPE, imageItem.getImagFile()));
            }
        }
        return multipartBody;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openSportService(int position) {
        Intent intent1 = new Intent(AddAcademicsActivity.this, AddSportDetailForAcademicActivity.class);
        intent1.putExtra("Id", sportsList.get(position).getId());
        intent1.putExtra("SportName", sportsList.get(position).getSports_name());
        intent1.putExtra("SporSelected", sportsList.get(position).isSelected());
        intent1.putExtra("position", position);
        startActivityForResult(intent1, Contants.REQ_PAGE_COMMUNICATOR);
    }
}
