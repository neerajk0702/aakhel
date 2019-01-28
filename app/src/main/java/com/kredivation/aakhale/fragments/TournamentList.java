package com.kredivation.aakhale.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class TournamentList extends Fragment {

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;

    public TournamentList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tournament_list, container, false);
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tabs);
        getActivity().setTitle("Tournament");
        dataToView();
        return view;
    }


    protected void dataToView() {
        TournamentList.ViewPagerAdapter adapter = new TournamentList.ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new UpcommingMatchFragment(), "UPCOMMING");
        adapter.addFragment(new OngingMatchFragment(), "ONGOING");
        adapter.addFragment(new PastMatchFragment(), "PAST");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     * Adapter for the viewpager using FragmentPagerAdapter
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


