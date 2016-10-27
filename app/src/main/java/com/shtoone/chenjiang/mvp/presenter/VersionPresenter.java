package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.VersionContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.bean.CheckUpdateBean;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class VersionPresenter extends BasePresenter<VersionContract.View> implements VersionContract.Presenter {

    private static final String TAG = VersionPresenter.class.getSimpleName();
    private CheckUpdateBean.UpdateInfoBean mUpdateInfo;


    public VersionPresenter(VersionContract.View mView) {
        super(mView);
    }


    @Override
    public void checkUpdate() {
        getView().showLoading();
        HttpHelper.getInstance().initService().checkUpdate().enqueue(new Callback<CheckUpdateBean>() {
            @Override
            public void onResponse(Call<CheckUpdateBean> call, Response<CheckUpdateBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        mUpdateInfo = response.body().getUpdateInfo();
                        if (mUpdateInfo.getIsForceUpdate() == 0) {
                            getView().showForceUpdateDialog(mUpdateInfo);
                        } else {
                            getView().showUpdateDialog(mUpdateInfo);

                        }

                    } else {
                        getView().setErrorMessage("访问失败");
                    }
                } else {
                    getView().setErrorMessage("服务器异常");
                }
            }

            @Override
            public void onFailure(Call<CheckUpdateBean> call, Throwable t) {
                getView().showError(t);
            }
        });
    }


    @Override
    public void start() {

    }

}
