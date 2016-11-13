package com.shtoone.chenjiang;

import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;
import com.shtoone.chenjiang.common.AppContext;
import com.shtoone.chenjiang.mvp.model.bean.CedianData;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.util.List;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApplication extends LitePalApplication {

    private static final String TAG = BaseApplication.class.getSimpleName();
    public static Context mContext;
    public static UserInfoBean mUserInfoBean;
    public static boolean isShowDialog = true;
    public RefWatcher mRefWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        LitePalApplication.initialize(this);
        //日志的开关和全局标签初始化
        KLog.init(true, "SHTW沉降观测");
        mContext = this;
        // 程序异常交由AppExceptionHandler来处理
//        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));
        //创建LeakCanary对象，观察内存泄漏
        mRefWatcher = LeakCanary.install(this);

        // 在主进程初始化调用哈
        BlockCanary.install(this, new AppContext()).start();
        //初始化数据库

        List<CedianData> mCedianData = DataSupport.findAll(CedianData.class);
        KLog.e("mCedianData.size()：：" + mCedianData.size());
//        for (int i = 0; i < 50; i++) {
//
//            GongdianData gongdianData = new GongdianData();
//            gongdianData.setName("@" + (i + 1));
//            boolean is = gongdianData.save();
//            KLog.e(is);
//        }

//        for (int i = 0; i < 45; i++) {
//
//            StaffData mStaffData = new StaffData();
//            mStaffData.setName("哎我" + i);
//            mStaffData.setType("司镜人员" + i);
//            mStaffData.setPhtoneNumber("13888888888");
//
//            boolean is = mStaffData.save();
//            KLog.e(is);
//        }


//        for (int i = 0; i < 45; i++) {
//
//            JidianData mJidianData = new JidianData();
//            mJidianData.setBianhao("SWLFJJKDK16511");
//            mJidianData.setName("CP181");
//            mJidianData.setXiuzhenghougaochengzhi("75.2655");
//            mJidianData.setLichengguanhao("DK");
//            mJidianData.setLichengzhi("6646.1616");
//
//            boolean is = mJidianData.save();
//            KLog.e(is);
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
