package com.kredivation.aakhale.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Sports;

import java.util.ArrayList;

public class SportsServiceGridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Sports> data;

    public SportsServiceGridViewAdapter(Context context, int layoutResourceId, ArrayList<Sports> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.sportsName = row.findViewById(R.id.sportsName);
            holder.MainLayout = row.findViewById(R.id.MainLayout);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        if (data.get(position).isSelected()) {
            //holder.MainLayout.setBackgroundColor(Color.parseColor("#fa6125"));
            holder.sportsName.setBackgroundColor(Color.parseColor("#fa6125"));
            holder.sportsName.setTextColor(Color.parseColor("#ffffff"));
        } else {
            //holder.MainLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.sportsName.setBackgroundColor(Color.parseColor("#e7e7e7"));
            holder.sportsName.setTextColor(Color.parseColor("#000000"));
        }
        //    ImageItem item = data.get(position);
        holder.sportsName.setText(data.get(position).getSports_name());
        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).isSelected()) {
                    data.get(position).setSelected(false);
                } else {
                    data.get(position).setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
        return row;
    }

    static class ViewHolder {
        ASTTextView sportsName;
        LinearLayout MainLayout;
    }
}