package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AcadamicAdapter;
import com.kredivation.aakhale.adapter.GroundAdapter;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.model.Academics;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroundFragment extends Fragment implements View.OnClickListener {

    View view;
    ASTFontTextIconView sortBy;
    ListView groundListView;

    ArrayList<Academics> dataModels;
    private static GroundAdapter adapter;


    public GroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ground, container, false);
        init();
        getActivity().setTitle("Ground");
        return view;
    }

    private void init() {
        sortBy = view.findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        groundListView = view.findViewById(R.id.groundListView);
        dataToView();
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


        adapter = new GroundAdapter(dataModels, getContext());
        groundListView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortBy:
                break;
        }
    }
}
