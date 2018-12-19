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
import com.kredivation.aakhale.components.ASTTextView;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;

public class TopperformanceAdapter extends MetalRecyclerViewPager.MetalAdapter<TopperformanceAdapter.TopperformanceViewHolder> {

    // private final List<String> metalList;

    int[] mResources = {
            R.drawable.tropy1,
            R.drawable.tropy2,
            R.drawable.tropy1,
            R.drawable.tropy2,
    };


    public TopperformanceAdapter(@NonNull DisplayMetrics metrics) {
        super(metrics);
    }

    @Override
    public TopperformanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.toperformancer_item, parent, false);
        return new TopperformanceViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(TopperformanceViewHolder holder, int position) {
        // don't forget about calling supper.onBindViewHolder!
        super.onBindViewHolder(holder, position);
        holder.bgimage.setImageResource(mResources[position]);

    }

    @Override
    public int getItemCount() {
        return mResources.length;
    }

    static class TopperformanceViewHolder extends MetalRecyclerViewPager.MetalViewHolder {

        ImageView bgimage;
        ASTTextView title;

        public TopperformanceViewHolder(View itemView) {
            super(itemView);
            bgimage = itemView.findViewById(R.id.bgimage);
            title = itemView.findViewById(R.id.title);
        }
    }
}
