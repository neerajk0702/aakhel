package com.kredivation.aakhale.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.UmpireDetailActivity;
import com.kredivation.aakhale.model.Team;

import java.util.ArrayList;


public class AddChatAdapter extends RecyclerView.Adapter<AddChatAdapter.ViewHolder> {
    private ArrayList<Team> sportsList;
    Context context;
    String userId;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView teameName, teameAdress;
        ImageView imageSports;
        MaterialCardView MainLayout;

        public ViewHolder(View v) {
            super(v);
            teameName = v.findViewById(R.id.teameName);
            teameAdress = v.findViewById(R.id.teameAdress);
            imageSports = v.findViewById(R.id.imageSports);
            MainLayout = v.findViewById(R.id.MainLayout);
        }
    }

    public AddChatAdapter(Context context, ArrayList<Team> sportsList) {
        this.sportsList = sportsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.add_chat_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.teameName.setText(sportsList.get(position).getName() + "");


        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UmpireDetailActivity.class);
                //intent.putExtra("id", sportsList.get(position).getId());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }


}
