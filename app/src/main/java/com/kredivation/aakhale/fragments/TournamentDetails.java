package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.GroundAdapter;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Tournament;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TournamentDetails extends Fragment {

    ImageView displayImage, matchPerview;
    TextView name, status, uniqeId, dateTime, aboutTournament, teamneName, over, teame, formate, venueAddress, enteryFee, facilites, price, ruleRegulation, UmpireList;
    Bundle bundle;
    View view;

    public TournamentDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tournament_details, container, false);
        init();
        return view;
    }

    private void init() {
        getTournamentDetailMatch();
    }


    //get GgetTournamentMatch
    private void getTournamentDetailMatch() {
        bundle = getArguments();
        int id = getArguments().getInt("id");
        if (Utility.isOnline(getContext())) {
            String serviceURL = Contants.BASE_URL + Contants.tournamentAPi + "/" + id;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getTournamentDetailMatch", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                int total_pages = mainObj.optInt("total_pages");
                                JSONArray dataArray = mainObj.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        Tournament tournament = new Tournament();
                                        JSONObject jsonObject = dataArray.getJSONObject(i);
                                        String end_date = jsonObject.optString("end_date");
                                        String unique_id = jsonObject.optString("unique_id");
                                        int is_active = jsonObject.optInt("is_active");
                                        String rules_regulations = jsonObject.optString("rules_regulations");
                                        String tournament_zipcode = jsonObject.optString("tournament_zipcode");
                                        String format = jsonObject.optString("format");
                                        String display_picture = jsonObject.optString("display_picture");
                                        String created_at = jsonObject.optString("created_at");
                                        String tournament_country = jsonObject.optString("tournament_country");
                                        String overs = jsonObject.optString("overs");

                                        String tournament_city = jsonObject.optString("tournament_city");

                                        String entry_fees = jsonObject.optString("entry_fees");

                                        String updated_at = jsonObject.optString("updated_at");

                                        String about_tournament = jsonObject.optString("about_tournament");

                                        String user_id = jsonObject.optString("user_id");

                                        String tournament_state = jsonObject.optString("tournament_state");

                                        String tournament_address = jsonObject.optString("tournament_address");

                                        String prizes = jsonObject.optString("prizes");

                                        String name = jsonObject.optString("name");

                                        int id = jsonObject.optInt("id");

                                        String facilities = jsonObject.optString("facilities");

                                        String start_date = jsonObject.optString("start_date");

                                        //   int status = jsonObject.optInt("status");


                                    }
                                }
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }

}
