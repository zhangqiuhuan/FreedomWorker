package com.work.freedomworker.utils;

import android.app.Activity;

import com.work.freedomworker.R;
import com.gyf.immersionbar.ImmersionBar;


public class ImmersionBarUtil {

    public static void ImmersionBarWhite(Activity context){
        ImmersionBar.with(context).statusBarColor(R.color.white).statusBarDarkFont(true).init();
    }

    public static void ImmersionBarWhite2(Activity context){
        ImmersionBar.with(context).statusBarColor(R.color.colorAccent).statusBarDarkFont(true).init();
    }



}
