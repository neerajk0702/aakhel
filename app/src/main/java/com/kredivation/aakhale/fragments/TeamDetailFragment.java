package com.kredivation.aakhale.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.MatchPlayedAdapter;
import com.kredivation.aakhale.adapter.TopPlayersAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Match_played;
import com.kredivation.aakhale.model.Team_player;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class TeamDetailFragment extends Fragment {
    ImageView imageView, fab;
    TextView nametxt, ratingTxt, wonmatch, Tournamentwon;
    View view;
    Bundle bundle;
    ListView matchPlayeList;
    ArrayList<Team_player> team_playerArrayList = new ArrayList<>();
    ArrayList<Match_played> match_playedList = new ArrayList<>();
    LinearLayout teameMemberImageView, matchPlayedView;

    public TeamDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_detail, container, false);
        init();
        return view;

    }

    public void init() {
        imageView = view.findViewById(R.id.imageView);
        fab = view.findViewById(R.id.fab);
        nametxt = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        wonmatch = view.findViewById(R.id.wonmatch);
        Tournamentwon = view.findViewById(R.id.Tournamentwon);
        // gallaryIMageviewPager = view.findViewById(R.id.tgallaryIMageviewPager);
        matchPlayedView = view.findViewById(R.id.matchPlayedView);
        imageView = view.findViewById(R.id.imageView);
        teameMemberImageView = view.findViewById(R.id.teameMemberImageView);
        dataToView();
    }


    public void dataToView() {
        getTeamsDetails();
        //  teamsPlayerAdapter = new TeamsPlayerAdapter(getContext(), team_playerArrayList);
        //gallaryIMageviewPager.setAdapter(teamsPlayerAdapter);
    }

    ASTProgressBar astProgressBar;

    // getTeamsDetails
    private void getTeamsDetails() {
        bundle = getArguments();
        int id = getArguments().getInt("id");

        if (Utility.isOnline(getContext())) {
            astProgressBar = new ASTProgressBar(getContext());
            astProgressBar.show();
            String serviceURL = Contants.BASE_URL + Contants.teamCreate + "/" + id;
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, "getTeamsDetails", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                JSONObject jsonObject = mainObj.optJSONObject("basic_info");
                                if (jsonObject != null) {
                                    astProgressBar.dismiss();
                                    int id = jsonObject.optInt("id");
                                    String name = jsonObject.optString("name");
                                    String unique_id = jsonObject.optString("unique_id");
                                    String user_id = jsonObject.optString("user_id");
                                    String created_at = jsonObject.optString("created_at");
                                    String updated_at = jsonObject.optString("updated_at");
                                    int is_active = jsonObject.optInt("is_active");
                                    String about_team = jsonObject.optString("about_team");
                                    String team_city = jsonObject.optString("team_city");
                                    String team_state = jsonObject.optString("team_state");
                                    String team_country = jsonObject.optString("team_country");
                                    String team_zipcode = jsonObject.optString("team_zipcode");
                                    String team_thumbnail = jsonObject.optString("team_thumbnail");
                                    nametxt.setText(name);
                                    ratingTxt.setText("");
                                    wonmatch.setText("");
                                    Tournamentwon.setText("");

                                    Picasso.with(getContext()).load("http://cricket.vikaskumar.info/" + team_thumbnail)
                                            .placeholder(R.drawable.noimage).into(imageView);

                                    JSONArray team_player = mainObj.optJSONArray("team_player");
                                    if (team_player != null) {
                                        for (int i = 0; i < team_player.length(); i++) {
                                            try {
                                                Team_player team_player1 = new Team_player();
                                                JSONObject object = team_player.getJSONObject(i);
                                                String pname = object.optString("name");
                                                String pstatus = object.optString("status");
                                                String profile_picture = object.optString("profile_picture");
                                                addTeameMember(pname, pstatus, profile_picture);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }


                                    JSONArray match_played = mainObj.optJSONArray("match_played");
                                    if (match_played != null) {
                                        for (int i = 0; i < match_played.length(); i++) {
                                            try {
                                                Match_played match_played1 = new Match_played();
                                                JSONObject object = match_played.getJSONObject(i);
                                                String pname = object.optString("name");
                                                String pstatus = object.optString("status");
                                                String address = object.optString("address");
                                                String city = object.optString("city");
                                                String state = object.optString("state");
                                                String zipcode = object.optString("zipcode");
                                                String date = object.optString("date");

                                                String addressstr = address + "," + city + "," + state + "," + city + "," + zipcode;
                                                addMatchPlayed(pname, addressstr, date);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
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


    //addSportS
    public void addTeameMember(String name, String statusstr, String imagPathe) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.teame_player_row, null);
        ImageView playerImage = inflatedLayout.findViewById(R.id.playerImage);
        TextView playeName = inflatedLayout.findViewById(R.id.playeName);
        TextView status = inflatedLayout.findViewById(R.id.status);
        playeName.setText(name);
        if (statusstr.equalsIgnoreCase("0")) {
            status.setText("PENDING");
            status.setTextColor(getContext().getResources().getColor(R.color.orange_color));
        } else if (statusstr.equalsIgnoreCase("0")) {
            status.setText("APPROVED");
            status.setTextColor(getContext().getResources().getColor(R.color.green2));
        }


        if (statusstr != null && !statusstr.equals("")) {
            Picasso.with(getContext()).load("http://cricket.vikaskumar.info/" + imagPathe)
                    .placeholder(R.drawable.noimage).into(playerImage);
        }

        teameMemberImageView.addView(inflatedLayout);

    }

    //addManager
    public void addMatchPlayed(String nametxt, String address, String datetxt) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.matchplay_row, null);
        TextView tmeavsteam = inflatedLayout.findViewById(R.id.tmeavsteam);
        TextView stadiumaddress = inflatedLayout.findViewById(R.id.stadiumaddress);
        TextView date = inflatedLayout.findViewById(R.id.date);
        tmeavsteam.setText(nametxt);
        stadiumaddress.setText(address);
        date.setText(datetxt);
        matchPlayedView.addView(inflatedLayout);

    }
}
