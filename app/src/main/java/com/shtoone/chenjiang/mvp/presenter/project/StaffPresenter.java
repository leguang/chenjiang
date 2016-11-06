package com.shtoone.chenjiang.mvp.presenter.project;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.project.StaffContract;
import com.shtoone.chenjiang.mvp.model.bean.StaffData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

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
public class StaffPresenter extends BasePresenter<StaffContract.View> implements StaffContract.Presenter {

    private static final String TAG = StaffPresenter.class.getSimpleName();

    public StaffPresenter(StaffContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        KLog.e("start^^^^^^^^^^^^^^^^^^^");
        queryData(0);
    }

    @Override
    public void queryData(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<StaffData>>() {
                    @Override
                    public void call(Subscriber<? super List<StaffData>> subscriber) {
                        try {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            List<StaffData> mStaffData = DataSupport.select("*")
                                    .order("id").limit(Constants.PAGE_SIZE).offset(pagination * Constants.PAGE_SIZE)
                                    .find(StaffData.class);
                            subscriber.onNext(mStaffData);

                            KLog.e("mStaffData::" + mStaffData.size());

                        } catch (Exception ex) {
                            subscriber.onError(ex);

                        }
                        subscriber.onCompleted();

                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxSubscriber<List<StaffData>>() {
                            @Override
                            public void _onNext(List<StaffData> staffDatas) {
                                getView().refresh(staffDatas,pagination);
                            }
                        })
        );
    }
}
