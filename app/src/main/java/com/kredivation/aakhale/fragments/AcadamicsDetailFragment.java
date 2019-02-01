package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

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
    ImageView fab;
    ASTProgressBar astProgressBar;
    Bundle bundle;
    int id;
    LinearLayout galeeryImageView, servicesLayoutView, teamLiastView;
    int serviceCount;

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
        fab = view.findViewById(R.id.fab);
        galeeryImageView = view.findViewById(R.id.galeeryImageView);
        servicesLayoutView = view.findViewById(R.id.servicesLayoutView);
        teamLiastView = view.findViewById(R.id.teamLiastView);
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
                                    if (academy_photosArayList != null) {
                                        for (int i = 0; i < academy_photosArayList.size(); i++) {
                                            addGalleryImage(academy_photosArayList.get(i));
                                        }

                                    }
                                    String[] free_servicesList = team_member.split(",");
                                    List<String> free_servicesLis = Arrays.asList(free_servicesList);
                                    if (free_servicesLis != null) {
                                        for (int i = 0; i < free_servicesLis.size(); i++) {
                                            addFreeService(free_servicesLis.get(i));
                                        }
                                    }






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


    //add free service layout in runtime
    public void addGalleryImage(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.gallery_row, null);
        ImageView image = inflatedLayout.findViewById(R.id.image);

        Picasso.with(getContext()).load("http://cricket.vikaskumar.info/" + name)
                .placeholder(R.drawable.noimage).into(image);
        galeeryImageView.addView(inflatedLayout);

    }


    //add free service layout in runtime
    public void addFreeService(String name) {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "-");
        servicesName.setText(name);
        servicesLayoutView.addView(inflatedLayout);

    }

}
