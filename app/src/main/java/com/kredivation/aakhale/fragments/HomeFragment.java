package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kredivation.aakhale.ApplicationHelper;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.FullMetalAdapter;
import com.kredivation.aakhale.adapter.TopperformanceAdapter;
import com.kredivation.aakhale.adapter.UpcommingMatchAdapter;
import com.kredivation.aakhale.components.ASTFontTextIconView;
import com.kredivation.aakhale.pagerlib.MetalRecyclerViewPager;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ASTFontTextIconView playersTxt, teamsTxt, umpiresTxt, stadiumsTxt, coachesTxt, tournamentTxt, PERFORMERTxt, upcommingTxt, EVENTSTxt, ACADEMICSTxt;
    LinearLayout playersLayout, teamsLayout, umpireLayout, stadiumsLayout, coachesLayout, tournamentLayout, PERFORMERLayout,
            UPCOMMINGLayout, EVENTSLayout, ACADEMICSLayout;


    List<String> list;
    int[] imageId = {
            R.drawable.ic_cricket_player,
            R.drawable.ic_team,
            R.drawable.ic_cricket_player,
            R.drawable.ic_cricket_ground,
            R.drawable.ic_team,
            R.drawable.ic_team,
            R.drawable.ic_cricket_player,
            R.drawable.ic_team,
            R.drawable.ic_cricket_player,
            R.drawable.ic_cricket_ground,
            R.drawable.ic_team,
            R.drawable.ic_team,
    };
    String[] name = {

            "Players",
            "Team",
            "Umpire",
            "Stadium",
            "Coach",
            "Tourementd",
            "Players",
            "Team",
            "Umpire",
            "Stadium",
            "Coach",
            "Tourementd",
    };

    public HomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);

        playersTxt = view.findViewById(R.id.playersTxt);
        teamsTxt = view.findViewById(R.id.teamsTxt);
        umpiresTxt = view.findViewById(R.id.umpiresTxt);
        stadiumsTxt = view.findViewById(R.id.stadiumsTxt);
        coachesTxt = view.findViewById(R.id.coachesTxt);
        tournamentTxt = view.findViewById(R.id.tournamentTxt);
        PERFORMERTxt = view.findViewById(R.id.PERFORMERTxt);
        upcommingTxt = view.findViewById(R.id.upcommingTxt);
        EVENTSTxt = view.findViewById(R.id.EVENTSTxt);
        ACADEMICSTxt = view.findViewById(R.id.ACADEMICSTxt);
        playersLayout = view.findViewById(R.id.playersLayout);
        teamsLayout = view.findViewById(R.id.teamsLayout);
        umpireLayout = view.findViewById(R.id.umpireLayout);
        stadiumsLayout = view.findViewById(R.id.stadiumsLayout);
        coachesLayout = view.findViewById(R.id.coachesLayout);
        tournamentLayout = view.findViewById(R.id.tournamentLayout);
        PERFORMERLayout = view.findViewById(R.id.PERFORMERLayout);
        UPCOMMINGLayout = view.findViewById(R.id.UPCOMMINGLayout);
        EVENTSLayout = view.findViewById(R.id.EVENTSLayout);
        ACADEMICSLayout = view.findViewById(R.id.ACADEMICSLayout);
        settopViewPager();
        setMenuItem();
        setTopPerformanceViewPager();
        setUpcommingMatch();

        return view;
    }


    public void settopViewPager() {
        DisplayMetrics metrics = getDisplayMetrics();
        List<String> metalList = Arrays.asList("R.drawable.pager1", "R.drawable.pager2", "R.drawable.pager3", "aa");
        FullMetalAdapter fullMetalAdapter = new FullMetalAdapter(metrics);

        MetalRecyclerViewPager viewPager = (MetalRecyclerViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(fullMetalAdapter);


    }

    /**
     * set menu item icon and action.........
     */

    public void setMenuItem() {
        playersTxt.setText(Html.fromHtml("&#xf101;"));
        teamsTxt.setText(Html.fromHtml("&#xf100;"));
        umpiresTxt.setText(Html.fromHtml("&#xf105;"));
        stadiumsTxt.setText(Html.fromHtml("&#xf102;"));
        coachesTxt.setText(Html.fromHtml("&#xf103;"));
        tournamentTxt.setText(Html.fromHtml("&#xf104;"));
        PERFORMERTxt.setText(Html.fromHtml("&#xf105;"));
        upcommingTxt.setText(Html.fromHtml("&#xf107;"));
        EVENTSTxt.setText(Html.fromHtml("&#xf108;"));
        ACADEMICSTxt.setText(Html.fromHtml("&#xf109;"));

        playersTxt.setOnClickListener(this);
        teamsTxt.setOnClickListener(this);
        umpiresTxt.setOnClickListener(this);
        stadiumsTxt.setOnClickListener(this);
        coachesTxt.setOnClickListener(this);
        tournamentTxt.setOnClickListener(this);
        PERFORMERTxt.setOnClickListener(this);
        upcommingTxt.setOnClickListener(this);
        EVENTSTxt.setOnClickListener(this);
        ACADEMICSTxt.setOnClickListener(this);

    }

    public void setTopPerformanceViewPager() {
        DisplayMetrics metrics = getDisplayMetrics();
        List<String> metalList = Arrays.asList("R.drawable.pager1", "R.drawable.pager2", "R.drawable.pager3", "aa");
        TopperformanceAdapter topperformanceAdapter = new TopperformanceAdapter(metrics);
        MetalRecyclerViewPager viewPager = view.findViewById(R.id.topperformanceviewPager);
        viewPager.setAdapter(topperformanceAdapter);


    }

    private DisplayMetrics getDisplayMetrics() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics;
    }

    public void setUpcommingMatch() {
        DisplayMetrics metrics = getDisplayMetrics();
        List<String> metalList = Arrays.asList("R.drawable.pager1", "R.drawable.pager2", "R.drawable.pager3", "aa");
        UpcommingMatchAdapter upcommingMatchAdapter = new UpcommingMatchAdapter(metrics);
        MetalRecyclerViewPager viewPager = view.findViewById(R.id.upcommingMatch);
        viewPager.setAdapter(upcommingMatchAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ACADEMICSTxt:
                AcademicsFragment academicsFragment = new AcademicsFragment();
                updateFragment(academicsFragment, null);
                break;
        }
    }

    public void updateFragment(Fragment pageFragment, Bundle bundle) {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        pageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainView, pageFragment);
        fragmentTransaction.commit();
    }
}
