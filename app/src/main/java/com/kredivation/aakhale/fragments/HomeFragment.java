package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.PlayerActivity;
import com.kredivation.aakhale.activity.TeamListActivity;
import com.kredivation.aakhale.activity.TournamentList;
import com.kredivation.aakhale.activity.UmpireListActivity;
import com.kredivation.aakhale.adapter.HomePostAdapter;
import com.kredivation.aakhale.adapter.MatchPAdapter;
import com.kredivation.aakhale.adapter.TopperformanceAdapter;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Match;
import com.kredivation.aakhale.model.Post_info;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ASTFontTextIconView playersTxt, teamsTxt, umpiresTxt, stadiumsTxt, coachesTxt, tournamentTxt, PERFORMERTxt, upcommingTxt, EVENTSTxt, ACADEMICSTxt;
    LinearLayout playersLayout, teamsLayout, umpireLayout, stadiumsLayout, coachesLayout, tournamentLayout, PERFORMERLayout,
            UPCOMMINGLayout, EVENTSLayout, ACADEMICSLayout;


    List<String> list;
    int[] imageId = {
            R.drawable.ic_cricket_player,
            R.drawable.ic_team,
            R.drawable.ic_cricket_player,
            R.drawable.ic_cricket_ground,
            R.drawable.ic_team,
            R.drawable.ic_team,
            R.drawable.ic_cricket_player,
            R.drawable.ic_team,
            R.drawable.ic_cricket_player,
            R.drawable.ic_cricket_ground,
            R.drawable.ic_team,
            R.drawable.ic_team,
    };
    String[] name = {

            "Players",
            "Team",
            "Umpire",
            "Stadium",
            "Coach",
            "Tourementd",
            "Players",
            "Team",
            "Umpire",
            "Stadium",
            "Coach",
            "Tourementd",
    };

    public HomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    ArrayList<Match> matchArrayList;
    private MatchPAdapter matchPAdapter;
    private boolean matchloading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int currentPage = 1;
    private ProgressBar matchProgress;
    int match_total_pages = 1;
    private ProgressBar postProgress;
    ArrayList<Post_info> postList;
    private boolean postloading = true;
    int postpastVisiblesItems, postvisibleItemCount, posttotalItemCount;
    int postcurrentPage = 1;
    int post_total_pages = 1;
    HomePostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        playersTxt = view.findViewById(R.id.playersTxt);
        teamsTxt = view.findViewById(R.id.teamsTxt);
        umpiresTxt = view.findViewById(R.id.umpiresTxt);
        stadiumsTxt = view.findViewById(R.id.stadiumsTxt);
        coachesTxt = view.findViewById(R.id.coachesTxt);
        tournamentTxt = view.findViewById(R.id.tournamentTxt);
        PERFORMERTxt = view.findViewById(R.id.PERFORMERTxt);
        upcommingTxt = view.findViewById(R.id.upcommingTxt);
        EVENTSTxt = view.findViewById(R.id.EVENTSTxt);
        ACADEMICSTxt = view.findViewById(R.id.ACADEMICSTxt);
        playersLayout = view.findViewById(R.id.playersLayout);
        teamsLayout = view.findViewById(R.id.teamsLayout);
        umpireLayout = view.findViewById(R.id.umpireLayout);
        stadiumsLayout = view.findViewById(R.id.stadiumsLayout);
        coachesLayout = view.findViewById(R.id.coachesLayout);
        tournamentLayout = view.findViewById(R.id.tournamentLayout);
        PERFORMERLayout = view.findViewById(R.id.PERFORMERLayout);
        UPCOMMINGLayout = view.findViewById(R.id.UPCOMMINGLayout);
        EVENTSLayout = view.findViewById(R.id.EVENTSLayout);
        ACADEMICSLayout = view.findViewById(R.id.ACADEMICSLayout);
        setPostData();
        setMenuItem();
        setTopPerformanceViewPager();
        setUpcommingMatch();

        return view;
    }


    public void setPostData() {
        postProgress = view.findViewById(R.id.postProgress);
        RecyclerView rvList = view.findViewById(R.id.postrv);
        rvList.setHasFixedSize(false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvList.setLayoutManager(layoutManager);
        postList = new ArrayList<>();
        postAdapter = new HomePostAdapter(postList, getContext());
        rvList.setAdapter(postAdapter);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    postvisibleItemCount = layoutManager.getChildCount();
                    posttotalItemCount = layoutManager.getItemCount();
                    postpastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (postloading) {
                        if ((postvisibleItemCount + postpastVisiblesItems) >= posttotalItemCount) {
                            postloading = false;
                            postcurrentPage += 1;
                            if (postcurrentPage <= post_total_pages) {
                                getPostLIst();
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
        getPostLIst();

    }

    /**
     * set menu item icon and action.........
     */

    public void setMenuItem() {
        playersTxt.setText(Html.fromHtml("&#xf101;"));
        teamsTxt.setText(Html.fromHtml("&#xf100;"));
        umpiresTxt.setText(Html.fromHtml("&#xf105;"));
        stadiumsTxt.setText(Html.fromHtml("&#xf102;"));
        coachesTxt.setText(Html.fromHtml("&#xf103;"));
        tournamentTxt.setText(Html.fromHtml("&#xf104;"));
        PERFORMERTxt.setText(Html.fromHtml("&#xf105;"));
        upcommingTxt.setText(Html.fromHtml("&#xf107;"));
        EVENTSTxt.setText(Html.fromHtml("&#xf108;"));
        ACADEMICSTxt.setText(Html.fromHtml("&#xf109;"));

        stadiumsLayout.setOnClickListener(this);
        coachesLayout.setOnClickListener(this);
        tournamentLayout.setOnClickListener(this);
        umpireLayout.setOnClickListener(this);
        UPCOMMINGLayout.setOnClickListener(this);
        playersLayout.setOnClickListener(this);
        teamsLayout.setOnClickListener(this);
        ACADEMICSLayout.setOnClickListener(this);
    }

    public void setTopPerformanceViewPager() {
        DisplayMetrics metrics = getDisplayMetrics();
        List<String> metalList = Arrays.asList("R.drawable.pager1", "R.drawable.pager2", "R.drawable.pager3", "aa");
        TopperformanceAdapter topperformanceAdapter = new TopperformanceAdapter(metrics);
        MetalRecyclerViewPager viewPager = view.findViewById(R.id.topperformanceviewPager);
        viewPager.setAdapter(topperformanceAdapter);


    }

    private DisplayMetrics getDisplayMetrics() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics;
    }

    //set upcoming match
    public void setUpcommingMatch() {
        matchProgress = view.findViewById(R.id.matchProgress);
        RecyclerView rvList = view.findViewById(R.id.upcommingMatch);
        rvList.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvList.setLayoutManager(mLayoutManager);
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

                    if (matchloading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            matchloading = false;
                            currentPage += 1;
                            if (currentPage <= match_total_pages) {
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
        getMatchList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ACADEMICSLayout:
                AcademicsListFragment academicsFragment = new AcademicsListFragment();
                updateFragment(academicsFragment, null);
                break;

            case R.id.teamsLayout:
                Intent intent = new Intent(getActivity(), TeamListActivity.class);
                startActivity(intent);
                break;
            case R.id.stadiumsLayout:
                GroundFragment groundFragment = new GroundFragment();
                updateFragment(groundFragment, null);
                break;

            case R.id.coachesLayout:
                CoachesFragments coachesFragments = new CoachesFragments();
                updateFragment(coachesFragments, null);
                break;
            case R.id.playersLayout:
                Intent Playerintent = new Intent(getActivity(), PlayerActivity.class);
                startActivity(Playerintent);
                break;
            case R.id.tournamentLayout:
                Intent tintent = new Intent(getActivity(), TournamentList.class);
                startActivity(tintent);
                break;
            case R.id.UPCOMMINGLayout:
                MatchListFragment matchListFragment = new MatchListFragment();
                updateFragment(matchListFragment, null);
                break;
            case R.id.umpireLayout:
                Intent uintent = new Intent(getActivity(), UmpireListActivity.class);
                startActivity(uintent);
                break;
        }


    }

    public void updateFragment(Fragment pageFragment, Bundle bundle) {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        pageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainView, pageFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    //get getMatchList
    private void getMatchList() {
        if (Utility.isOnline(getContext())) {
            matchProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.creatematchApi + "?page=" + currentPage + "&" + "list=past";
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getMatchList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            match_total_pages = mainObj.optInt("total_pages");
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
                                matchloading = true;
                                matchProgress.setVisibility(View.GONE);
                                // mSwipeRefreshLayout.setRefreshing(false);

                            } else {
                                Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                        matchProgress.setVisibility(View.GONE);
                        // mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }

    //get getpost
    private void getPostLIst() {
        if (Utility.isOnline(getContext())) {
            postProgress.setVisibility(View.VISIBLE);
            String serviceURL = Contants.BASE_URL + Contants.post + "?page=" + currentPage;
            JSONObject object = new JSONObject();

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getAllPostList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        try {
                            JSONObject mainObj = new JSONObject(result);
                            boolean status = mainObj.optBoolean("status");
                            if (status) {
                                post_total_pages = mainObj.optInt("total_pages");
                                JSONArray dataArray = mainObj.optJSONArray("data");
                                if (dataArray != null) {
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        Post_info postdata = new Post_info();
                                        JSONObject obj = dataArray.getJSONObject(i);
                                        JSONObject jsonObject = obj.getJSONObject("data");
                                        JSONObject post_info = jsonObject.getJSONObject("post_info");


                                        int id = post_info.optInt("id");
                                        String title = post_info.optString("title");
                                        String featured_image = post_info.optString("featured_image");
                                        JSONArray featured_array = null;
                                        if (featured_image != null) {
                                            featured_array = new JSONArray(featured_image);
                                        }
                                        String description = post_info.optString("description");
                                        String author_id = post_info.optString("author_id");
                                        int is_author_admin = post_info.optInt("is_author_admin");
                                        String created_at = post_info.optString("created_at");
                                        String updated_at = post_info.optString("updated_at");
                                        int is_active = post_info.optInt("is_active");
                                        postdata.setId(String.valueOf(id));
                                        postdata.setIs_active(String.valueOf(is_active));
                                        postdata.setTitle(title);
                                        postdata.setFeatured_image(featured_array);
                                        postdata.setDescription(description);
                                        postdata.setAuthor_id(author_id);
                                        postdata.setIs_author_admin(String.valueOf(is_author_admin));
                                        postdata.setCreated_at(created_at);
                                        postdata.setUpdated_at(updated_at);
                                        postdata.setIs_active(String.valueOf(is_active));
                                        postList.add(postdata);

                                        JSONObject writer_info = jsonObject.getJSONObject("writer_info");
                                        int writer_infoid = writer_info.optInt("id");
                                        String unique_id = writer_info.optString("unique_id");
                                        String email = writer_info.optString("email");
                                        String writer_infocreated_at = writer_info.optString("created_at");
                                        String writer_infoupdated_at = writer_info.optString("updated_at");
                                        int role = writer_info.optInt("role");
                                        int writer_infois_active = writer_info.optInt("is_active");
                                        int gender = writer_info.optInt("gender");
                                        int player_role = writer_info.optInt("player_role");
                                        String full_name = writer_info.optString("full_name");


                                        String mobile = writer_info.optString("mobile");
                                        String date_of_birth = writer_info.optString("date_of_birth");
                                        String address = writer_info.optString("address");
                                        String city = writer_info.optString("city");
                                        String state = writer_info.optString("state");
                                        String country = writer_info.optString("country");
                                        String zipcode = writer_info.optString("zipcode");
                                        String users_sports = writer_info.optString("users_sports");
                                        String about = writer_info.optString("about");
                                        String experience = writer_info.optString("experience");
                                        String profile_picture = writer_info.optString("profile_picture");
                                        String auth_token = writer_info.optString("auth_token");
                                        String fee_per_match_day = writer_info.optString("fee_per_match_day");
                                        String estd = writer_info.optString("estd");
                                        String achievements = writer_info.optString("achievements");
                                        String staff = writer_info.optString("staff");
                                        String latest_photos = writer_info.optString("latest_photos");


                                    }
                                    if (postList != null && postList.size() > 0) {
                                        postAdapter.notifyDataSetChanged();
                                        postloading = true;
                                        postProgress.setVisibility(View.GONE);
                                    }
                                    //mSwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), Contants.Error, Toast.LENGTH_SHORT).show();
                        postProgress.setVisibility(View.GONE);
                        // mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }
}
