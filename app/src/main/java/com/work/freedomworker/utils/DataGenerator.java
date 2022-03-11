package com.work.freedomworker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.work.freedomworker.R;
import com.work.freedomworker.fragment.HomeFragment;
import com.work.freedomworker.fragment.MineFragment;
import com.work.freedomworker.fragment.TaskFragment;


public class DataGenerator {

    public static final int []mTabRes = new int[]{R.mipmap.ic_home_unfocus,R.mipmap.ic_task_unfocus,R.mipmap.ic_mine_unfocus};
    public static final int []mTabResPressed = new int[]{R.mipmap.ic_home,R.mipmap.ic_task,R.mipmap.ic_mine};
    public static final String []mTabTitle = new String[]{"首页","任务","我的"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[3];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = TaskFragment.newInstance(from);
        fragments[2] = MineFragment.newInstance(from);
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.main_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}

