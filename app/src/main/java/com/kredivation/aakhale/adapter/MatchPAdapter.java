package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.MatchDetailsActivity;
import com.kredivation.aakhale.model.Match;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MatchPAdapter extends RecyclerView.Adapter<MatchPAdapter.ViewHolder> {
    private ArrayList<Match> matchArrayList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView matchname, matchteam, address, date, time;
        MaterialCardView MainLayout;

        public ViewHolder(View v) {
            super(v);
            matchname = v.findViewById(R.id.matchname);
            matchteam = v.findViewById(R.id.matchteam);
            address = v.findViewById(R.id.address);
            MainLayout = v.findViewById(R.id.MainLayout);
            date = v.findViewById(R.id.date);
            time = v.findViewById(R.id.time);
        }
    }

    public MatchPAdapter(Context context, ArrayList<Match> matchArrayList) {
        this.matchArrayList = matchArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.match_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.matchname.setText(matchArrayList.get(position).getName() + "");
        try {
            String city = "";
            String state = "";
            if (matchArrayList.get(position).getMatchCity() != null) {
                JSONObject cityObj = new JSONObject(matchArrayList.get(position).getMatchCity());
                city = cityObj.optString("city_name");
            }
            if (matchArrayList.get(position).getMatchState() != null) {
                JSONObject stateObj = new JSONObject(matchArrayList.get(position).getMatchState());
                state = stateObj.optString("state_name");
            }
            String completeAddress = matchArrayList.get(position).getMatch_address() + "," + city + "," + state + "," + matchArrayList.get(position).getMatch_zipcode();
            holder.address.setText(completeAddress + "");
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        holder.date.setText(matchArrayList.get(position).getDate() + "");
        holder.time.setText(matchArrayList.get(position).getTime() + "");

        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, MatchDetailsActivity.class);
                intent1.putExtra("MatchDetail", new Gson().toJson(matchArrayList.get(position)));
                intent1.putExtra("MyMatchFlag",false);
                context.startActivity(intent1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return matchArrayList.size();
    }


}
