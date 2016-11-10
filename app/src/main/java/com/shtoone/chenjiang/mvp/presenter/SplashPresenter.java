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
        //调试的时候用
//        getView().go2Main();

        String usernameEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.USERNAME, "");
        String passwordEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.PASSWORD, "");
        String registerCodeEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.REGISTER_CODE, "");

        KLog.e("username加密从sp中:" + usernameEncrypted);
        KLog.e("password加密从sp中:" + passwordEncrypted);
        KLog.e("registerCode加密从sp中:" + registerCodeEncrypted);
        //进行解密
        String username = "";
        String password = "";
        String registerCode = "";
        if (TextUtils.isEmpty(usernameEncrypted) || TextUtils.isEmpty(passwordEncrypted) || TextUtils.isEmpty(registerCodeEncrypted)) {
            getView().go2LoginOrGuide();
            KLog.e("1111111111");
            return;
        }
        KLog.e("2222222222222");

        try {
            username = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, usernameEncrypted);
            password = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, passwordEncrypted);
            registerCode = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, registerCodeEncrypted);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            getView().go2LoginOrGuide();
            return;
        }
        KLog.e("username解密:" + username);
        KLog.e("password解密:" + password);
        KLog.e("registerCode解密:" + registerCode);

        HttpHelper.getInstance().initService().login(username, password, Constants.OSTYPE, registerCode).enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        BaseApplication.mUserInfoBean = mUserInfoBean = response.body();
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


    @Override
    public void start() {

    }

}
