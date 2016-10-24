package com.shtoone.chenjiang;

import android.app.Application;
import android.content.Context;

import com.shtoone.chenjiang.mvp.model.bean.DepartmentBean;
import com.shtoone.chenjiang.mvp.model.bean.ParametersBean;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();
    public static Context mContext;
    public static UserInfoBean mUserInfoBean;
    public static DepartmentBean mDepartmentBean = new DepartmentBean();
    public static ParametersBean mParametersBean = new ParametersBean();
    public RefWatcher mRefWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        //日志的开关和全局标签初始化
        KLog.init(true, "SHTW沉降观测");
        mContext = this;
        // 程序异常交由AppExceptionHandler来处理
//        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));
        //创建LeakCanary对象，观察内存泄漏
        mRefWatcher = LeakCanary.install(this);
    }
}
