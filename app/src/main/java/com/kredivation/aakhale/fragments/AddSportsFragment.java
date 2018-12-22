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
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSportsFragment extends Fragment {

    View view;
    ASTEditText sportsText;
    RecyclerView sprtsList;
    ASTButton saveBtn;


    public AddSportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_sports, container, false);
        getActivity().setTitle("Add Sports");
        return view;

    }

    public void init() {
        sportsText = view.findViewById(R.id.sportsText);
        sprtsList = view.findViewById(R.id.sprtsList);
        sprtsList.setLayoutManager(new LinearLayoutManager(getContext()));
        saveBtn = view.findViewById(R.id.saveBtn);
        addSportListAdapter();
    }


    //set data into recycle view
    private void addSportListAdapter() {
        ArrayList<ImageItem> sportsList = new ArrayList<>();
        ImageItem data = new ImageItem();
        for (int i = 1; i <= 5; i++) {
            data.setTitle("Cricket");
            sportsList.add(data);
        }

        AddSportsAdapter addSportsAdapter = new AddSportsAdapter(getContext(), sportsList);
        sprtsList.setAdapter(addSportsAdapter);
    }
}
