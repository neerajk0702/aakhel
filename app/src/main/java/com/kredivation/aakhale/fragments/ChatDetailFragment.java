package com.kredivation.aakhale.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.ResetPasswordActivity;
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
public class ChatDetailFragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    View view;
    EditText et_comment;
    TextView send_comment;
    ArrayList<Data> dataModels;
    String stret_comment;
    ChatMessageAdapter mAdapter;
    ASTProgressBar chatlistProgress;
    ArrayList<Data> megList;
    Timer timer;
    Bundle bundle;
    long id;

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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getActivity().setTitle("Chat Details");
        et_comment = view.findViewById(R.id.et_comment);
        send_comment = view.findViewById(R.id.send_comment);
        send_comment.setOnClickListener(this);
        dataToView();
    }


    public void dataToView() {
        dataModels = new ArrayList<>();
        if (megList != null && megList.size() > 0) {
            mAdapter = new ChatMessageAdapter(getContext(), megList, "1");
            recyclerView.setAdapter(mAdapter);
        }
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
            chatlistProgress = new ASTProgressBar(getContext());
            //chatlistProgress.show();
            JSONObject object = new JSONObject();
            try {
                object.put("user_id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String serviceURL = Contants.BASE_URL + Contants.CHATMSG;

            ServiceCaller serviceCaller = new ServiceCaller(getContext());
            serviceCaller.CallCommanGetServiceMethod(serviceURL, object, "getAllChatMessage", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        parseMessageData(result);
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

    public void parseMessageData(String result) {
        if (result != null) {
            final ContentData serviceData = new Gson().fromJson(result, ContentData.class);
            if (serviceData != null) {
                if (serviceData.isStatus()) {
                    if (serviceData.getData() != null) {
                        new AsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... voids) {
                                Boolean flag = false;
                                megList.clear();
                               /* for (Data data : serviceData.getData()) {
                                    megList.add(serviceData.getData());
                                }*/
                                megList.add(serviceData.getData());
                                flag = true;
                                return flag;
                            }

                            @Override
                            protected void onPostExecute(Boolean flag) {
                                super.onPostExecute(flag);
                                if (flag) {
                                    Collections.reverse(megList);
                                    int pos = recyclerView.getAdapter().getItemCount() - 1;
                                    recyclerView.smoothScrollToPosition(pos);
                                    mAdapter.notifyDataSetChanged();
                                }
                                if (chatlistProgress.isShowing()) {
                                    chatlistProgress.dismiss();
                                }
                            }
                        }.execute();
                    } else {
                        if (chatlistProgress.isShowing()) {
                            chatlistProgress.dismiss();
                        }
                    }
                } else {
                    if (chatlistProgress.isShowing()) {
                        chatlistProgress.dismiss();
                    }
                }
            }
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
                    Toast.makeText(getContext(), "Message  Send!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Message not Send!", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (chatlistProgress.isShowing()) {
            chatlistProgress.dismiss();
        }
    }
}
