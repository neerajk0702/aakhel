package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.utility.FontManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MyViewHolder> {

    private ArrayList<Data> megList;
    Context mContext;
    Typeface materialdesignicons_font;
    private long userId;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView servermsg, servertiming, serveruserName;
        TextView mycomment, mytiming, myName;
        LinearLayout myside, serverside;

        public MyViewHolder(View view) {
            super(view);
            servermsg = view.findViewById(R.id.servermsg);
            servertiming = view.findViewById(R.id.servertiming);
            serveruserName = view.findViewById(R.id.serveruserName);
            mycomment = view.findViewById(R.id.mycomment);
            mytiming = view.findViewById(R.id.mytiming);
            myName = view.findViewById(R.id.myName);
            myside = view.findViewById(R.id.myside);
            serverside = view.findViewById(R.id.serverside);

        }
    }


    public ChatMessageAdapter(Context mContext, ArrayList<Data> list, long userId) {
        this.megList = list;
        this.mContext = mContext;
        this.userId = userId;
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_msg_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       /* holder.rightArrowIcon.setTypeface(materialdesignicons_font);
        holder.rightArrowIcon.setText(Html.fromHtml("&#xf142;"));
        holder.leftArrowIcon.setTypeface(materialdesignicons_font);
        holder.leftArrowIcon.setText(Html.fromHtml("&#xf141;"));*/
        if (String.valueOf(userId).equals(megList.get(position).getSender_id())) {
            holder.myside.setVisibility(View.VISIBLE);
            holder.serverside.setVisibility(View.GONE);
            // Picasso.with(mContext).load(megList.get(position).getProfile_image()).placeholder(R.drawable.avter).resize(45, 45).into(holder.myImage);
            holder.myName.setText(megList.get(position).getFull_name());
            holder.mycomment.setText(megList.get(position).getMessage());
            holder.mytiming.setText(megList.get(position).getCreated_at());
        } else {
            holder.myside.setVisibility(View.GONE);
            holder.serverside.setVisibility(View.VISIBLE);
            // Picasso.with(mContext).load(megList.get(position).getProfile_image()).placeholder(R.drawable.avter).resize(45, 45).into(holder.userImage);
            holder.serveruserName.setText(megList.get(position).getFull_name());
            holder.servermsg.setText(megList.get(position).getMessage());
            holder.servertiming.setText(megList.get(position).getUpdated_at());
        }


    }

    @Override
    public int getItemCount() {
        return megList.size();
    }


}

