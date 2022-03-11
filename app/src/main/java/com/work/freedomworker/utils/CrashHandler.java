package com.work.freedomworker.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
//            try {
//                Thread.sleep(1000); // 1秒后重启，可有可无，仅凭个人喜好
//                Intent intent = new Intent(mContext, getTopActivity());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//            } catch (InterruptedException e) {
//                Log.e(TAG, "error : ", e);
//            }
            Toast.makeText(mContext, "程序出错，退出APP", Toast.LENGTH_LONG).show();
            Log.e(TAG, "程序出错，退出APP");

           // exitApp();
        }
    }


//    public void exitApp() {
//        if (AdConfig.neigong > 0) {
//            Toast.makeText(mContext, "退出App", Toast.LENGTH_SHORT).show();
//        }
//        //启动积分商城
//        if (AdConfig.neigong > 0) {
//
//            //删除记录次数的
//            SharedPreferenceUtil.getInstance().delete(mContext, "adconfig");
//
//            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.youbo.yspingtai");
//            intent.setAction("Android.intent.action.VIEW");
//            intent.setComponent(new ComponentName("com.youbo.yspingtai", "com.youbo.yspingtai.activity.SplashActivity"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory("android.intent.category.DEFAULT");
//            intent.putExtra("start", "onIntent");
//            Bundle bundle = new Bundle();
//            bundle.putString("App_name_id", String.valueOf(AdConfig.app_name_id));
//
//            bundle.putString("KsKaiPing", String.valueOf(AdConfig.kskaiping));
//            bundle.putString("KsBanner", String.valueOf(AdConfig.ksbanner));
//            bundle.putString("KsChaPing", String.valueOf(AdConfig.kschaping));
//            bundle.putString("KsJiLi", String.valueOf(AdConfig.ksjili));
//            bundle.putString("KsDianJi", String.valueOf(AdConfig.ksdianji));
//            bundle.putString("KsHuodongID", String.valueOf(AdConfig.kshuodongid));
//
//            bundle.putString("GdtKaiPing", String.valueOf(AdConfig.gdtkaiping));
//            bundle.putString("GdtBanner", String.valueOf(AdConfig.gdtbanner));
//            bundle.putString("GdtChaPing", String.valueOf(AdConfig.gdtchaping));
//            bundle.putString("GdtJiLi", String.valueOf(AdConfig.gdtjili));
//            bundle.putString("GdtDianJi", String.valueOf(AdConfig.gdtdianji));
//            bundle.putString("GdtHuodongID", String.valueOf(AdConfig.gdthuodongid));
//
//            bundle.putString("CsjKaiPing", String.valueOf(AdConfig.csjkaiping));
//            bundle.putString("CsjBanner", String.valueOf(AdConfig.csjbanner));
//            bundle.putString("CsjChaPing", String.valueOf(AdConfig.csjchaping));
//            bundle.putString("CsjJiLi", String.valueOf(AdConfig.csjjili));
//
//            bundle.putString("CsjDianJi", String.valueOf(AdConfig.csjdianji));
//            bundle.putString("CsjHuodongID", String.valueOf(AdConfig.csjhuodongid));
//
//
//            intent.putExtras(bundle);
//            mContext.startActivity(intent);
//        } else {
//            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.youbo.adtaskmanager");
//            intent.setAction("Android.intent.action.VIEW");
//            intent.setComponent(new ComponentName("com.youbo.adtaskmanager", "com.youbo.adtaskmanager.activity.LoginActivity"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory("android.intent.category.DEFAULT");
//            mContext.startActivity(intent);
//        }
//
//        //退出当前App
//        ActivityManager.getActivityManager().popAllActivity();
//        // 退出程序
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
//    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        Log.e(TAG, "程序出现异常：----------->");
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);

        return true;
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中，需要有对SD的读写权限！
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log"; // 崩溃日志的文件
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                //指定文件路径
                String fileUrl = Environment.getExternalStorageDirectory().getAbsolutePath();
                String path = fileUrl + "//DownLoad//crash//";
                //String path = "/sdcard/crash/"; // 崩溃日志的存储路径
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }


    /**
     * 获取外置SD卡根路径
     *
     * @param mContext
     * @param is_removable 是否存在外置SD卡
     * @return
     */
    private String getExtSDCardPath(Context mContext, boolean is_removable) {
        StorageManager storagerManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolume = null;
        try {
            storageVolume = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = storagerManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolume.getMethod("getPath");
            Method isRemovable = storageVolume.getMethod("isRemovable");
            Object result = getVolumeList.invoke(storagerManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removable == removable) {
                    return path;
                }
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
