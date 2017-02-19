package com.example.rohrlabs.nostalgia.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();

    public void addFragments (Fragment mFragments, String mTitles){
        this.mFragments.add(mFragments);
        this.mTitles.add(mTitles);
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
