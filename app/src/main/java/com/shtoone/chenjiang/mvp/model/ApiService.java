package com.shtoone.chenjiang.mvp.model;


import com.shtoone.chenjiang.mvp.model.bean.CheckUpdateBean;
import com.shtoone.chenjiang.mvp.model.bean.RegisterBean;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface ApiService {

    @GET("app.do?AppLogin")
    Call<UserInfoBean> login(@Query("userName") String userName, @Query("userPwd") String userPwd, @Query("OSType") String OSType, @Query("machineCode") String machineCode);

    @GET("app.do?checkUpdate")
    Call<CheckUpdateBean> checkUpdate();

    @GET("app.do?appRegister")
    Observable<RegisterBean> register(@Query("machineCode") String machineCode, @Query("phoneBrand") String phoneBrand, @Query("phoneSysVersion") String phoneSysVersion, @Query("phoneModel") String phoneModel);
}
