package com.kredivation.aakhale.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.ResetPasswordActivity;
import com.kredivation.aakhale.activity.TeamListActivity;
import com.kredivation.aakhale.adapter.ChatListAdapter;
import com.kredivation.aakhale.adapter.ChatMessageAdapter;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.framework.FileUploaderHelperWithProgress;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.ChatServiceContentData;
import com.kredivation.aakhale.model.ContentData;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MultipartBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatDetailFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    View view;
    EditText et_comment;
    TextView send_comment;
    String stret_comment;
    ChatMessageAdapter mAdapter;
    ASTProgressBar chatlistProgress;
    ArrayList<Data> megList;
    Timer timer;
    Bundle bundle;
    long id;
    int currentPage = 1;
    private ProgressBar loaddataProgress;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int total_pages = 1;

    public ChatDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_detail, container, false);
        init();
        return view;
    }


    public void init() {
        bundle = getArguments();
        id = bundle.getLong("id");
        recyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loaddataProgress = view.findViewById(R.id.loaddataProgress);

        getActivity().setTitle("Chat Details");
        et_comment = view.findViewById(R.id.et_comment);
        send_comment = view.findViewById(R.id.send_comment);
        send_comment.setOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                getAllChatMessage();
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
                getAllChatMessage();
            }
        });
        dataToView();
    }


    public void dataToView() {
        megList = new ArrayList<>();
        mAdapter = new ChatMessageAdapter(getContext(), megList, id);
        recyclerView.setAdapter(mAdapter);
        callGetMessageService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_comment:
                if (isValidate()) {
                    sendMessage();
                }
                break;
        }
    }


    public boolean isValidate() {
        stret_comment = et_comment.getText().toString();
        if (stret_comment.equals("")) {
            Toast.makeText(getContext(), "Please Enter Message!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }


    private void callGetMessageService() {
        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            megList.clear();
                            getAllChatMessage();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3000); //execute in every 50000 ms
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void getAllChatMessage() {
        if (Utility.isOnline(getContext())) {
            JSONObject object = new JSONObject();
            String serviceURL = Contants.BASE_URL + Contants.CHATMSG + "?user_id=" + id;

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getAllChatMessage", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete && result != null) {
                        parseMessageData(result);
                    } else {
                        Utility.alertForErrorMessage(Contants.Error, getContext());
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }

    public void parseMessageData(String result) {
        try {
            JSONObject mainObj = new JSONObject(result);
            boolean status = mainObj.optBoolean("status");
            if (status) {
                total_pages = mainObj.optInt("total_pages");
                JSONArray dataArray = mainObj.optJSONArray("data");
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject obj = dataArray.getJSONObject(i);
                        int id = obj.optInt("id");
                        String sender_id = obj.optString("sender_id");
                        String receiver_id = obj.optString("receiver_id");
                        String message = obj.optString("message");
                        String message_media = obj.optString("message_media");
                        String created_at = obj.optString("created_at");
                        String updated_at = obj.optString("updated_at");
                        Data data = new Data();
                        data.setId(id);
                        data.setSender_id(sender_id);
                        data.setReceiver_id(receiver_id);
                        data.setMessage(message);
                        data.setMessage_media(message_media);
                        data.setCreated_at(created_at);
                        data.setUpdated_at(updated_at);
                        megList.add(data);
                    }
                    mAdapter.notifyDataSetChanged();
                    loading = true;
                    loaddataProgress.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        } catch (JSONException e) {
        }
    }


    private void sendMessage() {
        if (Utility.isOnline(getContext())) {
            chatlistProgress = new ASTProgressBar(getContext());
            //   chatlistProgress.show();
            JSONObject object = new JSONObject();
            try {
                object.put("receiver_id", id);
                object.put("message", stret_comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String serviceURL = Contants.BASE_URL + Contants.CHATMSG;

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "sendMessage", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseInsertMessageData(result);
                    } else {
                        if (chatlistProgress.isShowing()) {
                            chatlistProgress.dismiss();
                        }
                        Utility.alertForErrorMessage(Contants.Error, getContext());
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }
    }

    public void parseInsertMessageData(String result) {
        if (result != null) {
            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
            if (serviceData != null) {
                if (serviceData.isStatus()) {
                    et_comment.setText("");
                    //Toast.makeText(getContext(), "Message Send!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Message not Send!", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (chatlistProgress.isShowing()) {
            chatlistProgress.dismiss();
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        megList.clear();
        getAllChatMessage();
    }
}
