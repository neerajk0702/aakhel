package com.kredivation.aakhale.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.ChatListAdapter;
import com.kredivation.aakhale.adapter.GroundAdapter;
import com.kredivation.aakhale.model.Academics;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    private View view;
    private Context context;
    private String userId;
    RecyclerView recyclerView;
    ChatListAdapter mAdapter;
    ArrayList<Academics> dataModels;

    public ChatListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void dataToView() {
        dataModels = new ArrayList<>();
        Academics academics = new Academics();
        for (int i = 1; i <= 10; i++) {
            academics.setSname("Sachin Cricket Stadium");
            academics.setScapacity("400000");
            academics.setSaddress("Gali no 23, Near New Delhi Mettro Station,New Delhi INDIA");
            academics.setSstatus("Available");
            dataModels.add(academics);
        }


        mAdapter = new ChatListAdapter(getContext(), dataModels);
        recyclerView.setAdapter(mAdapter);
    }


}