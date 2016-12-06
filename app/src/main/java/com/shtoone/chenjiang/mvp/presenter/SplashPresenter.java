package com.shtoone.chenjiang.mvp.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.SplashContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.entity.bean.CheckUpdateBean;
import com.shtoone.chenjiang.mvp.model.entity.bean.UserInfoBean;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.MeasureSpecificationData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.shtoone.chenjiang.utils.AESCryptUtils;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.security.GeneralSecurityException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Observers;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {
    private static final String TAG = SplashPresenter.class.getSimpleName();
    private int intRetry = 0;

    public SplashPresenter(SplashContract.View mView) {
        super(mView);
    }

    public void checkLogin() {
        String usernameEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.USERNAME, "");
        String passwordEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.PASSWORD, "");
        String registerCodeEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.REGISTER_CODE, "");
        String userInfoEncrypted = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.USER_INFO_BEAN, "");

        KLog.e("username加密从sp中:" + usernameEncrypted);
        KLog.e("password加密从sp中:" + passwordEncrypted);
        KLog.e("registerCode加密从sp中:" + registerCodeEncrypted);

        if (TextUtils.isEmpty(usernameEncrypted) || TextUtils.isEmpty(passwordEncrypted)
                || TextUtils.isEmpty(registerCodeEncrypted) || TextUtils.isEmpty(userInfoEncrypted)) {

            getView().go2LoginOrGuide();

        } else {
            //进行解密
            String userInfo = "";
            try {
                userInfo = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, userInfoEncrypted);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                getView().go2LoginOrGuide();
                return;
            }

            //后面下载会用到这个种的信息作为参数，所以提前加载到内存中。
            BaseApplication.mUserInfoBean = new Gson().fromJson(userInfo, UserInfoBean.class);
            getView().go2Main();
        }

        /**
         * 不需要进行网络验证，所以只需判断本地是否存有用户名和密码即可。
         */
//        try {
//            username = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, usernameEncrypted);
//            password = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, passwordEncrypted);
//            registerCode = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, registerCodeEncrypted);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//            getView().go2LoginOrGuide();
//            return;
//        }
//        KLog.e("username解密:" + username);
//        KLog.e("password解密:" + password);
//        KLog.e("registerCode解密:" + registerCode);
//
//        HttpHelper.getInstance().initService().login(username, password, Constants.OSTYPE, registerCode).enqueue(new Callback<UserInfoBean>() {
//            @Override
//            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getStatus() == 0) {
//                        BaseApplication.mUserInfoBean = mUserInfoBean = response.body();
//                        //进入管理层界面
//                        getView().go2Main();
//                    } else {
//                        getView().go2LoginOrGuide();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoBean> call, Throwable t) {
//                getView().go2LoginOrGuide();
//            }
//        });
    }

    public void checkUpdate() {
        HttpHelper.getInstance().initService().checkUpdate().enqueue(new Callback<CheckUpdateBean>() {
            @Override
            public void onResponse(Call<CheckUpdateBean> call, Response<CheckUpdateBean> response) {
                if (response.isSuccessful()) {
                    CheckUpdateBean mCheckUpdateBean = response.body();
                    if (mCheckUpdateBean.getStatus() == 0) {
                        checkLogin();
//                        EventBus.getDefault().postSticky(mCheckUpdateBean.getUpdateInfo());
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckUpdateBean> call, Throwable t) {
                t.printStackTrace();
                KLog.e(t);
                checkLogin();
            }
        });
    }

    private void initMeasureSpecificationData() {
        KLog.e("Thread.currentThread().getName()::" + Thread.currentThread().getName());
        intRetry++;
        boolean isInitialize = (boolean) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.IS_INITIALIZE, false);
        if (isInitialize && intRetry > 10) {
            return;
        }
        //初始化测量参数标准
        //先删除，保证表里面永远都只有两行数据即可。
        DataSupport.deleteAll(MeasureSpecificationData.class);
        MeasureSpecificationData mStandardData = new MeasureSpecificationData();
        mStandardData.setQianhoushijuleijicha(6.0f + "");
        mStandardData.setShixianchangdu(50 + "");
        mStandardData.setQianhoushijucha(1.5f + "");
        mStandardData.setLiangcidushucha(0.4f + "");
        mStandardData.setLiangcigaochazhicha(0.6f + "");
        mStandardData.setShixiangaodu(0.55f + "");

        //初始化修改后的标准参考值，一开始的参考值是跟国家二等水准测量标准是一样，后面手动修改的则update。因此只要克隆成一模一样的。

        MeasureSpecificationData mCurrentData = new MeasureSpecificationData();
        mCurrentData.setQianhoushijuleijicha(6.0f + "");
        mCurrentData.setShixianchangdu(50 + "");
        mCurrentData.setQianhoushijucha(1.5f + "");
        mCurrentData.setLiangcidushucha(0.4f + "");
        mCurrentData.setLiangcigaochazhicha(0.6f + "");
        mCurrentData.setShixiangaodu(0.55f + "");

        if (mStandardData.save() && mCurrentData.save()) {
            SharedPreferencesUtils.put(BaseApplication.mContext, Constants.IS_INITIALIZE, true);
        } else {
            //递归10层。
            initMeasureSpecificationData();
        }
    }

    @Override
    public void start() {
        //在异步线程中初始化数据库中的数据
        mRxManager.add(Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        try {
                            initMeasureSpecificationData();
                            subscriber.onCompleted();

                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {
                                checkUpdate();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                KLog.e(e);
                                checkUpdate();
                            }

                            @Override
                            public void onNext(Object o) {
                                KLog.e("111111");
                            }
                        })

        );
    }
}
