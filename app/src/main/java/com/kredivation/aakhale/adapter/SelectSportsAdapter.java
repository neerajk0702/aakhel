package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.CoachDeatailFragment;
import com.kredivation.aakhale.model.Sports;

import java.util.ArrayList;

public class SelectSportsAdapter extends ArrayAdapter<Sports> {
    Context mContext;
    ArrayList<Sports> dataSet;

    private static class ViewHolder {
        TextView nametext;
        CheckBox checkbox;

    }

    public SelectSportsAdapter(ArrayList<Sports> data, Context context) {
        super(context, R.layout.spinner_item_with_checkbox, data);
        this.dataSet = data;
        this.mContext = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Sports sports = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_item_with_checkbox, parent, false);
            viewHolder.nametext = convertView.findViewById(R.id.nametext);
            viewHolder.checkbox = convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(sports.getId()==0){
            viewHolder.checkbox.setVisibility(View.GONE);
        }else {
            viewHolder.checkbox.setVisibility(View.VISIBLE);
        }
        viewHolder.nametext.setText(sports.getSports_name());

        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sports.setSelected(false);
                } else {
                    sports.setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}