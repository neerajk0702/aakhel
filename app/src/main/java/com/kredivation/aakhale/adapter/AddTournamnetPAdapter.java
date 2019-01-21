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
import com.kredivation.aakhale.fragments.UmpireDetailFragment;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;


public class AddTournamnetPAdapter extends RecyclerView.Adapter<AddTournamnetPAdapter.ViewHolder> {
    private ArrayList<ImageItem> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, date;
        ImageView imageSports;
        LinearLayout MainLayout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            address = v.findViewById(R.id.address);
            date = v.findViewById(R.id.date);
            imageSports = v.findViewById(R.id.imageSports);
            MainLayout = v.findViewById(R.id.MainLayout);
        }
    }

    public AddTournamnetPAdapter(Context context, ArrayList<ImageItem> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.trow, parent, false);
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
