package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.AddYusheshuizhunxianContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CedianData;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.model.entity.db.StaffData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2016/10/14 0014 13:17
 * Email：langmanleguang@qq.com
 */
public class AddYusheshuizhunxianPresenter extends BasePresenter<AddYusheshuizhunxianContract.View> implements AddYusheshuizhunxianContract.Presenter {
    private static final String TAG = AddYusheshuizhunxianPresenter.class.getSimpleName();

    public AddYusheshuizhunxianPresenter(AddYusheshuizhunxianContract.View mView) {
        super(mView);
    }


    @Override
    public void start() {
        requestJidian();
        requestCedian();
        requestStaff();
    }

    @Override
    public void requestJidian() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<String[]>() {
                    @Override
                    public void call(Subscriber<? super String[]> subscriber) {
                        try {
                            List<JidianData> mJidianData = DataSupport.findAll(JidianData.class);
                            String[] arrayJidianName = new String[1];
                            if (mJidianData.size() > 0) {
                                arrayJidianName = new String[mJidianData.size()];
                                for (int i = 0; i < mJidianData.size(); i++) {
                                    arrayJidianName[i] = mJidianData.get(i).getName();
                                }
                            }
                            subscriber.onNext(arrayJidianName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            subscriber.onError(ex);
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<String[]>() {
                            @Override
                            public void _onNext(String[] strings) {
                                getView().responseJidian(strings);
                            }
                        })
        );
    }

    @Override
    public void requestCedian() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<String[]>() {
                    @Override
                    public void call(Subscriber<? super String[]> subscriber) {
                        try {
                            List<CedianData> mCedianData = DataSupport.findAll(CedianData.class);
                            String[] arrayCedianName = new String[1];
                            if (mCedianData.size() > 0) {
                                arrayCedianName = new String[mCedianData.size()];
                                for (int i = 0; i < mCedianData.size(); i++) {
                                    arrayCedianName[i] = mCedianData.get(i).getCedianname();
                                }
                            }
                            subscriber.onNext(arrayCedianName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            subscriber.onError(ex);
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<String[]>() {
                            @Override
                            public void _onNext(String[] strings) {
                                getView().responseCedian(strings);
                            }
                        })
        );
    }

    @Override
    public void requestStaff() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        List<StaffData> mStaffData = null;
                        List<String> listStaffName = null;
                        try {
                            mStaffData = DataSupport.findAll(StaffData.class);
                            if (mStaffData.size() > 0) {
                                listStaffName = new ArrayList<String>();
                                for (StaffData staffData : mStaffData) {
                                    listStaffName.add(staffData.getUserName());
                                }
                            }
                            subscriber.onNext(listStaffName);
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                            getView().showError(ex);
                        }
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxSubscriber<List<String>>() {
                            @Override
                            public void _onNext(List<String> staffDatas) {
                                getView().responseStaff(staffDatas);
                            }
                        })
        );
    }

    @Override
    public void save(YusheshuizhunxianData mYusheshuizhunxianData, String dateTime) {

    }
}
