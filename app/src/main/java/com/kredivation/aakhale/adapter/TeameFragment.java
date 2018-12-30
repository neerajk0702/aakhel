package com.kredivation.aakhale.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeameFragment extends Fragment {
    View view;
    RecyclerView topTeamsList;

    public TeameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teame, container, false);
        getActivity().setTitle("Teams");
        init();
        return view;

    }

    public void init() {
        topTeamsList = view.findViewById(R.id.topTeamsList);
        topTeamsList.setLayoutManager(new LinearLayoutManager(getContext()));
        addSportListAdapter();
    }


    //set data into recycle view
    private void addSportListAdapter() {
        ArrayList<ImageItem> sportsList = new ArrayList<>();
        ImageItem data = new ImageItem();
        for (int i = 1; i <= 5; i++) {
            data.setTitle("Ghaziabd Tiger");
            sportsList.add(data);
        }

        TeamsAdapter playerAdapter = new TeamsAdapter(getContext(), sportsList);
        topTeamsList.setAdapter(playerAdapter);
    }
}
