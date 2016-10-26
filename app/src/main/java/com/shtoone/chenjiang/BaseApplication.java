package com.shtoone.chenjiang;

import android.content.Context;

import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePalApplication;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApplication extends LitePalApplication {

    private static final String TAG = BaseApplication.class.getSimpleName();
    public static Context mContext;
    public static UserInfoBean mUserInfoBean;
    public RefWatcher mRefWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        LitePalApplication.initialize(this);
        //日志的开关和全局标签初始化
        KLog.init(true, "SHTW沉降观测");
        mContext = this;
        // 程序异常交由AppExceptionHandler来处理
//        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));
        //创建LeakCanary对象，观察内存泄漏
        mRefWatcher = LeakCanary.install(this);

        //初始化数据库

//        for (int i = 0; i < 5; i++) {

//        GongdianData gongdianData = new GongdianData();
//        gongdianData.setId(1516161);
//        boolean is = gongdianData.save();
//        KLog.e(is);
//        }

//        for (int i = 0; i < 100; i++) {
//
//            LevelLineData levelLineData = new LevelLineData();
//            levelLineData.setId(1516161);
//            levelLineData.setBiaoDuan("前期标");
//            levelLineData.setMeasureType("aBBFF");
//            levelLineData.setObserveDate("2016-01-15");
//            levelLineData.setObserveType("附和");
//            levelLineData.setRouteType("aBBFF");
//            boolean isis = levelLineData.save();
//
//            KLog.e(isis);
//        }


    }
}
