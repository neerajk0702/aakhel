package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddTournamnetPAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class UpcommingMatchFragment extends Fragment {

    View view;
    RecyclerView tList;
    ASTButton saveBtn;

    public UpcommingMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcomming_match, container, false);
        init();
        return view;
    }

    public void init() {
        tList = view.findViewById(R.id.tList);
        tList.setLayoutManager(new LinearLayoutManager(getContext()));
        saveBtn = view.findViewById(R.id.saveBtn);
        addSportListAdapter();
    }


    //set data into recycle view

    private void addSportListAdapter() {
        ArrayList<ImageItem> sportsList = new ArrayList<>();
        ImageItem data = new ImageItem();
        for (int i = 1; i <= 5; i++) {
            data.setTitle("Turnament Name");
            sportsList.add(data);
        }

        AddTournamnetPAdapter addUmpireAdapter = new AddTournamnetPAdapter(getContext(), sportsList);
        tList.setAdapter(addUmpireAdapter);
    }

}
