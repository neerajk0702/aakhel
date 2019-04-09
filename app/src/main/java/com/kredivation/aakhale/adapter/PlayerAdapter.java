package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.PlayerDetailsActivity;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private ArrayList<Data> playerList;
    Context context;
    String userId;
    boolean addPlayerFlag = false;
    Typeface materialdesignicons_font;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, teamName, ratingTxt, selectCheck, userId;
        ImageView imageView, menumore;
        MaterialCardView root_layout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            teamName = v.findViewById(R.id.teamName);
            ratingTxt = v.findViewById(R.id.ratingTxt);
            imageView = v.findViewById(R.id.imageView);
            menumore = v.findViewById(R.id.menumore);
            root_layout = v.findViewById(R.id.root_layout);
            selectCheck = v.findViewById(R.id.selectCheck);
            selectCheck.setTypeface(materialdesignicons_font);
            userId = v.findViewById(R.id.userId);
        }
    }

    public PlayerAdapter(Context context, ArrayList<Data> playerList, boolean addPlayerFlag) {
        this.playerList = playerList;
        this.context = context;
        this.addPlayerFlag = addPlayerFlag;
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(context, "fonts/materialdesignicons-webfont.otf");
    }

    public PlayerAdapter(Context context, ArrayList<Data> playerList) {
        this.playerList = playerList;
        this.context = context;
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(context, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.players_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(playerList.get(position).getFull_name());
        holder.teamName.setText(playerList.get(position).getComplateAddress());
        holder.userId.setText(playerList.get(position).getUnique_id());
        if (playerList.get(position).getProfile_picture() != null && !playerList.get(position).getProfile_picture().equals("")) {
            Picasso.with(context).load(Contants.BASE_URL + playerList.get(position).getProfile_picture())
                    .placeholder(R.drawable.noimage).into(holder.imageView);
        }

        if (addPlayerFlag) {
            holder.selectCheck.setVisibility(View.VISIBLE);
            if (playerList.get(position).isSelectValue()) {
                holder.selectCheck.setText(Html.fromHtml("&#xf132;"));
                holder.selectCheck.setTextColor(Color.parseColor("#198719"));
            } else {
                holder.selectCheck.setText(Html.fromHtml("&#xf131;"));
                holder.selectCheck.setTextColor(Color.parseColor("#000000"));
            }
        }
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addPlayerFlag) {

                    String dataValue = new Gson().toJson(playerList.get(position));
                    Intent intent = new Intent(context, PlayerDetailsActivity.class);
                    intent.putExtra("id", playerList.get(position).getId());
                    intent.putExtra("Detail", dataValue);
                    context.startActivity(intent);
                }
            }
        });

        holder.selectCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerList.get(position).isSelectValue()) {
                    playerList.get(position).setSelectValue(false);
                } else {
                    playerList.get(position).setSelectValue(true);
                }
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
