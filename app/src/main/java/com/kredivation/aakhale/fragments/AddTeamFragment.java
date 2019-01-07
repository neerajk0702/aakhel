package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddTeamsAdapter;
import com.kredivation.aakhale.adapter.AddUmpireAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTeamFragment extends Fragment {

    View view;
    ASTEditText umpreSearchTxt;
    RecyclerView teamList;
    ASTButton saveBtn;

    public AddTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_team, container, false);
        init();
        getActivity().setTitle("Add Teams");
        return view;
    }

    public void init() {
        teamList = view.findViewById(R.id.teamList);
        umpreSearchTxt = view.findViewById(R.id.umpreSearchTxt);
        teamList.setLayoutManager(new LinearLayoutManager(getContext()));
        saveBtn = view.findViewById(R.id.saveBtn);
        addSportListAdapter();
    }


    //set data into recycle view
    private void addSportListAdapter() {
        ArrayList<ImageItem> sportsList = new ArrayList<>();
        ImageItem data = new ImageItem();
        for (int i = 1; i <= 5; i++) {
            data.setTitle("Noida Fresher");
            sportsList.add(data);
        }

        AddTeamsAdapter addTeamsAdapter = new AddTeamsAdapter(getContext(), sportsList);
        teamList.setAdapter(addTeamsAdapter);
    }
}