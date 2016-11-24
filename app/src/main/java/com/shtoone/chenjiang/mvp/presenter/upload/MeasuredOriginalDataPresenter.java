package com.shtoone.chenjiang.mvp.presenter.upload;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.upload.MeasuredOriginalDataContract;
import com.shtoone.chenjiang.mvp.model.entity.db.OriginalData;
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
public class MeasuredOriginalDataPresenter extends BasePresenter<MeasuredOriginalDataContract.View> implements MeasuredOriginalDataContract.Presenter {

    private static final String TAG = MeasuredOriginalDataPresenter.class.getSimpleName();

    public MeasuredOriginalDataPresenter(MeasuredOriginalDataContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        request(0);
    }


    @Override
    public void request(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<OriginalData>>() {
                    @Override
                    public void call(Subscriber<? super List<OriginalData>> subscriber) {
                        List<OriginalData> mOriginalData = null;
                        try {
                            mOriginalData = DataSupport.select("*")
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(OriginalData.class);
                            subscriber.onNext(mOriginalData);
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                        //做此判断的目的是为了不让最后onCompleted()调用的showContent()遮住了showEmpty()的显示。
                        if (mOriginalData != null && mOriginalData.size() > 0) {
                            subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxSubscriber<List<OriginalData>>() {
                            @Override
                            public void _onNext(List<OriginalData> mOriginalData) {
                                getView().response(mOriginalData, pagination);
                            }
                        })
        );
    }
}
