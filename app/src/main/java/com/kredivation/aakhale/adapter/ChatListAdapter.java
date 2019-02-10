package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.ChatDetailFragment;
import com.kredivation.aakhale.fragments.ChatFragment;
import com.kredivation.aakhale.fragments.PlayerDetailsFragment;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.utility.FontManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private ArrayList<Academics> chatList;
    Context mContext;
    Typeface materialdesignicons_font;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, locationIcon, location;
        ImageView userImage;
        MaterialCardView cardView;

        public MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.userName);
            userImage = view.findViewById(R.id.userImage);
            locationIcon = view.findViewById(R.id.locationIcon);
            location = view.findViewById(R.id.location);
            cardView = view.findViewById(R.id.cardView);
        }
    }


    public ChatListAdapter(Context mContext, ArrayList<Academics> list) {
        this.chatList = list;
        this.mContext = mContext;
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.location.setText(chatList.get(position).getSname());
        holder.userName.setText(chatList.get(position).getSaddress());
        holder.locationIcon.setTypeface(materialdesignicons_font);
        holder.locationIcon.setText(Html.fromHtml("&#xf34e;"));
        //Picasso.with(mContext).load(chatList.get(position).getProfile_image()).placeholder(R.drawable.userimage).resize(80, 80).into(holder.userImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatDetailFragment chatFragment = new ChatDetailFragment();
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) mContext).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, chatFragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


}
