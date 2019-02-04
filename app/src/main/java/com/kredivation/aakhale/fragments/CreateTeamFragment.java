package com.kredivation.aakhale.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddTournamentImageAdapter;
import com.kredivation.aakhale.adapter.GridViewAdapter;
import com.kredivation.aakhale.adapter.SportsServiceGridViewAdapter;
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
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.State;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FilePickerHelper;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment implements View.OnClickListener {
    View view;
    ASTEditText teameName, aboutTeam, zipCode;
    Spinner stateSpinner, citySpinner;
    TextView addMoreView, addMoreViewImage, addplayelbl;
    ASTButton continuebtn;
    private GridViewAdapter gridAdapter;
    private ArrayList<String> stateIdList;
    private ArrayList<String> cityIdList;
    private String stateId, cityId;
    List<City> cityInfoList;
    LinearLayout addPlayerLayout;
    ASTEditText addviewedtxt;
    List<AddViewDynamically> addPlayerview = new ArrayList<AddViewDynamically>();
    public final int SELECT_PHOTO = 102;
    public final int REQUEST_CAMERA = 101;
    private String userChoosenTask;
    ASTProgressBar astProgressBar;
    ImageView image;
    File imgFile;
    String name, aboutTeamstr, stateSpinnerstr, citySpinnerstr, zipCodestr;
    public CreateTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);
        init();
        getActivity().setTitle("Create Team");
        return view;
    }

    public void init() {
        image = view.findViewById(R.id.image);
        teameName = view.findViewById(R.id.teameName);
        aboutTeam = view.findViewById(R.id.aboutTeam);
        zipCode = view.findViewById(R.id.zipCode);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        addMoreView = view.findViewById(R.id.addMoreView);
        addMoreViewImage = view.findViewById(R.id.addMoreViewImage);
        continuebtn = view.findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(this);
        addplayelbl = view.findViewById(R.id.addplayelbl);
        addPlayerLayout = view.findViewById(R.id.addPlayerLayout);
        addMoreView.setOnClickListener(this);
        addMoreViewImage.setOnClickListener(this);
        String s = "Add Player(Enter Unique Id)";
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1f), 0, 10, 0); // set size
        //   ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        addplayelbl.setText(ss1);
        getTeamFormData();
        addMorePLayer();
    }


    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreView:
               /* fnNewDialog = new SearchDialog(getContext()) {
                    @Override
                    public void actionPerform() {

                    }
                };

                fnNewDialog.show();*/
                addMorePLayer();
                break;

            case R.id.addMoreViewImage:
                selectImage();
                break;

            case R.id.continuebtn:
                if (isvalidateSignup()) {
                    addTeam();
                }
        }
    }

    public void addMorePLayer() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText("Enter Unique Id");
        addPlayerLayout.addView(inflatedLayout);
        AddViewDynamically addViewDynamically = new AddViewDynamically();
        addViewDynamically.setFullname(addviewedtxt);
        addPlayerview.add(addViewDynamically);
    }


    private void getTeamFormData() {
        if (Utility.isOnline(getContext())) {
            ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + Contants.teamForm;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getTeamFormData", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        if (result != null) {
                            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
                            if (serviceData != null) {
                                setSpinnerValue(serviceData);
                            }
                        } else {
                            Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (dotDialog.isShowing()) {
                dotDialog.dismiss();
            }
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
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
            ArrayAdapter<String> statedapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, stateList);
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
            ArrayAdapter<String> citydapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, cityList);
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


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        Uri uri = Utility.getImageUri(getContext(), thumbnail);

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
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
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
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                astProgressBar = new ASTProgressBar(getContext());
                astProgressBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                Boolean flag = false;
                File sdcardPath = Utility.getExternalStorageFilePath(getContext());
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
                MediaScannerConnection.scanFile(getContext(), new String[]{imgFile.toString()}, null,
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
                Picasso.with(getActivity()).load(imgFile).into(image);
                // setImageIntoList(imgFile);

                astProgressBar.dismiss();
            }
        }.execute();

        return true;
    }



    public boolean isvalidateSignup() {
        name = teameName.getText().toString();
        aboutTeamstr = aboutTeam.getText().toString();
        stateSpinnerstr = stateSpinner.getSelectedItem().toString();
        citySpinnerstr = citySpinner.getSelectedItem().toString();
        zipCodestr = zipCode.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getContext(), "Please Enter Team Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (aboutTeamstr.equals("")) {
            Toast.makeText(getContext(), "Please Enter About Team", Toast.LENGTH_SHORT).show();
            return false;
        } else if (stateSpinnerstr.equals("") || stateSpinnerstr.equalsIgnoreCase("Select State")) {
            Toast.makeText(getContext(), "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        } else if (citySpinnerstr.equals("") || citySpinnerstr.equalsIgnoreCase("Select City")) {
            Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_SHORT).show();
            return false;
        } else if (zipCodestr.equals("")) {
            Toast.makeText(getContext(), "Please Enter Zip Code", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }


    private void addTeam() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar dotDialog = new ASTProgressBar(getContext());
            dotDialog.show();
            final String url = Contants.BASE_URL + Contants.teamCreate;
            HashMap<String, String> payloadList = new HashMap<String, String>();
            try {
                payloadList.put("name", name);
                payloadList.put("about_team", aboutTeamstr);
                payloadList.put("team_state", stateId);
                payloadList.put("team_city", cityId);
                payloadList.put("team_zipcode", zipCodestr);
                payloadList.put("team_player", "aku001,aku002,aku003");
            } catch (Exception e) {
                //e.printStackTrace();
            }
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelperWithProgress fileUploaderHelper = new FileUploaderHelperWithProgress(getContext(), payloadList, multipartBody, url) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        ASTUIUtil.showToast(getContext(), "Team  add! Success");
                    } else {
                        ASTUIUtil.showToast(getContext(), "Team not add!");
                    }
                    if (dotDialog.isShowing()) {
                        dotDialog.dismiss();
                    }
                }
            };
            fileUploaderHelper.execute();
        }
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
                    Toast.makeText(getContext(), "Tournament added successfully", Toast.LENGTH_LONG).show();
                } else {
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //
            }
        }

    }

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE = MediaType.parse("image/png");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (imgFile != null && imgFile.exists()) {
            multipartBody.addFormDataPart("team_thumbnail", imgFile.getName(), RequestBody.create(MEDIA_TYPE, imgFile));
        }
        return multipartBody;
    }

}
