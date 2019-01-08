package com.kredivation.aakhale.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AcadamicAdapter;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.model.Academics;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcademicsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcademicsListFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ASTFontTextIconView sortBy;
    ListView acadamicListView;

    ArrayList<Academics> dataModels;
    ListView listView;
    private static AcadamicAdapter adapter;


    public AcademicsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddHomeMyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcademicsListFragment newInstance(String param1, String param2) {
        AcademicsListFragment fragment = new AcademicsListFragment();
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

    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.academics_fragments, container, false);
        init();
        getActivity().setTitle("Academies");
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
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Rahul Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Saurav Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Neeraj Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Narayan Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Semwal Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India ", "3", "0"));
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India", "3", "0"));
        dataModels.add(new Academics("Sachin Cricket Stadium", "Noida Up India", "3", "0"));
        adapter = new AcadamicAdapter(dataModels, getContext());
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
