package com.work.freedomworker.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author gaozhen
 * @date 2018/1/26.
 * @descripe TODO
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 是否可见状态
     */
    private boolean isVisible;

    /**
     * 标志位，View已经初始化完成。
     */
    private boolean isPrepared/* = true*/;
    private boolean forceLoad;

    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

    public FragmentActivity mContext;
    public View rootView;

    Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
        // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
        // 导致initData反复执行,所以这里注释掉
        // isFirstLoad = true;

        // 取消 isFirstLoad = true的注释 , 因为上述的initData本身就是应该执行的
        // onCreateView执行 证明被移出过FragmentManager initData确实要执行.
        // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
        if (rootView == null) {
            mContext = getActivity();
            isFirstLoad = true;
            rootView = layoutInflater.inflate(setContentView(), container, false);
            unbinder= ButterKnife.bind(this, rootView);
            isPrepared = true;
            lazyLoad();
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }

//        ImmersionBar.setTitleBar(mContext, toolbar);
        initView();
        initListener();
        Log.e("initData11","onCreateView");
        initData();
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // unbinder.unbind();//用原始的findViewBuId()的方法进行控件赋值或者去掉onDestroyView里面的ButterKnife.unbind(this)方法。即不进行清除。
    }

    /*@Override
    public void initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init();
    }*/


    protected abstract int setContentView();

    public void showToast(String text){
        Toast.makeText(mContext,text, Toast.LENGTH_SHORT).show();
    }
    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     * visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }



    protected void onVisible() {
        lazyLoad();
    }


    protected void onInvisible() {
    }


    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (isPrepared && isVisible && isFirstLoad) {
            Log.e("initData11","lazyLoad1");

            isFirstLoad = true;
            initData();
        }else if (forceLoad && isPrepared&&isFirstLoad){
            Log.e("initData11","lazyLoad2");
            isFirstLoad = true;
            initData();
        }
    }

    public void setForceLoad(boolean forceLoad) {
        this.forceLoad = forceLoad;
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
}

