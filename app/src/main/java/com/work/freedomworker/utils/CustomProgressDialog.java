package com.work.freedomworker.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.work.freedomworker.R;

import java.util.ArrayList;
import java.util.List;

public class CustomProgressDialog extends Dialog implements View.OnClickListener{
    private TextView tv_cancel;
    private TextView tv_text;
    private ProgressBar progressBar;

    private List<ListenerDialog> aList;
    private String text_cancel;
    private String text;
    private int pro;
    private ListenerDialog mListener;


    public CustomProgressDialog( Context context) {
        super(context);
    }

    public CustomProgressDialog( Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);  //区域外键
        setCancelable(false);  //返回键
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        aList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_progress);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        tv_cancel = findViewById(R.id.tv_cancel);
        tv_text = findViewById(R.id.tv_message);
        progressBar=findViewById(R.id.progress);
        tv_cancel.setOnClickListener(this);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                // 清空文本信息
                if (!TextUtils.isEmpty(text))
                    text = "";
                if (! TextUtils.isEmpty(text_cancel))
                    text_cancel = "";

                if (mListener != null && aList.contains(mListener)){
                    aList.remove(mListener);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                if (mListener != null){
                    mListener.onClick(this);
                }
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus){

            /**
             * 设置文本
             */
            if (! TextUtils.isEmpty(text))
                tv_text.setText(text);
            if (! TextUtils.isEmpty(text_cancel))
                tv_cancel.setText(text_cancel);

            progressBar.setProgress(pro);
            if (mListener != null && !aList.contains(mListener)){
                aList.add(mListener);
            }
        }
    }

    public CustomProgressDialog setListenerDialog(ListenerDialog listener){
        this.mListener = listener;
        return this;
    }

    public CustomProgressDialog setCancelStr(String cancelStr){
        this.text_cancel = cancelStr;
        return this;
    }


    public CustomProgressDialog setText(String text){
        this.text = text;
        return this;
    }
    public CustomProgressDialog setProgress(int pro){
        this.pro = pro;
        return this;
    }

    public interface ListenerDialog{
        void onClick(Dialog dialog);
    }
}
