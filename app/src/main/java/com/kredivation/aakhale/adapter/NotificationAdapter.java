package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<ImageItem> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, time, date, accept, reject;
        ImageView imageSports;
        MaterialCardView MainLayout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            time = v.findViewById(R.id.time);
            date = v.findViewById(R.id.date);
            imageSports = v.findViewById(R.id.imageSports);
            MainLayout = v.findViewById(R.id.MainLayout);
            accept = v.findViewById(R.id.accept);
            reject = v.findViewById(R.id.reject);
        }
    }

    public NotificationAdapter(Context context, ArrayList<ImageItem> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
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
        holder.name.setText(sportsList.get(position).getTitle() + "");


    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }


}
