package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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
import com.kredivation.aakhale.activity.TeamDetailActivity;
import com.kredivation.aakhale.model.Team;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
    private ArrayList<Team> teamArrayList;
    Context context;
    String userId;
    boolean selectFlage;
    Typeface materialdesignicons_font;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, teamaddress, ratingTxt, selectCheck, userid;
        ImageView sportsIcon, menumore, imageView;
        CardView root_layout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            teamaddress = v.findViewById(R.id.teamaddress);
            ratingTxt = v.findViewById(R.id.ratingTxt);
            root_layout = v.findViewById(R.id.root_layout);
            sportsIcon = v.findViewById(R.id.sportsIcon);
            menumore = v.findViewById(R.id.menumore);
            imageView = v.findViewById(R.id.imageView);
            userid = v.findViewById(R.id.userid);
            selectCheck = v.findViewById(R.id.selectCheck);
            selectCheck.setTypeface(materialdesignicons_font);
        }
    }

    public TeamsAdapter(Context context, ArrayList<Team> teamArrayList, boolean selectFlag) {
        this.teamArrayList = teamArrayList;
        this.context = context;
        this.selectFlage = selectFlag;
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(context, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.teams_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.userid.setText(teamArrayList.get(position).getUnique_id());
        holder.name.setText(teamArrayList.get(position).getName());
        holder.teamaddress.setText(teamArrayList.get(position).getTeam_city());
        Picasso.with(context).load(Contants.BASE_URL + teamArrayList.get(position).getTeam_thumbnail()).placeholder(R.drawable.noimage).into(holder.imageView);
        if (selectFlage) {
            holder.selectCheck.setVisibility(View.VISIBLE);
            if (teamArrayList.get(position).isSelectValue()) {
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
                Intent intent = new Intent(context, TeamDetailActivity.class);
                intent.putExtra("id", teamArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.selectCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teamArrayList.get(position).isSelectValue()) {
                    teamArrayList.get(position).setSelectValue(false);
                } else {
                    teamArrayList.get(position).setSelectValue(true);
                }
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamArrayList.size();
    }

}
