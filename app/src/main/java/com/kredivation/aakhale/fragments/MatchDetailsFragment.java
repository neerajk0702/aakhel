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
    RecyclerView teameList, umpiresList;

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
        teameList = view.findViewById(R.id.teameList);
        umpiresList = view.findViewById(R.id.umpiresList);
        dataToView();
    }

    public void dataToView() {
        getMatchDetails();
    }


    // getMatchDetails
    private void getMatchDetails() {
        bundle = getArguments();
        int id = getArguments().getInt("id");
        if (Utility.isOnline(getContext())) {
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
                                JSONArray dataArray = mainObj.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONArray basic_info = dataArray.getJSONArray(i);
                                        for (int j = 0; j < basic_info.length(); j++) {
                                            JSONObject jsonObject = dataArray.getJSONObject(i);
                                            int id = jsonObject.optInt("id");
                                            String name = jsonObject.optString("name");
                                            String unique_id = jsonObject.optString("unique_id");
                                            String user_id = jsonObject.optString("user_id");
                                            String created_at = jsonObject.optString("created_at");
                                            String updated_at = jsonObject.optString("updated_at");
                                            int is_active = jsonObject.optInt("is_active");
                                            String match_city = jsonObject.optString("match_city");
                                            String match_state = jsonObject.optString("match_state");
                                            String match_country = jsonObject.optString("match_country");
                                            String match_zipcode = jsonObject.optString("match_zipcode");
                                            String ground_id = jsonObject.optString("ground_id");
                                            String time = jsonObject.optString("time");
                                            String date = jsonObject.optString("date");
                                            String match_type = jsonObject.optString("match_type");
                                            String format = jsonObject.optString("format");
                                            String over = jsonObject.optString("over");

                                            nametxt.setText(name);
                                            datetime.setText(date);
                                            uniqueId.setText(unique_id);
                                            statustxt.setText(is_active);
                                            overtxt.setText(over);
                                            //  teams.setText(teams);
                                            formateMatch.setText(format);
                                            //venueAddress.setText(venueAddress);
                                            //venueAddress1.setText(name);

                                        }


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
