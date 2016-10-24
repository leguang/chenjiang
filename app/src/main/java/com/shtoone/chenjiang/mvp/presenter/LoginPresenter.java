package com.shtoone.chenjiang.mvp.presenter;


import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.LoginContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Author：leguang on 2016/10/14 0014 13:17
 * Email：langmanleguang@qq.com
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private UserInfoBean mUserInfoBean;


    public LoginPresenter(LoginContract.View mView) {
        super(mView);
    }

    @Override
    public void login(String username, String password) {
        HttpHelper.getInstance().initService().login(username, password, Constants.OSTYPE, "aaaaa").enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        BaseApplication.mUserInfoBean = mUserInfoBean = response.body();
                        getView().savaData();
                        initParameters();
                        getView().setSuccessMessage();
                        //进入管理层界面
                        getView().go2Main();

                    } else {
                        getView().setErrorMessage("用户名或密码错误");
                    }
                } else {
                    getView().setErrorMessage("服务器异常");
                }
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                if (t instanceof ConnectException) {
                    getView().setErrorMessage("网络异常");
                } else if (t instanceof HttpException) {
                    getView().setErrorMessage("服务器异常");
                } else if (t instanceof SocketTimeoutException) {
                    getView().setErrorMessage("连接超时");
                } else {
                    getView().setErrorMessage("数据异常");
                }
            }
        });

    }

    private void initParameters() {
//        BaseApplication.mParametersBean.userGroupID = mUserInfoBean.getDepartId();
//        BaseApplication.mDepartmentBean.departmentID = mUserInfoBean.getDepartId();
//        BaseApplication.mDepartmentBean.departmentName = mUserInfoBean.getDepartName();
    }
}
