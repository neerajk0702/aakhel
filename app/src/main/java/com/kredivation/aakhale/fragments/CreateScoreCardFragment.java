package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateScoreCardFragment extends Fragment {

    View view;
    ASTEditText teame1, teame2;
    ASTButton continuebtn;

    public CreateScoreCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_score_card, container, false);
        getActivity().setTitle("Create ScoreCard");
        init();
        return view;

    }

    public void init() {
      //  teame1 = view.findViewById(R.id.teame1);
       // teame2 = view.findViewById(R.id.teame2);
        continuebtn = view.findViewById(R.id.continuebtn);
    }

}
