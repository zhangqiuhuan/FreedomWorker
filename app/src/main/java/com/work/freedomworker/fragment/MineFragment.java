package com.work.freedomworker.fragment;

import android.os.Bundle;

import com.work.freedomworker.R;
import com.work.freedomworker.base.BaseLazyLoadFragment;

public class MineFragment extends BaseLazyLoadFragment {


    public static MineFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
        return  R.layout.fragment_mine;
    }

    @Override
    protected void onBaseFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
