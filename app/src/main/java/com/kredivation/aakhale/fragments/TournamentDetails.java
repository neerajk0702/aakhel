package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.GroundAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Tournament;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TournamentDetails extends Fragment {

    ImageView displayImage;
    TextView nametxt, statusttxt, uniqeIdtxt, dateTime,
            aboutTournament, teamneName, over, teametxt, formatetxt,
            venueAddress, enteryFee;
    Bundle bundle;
    View view;
    LinearLayout facilitiesesView, priceView, UmpireView, ruleRegulationView, teamesView;

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
        getActivity().setTitle("Tournament Detail");
        displayImage = view.findViewById(R.id.displayImage);
        nametxt = view.findViewById(R.id.name);
        statusttxt = view.findViewById(R.id.status);
        uniqeIdtxt = view.findViewById(R.id.uniqeId);
        dateTime = view.findViewById(R.id.dateTime);
        aboutTournament = view.findViewById(R.id.aboutTournament);
        over = view.findViewById(R.id.over);
        teametxt = view.findViewById(R.id.teame);
        formatetxt = view.findViewById(R.id.formate);
        facilitiesesView = view.findViewById(R.id.facilitiesesView);
        enteryFee = view.findViewById(R.id.enteryFee);
        venueAddress = view.findViewById(R.id.venueAddress);


        ruleRegulationView = view.findViewById(R.id.ruleRegulationView);
        facilitiesesView = view.findViewById(R.id.facilitiesesView);
        priceView = view.findViewById(R.id.priceView);
        UmpireView = view.findViewById(R.id.UmpireView);
        teamesView = view.findViewById(R.id.teamesView);

        getTournamentDetailMatch();
    }


    ASTProgressBar astProgressBar;

    //get GgetTournamentMatch
    private void getTournamentDetailMatch() {
        bundle = getArguments();
        int id = getArguments().getInt("id");
        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
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
                                JSONObject jsonObject = mainObj.optJSONObject("data");
                                if (jsonObject != null) {
                                    astProgressBar.dismiss();
                                    JSONObject basic_info = jsonObject.optJSONObject("basic_info");
                                    if (basic_info != null) {
                                        String end_date = basic_info.optString("end_date");
                                        String unique_id = basic_info.optString("unique_id");
                                        int is_active = basic_info.optInt("is_active");
                                        String tournament_zipcode = basic_info.optString("tournament_zipcode");
                                        String format = basic_info.optString("format");
                                        String display_picture = basic_info.optString("display_picture");
                                        String created_at = basic_info.optString("created_at");
                                        String tournament_country = basic_info.optString("tournament_country");
                                        String overs = basic_info.optString("overs");
                                        String tournament_city = basic_info.optString("tournament_city");
                                        String entry_fees = basic_info.optString("entry_fees");
                                        String updated_at = basic_info.optString("updated_at");
                                        String about_tournament = basic_info.optString("about_tournament");
                                        String user_id = basic_info.optString("user_id");
                                        String tournament_state = basic_info.optString("tournament_state");
                                        String tournament_address = basic_info.optString("tournament_address");
                                        String name = basic_info.optString("name");
                                        int id = basic_info.optInt("id");
                                        String facilities = basic_info.optString("facilities");
                                        String start_date = basic_info.optString("start_date");
                                        int statuss = basic_info.optInt("status");
                                        if (display_picture != null && !display_picture.equals("")) {
                                            Picasso.with(getContext()).load("http://cricket.vikaskumar.info/" + display_picture)
                                                    .placeholder(R.drawable.noimage).into(displayImage);
                                        }

                                        nametxt.setText(name);
                                        if (statuss == 1) {
                                            statusttxt.setText("Open");
                                        }
                                        uniqeIdtxt.setText(user_id);
                                        dateTime.setText(start_date);
                                        aboutTournament.setText(about_tournament);
                                        over.setText(overs);
                                        teametxt.setText("");
                                        formatetxt.setText(format);
                                        enteryFee.setText(entry_fees);
                                        venueAddress.setText(tournament_address);

                                        JSONArray tournament_teamArray = jsonObject.optJSONArray("tournament_team");
                                        if (tournament_teamArray != null) {
                                            for (int i = 0; i < tournament_teamArray.length(); i++) {
                                                try {
                                                    JSONObject object = tournament_teamArray.getJSONObject(i);
                                                    String pname = object.optString("name");
                                                    String statusp = object.optString("status");
                                                    addSportmatchView(pname, statusp);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                        }


                                        String facilities_services = basic_info.optString("facilities");
                                      //  String facilities_servicesstr = facilities_services.substring(1, facilities_services.length() - 1);


                                        JSONArray free_servicesjsonArray = new JSONArray(facilities_services);
                                        for (int i = 0; i < free_servicesjsonArray.length(); i++) {
                                            addFreeService(free_servicesjsonArray.get(i).toString());
                                        }

                                        String prizes = basic_info.optString("prizes");

                                       // String prizesarstr = prizes.substring(1, prizes.length() - 1);


                                        JSONArray prizesarstr_servicesjsonArray = new JSONArray(prizes);
                                        for (int i = 0; i < prizesarstr_servicesjsonArray.length(); i++) {
                                            addPrice(prizesarstr_servicesjsonArray.get(i).toString());
                                        }

                                        String rules_regulations = basic_info.optString("rules_regulations");
                                      //  String strrules_regulations = rules_regulations.substring(1, rules_regulations.length() - 1);


                                        JSONArray strrules_regulations_jsonArray = new JSONArray(rules_regulations);
                                        for (int i = 0; i < strrules_regulations_jsonArray.length(); i++) {
                                            ruleRegulation(strrules_regulations_jsonArray.get(i).toString());
                                        }

                                    }
                                }

                            }
                            astProgressBar.dismiss();
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
    public void addSportmatchView(String name, String status) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.sports_item_row, null);
        TextView teamneName = inflatedLayout.findViewById(R.id.teamneName);
        ImageView matchPerview = inflatedLayout.findViewById(R.id.matchPerview);
        teamneName.setText(name);
        teamesView.addView(inflatedLayout);

    }

    int serviceCount;

    //add free service layout in runtime
    public void addFreeService(String name) {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "-");
        servicesName.setText(name);
        facilitiesesView.addView(inflatedLayout);

    }


    //add free service layout in runtime
    public void addPrice(String name) {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "-");
        servicesName.setText(name);
        priceView.addView(inflatedLayout);

    }


    //add free service layout in runtime
    public void ruleRegulation(String name) {
        serviceCount++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.freeservices_item_row, null);
        TextView servicesName = inflatedLayout.findViewById(R.id.name);
        TextView count = inflatedLayout.findViewById(R.id.count);
        count.setText(serviceCount + "-");
        servicesName.setText(name);
        ruleRegulationView.addView(inflatedLayout);

    }

}
