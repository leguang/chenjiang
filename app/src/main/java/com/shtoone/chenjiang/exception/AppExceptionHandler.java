package com.shtoone.chenjiang.exception;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.shtoone.chenjiang.common.ActivityManager;
import com.shtoone.chenjiang.mvp.view.others.LaunchActivity;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * public class AndroidUtilsApplication extends Application {
 * public void onCreate() {
 * super.onCreate();
 * //崩溃处理
 * CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
 * crashHandlerUtil.init(this);
 * crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");
 * }
 * }
 * Created by Administrator
 * on 2016/5/19.
 */
@SuppressWarnings("unused")
public class AppExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = AppExceptionHandler.class.getSimpleName();

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static AppExceptionHandler instance;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
    private String crashTip = "很抱歉，程序出现异常，即将退出！";

    public String getCrashTip() {
        return crashTip;
    }

    public void setCrashTip(String crashTip) {
        this.crashTip = crashTip;
    }

    /**
     * 保证只有一个CrashHandler实例
     */
    private AppExceptionHandler(Context mContext) {
        this.mContext = mContext;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     *
     * @return 单例
     */
    public static AppExceptionHandler getInstance(Context mContext) {
        if (instance == null) {
            instance = new AppExceptionHandler(mContext);
        }
        return instance;
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     *
     * @param thread 线程
     * @param ex     异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param throwable 异常
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable throwable) {
        if (throwable == null || mContext == null) {
            return false;
        }

        boolean isSuccess = true;
        try {
            //收集设备参数信息
            collectDeviceInfo(mContext);
            //保存日志文件
            isSuccess = saveCrashInfo2File(throwable);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (!isSuccess) {
                return false;
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        //弹出Dialog提示用户退出App或重启App
                        showDialog();
                        Looper.loop();
                    }
                }.start();
            }
        }
        return true;
    }

    private void showDialog() {
        final Activity currentActivity = ActivityManager.getInstance().currentActivity();
        new AlertDialog.Builder(currentActivity)
                .setTitle("异常")
                .setMessage("当前应用程序发生异常，请您选择退出应用或重启应用！")
                .setCancelable(false)
                .setNegativeButton("重启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(mContext, LaunchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        ActivityManager.getInstance().appExit();
                        KLog.e("重启");
                    }
                })
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityManager.getInstance().appExit();
                    }
                }).show();
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx 上下文
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
     * 保存错误信息到文件中
     *
     * @param ex 异常
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private Boolean saveCrashInfo2File(Throwable ex) {
        boolean isSave = false;
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
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getPath() + "/crash/";
                Log.d(TAG, "path=" + path);
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
                isSave = true;
            }
            return isSave;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

}
