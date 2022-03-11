package com.work.freedomworker.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.work.freedomworker.R;
import com.work.freedomworker.utils.CustomDefaultToast;
import com.work.freedomworker.utils.CustomToastDialog;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    public Activity mContext;

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    FrameLayout viewContent;
    public Bundle bundle;
    private InputMethodManager mInputMethodManager;

    private CustomToastDialog mCustomToastDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = this;

        viewContent = findViewById(R.id.viewContent);
        View view = LayoutInflater.from(mContext).inflate(getContentView(), viewContent);
        bundle = savedInstanceState;
        ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
       ActivityManager.getActivityManager().pushActivity(this);

//        //初始时加入如下两行代码
//        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
//        refWatcher.watch(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInputMethodManager = null;

    }

    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    //显示Toast
    public void showCustomDefaultToast(int flag, String message){
        CustomDefaultToast customDefaultToast = new CustomDefaultToast(mContext);
        customDefaultToast.showDefault(flag, message);
    }
    public CustomToastDialog getmCustomToastDialog(){
        if (mCustomToastDialog == null)
            mCustomToastDialog = new CustomToastDialog(mContext, R.style.dialog_style);
        return mCustomToastDialog;
    }

    public void showCustomDefaultDialog(String message, String btnmessage){
        getmCustomToastDialog()
                .setTextStr(message)
                .setOkStr(btnmessage)
                .setListener(new CustomToastDialog.ListenerCustomToastDialo() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }


    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mInputMethodManager != null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    @Override
    protected void onPause() {
        super.onPause();
       ActivityManager.getActivityManager().pushActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.getActivityManager().popActivity(this);
    }
}
