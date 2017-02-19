package com.example.rohrlabs.nostalgia.Adapters;

import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private ArrayList<android.app.Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();

    public void addFragments (android.app.Fragment mFragments, String mTitles){
        this.mFragments.add(mFragments);
        this.mTitles.add(mTitles);
    }

    public ViewPagerAdapter(android.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.app.Fragment getItem(int position) {
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
