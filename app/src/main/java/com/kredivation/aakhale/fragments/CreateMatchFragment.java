package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMatchFragment extends Fragment {
    View view;

    public CreateMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_match, container, false);
        getActivity().setTitle("Create Match");
        return view;
    }

}
