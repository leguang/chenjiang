package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.MeasureContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasurePresenter extends BasePresenter<MeasureContract.View> implements MeasureContract.Presenter {

    private static final String TAG = MeasurePresenter.class.getSimpleName();


    public MeasurePresenter(MeasureContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }

}
