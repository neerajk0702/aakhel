package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.fragments.CoachDeatailFragment;
import com.kredivation.aakhale.model.Academics;

import java.util.ArrayList;

public class CoachAdapter extends ArrayAdapter<Academics> {
    Context mContext;
    ArrayList<Academics> dataSet;

    private static class ViewHolder {
        TextView name;
        TextView coachType, ratingTxt;
        LinearLayout root_layout;

    }

    public CoachAdapter(ArrayList<Academics> data, Context context) {
        super(context, R.layout.coach_item_row, data);
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
            convertView = inflater.inflate(R.layout.coach_item_row, parent, false);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.coachType = convertView.findViewById(R.id.coachType);
            viewHolder.ratingTxt= convertView.findViewById(R.id.ratingTxt);
            viewHolder.root_layout = convertView.findViewById(R.id.root_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(academics.getName());
        viewHolder.coachType.setText(academics.getAddress());

        viewHolder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoachDeatailFragment coachDeatailFragment = new CoachDeatailFragment();
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) mContext).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, coachDeatailFragment);
                fragmentTransaction.commit();
            }
        });
        return convertView;
    }
}