package com.shtoone.chenjiang.mvp.model;


import com.shtoone.chenjiang.mvp.model.bean.CedianInfoBean;
import com.shtoone.chenjiang.mvp.model.bean.CheckUpdateBean;
import com.shtoone.chenjiang.mvp.model.bean.DuanmianInfoBean;
import com.shtoone.chenjiang.mvp.model.bean.GongdianInfoBean;
import com.shtoone.chenjiang.mvp.model.bean.JidianBean;
import com.shtoone.chenjiang.mvp.model.bean.RegisterBean;
import com.shtoone.chenjiang.mvp.model.bean.StaffBean;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;
import com.shtoone.chenjiang.mvp.model.bean.YusheshuizhunxianInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface ApiService {

    //登录验证
    @GET("app.do?appLogin")
    Call<UserInfoBean> login(@Query("account") String account, @Query("userPwd") String userPwd, @Query("OSType") String OSType, @Query("regCode") String regCode);

    //更新检测
    @GET("app.do?checkUpdate")
    Call<CheckUpdateBean> checkUpdate();

    //注册
    @GET("app.do?appRegister")
    Observable<RegisterBean> register(@Query("machineCode") String machineCode, @Query("phoneBrand") String phoneBrand, @Query("phoneSysVersion") String phoneSysVersion, @Query("phoneModel") String phoneModel);

    //下载工点信息
    @GET("app.do?gdxxsDownload")
    Observable<GongdianInfoBean> gongdianInfoDownload(@Query("userId") String userId);

    //下载工点信息
    @GET("app.do?dmxxsDownload")
    Observable<DuanmianInfoBean> duanmianInfoDownload(@Query("gongDianId") String gongDianId);

    //下载测点信息
    @GET("app.do?ljcdsDownload")
    Observable<CedianInfoBean> cedianInfoDownload(@Query("duanMianId") String duanMianId);

    //下载预设水准线信息
    @GET("app.do?ysszxsDownload")
    Observable<YusheshuizhunxianInfoBean> yusheshuizhunxianInfoDownload(@Query("departId") String departId);

    //下载基点信息
    @GET("app.do?gzjdsDownload")
    Observable<JidianBean> jidianDownload();

    //下载人员信息
    @GET("app.do?obsDownload")
    Observable<StaffBean> staffDownload();
}
