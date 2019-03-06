package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.LoginActivity;
import com.kredivation.aakhale.components.ASTProgressBar;
import com.kredivation.aakhale.fragments.NotificationListFragment;
import com.kredivation.aakhale.framework.IAsyncWorkCompletedCallback;
import com.kredivation.aakhale.framework.ServiceCaller;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<Data> notificationList;
    Context context;
    String userId;
    int userRoleId;
    long userIdValue;
    NotificationListFragment fragment;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, time, date, accept, reject;
        ImageView imageSports;
        MaterialCardView MainLayout;
        LinearLayout acceptrejectLayout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            time = v.findViewById(R.id.time);
            date = v.findViewById(R.id.date);
            imageSports = v.findViewById(R.id.imageSports);
            MainLayout = v.findViewById(R.id.MainLayout);
            accept = v.findViewById(R.id.accept);
            reject = v.findViewById(R.id.reject);
            acceptrejectLayout = v.findViewById(R.id.acceptrejectLayout);
        }
    }

    public NotificationAdapter(Context context, ArrayList<Data> list, NotificationListFragment fragment) {
        this.notificationList = list;
        this.context = context;
        this.fragment = fragment;
        SharedPreferences UserInfo = context.getSharedPreferences("UserInfoSharedPref", context.MODE_PRIVATE);
        userIdValue = UserInfo.getLong("id", 0);
        userRoleId = UserInfo.getInt("role", 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.notofication_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(notificationList.get(position).getMessage() + "");
        holder.date.setText(notificationList.get(position).getCreated_at() + "");
        if (notificationList.get(position).getNotificationData() != null) {
            JSONObject notidata = notificationList.get(position).getNotificationData();
            JSONObject resObj = notidata.optJSONObject("respond");
            if (resObj != null) {
                int status = resObj.optInt("status");
                if (status == 0) {
                    holder.acceptrejectLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.acceptrejectLayout.setVisibility(View.GONE);
                }
            } else {
                holder.acceptrejectLayout.setVisibility(View.GONE);
            }
        } else {
            holder.acceptrejectLayout.setVisibility(View.GONE);
        }
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickResponce(1, position);
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickResponce(2, position);
            }
        });
    }

    private void clickResponce(int acceptRejectId, int position) {
        int anotherUsetid = 0;
        int player_challenge_id = 0;
        int team_player_id = 0;
        int team_coach_id = 0;
        int user_coach_id = 0;
        int users_tournament_umpire_id = 0;
        int users_match_umpire_id = 0;
        int team_challenge_id = 0;
        int match_team_id = 0;
        int tournament_team_id = 0;
        int match_umpire_id = 0;
        int tournament_umpire_id = 0;
        int team_tournament_team_id = 0;
        if (notificationList.get(position).getNotificationData() != null) {
            JSONObject notidata = notificationList.get(position).getNotificationData();
            anotherUsetid = notidata.optInt("id");
            JSONObject resObj = notidata.optJSONObject("respond");
            if (resObj != null) {
                player_challenge_id = resObj.optInt("player_challenge_id");
                team_player_id = resObj.optInt("team_player_id");
                user_coach_id = resObj.optInt("team_coach_id");
                users_tournament_umpire_id = resObj.optInt("tournament_umpire_id");
                users_match_umpire_id = resObj.optInt("match_umpire_id");
                team_challenge_id = resObj.optInt("team_challenge_id");
                if (user_coach_id == 0) {
                    team_coach_id = resObj.optInt("team_coach_id");
                }
                match_team_id = resObj.optInt("match_team_id");
                team_tournament_team_id = resObj.optInt("tournament_team_id");
                if (users_match_umpire_id == 0) {
                    match_umpire_id = resObj.optInt("match_umpire_id");
                }
                if (users_tournament_umpire_id == 0) {
                    tournament_umpire_id = resObj.optInt("tournament_umpire_id");
                }
                if (team_tournament_team_id == 0) {
                    tournament_team_id = resObj.optInt("tournament_team_id");
                }
            }
        }
        String key = "";
        String url = "";
        if (player_challenge_id > 0) {
            key = "player_challenge_id";
            url = Contants.RESPONDCHALLENGE;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, player_challenge_id, url);
        } else if (team_player_id > 0) {
            key = "team_player_id";
            url = Contants.respond_player_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, team_player_id, url);
        } else if (user_coach_id > 0) {
            key = "team_coach_id";
            url = Contants.respond_coach_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, user_coach_id, url);
        } else if (users_tournament_umpire_id > 0) {
            key = "tournament_umpire_id";
            url = Contants.umpire_tournament_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, users_tournament_umpire_id, url);
        } else if (users_match_umpire_id > 0) {
            key = "match_umpire_id";
            url = Contants.umpire_match_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, users_match_umpire_id, url);
        } else if (team_challenge_id > 0) {
            key = "team_challenge_id";
            url = Contants.respond_challenge;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, team_challenge_id, url);
        } else if (team_player_id > 0) {
            key = "team_player_id";
            url = Contants.team_respond_player_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, team_player_id, url);
        } else if (team_coach_id > 0) {
            key = "team_coach_id";
            url = Contants.team_respond_coach_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, team_coach_id, url);
        } else if (match_team_id > 0) {
            key = "match_team_id";
            url = Contants.team_respond_match_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, match_team_id, url);
        } else if (team_tournament_team_id > 0) {
            key = "tournament_team_id";
            url = Contants.team_respond_tournament_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, team_tournament_team_id, url);
        } else if (tournament_umpire_id > 0) {
            key = "tournament_umpire_id";
            url = Contants.tournament_respond_umpire_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, tournament_umpire_id, url);
        } else if (match_umpire_id > 0) {
            key = "match_umpire_id";
            url = Contants.match_respond_umpire_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, match_umpire_id, url);
        } else if (tournament_team_id > 0) {
            key = "tournament_team_id";
            url = Contants.tournament_respond_team_request;
            RESPONDCHALLENGE(acceptRejectId, notificationList.get(position).getId(), key, tournament_team_id, url);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    private void RESPONDCHALLENGE(int Acceptid, long notiId, String key, int anotherUsetid, String url) {
        if (Utility.isOnline(context)) {
            final ASTProgressBar dotDialog = new ASTProgressBar(context);
            dotDialog.show();
            String serviceURL = Contants.BASE_URL + url;
            JSONObject object = new JSONObject();
            try {
                object.put(key, anotherUsetid);
                object.put("response", Acceptid);
                object.put("notification_id", notiId);
            } catch (JSONException e) {
                // e.printStackTrace();
            }
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.CallCommanServiceMethod(serviceURL, object, "RESPONDCHALLENGE", new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.optBoolean("status");
                            String message = jsonObject.optString("message");
                            if (status) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            if (dotDialog.isShowing()) {
                                dotDialog.dismiss();
                            }
                            fragment.refreshAfterActionTaken();
                        } catch (JSONException e) {
                        }
                    } else {
                        Utility.alertForErrorMessage(Contants.Error, context);
                    }
                    if (dotDialog.isShowing()) {
                        dotDialog.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, context);//off line msg....
        }
    }
}
