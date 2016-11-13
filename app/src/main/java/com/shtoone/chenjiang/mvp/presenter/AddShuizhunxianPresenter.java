package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.AddShuizhunxianContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/14 0014 13:17
 * Email：langmanleguang@qq.com
 */
public class AddShuizhunxianPresenter extends BasePresenter<AddShuizhunxianContract.View> implements AddShuizhunxianContract.Presenter {

    private static final String TAG = AddShuizhunxianPresenter.class.getSimpleName();

    public AddShuizhunxianPresenter(AddShuizhunxianContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
    }
}
