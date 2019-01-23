package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddTournamnetPAdapter;
import com.kredivation.aakhale.adapter.MatchPAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.model.ImageItem;
import com.kredivation.aakhale.model.Match;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MatchFragmentP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchFragmentP extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    RecyclerView topTeamsList;
    ASTButton saveBtn;

    public MatchFragmentP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchFragmentP.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchFragmentP newInstance(String param1, String param2) {
        MatchFragmentP fragment = new MatchFragmentP();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_fragment, container, false);
        init();
        return view;
    }

    public void init() {
        topTeamsList = view.findViewById(R.id.topTeamsList);
        topTeamsList.setLayoutManager(new LinearLayoutManager(getContext()));
        saveBtn = view.findViewById(R.id.saveBtn);
        addSportListAdapter();
    }


    //set data into recycle view

    private void addSportListAdapter() {
        ArrayList<Match> sportsList = new ArrayList<>();
        Match data = new Match();
        for (int i = 1; i <= 5; i++) {
            data.setName("Noida King vs Ghaziabad Rider");
            sportsList.add(data);
        }

        MatchPAdapter matchPAdapter = new MatchPAdapter(getContext(), sportsList);
        topTeamsList.setAdapter(matchPAdapter);
    }

}
