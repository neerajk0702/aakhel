package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.GalleryAdapter;
import com.kredivation.aakhale.adapter.PlayerAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcadamicsDetailFragment extends Fragment {
    View view;
    TextView name, ratingTxt, address, uniqueId, establishText;
    RecyclerView imageViewList, servicesList, teamsList;
    ImageView fab;
    ASTProgressBar astProgressBar;
    Bundle bundle;
    int id;

    public AcadamicsDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_acadamics_detail, container, false);
        init();
        return view;
    }

    public void init() {
        name = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        address = view.findViewById(R.id.address);
        uniqueId = view.findViewById(R.id.uniqueId);
        establishText = view.findViewById(R.id.establishText);
        imageViewList = view.findViewById(R.id.imageViewList);
        servicesList = view.findViewById(R.id.servicesList);
        teamsList = view.findViewById(R.id.teamsList);
        fab = view.findViewById(R.id.fab);
        dataToView();

    }


    public void dataToView() {
        getAcadmicsDetails();
    }


    // getMatchDetails
    private void getAcadmicsDetails() {
        bundle = getArguments();
        int id = getArguments().getInt("id");

        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.addAcademy + "/" + id;
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, "getAcadmicsDetails", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                JSONObject jsonObject = mainObj.optJSONObject("data");
                                if (jsonObject != null) {
                                    astProgressBar.dismiss();
                                    int id = jsonObject.optInt("id");
                                    String academy_name = jsonObject.optString("academy_name");
                                    String unique_id = jsonObject.optString("unique_id");
                                    String user_id = jsonObject.optString("user_id");
                                    String created_at = jsonObject.optString("created_at");
                                    String updated_at = jsonObject.optString("updated_at");
                                    int is_active = jsonObject.optInt("is_active");
                                    String street_address = jsonObject.optString("street_address");
                                    String academy_city = jsonObject.optString("academy_city");
                                    String academy_state = jsonObject.optString("academy_state");
                                    String academy_country = jsonObject.optString("academy_country");
                                    String academy_zipcode = jsonObject.optString("academy_zipcode");
                                    String academy_description = jsonObject.optString("academy_description");

                                    String academy_photos = jsonObject.optString("academy_photos");
                                    String academy_manager = jsonObject.optString("academy_manager");
                                    String academy_coach = jsonObject.optString("academy_manager");
                                    String academy_owner = jsonObject.optString("academy_manager");
                                    String team_member = jsonObject.optString("academy_manager");


                                    name.setText(academy_name);
                                    ratingTxt.setText(academy_name);
                                    address.setText(street_address);
                                    uniqueId.setText(user_id);
                                    establishText.setText(academy_description);

                                    String[] academy_photosList = academy_photos.split(",");
                                    List<String> academy_photosArayList = Arrays.asList(academy_photosList);

                                    GalleryAdapter galleryAdapter = new GalleryAdapter(getContext(), academy_photosArayList);
                                    imageViewList.setAdapter(galleryAdapter);


                                }
                            }

                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                        astProgressBar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }


}
