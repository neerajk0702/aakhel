package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.ChatListAdapter;
import com.kredivation.aakhale.adapter.ChatMessageAdapter;
import com.kredivation.aakhale.model.Academics;
import com.kredivation.aakhale.model.Data;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatDetailFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    EditText et_comment;
    TextView add_comment;
    ArrayList<Data> dataModels;

    ChatMessageAdapter mAdapter;

    public ChatDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_detail, container, false);
        init();
        return view;
    }


    public void init() {
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataToView();
        getActivity().setTitle("Chat Details");
    }


    public void dataToView() {
        dataModels = new ArrayList<>();
        Data data = new Data();
        int idd = 1;
        for (int i = 1; i <= 10; i++) {
            data.setFull_name("Sachin Cricket Stadium");
            data.setId(idd);
            data.setAddress("Gali no 23, Near New Delhi Mettro Station,New Delhi INDIA");
            dataModels.add(data);
            idd++;
        }


        mAdapter = new ChatMessageAdapter(getContext(), dataModels, "1");
        recyclerView.setAdapter(mAdapter);
    }

}
