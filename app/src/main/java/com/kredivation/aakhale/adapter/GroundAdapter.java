package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.kredivation.aakhale.fragments.PlayerDetailsFragment;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.GroundData;

import java.util.ArrayList;
import java.util.Date;

public class GroundAdapter extends RecyclerView.Adapter<GroundAdapter.ViewHolder> {
    private ArrayList<GroundData> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stadiunName, capacity, addressTxt;
        ImageView sportsIcon, menumore;
        LinearLayout root_layout;
        ASTButton avilableTxt;


        public ViewHolder(View v) {
            super(v);
            stadiunName = v.findViewById(R.id.stadiunName);
            addressTxt = v.findViewById(R.id.addressTxt);
            capacity = v.findViewById(R.id.capacity);
            avilableTxt = v.findViewById(R.id.avilableTxt);
            menumore = v.findViewById(R.id.menumore);
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

        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerDetailsFragment playerDetailsFragment = new PlayerDetailsFragment();
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, playerDetailsFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }


}