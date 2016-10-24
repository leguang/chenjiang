package com.shtoone.chenjiang.mvp.contract;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface LoginContract {
    interface View extends BaseContract.View {
        void savaData();

        void setErrorMessage(String message);

        void setSuccessMessage();

        void go2Main();

    }

    interface Presenter extends BaseContract.Presenter {
        void login(String username, String password);
    }

}
