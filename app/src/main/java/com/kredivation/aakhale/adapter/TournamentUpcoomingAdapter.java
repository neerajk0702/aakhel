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
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.fragments.TournamentDetails;
import com.kredivation.aakhale.model.Tournament;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TournamentUpcoomingAdapter extends RecyclerView.Adapter<TournamentUpcoomingAdapter.ViewHolder> {
    private ArrayList<Tournament> tournamentArrayList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, date;
        ImageView imageView;
        LinearLayout root_layout;
        ASTButton avilableTxt;


        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            address = v.findViewById(R.id.address);
            date = v.findViewById(R.id.date);
            avilableTxt = v.findViewById(R.id.avilableTxt);
            root_layout = v.findViewById(R.id.root_layout);
            imageView = v.findViewById(R.id.imageView);
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

        holder.address.setText(tournamentArrayList.get(position).getTournament_address());
        holder.date.setText(tournamentArrayList.get(position).getStart_date() + "-" + tournamentArrayList.get(position).getEnd_date());

        Picasso.with(context).load(tournamentArrayList.get(position).getDisplay_picture()).placeholder(R.drawable.noimage).into(holder.imageView);
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", tournamentArrayList.get(position).getId());
                TournamentDetails coachDeatailFragment = new TournamentDetails();
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
        return tournamentArrayList.size();
    }


}