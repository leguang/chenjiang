package com.shtoone.chenjiang.mvp.presenter.project;


import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.project.ShuizhunxianMenuContract;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
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
public class ShuizhunxianMenuPresenter extends BasePresenter<ShuizhunxianMenuContract.View> implements ShuizhunxianMenuContract.Presenter {

    private static final String TAG = ShuizhunxianMenuPresenter.class.getSimpleName();

    public ShuizhunxianMenuPresenter(ShuizhunxianMenuContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        requestShuizhunxian(0);
    }

    @Override
    public void requestShuizhunxian(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<YusheshuizhunxianData>>() {
                    @Override
                    public void call(Subscriber<? super List<YusheshuizhunxianData>> subscriber) {
                        List<YusheshuizhunxianData> mShuizhunxianData = null;
                        try {
                            //分页查询每次查询PAGE_SIZE条，从0开始。
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
                                getView().responseShuizhunxian(mShuizhunxianData, pagination);
                            }
                        })

        );
    }
}
