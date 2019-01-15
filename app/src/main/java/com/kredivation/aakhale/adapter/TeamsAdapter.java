package com.kredivation.aakhale.adapter;

import android.content.Context;
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
import com.kredivation.aakhale.fragments.PlayerDetailsFragment;
import com.kredivation.aakhale.fragments.TeamDetailFragment;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;


public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
    private ArrayList<ImageItem> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, teamName, ratingTxt;
        ImageView sportsIcon, menumore;
        LinearLayout root_layout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            teamName = v.findViewById(R.id.teamName);
            ratingTxt = v.findViewById(R.id.ratingTxt);
            root_layout = v.findViewById(R.id.root_layout);
            sportsIcon = v.findViewById(R.id.sportsIcon);
            menumore = v.findViewById(R.id.menumore);
        }
    }

    public TeamsAdapter(Context context, ArrayList<ImageItem> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.teams_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(sportsList.get(position).getTitle() + "");
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamDetailFragment teamDetailFragment = new TeamDetailFragment();
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, teamDetailFragment);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }

}
