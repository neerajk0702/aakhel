package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.Users_roles;

import java.util.ArrayList;


public class UserTypeAdapter extends RecyclerView.Adapter<UserTypeAdapter.ViewHolder> {
    private ArrayList<Users_roles> araList;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namearea;

        public ViewHolder(View v) {
            super(v);
            namearea = v.findViewById(R.id.namearea);
        }
    }

    public UserTypeAdapter(Context context, ArrayList<Users_roles> myDataset) {
        araList = myDataset;
        this.context = context;
    }

    @Override
    public UserTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.area_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull UserTypeAdapter.ViewHolder holder, final int position) {
        holder.namearea.setText(araList.get(position).getUser_type());

    }

    @Override
    public int getItemCount() {
        return araList.size();
    }

}
