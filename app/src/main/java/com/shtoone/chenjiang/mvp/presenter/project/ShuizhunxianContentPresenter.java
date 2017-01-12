package com.shtoone.chenjiang.mvp.presenter.project;


import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.project.ShuizhunxianContentContract;
import com.shtoone.chenjiang.mvp.contract.project.ShuizhunxianMenuContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
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
public class ShuizhunxianContentPresenter extends BasePresenter<ShuizhunxianContentContract.View> implements ShuizhunxianContentContract.Presenter {

    private static final String TAG = ShuizhunxianContentPresenter.class.getSimpleName();

    public ShuizhunxianContentPresenter(ShuizhunxianContentContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
    }

    @Override
    public void requestShuizhunxian(final int pagination, final String yusheshuizhunxianID) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<ShuizhunxianData>>() {
                    @Override
                    public void call(Subscriber<? super List<ShuizhunxianData>> subscriber) {
                        List<ShuizhunxianData> mShuizhunxianData = null;
                        try {
                            //分页查询每次查询PAGE_SIZE条，从0开始。
                            mShuizhunxianData = DataSupport.where("yusheshuizhunxianID = ? ", yusheshuizhunxianID)
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(ShuizhunxianData.class);

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
                        .subscribe(new RxSubscriber<List<ShuizhunxianData>>() {
                            @Override
                            public void _onNext(List<ShuizhunxianData> mShuizhunxianData) {
                                getView().responseShuizhunxian(mShuizhunxianData, pagination);
                            }
                        })

        );
    }
}
