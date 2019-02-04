package com.kredivation.aakhale.adapter;

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
import com.kredivation.aakhale.fragments.CoachDeatailFragment;
import com.kredivation.aakhale.fragments.PlayerDetailsFragment;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private ArrayList<Data> playerList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, teamName, ratingTxt;
        ImageView imageView, menumore;
        LinearLayout root_layout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            teamName = v.findViewById(R.id.teamName);
            ratingTxt = v.findViewById(R.id.ratingTxt);
            imageView = v.findViewById(R.id.imageView);
            menumore = v.findViewById(R.id.menumore);
            root_layout = v.findViewById(R.id.root_layout);
        }
    }

    public PlayerAdapter(Context context, ArrayList<Data> playerList) {
        this.playerList = playerList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.players_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(playerList.get(position).getFull_name());

        holder.teamName.setText(playerList.get(position).getAddress());
        // holder.ratingTxt.setText(playerList.get(position).getAddress());
        if (playerList.get(position).getProfile_picture() != null && !playerList.get(position).getProfile_picture().equals("")) {
            Picasso.with(context).load(playerList.get(position).getProfile_picture())
                    .placeholder(R.drawable.noimage).into(holder.imageView);
        }
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", playerList.get(position).getId());
                PlayerDetailsFragment coachDeatailFragment = new PlayerDetailsFragment();
                coachDeatailFragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, coachDeatailFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
