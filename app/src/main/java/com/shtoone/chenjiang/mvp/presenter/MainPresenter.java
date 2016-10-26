package com.shtoone.chenjiang.mvp.presenter;


import android.util.Log;

import com.shtoone.chenjiang.mvp.contract.MainContract;
import com.shtoone.chenjiang.mvp.model.bean.LevelLineData;
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


    public void requestGongdianData() {
        Observable.create(new Observable.OnSubscribe<List<LevelLineData>>() {
            @Override
            public void call(Subscriber<? super List<LevelLineData>> subscriber) {
                try {
                    List<LevelLineData> mLevelLineData = DataSupport.findAll(LevelLineData.class);
                    subscriber.onNext(mLevelLineData);
                } catch (Exception ex) {
                    Log.e(TAG, "Error reading from the database", ex);
                    subscriber.onError(ex);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<LevelLineData>>() {

                               @Override
                               public void _onNext(List<LevelLineData> levelLineDatas) {

                               }
                           }
                );

    }


    public void requestLevelLineData() {
        Observable.create(new Observable.OnSubscribe<List<LevelLineData>>() {
            @Override
            public void call(Subscriber<? super List<LevelLineData>> subscriber) {
                try {
                    List<LevelLineData> mLevelLineData = DataSupport.findAll(LevelLineData.class);
                    KLog.e("call::" + mLevelLineData.size());


                    subscriber.onNext(mLevelLineData);
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<LevelLineData>>() {

                               @Override
                               public void _onNext(List<LevelLineData> levelLineDatas) {
                                   KLog.e("_onNext::" + levelLineDatas.size());
                                   getView().refresh(levelLineDatas);
                               }
                           }
                );
    }

    @Override
    public void start() {
        getView().showLoading();
        KLog.e("presenter::start");
        requestLevelLineData();
    }
}
