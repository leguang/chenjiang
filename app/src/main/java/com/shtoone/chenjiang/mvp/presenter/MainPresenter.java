package com.shtoone.chenjiang.mvp.presenter;


import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.MainContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();


    public MainPresenter(MainContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        requestGongdianData();
        requestShuizhunxianData(0);
    }

    @Override
    public void requestGongdianData() {
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
                .subscribe(new Subscriber<List<GongdianData>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(List<GongdianData> gongdianDatas) {
                                   getView().responseGongdianData(gongdianDatas);
                               }
                           }
                ));
    }

    @Override
    public void requestShuizhunxianData(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<YusheshuizhunxianData>>() {
            @Override
            public void call(Subscriber<? super List<YusheshuizhunxianData>> subscriber) {
                List<YusheshuizhunxianData> mShuizhunxianData = null;
                try {
                    mShuizhunxianData = DataSupport.select("*")
                            .order("id").limit(Constants.PAGE_SIZE)
                            .offset(pagination * Constants.PAGE_SIZE)
                            .find(YusheshuizhunxianData.class);
                    subscriber.onNext(mShuizhunxianData);
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
                //做此判断的目的是为了不让最后onCompleted()调用的showContent()遮住了showEmpty()的显示。
                if (mShuizhunxianData != null && mShuizhunxianData.size() > 0) {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<YusheshuizhunxianData>>() {
                               @Override
                               public void _onNext(List<YusheshuizhunxianData> mShuizhunxianData) {
                                   getView().responseShuizhunxianData(mShuizhunxianData, pagination);
                               }
                           }
                ));
    }
}
