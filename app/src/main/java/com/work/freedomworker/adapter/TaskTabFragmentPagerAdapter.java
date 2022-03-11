package com.work.freedomworker.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TaskTabFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;//各导航的Fragment
    private List<String> mTitle; //导航的标题

    public TaskTabFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment>fragments, List<String>title){
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentList=fragments;
        mTitle=title;

    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }

}
