package com.kredivation.aakhale.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

public class SportsServiceGridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList();

    public SportsServiceGridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.sportsName = (ASTTextView) row.findViewById(R.id.sportsName);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        //    ImageItem item = data.get(position);
        holder.sportsName.setText(data.get(position).getTitle());
        return row;
    }

    static class ViewHolder {
        ASTTextView sportsName;
    }
}