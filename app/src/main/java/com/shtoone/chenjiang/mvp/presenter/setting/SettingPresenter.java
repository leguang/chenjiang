package com.shtoone.chenjiang.mvp.presenter.setting;

import com.shtoone.chenjiang.mvp.contract.setting.SettingContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private static final String TAG = SettingPresenter.class.getSimpleName();


    public SettingPresenter(SettingContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }

}
