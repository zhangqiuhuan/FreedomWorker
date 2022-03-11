package com.work.freedomworker.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getTodayDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
        // return "2021-09-27";
    }


    /**
     * long时间戳转字符串，可传dataFormat,自己可以修改成想要的时间格式
     */
    public static String formatDate(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }


    /**
     * string转为long型
     *
     * @param datetime
     * @return
     */
    public static Long stringtoLong(String datetime) {
        long songtime = 0;
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            c.setTime(format.parse(datetime));
            songtime = c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return songtime;
    }

    public static String formatDate(String date, String dataFormat) {

        Date d = new Date();

        try {
            d = new SimpleDateFormat(dataFormat).parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        String startDateString = sdf.format(d);

        return startDateString;
    }

    public static Date stringToDate(String date, String dataFormat) {

        Date d = new Date();

        try {
            d = new SimpleDateFormat(dataFormat).parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return d;
    }



    /**
     *
     * @param dateType 1-减一周，2-减一月，3-减一年
     * @return
     */
    public static String getMinusDate(int dateType){
        String hql=null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String resultDate=null;

        if (dateType==1) {
            cal.add(Calendar.WEEK_OF_MONTH, -1);
        }
        else if(dateType==2){
            cal.add(Calendar.MONTH, -1);
        }
        else if (dateType==3) {
            cal.add(Calendar.YEAR, -1);
        }
        resultDate=format.format(cal.getTime());

       return resultDate;
    }

    /**
     * //两个日期时间差
     * @param startTime
     * @param endTime
     * @param format
     * @return
     */
    public static long dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day>=1) {
                return day;
            }else {
                if (day==0) {
                    return 1;
                }else {
                    return 0;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * //两个日期时间差
     * @param startTime
     * @param endTime
     * @param format
     * @return
     */
    public static long dateDiffMin(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour=0;
        long min = 0;
        long sec=0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
             hour = diff % nd / nh;// 计算差多少小时
             min = diff % nd % nh / nm;// 计算差多少分钟
             sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            Log.e("TAG","时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
//            if (day>=1) {
//                return day;
//            }else {
//                if (day==0) {
//                    return 1;
//                }else {
//                    return 0;
//                }
//
//            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long result=(hour*60*60*1000)+(min*60*1000)+(sec*1000);
        return result;

    }

}
