package com.shtoone.chenjiang.mvp.contract;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface LoginContract {
    interface View extends BaseContract.View {
        void savaData(UserInfoBean mUserInfoBean);

        void setErrorMessage(String message);

        void setSuccessMessage();

        void go2Main();

    }

    interface Presenter extends BaseContract.Presenter {
        void login(String username, String password);
    }

}
