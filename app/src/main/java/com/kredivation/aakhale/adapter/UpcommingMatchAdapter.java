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

import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;

public class UpcommingMatchAdapter extends MetalRecyclerViewPager.MetalAdapter<UpcommingMatchAdapter.UpcommingMatchViewHolder> {

    // private final List<String> metalList;

    int[] mResources = {
            R.drawable.match1,
            R.drawable.match1,
            R.drawable.match1,
            R.drawable.match1,
    };


    public UpcommingMatchAdapter(@NonNull DisplayMetrics metrics) {
        super(metrics);
    }

    @Override
    public UpcommingMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_match_item, parent, false);
        return new UpcommingMatchViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(UpcommingMatchViewHolder holder, int position) {
        // don't forget about calling supper.onBindViewHolder!
        super.onBindViewHolder(holder, position);
        holder.imageView.setImageResource(mResources[position]);

    }

    @Override
    public int getItemCount() {
        return mResources.length;
    }

    static class UpcommingMatchViewHolder extends MetalRecyclerViewPager.MetalViewHolder {

        ImageView imageView;

        public UpcommingMatchViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
