package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.GroundAdapter;
import com.kredivation.aakhale.adapter.MatchPAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.GroundData;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Match;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    RecyclerView rvList;
    ASTButton saveBtn;
    ASTFontTextIconView sortBy;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Match> matchArrayList;
    private MatchPAdapter matchPAdapter;

    public MatchListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchListFragment newInstance(String param1, String param2) {
        MatchListFragment fragment = new MatchListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    long total_pages = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_list, container, false);
        init();
        return view;
    }

    public void init() {
        saveBtn = view.findViewById(R.id.saveBtn);
        getActivity().setTitle("Matches");
        sortBy = view.findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        rvList = view.findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = view.findViewById(R.id.loaddataProgress);
        matchArrayList = new ArrayList<>();
        matchPAdapter = new MatchPAdapter(getContext(), matchArrayList);
        rvList.setAdapter(matchPAdapter);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            currentPage += 1;
                            if (currentPage <= total_pages) {
                                getMatchList();
                            }
                        }
                    }
                }
            }


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING || newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // playerAdapter.onScrolled(recyclerView);
                }
            }

        });

        // SwipeRefreshLayout
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server first time
                getMatchList();
            }
        });
    }


    //get getMatchList
    private void getMatchList() {
        if (Utility.isOnline(getContext())) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.creatematchApi + "?page=" + currentPage + "&" + "list=upcoming";
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getMatchList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            total_pages = mainObj.optInt("total_pages");
                            if (status) {
                                JSONArray mainDataArray = mainObj.getJSONArray("data");
                                if (mainDataArray != null) {
                                    for (int i = 0; i < mainDataArray.length(); i++) {
                                        JSONObject obj = mainDataArray.optJSONObject(i);
                                        JSONObject dataArray = obj.optJSONObject("data");
                                        Match matchdata = new Match();
                                        JSONArray umpireArray = dataArray.getJSONArray("match_umpire");
                                        JSONArray teamArray = dataArray.getJSONArray("match_team");
                                        JSONObject ground_data = dataArray.getJSONObject("ground_data");
                                        matchdata.setMatchUmpire(umpireArray.toString());
                                        matchdata.setMatchteam(teamArray.toString());
                                        matchdata.setMatchGround(ground_data.toString());

                                        JSONObject jsonObject = dataArray.getJSONObject("basic_info");

                                        int match_type = jsonObject.optInt("match_type");
                                        int is_active = jsonObject.optInt("is_active");
                                        JSONObject match_city = jsonObject.getJSONObject("match_city");
                                        JSONObject match_state = jsonObject.getJSONObject("match_state");
                                        JSONObject match_country = jsonObject.getJSONObject("match_country");
                                        matchdata.setMatchCity(match_city.toString());
                                        matchdata.setMatchState(match_state.toString());
                                        matchdata.setMatchCountry(match_country.toString());

                                        String format = jsonObject.optString("format");
                                        String ground_id = jsonObject.optString("ground_id");
                                        String date = jsonObject.optString("date");
                                        int id = jsonObject.optInt("id");
                                        String unique_id = jsonObject.optString("unique_id");
                                        String match_zipcode = jsonObject.optString("match_zipcode");
                                        String over = jsonObject.optString("over");
                                        String time = jsonObject.optString("time");
                                        String tournament_id = jsonObject.optString("tournament_id");
                                        String name = jsonObject.optString("name");
                                        String created_at = jsonObject.optString("created_at");
                                        String user_id = jsonObject.optString("user_id");
                                        String match_address = jsonObject.optString("match_address");
                                        matchdata.setIs_active(String.valueOf(is_active));
                                        matchdata.setId(id);
                                        matchdata.setMatch_type(String.valueOf(match_type));
                                        matchdata.setFormat(format);
                                        matchdata.setGround_id(ground_id);
                                        matchdata.setDate(date);
                                        matchdata.setUnique_id(unique_id);
                                        matchdata.setMatch_zipcode(match_zipcode);
                                        matchdata.setOver(over);
                                        matchdata.setTime(time);
                                        matchdata.setTournament_id(tournament_id);
                                        matchdata.setName(name);
                                        matchdata.setCreated_at(created_at);
                                        matchdata.setUser_id(user_id);
                                        matchdata.setMatch_address(match_address);
                                        matchArrayList.add(matchdata);
                                    }
                                }
                                matchPAdapter.notifyDataSetChanged();
                                loading = true;
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);

                            } else {
                                Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                        loaddataProgress.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortBy:
                break;
        }
    }

    @Override
    public void onRefresh() {
        currentPage=1;
        mSwipeRefreshLayout.setRefreshing(true);
        matchArrayList.clear();
        getMatchList();
    }

}
