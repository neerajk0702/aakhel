package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kredivation.aakhale.R;

import java.util.List;


public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private List<String> araList;
    Context context;
    String feId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namearea;

        public ViewHolder(View v) {
            super(v);
            namearea = v.findViewById(R.id.namearea);
        }
    }

    public AreaAdapter(Context context, List<String> myDataset) {
        araList = myDataset;
        this.context = context;
    }

    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.area_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.ViewHolder holder, final int position) {
        holder.namearea.setText(araList.get(position));

    }

    @Override
    public int getItemCount() {
        return araList.size();
    }

}
