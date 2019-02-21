package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.Constants;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.PostDetailFragment;
import com.kredivation.aakhale.fragments.dummy.DummyContent;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.model.Post_info;
import com.kredivation.aakhale.utility.Contants;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class MyPostItemRecyclerViewAdapter extends RecyclerView.Adapter<MyPostItemRecyclerViewAdapter.ViewHolder> {

    List<Post_info> Itemlist;
    Context context;

    public MyPostItemRecyclerViewAdapter(List<Post_info> gaggeredList, Context ctx) {
        Itemlist = gaggeredList;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_postitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.lblTxt.setText(Itemlist.get(position).getTitle());

        for (int i = 0; i < Itemlist.get(position).getFeatured_image().length(); i++) {

            Picasso.with(context).load(Contants.BASE_URL +  Itemlist.get(position).getFeatured_image().optString(i))
                    .placeholder(R.drawable.noimage).into(holder.image);
        }


        holder.descrition.setText(Itemlist.get(position).getDescription());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailFragment matchDetailsFragment = new PostDetailFragment();
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, matchDetailsFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        //  holder.image.setText(Itemlist.get(position).image);
    }

    @Override
    public int getItemCount() {
        return Itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView image;
        public final TextView lblTxt;
        public final TextView descrition;
        CardView card_view;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = view.findViewById(R.id.image);
            lblTxt = view.findViewById(R.id.lblTxt);
            descrition = (TextView) view.findViewById(R.id.descrition);
            card_view = view.findViewById(R.id.card_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblTxt.getText() + "'";
        }
    }
}
