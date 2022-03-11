package com.work.freedomworker.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.work.freedomworker.R;


/**
 * Created by Administrator on 2018/4/9.
 */

public class CustomDefaultToast {

    private static Toast mToast;
    private TextView tv_text;
    private boolean canceled = true;

    public CustomDefaultToast(Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        tv_text = layout.findViewById(R.id.tv_toast_text);
        if (mToast == null) {
            mToast = new Toast(context);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(layout);
    }


    /**
     * 默认显示
     * @param flag 显示的位置 0表示默认显示 1表示中间显示
     * @param message
     */
    public void showDefault(int flag, String message){
        //取消
        if (!canceled){
            mToast.cancel();
            canceled = true;
        }

        tv_text.setText(message);
        if (flag == 0){
            mToast.setGravity(Gravity.BOTTOM , 0, 100);  //默认显示
        }else {
            mToast.setGravity(Gravity.CENTER , 0, 100);    //居中显示
        }
        TimeCount mTimeCount = new TimeCount(Toast.LENGTH_SHORT, 1);
        mTimeCount.start();
        mToast.show();
    }

    class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture 总计时时长
         * @param countDownInterval 时间间隔
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

            System.out.println("倒计时：" + l);

            if (canceled)
                canceled = false;
        }

        /**
         * 倒计时完毕
         */
        @Override
        public void onFinish() {
            System.out.println("倒计时完毕");
            canceled = true;
        }
    }





}
