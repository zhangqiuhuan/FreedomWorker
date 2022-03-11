package com.work.freedomworker;

import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.work.freedomworker.base.BaseActivity;
import com.work.freedomworker.utils.DataGenerator;
import com.work.freedomworker.utils.ImmersionBarUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    public String TAG = "MainActivity";
    @BindView(R.id.tl_navigationbar)
    TabLayout tlNavigationBar;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_task)
    ImageView ivTask;
    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.ll_task)
    LinearLayout llTask;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;

    private Fragment[] mFragmensts;

    List<String> mTitleList;


    private static boolean isPermissionRequested = false;

    private static MainActivity instance;
    static{
        instance=new MainActivity();
    }

    public static MainActivity getInstance(){
        if(instance==null){
            instance=new MainActivity();
        }
        return instance;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }



    @Override
    protected void initView() {
        //请求权限
        //requestPermission();


        mTitleList = new ArrayList<>();
//        mTitleList.add("")
        ImmersionBarUtil.ImmersionBarWhite2(mContext);

        mFragmensts = DataGenerator.getFragments("TabLayout Tab");

        tlNavigationBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                onTabItemSelected(tab.getPosition(),1);

                //改变Tab 状态
                for (int i = 0; i < tlNavigationBar.getTabCount(); i++) {
                    if (i == tab.getPosition()) {
                        tlNavigationBar.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabResPressed[i]));
                    } else {
                        tlNavigationBar.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabRes[i]));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tlNavigationBar.addTab(tlNavigationBar.newTab().setIcon(getResources().getDrawable(R.mipmap.ic_home)).setText(DataGenerator.mTabTitle[0]));
        tlNavigationBar.addTab(tlNavigationBar.newTab().setIcon(getResources().getDrawable(R.mipmap.ic_task)).setText(DataGenerator.mTabTitle[1]));
        tlNavigationBar.addTab(tlNavigationBar.newTab().setIcon(getResources().getDrawable(R.mipmap.ic_mine)).setText(DataGenerator.mTabTitle[2]));
    }
    /**
     * Android6.0之后需要动态申请权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissionsList = new ArrayList<>();

            String[] permissions = {
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES

            };

            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                    permissionsList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }

            if (permissionsList.isEmpty()) {
                return;
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHome(1);

            }
        });
        llTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTask(0);
            }
        });
        llMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMine(1);
            }
        });
    }
    private void onTabItemSelected(int position,int type) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;

            case 2:
                fragment = mFragmensts[2];
                break;
        }
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();
        }
    }

    private void defaultText(){
        tvHome.setTextColor(getResources().getColor(R.color.defaultcolor));
        tvTask.setTextColor(getResources().getColor(R.color.defaultcolor));
        tvMine.setTextColor(getResources().getColor(R.color.defaultcolor));
    }

    private void selectText(int index){
        switch (index){
            case 0:
                tvHome.setTextColor(getResources().getColor(R.color.maincolor));
                break;
            case 1:
                tvTask.setTextColor(getResources().getColor(R.color.maincolor));
                break;
            case 2:
                tvMine.setTextColor(getResources().getColor(R.color.maincolor));
                break;
        }


    }
    private void defaultIcon(){
        ivHome.setBackgroundResource(DataGenerator.mTabRes[0]);
        ivTask.setBackgroundResource(DataGenerator.mTabRes[1]);
        ivMine.setBackgroundResource(DataGenerator.mTabRes[2]);
    }

    private void selectIcon(int index){
        switch (index){
            case 0:
                ivHome.setBackgroundResource(DataGenerator.mTabResPressed[0]);
                break;
            case 1:
                ivTask.setBackgroundResource(DataGenerator.mTabResPressed[1]);
                break;
            case 2:
                ivMine.setBackgroundResource(DataGenerator.mTabResPressed[2]);
                break;
        }
    }
    public void selectHome(int type){
        onTabItemSelected(0,type);
        defaultText();
        defaultIcon();
        selectText(0);
        selectIcon(0);
    }

    public void selectTask(int type){
        onTabItemSelected(1,type);
        defaultText();
        defaultIcon();
        selectText(1);
        selectIcon(1);
    }

    public void selectMine(int type){
        onTabItemSelected(2,type);
        defaultText();
        defaultIcon();
        selectText(2);
        selectIcon(2);
    }


    //重写onKeyDown(),实现连续两次点击方可退出当前应用
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}