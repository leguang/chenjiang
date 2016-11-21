package com.shtoone.chenjiang.mvp.presenter.project;


import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.project.GongdianMenuContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
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

    public GongdianMenuPresenter(GongdianMenuContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        queryData(0);
    }

    @Override
    public void queryData(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<GongdianData>>() {
                    @Override
                    public void call(Subscriber<? super List<GongdianData>> subscriber) {
                        List<GongdianData> mGongdianData = null;
                        try {
                            //分页查询每次查询PAGE_SIZE条，从0开始。
                            mGongdianData = DataSupport.select("*")
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(GongdianData.class);
                            subscriber.onNext(mGongdianData);
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                        //做此判断的目的是为了不让最后onCompleted()调用的showContent()遮住了showEmpty()的显示。
                        if (mGongdianData != null && mGongdianData.size() > 0) {
                            subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<List<GongdianData>>() {
                            @Override
                            public void _onNext(List<GongdianData> gongdianDatas) {
                                getView().refresh(gongdianDatas, pagination);
                            }
                        })

        );
    }
}
