package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.model.Academics;

import java.util.ArrayList;

public class AcadamicAdapter extends ArrayAdapter<Academics> {
    Context mContext;
    ArrayList<Academics> dataSet;

    private static class ViewHolder {
        TextView name;
        TextView address, moreGamesTxt;
        ASTFontTextIconView matchIcon;

    }

    public AcadamicAdapter(ArrayList<Academics> data, Context context) {
        super(context, R.layout.acadamic_item, data);
        this.dataSet = data;
        this.mContext = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Academics academics = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.acadamic_item, parent, false);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.address = convertView.findViewById(R.id.address);
            viewHolder.moreGamesTxt = convertView.findViewById(R.id.moreGamesTxt);
            viewHolder.matchIcon = convertView.findViewById(R.id.matchIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(academics.getName());
        viewHolder.address.setText(academics.getAddress());
        viewHolder.matchIcon.setText(academics.getGameicon());
        return convertView;
    }
}