package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.fragments.GroundDetailFrgment;
import com.kredivation.aakhale.fragments.PlayerDetailsFragment;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.GroundData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class GroundAdapter extends RecyclerView.Adapter<GroundAdapter.ViewHolder> {
    private ArrayList<GroundData> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stadiunName, capacity, addressTxt;
        ImageView sportsIcon;
        CardView root_layout;
        ASTButton avilableTxt;


        public ViewHolder(View v) {
            super(v);
            stadiunName = v.findViewById(R.id.stadiunName);
            addressTxt = v.findViewById(R.id.addressTxt);
            capacity = v.findViewById(R.id.capacity);
            avilableTxt = v.findViewById(R.id.avilableTxt);
            root_layout = v.findViewById(R.id.root_layout);
            sportsIcon = v.findViewById(R.id.imageView);
        }
    }

    public GroundAdapter(Context context, ArrayList<GroundData> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
    }

    @Override
    public GroundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.ground__row, parent, false);
        GroundAdapter.ViewHolder vh = new GroundAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GroundAdapter.ViewHolder holder, final int position) {
        holder.stadiunName.setText(sportsList.get(position).getName());

        holder.addressTxt.setText(sportsList.get(position).getGround_address());
        holder.capacity.setText(sportsList.get(position).getCapacity());
        if (sportsList.get(position).getAvailable() == 1) {
            holder.avilableTxt.setText("Available");
        } else {
            holder.avilableTxt.setText("Not Available");
        }

        Picasso.with(context).load(sportsList.get(position).getDisplay_picture()).placeholder(R.drawable.noimage).into(holder.sportsIcon);
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroundDetailFrgment groundDetailFrgment = new GroundDetailFrgment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", sportsList.get(position).getId());
                groundDetailFrgment.setArguments(bundle);
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, groundDetailFrgment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }


}