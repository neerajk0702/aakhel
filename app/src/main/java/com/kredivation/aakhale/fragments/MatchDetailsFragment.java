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

import com.google.gson.JsonArray;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Match;
import com.kredivation.aakhale.model.Tournament;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchDetailsFragment extends Fragment {

    View view;
    TextView nametxt, datetime, uniqueId, statustxt, overtxt, teams, formateMatch, venueAddress, venueAddress1;
    LinearLayout umpireView, teamsView;

    public MatchDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_details, container, false);
        init();
        return view;
    }

    Bundle bundle;
    int id;

    public void init() {
        bundle = getArguments();
        id = getArguments().getInt("id");
        nametxt = view.findViewById(R.id.name);
        datetime = view.findViewById(R.id.datetime);
        uniqueId = view.findViewById(R.id.uniqueId);
        statustxt = view.findViewById(R.id.status);
        overtxt = view.findViewById(R.id.over);
        teams = view.findViewById(R.id.teams);
        formateMatch = view.findViewById(R.id.formateMatch);
        venueAddress = view.findViewById(R.id.venueAddress);
        venueAddress1 = view.findViewById(R.id.venueAddress1);
        umpireView = view.findViewById(R.id.umpireView);
        teamsView = view.findViewById(R.id.teamsView);
        dataToView();
    }

    public void dataToView() {
        getMatchDetails();
    }

    ASTProgressBar astProgressBar;

    // getMatchDetails
    private void getMatchDetails() {
        bundle = getArguments();
        int id = getArguments().getInt("id");
        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.creatematchApi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getMatchDetails", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                astProgressBar.dismiss();
                                JSONObject jsondata = mainObj.optJSONObject("data");
                                JSONObject basic_infodata = jsondata.optJSONObject("basic_info");

                                String name = basic_infodata.optString("name");
                                String unique_id = basic_infodata.optString("unique_id");
                                String user_id = basic_infodata.optString("user_id");
                                String created_at = basic_infodata.optString("created_at");
                                String updated_at = basic_infodata.optString("updated_at");
                                int is_active = basic_infodata.optInt("is_active");
                                String match_city = basic_infodata.optString("match_city");
                                String match_state = basic_infodata.optString("match_state");
                                String match_country = basic_infodata.optString("match_country");
                                String match_zipcode = basic_infodata.optString("match_zipcode");
                                String ground_id = basic_infodata.optString("ground_id");
                                String time = basic_infodata.optString("time");
                                String date = basic_infodata.optString("date");
                                String match_type = basic_infodata.optString("match_type");
                                String format = basic_infodata.optString("format");
                                String over = basic_infodata.optString("over");

                                nametxt.setText(name);
                                datetime.setText(date);
                                uniqueId.setText(unique_id);


                                if (is_active == 1) {
                                    statustxt.setText("Avilable");

                                } else {
                                    statustxt.setText("Not Avilable");
                                }


                                overtxt.setText(over);
                                // teams.setText(teams);
                                formateMatch.setText(format);
                                //venueAddress.setText(venueAddress);
                                //venueAddress1.setText(name);

                                JSONArray dataArray = jsondata.optJSONArray("match_team");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject jsonObject = dataArray.getJSONObject(i);
                                        String uname = jsonObject.optString("name");
                                        String ustatus = jsonObject.optString("status");
                                        addSportmatchView(uname);
                                    }
                                }


                                JSONArray udataArray = jsondata.optJSONArray("match_umpire");
                                if (dataArray != null) {
                                    for (int i = 0; i < udataArray.length(); i++) {
                                        JSONObject jsonObject = udataArray.getJSONObject(i);
                                        String uname = jsonObject.optString("name");
                                        String country = jsonObject.optString("country");
                                        String state = jsonObject.optString("state");
                                        String ustatus = jsonObject.optString("status");
                                        addUmpire(uname, country, state);


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
    public void addSportmatchView(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.sports_item_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        ImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        teamneName.setText(name);
        teamsView.addView(inflatedLayout);

    }


    //add free service layout in runtime
    public void addUmpire(String name, String country, String state) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.umpire_itemm_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        ImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        TextView address = inflatedLayout.findViewById(R.id.address);
        address.setText(country + "," + state);
        teamneName.setText(name);
        umpireView.addView(inflatedLayout);

    }

}
