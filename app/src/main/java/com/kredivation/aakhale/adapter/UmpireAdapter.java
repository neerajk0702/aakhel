package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.UmpireDetailActivity;
import com.kredivation.aakhale.model.Data;
import com.kredivation.aakhale.utility.Contants;
import com.kredivation.aakhale.utility.FontManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UmpireAdapter extends RecyclerView.Adapter<UmpireAdapter.ViewHolder> {
    private ArrayList<Data> sportsList;
    Context context;
    String userId;
    boolean selectFlage;
    Typeface materialdesignicons_font;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, coachType, ratingTxt, selectCheck, userId, address;
        ImageView sportsIcon, menumore, imageView;
        CardView root_layout;


        public ViewHolder(View v) {
            super(v);
            userId = v.findViewById(R.id.userId);
            name = v.findViewById(R.id.name);
            coachType = v.findViewById(R.id.coachType);
            ratingTxt = v.findViewById(R.id.ratingTxt);
            sportsIcon = v.findViewById(R.id.sportsIcon);
            root_layout = v.findViewById(R.id.root_layout);
            menumore = v.findViewById(R.id.menumore);
            imageView = v.findViewById(R.id.imageView);
            selectCheck = v.findViewById(R.id.selectCheck);
            selectCheck.setTypeface(materialdesignicons_font);
            address = v.findViewById(R.id.address);
        }
    }

    public UmpireAdapter(Context context, ArrayList<Data> sportsList, boolean selectFlag) {
        this.sportsList = sportsList;
        this.context = context;
        this.selectFlage = selectFlag;
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(context, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public UmpireAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.umpire_item_row, parent, false);
        UmpireAdapter.ViewHolder vh = new UmpireAdapter.ViewHolder(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull UmpireAdapter.ViewHolder holder, final int position) {
        holder.name.setText(sportsList.get(position).getFull_name());
        holder.address.setText(sportsList.get(position).getComplateAddress());
        try {
            if (sportsList.get(position).getUsersSportArray() != null) {
                JSONArray jsonArray = new JSONArray(sportsList.get(position).getUsersSportArray());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.optJSONObject(i);
                    String sports_name = obj.optString("sports_name");
                    holder.coachType.setText(sports_name + "");
                }
            }

        } catch (JSONException e) {
        }

        holder.userId.setText(sportsList.get(position).getUnique_id());
        Picasso.with(context).load(Contants.BASE_URL + sportsList.get(position).getProfile_picture()).placeholder(R.drawable.ic_uuuser).into(holder.imageView);
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataValue = new Gson().toJson(sportsList.get(position));
                Intent intent = new Intent(context, UmpireDetailActivity.class);
                intent.putExtra("id", sportsList.get(position).getId());
                intent.putExtra("Detail", dataValue);
                context.startActivity(intent);
            }
        });

        if (selectFlage) {
            holder.selectCheck.setVisibility(View.VISIBLE);
            if (sportsList.get(position).isSelectValue()) {
                holder.selectCheck.setText(Html.fromHtml("&#xf132;"));
                holder.selectCheck.setTextColor(Color.parseColor("#198719"));
            } else {
                holder.selectCheck.setText(Html.fromHtml("&#xf131;"));
                holder.selectCheck.setTextColor(Color.parseColor("#000000"));
            }
        }
        holder.selectCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sportsList.get(position).isSelectValue()) {
                    sportsList.get(position).setSelectValue(false);
                } else {
                    sportsList.get(position).setSelectValue(true);
                }
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }


}