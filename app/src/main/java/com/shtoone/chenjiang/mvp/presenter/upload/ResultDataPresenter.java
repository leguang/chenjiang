package com.shtoone.chenjiang.mvp.presenter.upload;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.upload.ResultDataContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ResultData;
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
public class ResultDataPresenter extends BasePresenter<ResultDataContract.View> implements ResultDataContract.Presenter {

    private static final String TAG = ResultDataPresenter.class.getSimpleName();

    public ResultDataPresenter(ResultDataContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        request(0);
    }


    @Override
    public void request(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<ResultData>>() {
                    @Override
                    public void call(Subscriber<? super List<ResultData>> subscriber) {
                        List<ResultData> mResultData = null;
                        try {
                            mResultData = DataSupport.select("*")
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(ResultData.class);
                            subscriber.onNext(mResultData);
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                        //做此判断的目的是为了不让最后onCompleted()调用的showContent()遮住了showEmpty()的显示。
                        if (mResultData != null && mResultData.size() > 0) {
                            subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxSubscriber<List<ResultData>>() {
                            @Override
                            public void _onNext(List<ResultData> mResultData) {
                                getView().response(mResultData, pagination);
                            }
                        })
        );
    }
}
