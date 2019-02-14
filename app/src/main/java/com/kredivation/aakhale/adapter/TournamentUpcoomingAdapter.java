package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
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

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.TournamentList;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.activity.TournamentDetails;
import com.kredivation.aakhale.components.CircleImageView;
import com.kredivation.aakhale.model.Tournament;
import com.kredivation.aakhale.utility.Contants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TournamentUpcoomingAdapter extends RecyclerView.Adapter<TournamentUpcoomingAdapter.ViewHolder> {
    private ArrayList<Tournament> tournamentArrayList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, date, userId;
        CircleImageView imageView;
        MaterialCardView root_layout;
        ASTButton avilableTxt;


        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            address = v.findViewById(R.id.address);
            date = v.findViewById(R.id.date);
            avilableTxt = v.findViewById(R.id.avilableTxt);
            root_layout = v.findViewById(R.id.root_layout);
            imageView = v.findViewById(R.id.imageView);
            userId = v.findViewById(R.id.userId);
        }
    }

    public TournamentUpcoomingAdapter(Context context, ArrayList<Tournament> tournaments) {
        this.tournamentArrayList = tournaments;
        this.context = context;
    }

    @Override
    public TournamentUpcoomingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.upcoming_match_item, parent, false);
        TournamentUpcoomingAdapter.ViewHolder vh = new TournamentUpcoomingAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentUpcoomingAdapter.ViewHolder holder, final int position) {
        holder.name.setText(tournamentArrayList.get(position).getName());
        holder.userId.setText(tournamentArrayList.get(position).getUnique_id());
        holder.address.setText(tournamentArrayList.get(position).getCompleteAddress());
        holder.date.setText(tournamentArrayList.get(position).getStart_date() + "-" + tournamentArrayList.get(position).getEnd_date());

        Picasso.with(context).load(Contants.BASE_URL + tournamentArrayList.get(position).getDisplay_picture()).placeholder(R.drawable.noimage).into(holder.imageView);
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tintent = new Intent(context, TournamentDetails.class);
                tintent.putExtra("id", tournamentArrayList.get(position).getId());
                context.startActivity(tintent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tournamentArrayList.size();
    }


}