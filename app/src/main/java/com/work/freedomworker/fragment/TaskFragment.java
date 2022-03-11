package com.work.freedomworker.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.work.freedomworker.R;
import com.work.freedomworker.adapter.TaskTabFragmentPagerAdapter;
import com.work.freedomworker.base.BaseLazyLoadFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TaskFragment extends BaseLazyLoadFragment {
    private static final String TAG = "TaskFragment";
    @BindView(R.id.vp_task)
    ViewPager vpTask;
    private TaskTabFragmentPagerAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    @BindView(R.id.tl_task)
    TabLayout tlTask;
    private TaskTabAllFragment fragment1, fragment2, fragment3, fragment4;
    private List<String> mTitles;
    private String[] title = {"全部", "已报名", "已录用", "已完成"};

    public static TaskFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_task;
    }

    @Override
    protected void onBaseFragmentVisibleChange(boolean isVisible) {
        Log.e(TAG, "onBaseFragmentVisibleChange-" + isVisible);
        if (isVisible) {
            //Log.e(TAG,"onBaseFragmentVisibleChange-"+isVisible);
        } else {
            //  mRefreshListener.onRefreshFinish();
        }
    }

    @Override
    protected void initView() {
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            mTitles.add(title[i]);
        }
        fragmentList.add(new TaskTabAllFragment());
        fragmentList.add(new TaskTabStayFragment());
        fragmentList.add(new TaskTabAlreadyFragment());
        fragmentList.add(new TaskTabFinishFragment());


        fragmentAdapter = new TaskTabFragmentPagerAdapter(getChildFragmentManager(), fragmentList, mTitles);
        vpTask.setAdapter(fragmentAdapter);
        tlTask.setupWithViewPager(vpTask);//与ViewPage建立关系
        vpTask.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //position 从0开始
                //  showToast("选择："+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {
    }


    @Override
    protected void initListener() {

    }
}
