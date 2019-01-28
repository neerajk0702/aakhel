package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.TopperformanceAdapter;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class TeamDetailFragment extends Fragment {
    ImageView imageView, fab;
    TextView name, ratingTxt, wonmatch, Tournamentwon, tmeavsteam, stadiumaddress, date, tmeavsteam1, stadiumaddress1, date1;
    View view;
    Bundle bundle;

    public TeamDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail, container, false);

        setTopPerformanceViewPager();
        return view;

    }

    public void init() {
        imageView = view.findViewById(R.id.imageView);
        fab = view.findViewById(R.id.fab);
        name = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        wonmatch = view.findViewById(R.id.wonmatch);
        Tournamentwon = view.findViewById(R.id.Tournamentwon);
        tmeavsteam = view.findViewById(R.id.tmeavsteam);
        stadiumaddress = view.findViewById(R.id.stadiumaddress);
        date = view.findViewById(R.id.date);
        tmeavsteam1 = view.findViewById(R.id.tmeavsteam1);
        stadiumaddress1 = view.findViewById(R.id.stadiumaddress1);
        date1 = view.findViewById(R.id.date1);

    }


    public void setTopPerformanceViewPager() {
        DisplayMetrics metrics = getDisplayMetrics();
        List<String> metalList = Arrays.asList("R.drawable.pager1", "R.drawable.pager2", "R.drawable.pager3", "aa");
        TopperformanceAdapter topperformanceAdapter = new TopperformanceAdapter(metrics);
        MetalRecyclerViewPager viewPager = view.findViewById(R.id.tgallaryIMageviewPager);
        viewPager.setAdapter(topperformanceAdapter);


    }

    private DisplayMetrics getDisplayMetrics() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics;
    }
}
