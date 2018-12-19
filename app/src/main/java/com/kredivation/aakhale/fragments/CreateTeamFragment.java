package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment implements View.OnClickListener {
    View view;
    ASTEditText teameName, aboutTeam, zipCode;
    Spinner stateSpinner, citySpinner;
    TextView addMoreView, addMoreViewImage;
    ListView AddPlayerLIst;
    GridView imagethumblinView;
    ASTButton continuebtn;

    public CreateTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);
        init();
        getActivity().setTitle("Create Team");
        return view;
    }

    public void init() {
        teameName = view.findViewById(R.id.teameName);
        aboutTeam = view.findViewById(R.id.aboutTeam);
        zipCode = view.findViewById(R.id.zipCode);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        addMoreView = view.findViewById(R.id.addMoreView);
        addMoreViewImage = view.findViewById(R.id.addMoreViewImage);
        AddPlayerLIst = view.findViewById(R.id.AddPlayerLIst);
        imagethumblinView = view.findViewById(R.id.imagethumblinView);
        continuebtn = view.findViewById(R.id.continuebtn);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreView:
                break;

            case R.id.addMoreViewImage:
                break;
        }
    }
}
