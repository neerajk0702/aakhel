package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AcadamicAdapter;
import com.kredivation.aakhale.adapter.CoachAdapter;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.model.Academics;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoachesFragments extends Fragment implements View.OnClickListener {

    View view;
    ASTFontTextIconView sortBy;
    ListView acadamicListView;

    ArrayList<Academics> dataModels;
    private static CoachAdapter adapter;

    public CoachesFragments() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coaches_fragments, container, false);
        init();
        getActivity().setTitle("Coaches");
        return view;
    }

    private void init() {
        sortBy = view.findViewById(R.id.sortBy);
        sortBy.setOnClickListener(this);
        acadamicListView = view.findViewById(R.id.acadamicListView);
        dataToView();
    }

    public void dataToView() {
        dataModels = new ArrayList<>();
        dataModels.add(new Academics("Sachin Cricket Stadium", "Head Coach", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Coach", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Coach", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Coach", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Coach", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Coach", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Coach", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Coach", "3", "0"));
        adapter = new CoachAdapter(dataModels, getContext());
        acadamicListView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortBy:
                break;
        }
    }


}

