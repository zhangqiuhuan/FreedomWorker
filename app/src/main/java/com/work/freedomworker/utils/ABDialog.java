package com.work.freedomworker.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.work.freedomworker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class ABDialog extends Dialog implements View.OnClickListener {

    private TextView tv_cancel;
    private TextView tv_ok;
    private TextView tv_text;

    private List<ListenerDeleteDialog> aList;
    private String text_cancel;
    private String text_ok;
    private String text;
    private ListenerDeleteDialog mListener;


       public ABDialog( Context context) {
        super(context);
    }

    public ABDialog( Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);  //区域外键
        setCancelable(false);  //返回键
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        aList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ab);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        tv_ok = findViewById(R.id.tv_delete_ok);
        tv_cancel = findViewById(R.id.tv_delete_cancel);
        tv_text = findViewById(R.id.tv_delete_text);
        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                // 清空文本信息
                if (!TextUtils.isEmpty(text))
                    text = "";
                if (! TextUtils.isEmpty(text_cancel))
                    text_cancel = "";
                if (! TextUtils.isEmpty(text_ok))
                    text_ok = "";

                if (mListener != null && aList.contains(mListener)){
                    aList.remove(mListener);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_delete_ok:
                if (mListener != null){
                    mListener.onClick(this, 1);
                }
                break;
            case R.id.tv_delete_cancel:
                if (mListener != null){
                    mListener.onClick(this, 2);
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
            if (! TextUtils.isEmpty(text_ok))
                tv_ok.setText(text_ok);
            if (! TextUtils.isEmpty(text_cancel))
                tv_cancel.setText(text_cancel);


            if (mListener != null && !aList.contains(mListener)){
                aList.add(mListener);
            }
        }
    }

    public ABDialog setListenerDeleteDialog(ListenerDeleteDialog listener){
        this.mListener = listener;
        return this;
    }

    public ABDialog setCancelStr(String cancelStr){
        this.text_cancel = cancelStr;
        return this;
    }

    public ABDialog setOKStr(String okStr){
        this.text_ok = okStr;
        return this;
    }

    public ABDialog setText(String text){
        this.text = text;
        return this;
    }

    public interface ListenerDeleteDialog{
        void onClick(Dialog dialog, int position);
    }
}
