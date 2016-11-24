package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.ShuizhunxianContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.model.entity.db.StaffData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/14 0014 13:17
 * Email：langmanleguang@qq.com
 */
public class ShuizhunxianPresenter extends BasePresenter<ShuizhunxianContract.View> implements ShuizhunxianContract.Presenter {
    private static final String TAG = ShuizhunxianPresenter.class.getSimpleName();

    public ShuizhunxianPresenter(ShuizhunxianContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<Map<String, String[]>>() {
                    @Override
                    public void call(Subscriber<? super Map<String, String[]>> subscriber) {

                        try {
                            Map<String, String[]> mapData = new HashMap<String, String[]>();
                            List<GongdianData> mGongdianData = DataSupport.findAll(GongdianData.class);
                            List<JidianData> mJidianData = DataSupport.findAll(JidianData.class);
                            List<StaffData> mStaffData = DataSupport.findAll(StaffData.class);

                            if (mGongdianData.size() > 0) {
                                String[] arrayGongdianName = new String[mGongdianData.size()];
                                for (int i = 0; i < mGongdianData.size(); i++) {
                                    arrayGongdianName[i] = mGongdianData.get(i).getZxlc();
                                }
                                mapData.put(Constants.GONGDIAN, arrayGongdianName);
                            }

                            if (mJidianData.size() > 0) {
                                String[] arrayJidianName = new String[mJidianData.size()];
                                for (int i = 0; i < mJidianData.size(); i++) {
                                    arrayJidianName[i] = mJidianData.get(i).getBianhao();
                                }
                                mapData.put(Constants.JIDIAN, arrayJidianName);
                            }

                            if (mStaffData.size() > 0) {
                                String[] arrayStaffName = new String[mStaffData.size()];
                                for (int i = 0; i < mStaffData.size(); i++) {
                                    arrayStaffName[i] = mStaffData.get(i).getUserName();
                                }
                                mapData.put(Constants.STAFF, arrayStaffName);
                            }
                            subscriber.onNext(mapData);
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<Map<String, String[]>>() {
                            @Override
                            public void _onNext(Map<String, String[]> map) {
                                getView().responseData(map);
                            }
                        })
        );
    }

    @Override
    public void requestStaffData() {
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
                                getView().responseStaffData(staffDatas);
                            }
                        })
        );
    }
}
