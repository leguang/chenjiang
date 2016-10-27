package com.shtoone.chenjiang.mvp.presenter;

import android.text.TextUtils;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.SplashContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.shtoone.chenjiang.utils.AESCryptUtils;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    private static final String TAG = SplashPresenter.class.getSimpleName();
    private UserInfoBean mUserInfoBean;


    public SplashPresenter(SplashContract.View mView) {
        super(mView);
    }

    @Override
    public void checkLogin() {
        String usernameEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.USERNAME, "");
        String passwordEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.PASSWORD, "");
        KLog.e("username加密从sp中:" + usernameEncrypted);
        KLog.e("password加密从sp中:" + passwordEncrypted);
        //进行解密
        String username = null;
        String password = null;
        if (!(TextUtils.isEmpty(usernameEncrypted) && TextUtils.isEmpty(passwordEncrypted))) {
            try {
                username = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, usernameEncrypted);
                password = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, passwordEncrypted);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }

        KLog.e("username解密:" + username);
        KLog.e("password解密:" + password);

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

            HttpHelper.getInstance().initService().login(username, password, Constants.OSTYPE, "aaaaa").enqueue(new Callback<UserInfoBean>() {
                @Override
                public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {
                            BaseApplication.mUserInfoBean = mUserInfoBean = response.body();
                            initParameters();
                            //进入管理层界面
                            getView().go2Main();
                        } else {
                            getView().go2LoginOrGuide();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserInfoBean> call, Throwable t) {
                    getView().go2LoginOrGuide();
                }
            });

        } else {
            getView().go2LoginOrGuide();
        }
    }

    @Override
    public void checkUpdate() {
        EventBus.getDefault().postSticky(new EventData(Constants.CHECKUPDATE));
        KLog.e("checkUpdatecheckUpdate");
//        Q.checkUpdate("get", "checkUpdateUrl", new CheckUpdateCallback2() {
//            @Override
//            public void onCheckUpdateSuccess(String result) {
//                //result:服务端返回的json,需要自己判断有无更新,解析成自己的实体类进行判断是否有版本更新
//
//            }
//
//            @Override
//            public void onCheckUpdateFailure(String failureMessage) {
//
//            }
//        });
    }

    private void initParameters() {
//        BaseApplication.mParametersBean.userGroupID = mUserInfoBean.getDepartId();
//        BaseApplication.mDepartmentBean.departmentID = mUserInfoBean.getDepartId();
//        BaseApplication.mDepartmentBean.departmentName = mUserInfoBean.getDepartName();
    }

    @Override
    public void start() {

    }

}
