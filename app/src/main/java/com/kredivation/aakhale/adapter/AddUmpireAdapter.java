package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.UmpireDetailFragment;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;


public class AddUmpireAdapter extends RecyclerView.Adapter<AddUmpireAdapter.ViewHolder> {
    private ArrayList<ImageItem> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView umpireName, matchUmpite, uniqeId;
        ImageView imageSports, closeList;
        LinearLayout MainLayout;

        public ViewHolder(View v) {
            super(v);
            umpireName = v.findViewById(R.id.umpireName);
            matchUmpite = v.findViewById(R.id.matchUmpite);
            uniqeId = v.findViewById(R.id.uniqeId);
            imageSports = v.findViewById(R.id.imageSports);
            closeList = v.findViewById(R.id.closeList);
            MainLayout = v.findViewById(R.id.MainLayout);
        }
    }

    public AddUmpireAdapter(Context context, ArrayList<ImageItem> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.umpire_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.umpireName.setText(sportsList.get(position).getTitle() + "");


        holder.closeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UmpireDetailFragment umpireDetailFragment = new UmpireDetailFragment();
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, umpireDetailFragment).addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }


}
