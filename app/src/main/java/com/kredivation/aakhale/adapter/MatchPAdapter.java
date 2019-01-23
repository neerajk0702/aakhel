package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Match;

import java.util.ArrayList;


public class MatchPAdapter extends RecyclerView.Adapter<MatchPAdapter.ViewHolder> {
    private ArrayList<Match> matchArrayList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, date, time;
        LinearLayout MainLayout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            address = v.findViewById(R.id.address);
            date = v.findViewById(R.id.date);
            MainLayout = v.findViewById(R.id.MainLayout);
            time = v.findViewById(R.id.time);
        }
    }

    public MatchPAdapter(Context context, ArrayList<Match> matchArrayList) {
        this.matchArrayList = matchArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.match_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(matchArrayList.get(position).getName() + "");
        holder.address.setText(matchArrayList.get(position).getMatch_address() + "");
        holder.date.setText(matchArrayList.get(position).getDate() + "");
        holder.time.setText(matchArrayList.get(position).getTime() + "");


    }

    @Override
    public int getItemCount() {
        return matchArrayList.size();
    }


}
