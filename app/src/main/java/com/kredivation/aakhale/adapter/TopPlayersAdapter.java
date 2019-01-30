/*
 * Copyright (C) 2017. Alexander Bilchuk <a.bilchuk@sandrlab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kredivation.aakhale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.model.Team_player;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopPlayersAdapter extends MetalRecyclerViewPager.MetalAdapter<TopPlayersAdapter.TopperformanceViewHolder> {

    // private final List<String> metalList;

    ArrayList<Team_player> team_playerArrayLis;
    Context context;

    public TopPlayersAdapter(@NonNull DisplayMetrics metrics, ArrayList<Team_player> team_playerArrayList, Context contx) {
        super(metrics);
        this.team_playerArrayLis = team_playerArrayList;
        this.context = contx;
    }

    @Override
    public TopperformanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teame_player_row, parent, false);
        return new TopperformanceViewHolder(viewItem);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TopperformanceViewHolder holder, int position) {
        // don't forget about calling supper.onBindViewHolder!
        super.onBindViewHolder(holder, position);
        holder.playeName.setText(team_playerArrayLis.get(position).getName());
        if (team_playerArrayLis.get(position).getStatus().equalsIgnoreCase("0")) {
            holder.status.setText("PENDING");
            holder.status.setTextColor(context.getResources().getColor(R.color.orange_color));
        } else if (team_playerArrayLis.get(position).getStatus().equalsIgnoreCase("0")) {
            holder.status.setText("APPROVED");
            holder.status.setTextColor(context.getResources().getColor(R.color.green2));
        }


        if (team_playerArrayLis.get(position).getProfile_picture() != null && !team_playerArrayLis.get(position).getProfile_picture().equals("")) {
            Picasso.with(context).load("http://cricket.vikaskumar.info/" + team_playerArrayLis.get(position).getProfile_picture())
                    .placeholder(R.drawable.noimage).into(holder.playerImage);
        }

    }

    @Override
    public int getItemCount() {
        return team_playerArrayLis.size();
    }

    static class TopperformanceViewHolder extends MetalRecyclerViewPager.MetalViewHolder {

        ASTTextView playeName, status;
        ImageView playerImage;

        public TopperformanceViewHolder(View v) {
            super(v);
            playeName = v.findViewById(R.id.playeName);
            status = v.findViewById(R.id.status);
            playerImage = v.findViewById(R.id.playerImage);
        }
    }
}
