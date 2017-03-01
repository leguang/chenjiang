package com.shtoone.chenjiang.mvp.presenter.about;


import com.shtoone.chenjiang.mvp.contract.about.AboutContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Created by Administrator on 2016/11/22.
 */
public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {


    public AboutPresenter(AboutContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }
}
