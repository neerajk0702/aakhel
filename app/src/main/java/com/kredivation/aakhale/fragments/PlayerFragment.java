package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddSportsAdapter;
import com.kredivation.aakhale.adapter.PlayerAdapter;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {

    View view;
    RecyclerView sprtsList;

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player, container, false);
        getActivity().setTitle("Players");
        init();
        return view;

    }

    public void init() {
        sprtsList = view.findViewById(R.id.sprtsList);
        sprtsList.setLayoutManager(new LinearLayoutManager(getContext()));
        addSportListAdapter();
    }


    //set data into recycle view
    private void addSportListAdapter() {
        ArrayList<ImageItem> sportsList = new ArrayList<>();
        ImageItem data = new ImageItem();
        for (int i = 1; i <= 5; i++) {
            data.setTitle("Rahul Rawat");
            sportsList.add(data);
        }

        PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), sportsList);
        sprtsList.setAdapter(playerAdapter);
    }
}
