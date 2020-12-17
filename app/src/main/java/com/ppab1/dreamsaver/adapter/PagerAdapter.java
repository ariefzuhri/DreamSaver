package com.ppab1.dreamsaver.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ppab1.dreamsaver.tab.FirstTabActivity;
import com.ppab1.dreamsaver.tab.SecondTabActivity;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    public PagerAdapter(@NonNull FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs=numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new FirstTabActivity();
            case 1:
                return new SecondTabActivity();
            default:
                return null;
        }
    }
    @Override
    public int getCount(){
        return numOfTabs;
    }
}