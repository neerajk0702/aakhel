package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.model.Academics;

import java.util.ArrayList;

public class GroundAdapter extends ArrayAdapter<Academics> {
    Context mContext;
    ArrayList<Academics> dataSet;

    private static class ViewHolder {
        TextView stadiunName;
        ASTButton avilableTxt;
        TextView capacity, addressTxt;
        ImageView imageView;
    }

    public GroundAdapter(ArrayList<Academics> data, Context context) {
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
            convertView = inflater.inflate(R.layout.ground__row, parent, false);
            viewHolder.stadiunName = convertView.findViewById(R.id.stadiunName);
            viewHolder.addressTxt = convertView.findViewById(R.id.addressTxt);
            viewHolder.capacity = convertView.findViewById(R.id.capacity);
            viewHolder.avilableTxt = convertView.findViewById(R.id.avilableTxt);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.stadiunName.setText(academics.getSname());
        viewHolder.addressTxt.setText(academics.getSaddress());
        viewHolder.avilableTxt.setText(academics.getSstatus());
        viewHolder.capacity.setText("Capacity:-" + academics.getScapacity());
        return convertView;
    }
}