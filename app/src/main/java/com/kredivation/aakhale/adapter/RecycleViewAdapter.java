package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }


    public RecycleViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutResourceId, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageItem item = data.get(position);
        holder.image.setImageBitmap(item.getImage());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


}

