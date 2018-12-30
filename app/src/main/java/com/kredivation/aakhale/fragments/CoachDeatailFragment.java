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
public class CoachDeatailFragment extends Fragment {

    View view;

    public CoachDeatailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coach_deatail, container, false);
        getActivity().setTitle("Coaches Details");
        return view;
    }

}
