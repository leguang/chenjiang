package com.shtoone.chenjiang.mvp.presenter.project;


import com.shtoone.chenjiang.mvp.contract.project.GongdianMenuContract;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.model.bean.UserInfoBean;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/14 0014 13:17
 * Email：langmanleguang@qq.com
 */
public class GongdianMenuPresenter extends BasePresenter<GongdianMenuContract.View> implements GongdianMenuContract.Presenter {

    private static final String TAG = GongdianMenuPresenter.class.getSimpleName();
    private UserInfoBean mUserInfoBean;

    public GongdianMenuPresenter(GongdianMenuContract.View mView) {
        super(mView);
    }


    @Override
    public void start() {

        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<GongdianData>>() {
                    @Override
                    public void call(Subscriber<? super List<GongdianData>> subscriber) {
                        try {
                            List<GongdianData> mGongdianData = DataSupport.findAll(GongdianData.class);
                            subscriber.onNext(mGongdianData);

                        } catch (Exception ex) {
                            subscriber.onError(ex);

                        }
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<List<GongdianData>>() {
                            @Override
                            public void _onNext(List<GongdianData> gongdianDatas) {
                                getView().refresh(gongdianDatas);
                            }
                        })

        );

    }


}
