package com.example.athleticskenya.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    TabLayout tabLayout;
    public TabsPagerAdapter(FragmentManager fm, TabLayout _tabLayout) {
        super(fm);
        this.tabLayout = _tabLayout;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new MaleFragment();
        }
        else if (position == 1)
        {
            fragment = new FemaleFragment();
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Male";
        }
        else if (position == 1)
        {
            title = "Female";
        }
        return title;
    }
}
