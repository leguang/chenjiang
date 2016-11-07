package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.UploadContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadPresenter extends BasePresenter<UploadContract.View> implements UploadContract.Presenter {

    private static final String TAG = UploadPresenter.class.getSimpleName();

    public UploadPresenter(UploadContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }
}
