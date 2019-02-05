package com.kredivation.aakhale.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.dummy.DummyContent;

import java.util.List;

public class MyPostItemRecyclerViewAdapter extends RecyclerView.Adapter<MyPostItemRecyclerViewAdapter.ViewHolder> {

    List<DummyContent> Itemlist;

    public MyPostItemRecyclerViewAdapter(List<DummyContent> gaggeredList) {
        Itemlist = gaggeredList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_postitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.lblTxt.setText(Itemlist.get(position).getLblTxt());
        holder.image.setImageResource(Itemlist.get(position).getImgPayhl());
        holder.descrition.setText(Itemlist.get(position).getLblTxt());



        //  holder.image.setText(Itemlist.get(position).image);
    }

    @Override
    public int getItemCount() {
        return Itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView image;
        public final TextView lblTxt;
        public final TextView descrition;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = view.findViewById(R.id.image);
            lblTxt = view.findViewById(R.id.lblTxt);
            descrition = (TextView) view.findViewById(R.id.descrition);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblTxt.getText() + "'";
        }
    }
}
