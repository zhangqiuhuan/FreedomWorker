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
 * Created by Administrator on 2018/4/10.
 */

public class CustomToastDialog extends Dialog {

    private Context mContext;
    private ListenerCustomToastDialo mListenr;
    private List<ListenerCustomToastDialo> aList;
    private String textStr;
    private String okStr;

    private TextView tv_text;
    private TextView tv_ok;

    public CustomToastDialog( Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomToastDialog( Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        aList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_toast);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tv_text = findViewById(R.id.tv_custom_toast_text);
        tv_ok = findViewById(R.id.tv_custom_toast_ok);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListenr != null){
                    mListenr.onClick(CustomToastDialog.this);
                }
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (TextUtils.isEmpty(okStr))
                    okStr =  "";

                if (mListenr != null && aList.contains(mListenr))
                    aList.remove(mListenr);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus){
            tv_text.setText(textStr);

            if (!TextUtils.isEmpty(okStr))
                tv_ok.setText(okStr);

            if (mListenr != null && !aList.contains(mListenr))
                aList.add(mListenr);
        }
    }

    public CustomToastDialog setTextStr(String textStr){
        this.textStr = textStr;
        return this;
    }

    public CustomToastDialog setOkStr(String okStr){
        this.okStr = okStr;
        return this;
    }

    public CustomToastDialog setListener(ListenerCustomToastDialo mListenr){
        this.mListenr = mListenr;
        return this;
    }

    public interface ListenerCustomToastDialo{
        void onClick(Dialog dialog);
    }
}
