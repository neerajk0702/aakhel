package com.kredivation.aakhale.fragments;


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
import com.kredivation.aakhale.adapter.UpcommingMatchAdapter;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    TextView name, ratingTxt, age, ExperienceTxt, OdieTxt, boloingTxt, roleTxt, testinformation, status, testinformation2, Ok2,

    challange, REQUEST, INVITE;
    MetalRecyclerViewPager topperformanceviewPager;
    ImageView fab;

    public PlayerDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerDetailsFragment newInstance(String param1, String param2) {
        PlayerDetailsFragment fragment = new PlayerDetailsFragment();
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
        view = inflater.inflate(R.layout.fragment_player_details, container, false);

        getActivity().setTitle("Players Details");
        init();
        return view;
    }

    public void init() {
        name = view.findViewById(R.id.name);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        age = view.findViewById(R.id.age);
        ExperienceTxt = view.findViewById(R.id.ExperienceTxt);
        OdieTxt = view.findViewById(R.id.OdieTxt);
        boloingTxt = view.findViewById(R.id.boloingTxt);
        roleTxt = view.findViewById(R.id.roleTxt);
        testinformation = view.findViewById(R.id.testinformation);
        status = view.findViewById(R.id.status);
        testinformation2 = view.findViewById(R.id.testinformation2);
        Ok2 = view.findViewById(R.id.Ok2);

        challange = view.findViewById(R.id.challange);
        REQUEST = view.findViewById(R.id.REQUEST);
        INVITE = view.findViewById(R.id.INVITE);
        fab = view.findViewById(R.id.fab);
        setTopPerformanceViewPager();
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
