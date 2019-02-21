package com.kredivation.aakhale.activity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.dialog.SearchDialog;
import com.kredivation.aakhale.framework.FileUploaderHelperWithProgress;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.AddViewDynamically;
import com.kredivation.aakhale.model.City;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FilePickerHelper;
import com.kredivation.aakhale.utility.FontManager;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
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
 * A simple {} subclass.
 */
public class AddTournament extends AppCompatActivity implements View.OnClickListener {

    ASTEditText tournamentName, venueAddress, zipCode, noofOver, enteryFee, aboutTournament;

    Spinner stateSpinner, citySpinner, formateSpinner, statusSpinner;
    LinearLayout container_add_facilities, container_add_rule_regulation, container_add_rule_price;
    TextView addsisplayPicture, addMoreprice, addMoreFacilites, addMorerule_regulation;
    ImageView addImageView;
    private ArrayList<String> stateIdList;
    private ArrayList<String> groundList;
    private ArrayList<String> cityIdList;
    ASTEditText addviewedtxt;
    private String stateId, cityId, groundId;
    List<City> cityInfoList;
    ASTButton continuebtn;
    List<AddViewDynamically> allfacilites = new ArrayList<AddViewDynamically>();
    List<AddViewDynamically> rulesregulations = new ArrayList<AddViewDynamically>();
    public final int SELECT_PHOTO = 102;
    public final int REQUEST_CAMERA = 101;
    private String userChoosenTask;
    File imgFile;
    List<AddViewDynamically> price = new ArrayList<AddViewDynamically>();
    String name, tournament_address, tournament_state, tournament_city, tournament_zipcode, overs,
            format, start_date, end_date, entry_fees, status, about_tournament, facilities, rules_regulations,
            prizes, display_picture, teams;

    ASTProgressBar astProgressBar;
    int Facilitescount = 1, Regulationcount = 1, priceCount = 1;

    TextView startdateTxt, enddateTxt;
    ImageView startdateIcon, enddateIcon;

    DatePickerDialog startdatepicker, enddatepicker;
    Calendar myCalendar;
    LinearLayout addTeameLayout, TeamViewLayout, UmpireViewLayout, addumpireLayout;
    ArrayList<Team> teamlist = new ArrayList<>();
    private Toolbar toolbar;
    String selectTeamId, selectUmpireId;

    public AddTournament() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_tournament);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        tournamentName = findViewById(R.id.tournamentName);
        venueAddress = findViewById(R.id.venueAddress);
        zipCode = findViewById(R.id.zipCode);
        noofOver = findViewById(R.id.noofOver);
        enteryFee = findViewById(R.id.enteryFee);
        aboutTournament = findViewById(R.id.aboutTournament);
        addMoreFacilites = findViewById(R.id.addMoreFacilites);
        addMorerule_regulation = findViewById(R.id.addMorerule_regulation);
        stateSpinner = findViewById(R.id.stateSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        formateSpinner = findViewById(R.id.formateSpinner);
        statusSpinner = findViewById(R.id.statusSpinner);
        container_add_facilities = findViewById(R.id.container_add_facilities);
        container_add_rule_regulation = findViewById(R.id.container_add_rule_regulation);
        container_add_rule_price = findViewById(R.id.container_add_rule_price);
        addsisplayPicture = findViewById(R.id.addsisplayPicture);
        addMoreprice = findViewById(R.id.addMoreprice);
        addMoreFacilites = findViewById(R.id.addMoreFacilites);

        startdateTxt = findViewById(R.id.startdateTxt);
        enddateTxt = findViewById(R.id.enddateTxt);
        startdateIcon = findViewById(R.id.startdateIcon);
        enddateIcon = findViewById(R.id.enddateIcon);
        startdateIcon.setOnClickListener(this);
        enddateIcon.setOnClickListener(this);
        addImageView = findViewById(R.id.addImageView);

        addImageView.setOnClickListener(this);
        addsisplayPicture.setOnClickListener(this);
        addMoreprice.setOnClickListener(this);
        addMoreFacilites.setOnClickListener(this);
        addMorerule_regulation.setOnClickListener(this);
        continuebtn = findViewById(R.id.continuebtn);

        TeamViewLayout = findViewById(R.id.TeamViewLayout);
        addTeameLayout = findViewById(R.id.addTeameLayout);
        addTeameLayout.setOnClickListener(this);
        continuebtn.setOnClickListener(this);
        UmpireViewLayout = findViewById(R.id.UmpireViewLayout);
        addumpireLayout = findViewById(R.id.addumpireLayout);
        addumpireLayout.setOnClickListener(this);

        addMoreFacilites("Facilites Name\t" + Facilitescount);
        addMorerule_regulation("Rule Regulation Name\t" + Regulationcount);
        addMoreprice("Price\t" + priceCount);
        getAddTournamentData();
        setendDate();
        setstartDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addMoreFacilites:
                Facilitescount++;
                addMoreFacilites("Facilites Name\t" + Facilitescount);
                break;
            case R.id.addMorerule_regulation:
                Regulationcount++;
                addMorerule_regulation("Rule Regulation Name\t" + Regulationcount);

                break;
            case R.id.addMoreprice:
                priceCount++;
                addMoreprice("Price\t" + priceCount);

                break;
            case R.id.addImageView:
                selectImage();
                break;
            case R.id.continuebtn:
                if (isvalidateSignup()) {
                    addTournamentServer();
                }
                break;
            case R.id.startdateIcon:
                startdatepicker.show();
                break;
            case R.id.enddateIcon:
                enddatepicker.show();
                break;
            case R.id.addTeameLayout:
                Intent intent1 = new Intent(AddTournament.this, TeamListActivity.class);
                intent1.putExtra("CreateMatch", true);
                startActivityForResult(intent1, Contants.REQ_PAGE_COMMUNICATOR);
                break;
            case R.id.addumpireLayout:
                Intent umintent = new Intent(AddTournament.this, UmpireListActivity.class);
                umintent.putExtra("CreateMatch", true);
                startActivityForResult(umintent, Contants.REQ_PAGE_COMMUNICATOR);
                break;

        }
    }

    private void setstartDate() {
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
                startdateTxt.setText(sdf.format(myCalendar.getTime()));
            }
        };
        startdatepicker = new DatePickerDialog(AddTournament.this, todate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setendDate() {
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
                enddateTxt.setText(sdf.format(myCalendar.getTime()));
            }
        };
        enddatepicker = new DatePickerDialog(AddTournament.this, todate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void addMoreFacilites(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(AddTournament.this);
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_facilities.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setFullname(addviewedtxt);
        allfacilites.add(addViewDynamically);
    }


    public void addMorerule_regulation(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(AddTournament.this);
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_rule_regulation.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setFullname(addviewedtxt);
        rulesregulations.add(addViewDynamically);


    }


    public void addMoreprice(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(AddTournament.this);
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_rule_price.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setFullname(addviewedtxt);
        price.add(addViewDynamically);
    }

    private void getAddTournamentData() {
        if (Utility.isOnline(AddTournament.this)) {
            ASTProgressBar dotDialog = new ASTProgressBar(AddTournament.this);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.tournamentForm;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(AddTournament.this);
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getAddTournamentData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setStateSpinnerValue(serviceData);
                            }
                        } else {
                            Toast.makeText(AddTournament.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddTournament.this, Contants.Error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (dotDialog.isShowing()) {
                dotDialog.dismiss();
            }
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, AddTournament.this);//off line msg....
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
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(AddTournament.this, R.layout.spinner_row, stateList);
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
            ArrayAdapter<String> citydapter = new ArrayAdapter<String>(AddTournament.this, R.layout.spinner_row, cityList);
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


    private void addTournamentServer() {
        String matchtype = "0";
        if (format.equals("Test")) {
            matchtype = "1";
        } else if (format.equals("ODI")) {
            matchtype = "2";
        } else if (format.equals("T-20")) {
            matchtype = "3";
        }

        String statuss = "0";
        if (status.equals("Open")) {
            statuss = "1";
        } else if (format.equals("Close")) {
            statuss = "2";
        }
        if (ASTUIUtil.isOnline(AddTournament.this)) {
            final String url = Contants.BASE_URL + Contants.tournamentAPi;
            HashMap<String, String> payloadList = new HashMap<String, String>();
            try {
                payloadList.put("name", name);
                payloadList.put("tournament_address", tournament_address);
                payloadList.put("tournament_state", stateId);
                payloadList.put("tournament_city", cityId);
                payloadList.put("tournament_zipcode", tournament_zipcode);
                payloadList.put("overs", overs);
                payloadList.put("format", matchtype);
                payloadList.put("start_date", start_date);
                payloadList.put("end_date", end_date);
                payloadList.put("entry_fees", entry_fees);
                payloadList.put("status", statuss);
                payloadList.put("about_tournament", about_tournament);

                JSONArray jsonArrayfacilities = new JSONArray();
                if (allfacilites != null && allfacilites.size() > 0) {
                    for (AddViewDynamically viewDynamically : allfacilites) {
                        jsonArrayfacilities.put(viewDynamically.getFullname().getText().toString());
                    }
                }
                payloadList.put("facilities", jsonArrayfacilities.toString());

                JSONArray jsonArrayrules_regulations = new JSONArray();
                if (rulesregulations != null && rulesregulations.size() > 0) {
                    for (AddViewDynamically viewDynamically : rulesregulations) {
                        jsonArrayrules_regulations.put(viewDynamically.getFullname().getText().toString());
                    }
                }
                payloadList.put("rules_regulations", jsonArrayrules_regulations.toString());

                JSONArray jsonArrayrulesprizes = new JSONArray();
                if (price != null && price.size() > 0) {
                    for (AddViewDynamically viewDynamically : price) {
                        jsonArrayrulesprizes.put(viewDynamically.getFullname().getText().toString());
                    }
                }
                payloadList.put("prizes", jsonArrayrulesprizes.toString());
                payloadList.put("teams", selectTeamId);
                payloadList.put("umpires", selectUmpireId);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelperWithProgress fileUploaderHelper = new FileUploaderHelperWithProgress(AddTournament.this, payloadList, multipartBody, url) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null && data.isStatus()) {
                        ASTUIUtil.showToast(AddTournament.this, data.getMessage());
                    } else {
                        ASTUIUtil.showToast(AddTournament.this, "Tournament not added!");
                    }
                }
            };
            fileUploaderHelper.execute();
        }
    }


    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE = MediaType.parse("image/png");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (imgFile != null && imgFile.exists()) {
            multipartBody.addFormDataPart("display_picture", imgFile.getName(), RequestBody.create(MEDIA_TYPE, imgFile));
        }

        return multipartBody;
    }


    private void showToast(String message) {
        Toast.makeText(AddTournament.this, message, Toast.LENGTH_LONG).show();
    }


    public boolean isvalidateSignup() {
        name = tournamentName.getText().toString();
        tournament_address = venueAddress.getText().toString();
        tournament_state = stateSpinner.getSelectedItem().toString();
        tournament_city = citySpinner.getSelectedItem().toString();
        tournament_zipcode = zipCode.getText().toString();
        overs = noofOver.getText().toString();
        format = formateSpinner.getSelectedItem().toString();
        start_date = startdateTxt.getText().toString();
        end_date = enddateTxt.getText().toString();
        entry_fees = enteryFee.getText().toString();
        status = statusSpinner.getSelectedItem().toString();
        about_tournament = aboutTournament.getText().toString();
        //    facilities = timeTxt.getText().toString();
        //   rules_regulations = dateTxt.getText().toString();
        //   prizes = dateTxt.getText().toString();
        //  display_picture = dateTxt.getText().toString();
        //  teams = dateTxt.getText().toString();


        if (name.equals("")) {
            Toast.makeText(AddTournament.this, "Please Enter Tournament Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_address.equals("")) {
            Toast.makeText(AddTournament.this, "Please Enter Tournament Venue", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_state.equals("") || tournament_state.equalsIgnoreCase("Select State")) {
            Toast.makeText(AddTournament.this, "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_city.equals("") || tournament_city.equalsIgnoreCase("Select City")) {
            Toast.makeText(AddTournament.this, "Please Select City", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tournament_zipcode.equals("")) {
            Toast.makeText(AddTournament.this, "Please Enter Zip Code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (overs.equals("")) {
            Toast.makeText(AddTournament.this, "Please Enter Over", Toast.LENGTH_SHORT).show();
            return false;
        } else if (format.equals("")) {
            Toast.makeText(AddTournament.this, "Please Enter Match Formate", Toast.LENGTH_SHORT).show();
            return false;
        } else if (start_date.equals("")) {
            Toast.makeText(AddTournament.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (end_date.equals("")) {
            Toast.makeText(AddTournament.this, "Please Select End Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (entry_fees.equals("")) {
            Toast.makeText(AddTournament.this, "Please Enter Entry Fees", Toast.LENGTH_SHORT).show();
            return false;
        } else if (about_tournament.equals("")) {
            Toast.makeText(AddTournament.this, "Please Enter About Tournament", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    /*
     *
     * Parse and Validate Teame Service Data
     */
    private void parseTournamentData(String result) {
        if (result != null) {
            try {
                JSONObject jsonRootObject = new JSONObject(result);
                String jsonStatus = jsonRootObject.optString("Status").toString();
                if (jsonStatus.equals("success")) {
                    JSONObject object = jsonRootObject.optJSONObject("data");
                    String userName = object.optString("name").toString();
                    Toast.makeText(AddTournament.this, "Tournament added successfully", Toast.LENGTH_LONG).show();
                } else {
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTournament.this);
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
        File file = new File(Utility.getExternalStorageFilePathCreateAppDirectory(AddTournament.this) + File.separator + fileName);
        String providerName = String.format(Locale.ENGLISH, "%s%s", AddTournament.this.getPackageName(), ".imagepicker.provider");
        Uri uri = FileProvider.getUriForFile(AddTournament.this, providerName, file);
        FilePickerHelper.grantAppPermission(AddTournament.this, intent, uri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, FilePickerHelper.CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }

    private void setImageView() {
        String academicPic = "academicPic" + System.currentTimeMillis() + ".png";
        File file = new File(Utility.getExternalStorageFilePathCreateAppDirectory(AddTournament.this) + File.separator + "academic.png");
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
                if (data != null && data.getExtras() != null) {
                    String selectedTeam = data.getExtras().getString("selectedTeam");
                    if (selectedTeam != null && !selectedTeam.equals("")) {
                        selectTeamId = data.getExtras().getString("selectTeamId");
                        ArrayList<Team> teamList = new Gson().fromJson(selectedTeam, new TypeToken<ArrayList<Team>>() {
                        }.getType());
                        for (Team team : teamList) {
                            showSelectedTeam(team);
                        }
                    }

                    String selectedUmpire = data.getExtras().getString("selectedUmpire");
                    if (selectedUmpire != null && !selectedUmpire.equals("")) {
                        selectUmpireId = data.getExtras().getString("selectUmpireId");
                        ArrayList<Data> teamList = new Gson().fromJson(selectedUmpire, new TypeToken<ArrayList<Data>>() {
                        }.getType());
                        for (Data umpire : teamList) {
                            showSelectedUmpire(umpire);
                        }
                    }
                }
            }
        }
    }

    public void showSelectedUmpire(Data data) {
        LayoutInflater inflater = LayoutInflater.from(AddTournament.this);
        View inflatedLayout = inflater.inflate(R.layout.create_match_team_layout, null);
        ImageView imageView = inflatedLayout.findViewById(R.id.imageView);
        TextView name = inflatedLayout.findViewById(R.id.name);
        name.setText(data.getFull_name());
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        userId.setText(data.getUnique_id());
        Picasso.with(AddTournament.this).load(Contants.BASE_URL + data.getProfile_picture()).placeholder(R.drawable.ic_uuuser).into(imageView);
        UmpireViewLayout.addView(inflatedLayout);
    }

    public void showSelectedTeam(Team team) {
        LayoutInflater inflater = LayoutInflater.from(AddTournament.this);
        View inflatedLayout = inflater.inflate(R.layout.create_match_team_layout, null);
        ImageView imageView = inflatedLayout.findViewById(R.id.imageView);
        TextView name = inflatedLayout.findViewById(R.id.name);
        name.setText(team.getName());
        TextView userId = inflatedLayout.findViewById(R.id.userId);
        userId.setText(team.getUnique_id());
        Picasso.with(AddTournament.this).load(Contants.BASE_URL + team.getTeam_thumbnail()).placeholder(R.drawable.ic_uuuser).into(imageView);
        TeamViewLayout.addView(inflatedLayout);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        Uri uri = Utility.getImageUri(AddTournament.this, thumbnail);

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
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(AddTournament.this.getContentResolver(), data.getData());
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

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                astProgressBar = new ASTProgressBar(AddTournament.this);
                astProgressBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {

                Boolean flag = false;
                File sdcardPath = Utility.getExternalStorageFilePath(AddTournament.this);
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
                MediaScannerConnection.scanFile(AddTournament.this, new String[]{imgFile.toString()}, null,
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
                Picasso.with(AddTournament.this).load(imgFile).into(addImageView);
                astProgressBar.dismiss();
            }
        }.execute();

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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