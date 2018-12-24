package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddSportsAdapter;
import com.kredivation.aakhale.adapter.AddUmpireAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUmpiresFragment extends Fragment {

    View view;
    ASTEditText umpreSearchTxt;
    RecyclerView umpireList;
    ASTButton saveBtn;

    public AddUmpiresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_umpires, container, false);
        init();
        getActivity().setTitle("Add Umpires");
        return view;
    }

    public void init() {
        umpireList = view.findViewById(R.id.umpireList);
        umpreSearchTxt = view.findViewById(R.id.umpreSearchTxt);
        umpireList.setLayoutManager(new LinearLayoutManager(getContext()));
        saveBtn = view.findViewById(R.id.saveBtn);
        addSportListAdapter();
    }


    //set data into recycle view
    private void addSportListAdapter() {
        ArrayList<ImageItem> sportsList = new ArrayList<>();
        ImageItem data = new ImageItem();
        for (int i = 1; i <= 5; i++) {
            data.setTitle("Ravi Sastri");
            sportsList.add(data);
        }

        AddUmpireAdapter addUmpireAdapter = new AddUmpireAdapter(getContext(), sportsList);
        umpireList.setAdapter(addUmpireAdapter);
    }
}
