package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.fragments.AcadamicsDetailFragment;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.utility.Contants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AcadamicAdapter extends RecyclerView.Adapter<AcadamicAdapter.ViewHolder> {
    private ArrayList<Academics> academicsArrayList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, ratingTxt, moreGamesTxt;
        ImageView imageView;
        ASTFontTextIconView matchIcon;
        LinearLayout root_layout;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            address = v.findViewById(R.id.address);
            ratingTxt = v.findViewById(R.id.ratingTxt);
            root_layout = v.findViewById(R.id.root_layout);
            imageView = v.findViewById(R.id.imageView);
            moreGamesTxt = v.findViewById(R.id.moreGamesTxt);
        }
    }

    public AcadamicAdapter(Context context, ArrayList<Academics> academicsArrayList) {
        this.academicsArrayList = academicsArrayList;
        this.context = context;
    }

    @Override
    public AcadamicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.acadamic_item, parent, false);
        AcadamicAdapter.ViewHolder vh = new AcadamicAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AcadamicAdapter.ViewHolder holder, final int position) {
        holder.name.setText(academicsArrayList.get(position).getAcademy_name());
        holder.address.setText(academicsArrayList.get(position).getStreet_address());
        try {
            JSONArray jsonArray = new JSONArray(academicsArrayList.get(position).getAcademy_photos());
            for (int i = 0; i < jsonArray.length(); i++) {
                Picasso.with(context).load(Contants.BASE_URL + jsonArray.get(i).toString()).placeholder(R.drawable.noimage).into(holder.imageView);
                break;
            }

        } catch (JSONException e) {
            // e.printStackTrace();
        }


        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcadamicsDetailFragment acadamicsDetailFragment = new AcadamicsDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", academicsArrayList.get(position).getId());
                acadamicsDetailFragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.mainView, acadamicsDetailFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return academicsArrayList.size();
    }

}
