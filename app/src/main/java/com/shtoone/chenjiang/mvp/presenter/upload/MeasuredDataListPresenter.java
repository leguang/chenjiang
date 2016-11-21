package com.shtoone.chenjiang.mvp.presenter.upload;

import com.shtoone.chenjiang.mvp.contract.upload.MeasuredDataListContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/14 0014 13:17
 * Email：langmanleguang@qq.com
 */
public class MeasuredDataListPresenter extends BasePresenter<MeasuredDataListContract.View> implements MeasuredDataListContract.Presenter {

    private static final String TAG = MeasuredDataListPresenter.class.getSimpleName();

    public MeasuredDataListPresenter(MeasuredDataListContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }


    @Override
    public void request(Class modelClass, int pagination) {

    }
}
