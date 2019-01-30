package com.kredivation.aakhale.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.Match_played;

import java.util.ArrayList;
import java.util.List;

public class MatchPlayedAdapter extends BaseAdapter {
    private Context context; //context
    ArrayList<Match_played> match_playedList;
    private LayoutInflater inflater;
    TextView tmeavsteam, stadiumaddress, date;

    public MatchPlayedAdapter(ArrayList<Match_played> match_playedList, Context context) {
        this.match_playedList = match_playedList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return match_playedList.size();
    }

    @Override
    public Object getItem(int position) {
        return match_playedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.matchplay_row, parent, false);
        }
        tmeavsteam = convertView.findViewById(R.id.tmeavsteam);
        stadiumaddress = convertView.findViewById(R.id.stadiumaddress);
        date = convertView.findViewById(R.id.date);

        tmeavsteam.setText(match_playedList.get(position).getName());
        stadiumaddress.setText(match_playedList.get(position).getAddress()
                        + " ," + match_playedList.get(position).getCity()
                        + " ," + match_playedList.get(position).getState()
                        + " ," + match_playedList.get(position).getZipcode());

                date.setText(match_playedList.get(position).getDate());

        return convertView;
    }


}