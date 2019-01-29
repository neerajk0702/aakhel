package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcadamicsDetailFragment extends Fragment {
    View view;
    TextView name, ratingTxt, address, uniqueId, establishText;
    RecyclerView imageViewList, servicesList, teamsList;
    ImageView fab;

    public AcadamicsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_acadamics_detail, container, false);
        init();
        return view;
    }

    public void init() {
        name = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        address = view.findViewById(R.id.address);
        uniqueId = view.findViewById(R.id.uniqueId);
        establishText = view.findViewById(R.id.establishText);
        imageViewList = view.findViewById(R.id.imageViewList);
        servicesList = view.findViewById(R.id.servicesList);
        teamsList = view.findViewById(R.id.teamsList);
        fab = view.findViewById(R.id.fab);
    }

}
