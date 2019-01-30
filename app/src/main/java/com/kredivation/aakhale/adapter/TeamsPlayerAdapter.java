package com.kredivation.aakhale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.fragments.TeamDetailFragment;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.model.Team_player;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TeamsPlayerAdapter extends RecyclerView.Adapter<TeamsPlayerAdapter.ViewHolder> {
    private ArrayList<Team_player> teamArrayList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ASTTextView playeName, status;
        ImageView playerImage;
        LinearLayout root_layout;

        public ViewHolder(View v) {
            super(v);
            playeName = v.findViewById(R.id.playeName);
            status = v.findViewById(R.id.status);
            playerImage = v.findViewById(R.id.playerImage);
        }
    }

    public TeamsPlayerAdapter(Context context, ArrayList<Team_player> teamArrayList) {
        this.teamArrayList = teamArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.teame_player_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.playeName.setText(teamArrayList.get(position).getName());

        if (teamArrayList.get(position).getStatus().equalsIgnoreCase("0")) {
            holder.status.setText("PENDING");
            holder.status.setTextColor(R.color.orange_color);
        } else if (teamArrayList.get(position).getStatus().equalsIgnoreCase("0")) {
            holder.status.setText("APPROVED");
            holder.status.setTextColor(R.color.green_color);
        }


        if (teamArrayList.get(position).getProfile_picture() != null && !teamArrayList.get(position).getProfile_picture().equals("")) {
            Picasso.with(context).load("http://cricket.vikaskumar.info/" + teamArrayList.get(position).getProfile_picture())
                    .placeholder(R.drawable.noimage).into(holder.playerImage);
        }


    }

    @Override
    public int getItemCount() {
        return teamArrayList.size();
    }

}
