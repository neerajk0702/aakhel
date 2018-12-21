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

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter implements Filterable {
    private Context context; //context
    List<String> mData;
    List<String> mStringFilterList;
    ValueFilter valueFilter;
    private LayoutInflater inflater;
    ImageView checkIcon;
    TextView textViewItemName;

    public ListAdapter(List<String> cancel_type, Context context) {
        this.mData = cancel_type;
        this.mStringFilterList = cancel_type;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        }


        // get the TextView for item name and item description
        textViewItemName = (TextView) convertView.findViewById(R.id.stringName);
        checkIcon = convertView.findViewById(R.id.checkIcon);
        textViewItemName.setText(mData.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIcon.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mData = (List<String>) results.values;
            notifyDataSetChanged();
        }

    }

}
