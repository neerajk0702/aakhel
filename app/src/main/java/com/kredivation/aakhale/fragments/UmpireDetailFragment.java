package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UmpireDetailFragment extends Fragment {
    View view;

    TextView name,ratingTxt;
    ImageView imageView,fab;

    public UmpireDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_umpire_detail, container, false);
        return view;
    }

}
