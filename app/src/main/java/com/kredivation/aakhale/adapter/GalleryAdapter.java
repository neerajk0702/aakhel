package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.PlayerDetailsFragment;
import com.kredivation.aakhale.model.Data;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    List<String> academy_photosArayList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
        }
    }

    public GalleryAdapter(Context context, List<String> academy_photosLis) {
        this.academy_photosArayList = academy_photosLis;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.image_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.with(context).load("http://cricket.vikaskumar.info/" + academy_photosArayList.get(position))
                .placeholder(R.drawable.noimage).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return academy_photosArayList.size();
    }

}
