package com.kredivation.aakhale.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.TeamListActivity;
import com.kredivation.aakhale.adapter.MyPostItemRecyclerViewAdapter;
import com.kredivation.aakhale.adapter.TeamsAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.fragments.dummy.DummyContent;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.ContentDataAsArray;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.Post_info;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostItemFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    ASTProgressBar postDilog;
    ArrayList<Post_info> postList;
    RecyclerView recyclerView;
    MyPostItemRecyclerViewAdapter rcAdapter;
    int currentPage = 1;
    LinearLayoutManager mLayoutManager;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int total_pages = 1;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;

    public PostItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostItemFragment newInstance(int columnCount) {
        PostItemFragment fragment = new PostItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postitem_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        //mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        //  recyclerView.setLayoutManager(mLayoutManager);
        loaddataProgress = view.findViewById(R.id.loaddataProgress);
        postList = new ArrayList<>();

       /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        });*/

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
                getPostLIst();
            }
        });

        getPostLIst();

        return view;
    }


/*
    private void getPostLIst() {
        if (Utility.isOnline(getContext())) {
            postDilog = new ASTProgressBar(getContext());
            //chatlistProgress.show();
            // JSONObject object = new JSONObject();
           */
/* try {
                object.put("user_id", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
*//*

            String serviceURL = Contants.BASE_URL + Contants.post;
            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, "getAllPostList", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parsePostData(result);
                    } else {
                        if (postDilog.isShowing()) {
                            postDilog.dismiss();
                        }
                        Utility.alertForErrorMessage(Contants.Error, getContext());
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }

    public void parsePostData(String result) {
        if (result != null) {
            postList = new ArrayList<>();
            final ContentDataAsArray serviceData = new Gson().fromJson(result, ContentDataAsArray.class);
            if (serviceData != null) {
                if (serviceData.isStatus()) {
                    if (serviceData.getData() != null) {
                        new AsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... voids) {
                                Boolean flag = false;
                                postList.clear();
                                for (Data data : serviceData.getData()) {
                                    postList.add(data);
                                }
                                flag = true;
                                return flag;
                            }

                            @Override
                            protected void onPostExecute(Boolean flag) {
                                super.onPostExecute(flag);
                                if (flag) {
                                    if (postList != null && postList.size() > 0) {
                                        rcAdapter = new MyPostItemRecyclerViewAdapter(postList, getContext());
                                        recyclerView.setAdapter(rcAdapter);
                                    }
                                }
                                if (postDilog.isShowing()) {
                                    postDilog.dismiss();
                                }
                            }
                        }.execute();
                    } else {
                        if (postDilog.isShowing()) {
                            postDilog.dismiss();
                        }
                    }
                } else {
                    if (postDilog.isShowing()) {
                        postDilog.dismiss();
                    }
                }
            }
        }
    }
*/


    //get getTeamList
    private void getPostLIst() {
        if (Utility.isOnline(getContext())) {
            loaddataProgress.setVisibility(View.VISIBLE);
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
                                total_pages = mainObj.optInt("total_pages");
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
                                        rcAdapter = new MyPostItemRecyclerViewAdapter(postList, getContext());
                                        recyclerView.setAdapter(rcAdapter);
                                    }
                                    loading = true;
                                    loaddataProgress.setVisibility(View.GONE);
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
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
    public void onRefresh() {
        currentPage=1;
        mSwipeRefreshLayout.setRefreshing(true);
        postList.clear();
        getPostLIst();
    }

    @Override
    public void onClick(View v) {

    }
}
