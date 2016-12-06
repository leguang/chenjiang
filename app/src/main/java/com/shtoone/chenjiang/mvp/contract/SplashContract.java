package com.shtoone.chenjiang.mvp.contract;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface SplashContract {
    interface View extends BaseContract.View {
        void go2LoginOrGuide();

        void go2Main();
    }

    interface Presenter extends BaseContract.Presenter {
    }
}
