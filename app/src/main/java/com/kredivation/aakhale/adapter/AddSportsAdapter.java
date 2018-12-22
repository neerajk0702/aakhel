package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;


public class AddSportsAdapter extends RecyclerView.Adapter<AddSportsAdapter.ViewHolder> {
    private ArrayList<ImageItem> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleName;
        ImageView imageSports, closeList;

        public ViewHolder(View v) {
            super(v);
            titleName = v.findViewById(R.id.titleName);
            imageSports = v.findViewById(R.id.imageSports);
            closeList = v.findViewById(R.id.closeList);
        }
    }

    public AddSportsAdapter(Context context, ArrayList<ImageItem> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.sports_name_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleName.setText(sportsList.get(position).getTitle() + "");


        holder.closeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }

}
