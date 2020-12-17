package com.ppab1.dreamsaver.pageradapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ppab1.dreamsaver.fragment.OngoingFragment;
import com.ppab1.dreamsaver.fragment.FinishedFragment;

public class TargetPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    public TargetPagerAdapter(@NonNull FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs=numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new OngoingFragment();
            case 1:
                return new FinishedFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount(){
        return numOfTabs;
    }
}