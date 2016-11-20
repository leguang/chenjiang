package com.shtoone.chenjiang.mvp.presenter;


import com.shtoone.chenjiang.mvp.contract.MainContract;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.model.bean.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

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
    }

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
                .subscribe(new RxSubscriber<List<GongdianData>>() {

                               @Override
                               public void _onNext(List<GongdianData> mGongdianData) {
                                   KLog.e("_onNext111111111111");
                                   getView().responseGongdianData(mGongdianData);
                                   //下载完工点后就下载预设水准线
                                   requestShuizhunxianData();
                               }
                           }
                ));
    }


    public void requestShuizhunxianData() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<YusheshuizhunxianData>>() {
            @Override
            public void call(Subscriber<? super List<YusheshuizhunxianData>> subscriber) {
                try {
                    List<YusheshuizhunxianData> mShuizhunxianData = DataSupport.findAll(YusheshuizhunxianData.class);
                    KLog.e("call::" + mShuizhunxianData.size());
                    KLog.e("map method in thread: " + Thread.currentThread().getName());

                    subscriber.onNext(mShuizhunxianData);
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<YusheshuizhunxianData>>() {

                               @Override
                               public void _onNext(List<YusheshuizhunxianData> mShuizhunxianData) {
                                   KLog.e("_onNext::" + mShuizhunxianData.size());
                                   getView().responseShuizhunxianData(mShuizhunxianData);
                                   KLog.e("map method in thread: " + Thread.currentThread().getName());
                               }
                           }
                ));
    }


}
