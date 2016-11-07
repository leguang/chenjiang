package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.DownloadContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DownloadPresenter extends BasePresenter<DownloadContract.View> implements DownloadContract.Presenter {

    private static final String TAG = DownloadPresenter.class.getSimpleName();

    public DownloadPresenter(DownloadContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }
}
