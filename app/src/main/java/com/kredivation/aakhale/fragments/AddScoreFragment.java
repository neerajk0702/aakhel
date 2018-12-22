package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddScoreFragment extends Fragment {
    ASTEditText scoreTxt, scoreTxt2;
    Spinner playerSpineer, playerSpineer2;
    ASTButton continuebtn;
    View view;

    public AddScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_score, container, false);
        init();
        getActivity().setTitle("Add Score");
        return view;
    }

    public void init() {
        scoreTxt = view.findViewById(R.id.scoreTxt);
        scoreTxt2 = view.findViewById(R.id.scoreTxt2);
        playerSpineer = view.findViewById(R.id.playerSpineer);
        playerSpineer2 = view.findViewById(R.id.playerSpineer2);
        continuebtn = view.findViewById(R.id.continuebtn);
    }

}
