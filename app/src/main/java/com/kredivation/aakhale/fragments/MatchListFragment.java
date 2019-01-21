package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.MatchPAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    RecyclerView rvList;
    ASTButton saveBtn;

    public MatchListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchListFragment newInstance(String param1, String param2) {
        MatchListFragment fragment = new MatchListFragment();
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
        view = inflater.inflate(R.layout.fragment_match_list, container, false);
        init();
        return view;
    }

    public void init() {
        rvList = view.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        saveBtn = view.findViewById(R.id.saveBtn);
        getActivity().setTitle("Matches");
        addSportListAdapter();
    }


    //set data into recycle view

    private void addSportListAdapter() {
        ArrayList<ImageItem> sportsList = new ArrayList<>();
        ImageItem data = new ImageItem();
        for (int i = 1; i <= 5; i++) {
            data.setTitle("Noida King vs Ghaziabad Rider");
            sportsList.add(data);
        }

        MatchPAdapter matchPAdapter = new MatchPAdapter(getContext(), sportsList);
        rvList.setAdapter(matchPAdapter);
    }

}
