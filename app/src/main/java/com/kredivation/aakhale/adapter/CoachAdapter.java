package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.CoachDeatailFragment;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.utility.Contants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CoachAdapter extends RecyclerView.Adapter<CoachAdapter.ViewHolder> {
    private ArrayList<Data> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, ratingTxt, userid;
        ImageView sportsIcon, menumore, imageView;
        MaterialCardView root_layout;


        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            address = v.findViewById(R.id.address);
            ratingTxt = v.findViewById(R.id.ratingTxt);
            sportsIcon = v.findViewById(R.id.sportsIcon);
            root_layout = v.findViewById(R.id.root_layout);
            menumore = v.findViewById(R.id.menumore);
            imageView = v.findViewById(R.id.imageView);
            userid = v.findViewById(R.id.userid);
        }
    }

    public CoachAdapter(Context context, ArrayList<Data> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
    }

    @Override
    public CoachAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.coach_item_row, parent, false);
        CoachAdapter.ViewHolder vh = new CoachAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CoachAdapter.ViewHolder holder, final int position) {
        holder.name.setText(sportsList.get(position).getFull_name());
        holder.userid.setText(sportsList.get(position).getUnique_id());
        holder.address.setText(sportsList.get(position).getComplateAddress());
        // holder.ratingTxt.setText(sportsList.get(position).getCapacity());
        Picasso.with(context).load(Contants.BASE_URL + sportsList.get(position).getProfile_picture()).placeholder(R.drawable.ic_uuuser).into(holder.imageView);
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataValue = new Gson().toJson(sportsList.get(position));
                Bundle bundle = new Bundle();
                bundle.putLong("id", sportsList.get(position).getId());
                bundle.putString("Detail", dataValue);
                CoachDeatailFragment coachDeatailFragment = new CoachDeatailFragment();
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
        return sportsList.size();
    }


}