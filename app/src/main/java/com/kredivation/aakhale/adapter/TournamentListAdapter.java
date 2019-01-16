package com.kredivation.aakhale.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kredivation.aakhale.fragments.OngingMatchFragment;
import com.kredivation.aakhale.fragments.PastMatchFragment;
import com.kredivation.aakhale.fragments.UpcommingMatchFragment;

public class TournamentListAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TournamentListAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UpcommingMatchFragment tab1 = UpcommingMatchFragment.newInstance("", "");
                return tab1;
            case 1:
                OngingMatchFragment tab2 = OngingMatchFragment.newInstance("", "");
                return tab2;
            case 2:
                PastMatchFragment tab3 = PastMatchFragment.newInstance("", "");
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}