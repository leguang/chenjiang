package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.VersionContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.entity.bean.CheckUpdateBean;
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
    private Call mCall;


    public VersionPresenter(VersionContract.View mView) {
        super(mView);
    }


    @Override
    public void checkUpdate() {
        getView().showLoading();
        mCall = HttpHelper.getInstance().initService().checkUpdate();
        mCall.enqueue(new Callback<CheckUpdateBean>() {
            @Override
            public void onResponse(Call<CheckUpdateBean> call, Response<CheckUpdateBean> response) {
                if (getView() == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
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
                if (isViewAttached()) {
                    getView().showError(t);
                }
            }
        });
    }


    @Override
    public void start() {

    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
