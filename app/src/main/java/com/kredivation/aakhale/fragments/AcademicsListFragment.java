package com.kredivation.aakhale.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AcadamicAdapter;
import com.kredivation.aakhale.adapter.TeamsAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcademicsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcademicsListFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ASTFontTextIconView sortBy;
    ListView acadamicListView;


    View view;
    RecyclerView rvList;
    ASTButton saveBtn;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Academics> academicsArrayList;
    private AcadamicAdapter acadamicAdapter;


    public AcademicsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddHomeMyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcademicsListFragment newInstance(String param1, String param2) {
        AcademicsListFragment fragment = new AcademicsListFragment();
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

    private Context context;
    int total_pages = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.academics_fragments, container, false);
        init();
        getActivity().setTitle("Academies");
        return view;
    }


    public void init() {
        sortBy = view.findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        rvList = view.findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        loaddataProgress = view.findViewById(R.id.loaddataProgress);
        academicsArrayList = new ArrayList<>();
        acadamicAdapter = new AcadamicAdapter(getContext(), academicsArrayList);
        rvList.setAdapter(acadamicAdapter);

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
                                getAcademyList();
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
                getAcademyList();
            }
        });
    }


    //get getTeamList
    private void getAcademyList() {
        if (Utility.isOnline(getContext())) {
            loaddataProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.addAcademy + "?page=" + currentPage;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getAcademyList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                total_pages = mainObj.optInt("total_pages");
                                JSONArray dataArray = mainObj.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        Academics academicsData = new Academics();
                                        JSONObject jsonObject = dataArray.getJSONObject(i);

                                        String academy_owner = jsonObject.optString("academy_owner");
                                        String academy_city = jsonObject.optString("academy_city");
                                        String academy_description = jsonObject.optString("academy_description");
                                        String academy_name = jsonObject.optString("academy_name");
                                        int is_active = jsonObject.optInt("is_active");
                                        String academy_country = jsonObject.optString("academy_country");
                                        String academy_photos = jsonObject.optString("academy_photos");
                                        String team_member = jsonObject.optString("team_member");
                                        int id = jsonObject.optInt("id");
                                        String unique_id = jsonObject.optString("unique_id");
                                        String academy_zipcode = jsonObject.optString("academy_zipcode");
                                        String updated_at = jsonObject.optString("updated_at");
                                        String created_at = jsonObject.optString("created_at");
                                        String user_id = jsonObject.optString("user_id");
                                        String academy_manager = jsonObject.optString("academy_manager");
                                        String academy_state = jsonObject.optString("academy_state");
                                        String academy_coach = jsonObject.optString("academy_coach");
                                        String academy_sports = jsonObject.optString("academy_sports");
                                        String street_address = jsonObject.optString("street_address");
                                        String academy_services = jsonObject.optString("academy_services");

                                        academicsData.setId(id);
                                        academicsData.setAcademy_owner(academy_owner);
                                        academicsData.setAcademy_city(academy_city);
                                        academicsData.setAcademy_description(academy_description);
                                        academicsData.setAcademy_name(academy_name);
                                        academicsData.setIs_active(is_active);
                                        academicsData.setAcademy_country(academy_country);
                                        academicsData.setAcademy_photos(academy_photos);
                                        academicsData.setTeam_member(team_member);
                                        academicsData.setUnique_id(unique_id);
                                        academicsData.setAcademy_zipcode(academy_zipcode);
                                        academicsData.setUpdated_at(updated_at);
                                        academicsData.setCreated_at(created_at);
                                        academicsData.setUser_id(user_id);
                                        academicsData.setAcademy_manager(academy_manager);
                                        academicsData.setAcademy_state(academy_state);
                                        academicsData.setAcademy_coach(academy_coach);
                                        academicsData.setAcademy_sports(academy_sports);
                                        academicsData.setStreet_address(street_address);
                                        academicsData.setAcademyServices(academy_services);
                                        academicsArrayList.add(academicsData);
                                    }
                                }
                                acadamicAdapter.notifyDataSetChanged();
                                loading = true;
                                loaddataProgress.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
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
        mSwipeRefreshLayout.setRefreshing(true);
        academicsArrayList.clear();
        getAcademyList();
    }


}