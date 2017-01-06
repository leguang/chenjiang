package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.ShuizhunxianContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.model.entity.db.StaffData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

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

    @Override
    public void save(final YusheshuizhunxianData mYusheshuizhunxianData) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        //生成测站并存到数据库中这种操作应考虑放到编辑水准线，保存的那里。后面点击测量的时候应该读取数据库，而产生数据后应该是修改相应测站的行。
                        try {
                            String[] arrayJidianAndCedian = mYusheshuizhunxianData.getXianluxinxi().split(",");

                            int intNumber = 0;

                            List<CezhanData> listCezhan = new ArrayList<>();


                            for (int i = 0; i < arrayJidianAndCedian.length - 1; i++) {
                                intNumber++;
                                CezhanData mCezhanData = new CezhanData();
                                mCezhanData.setNumber(intNumber);
                                mCezhanData.setShuizhunxianID(mYusheshuizhunxianData.getId() + "");
                                mCezhanData.setMeasureDirection("往测");
                                //其实不是这样的，应该根据水准线的观测类型，还要根据奇数站和偶数站的时候不同的前后后前的顺序不一样就会显示不一样,后期再处理。
                                if (intNumber % 2 == 0) {
                                    mCezhanData.setObserveType(Constants.FBBF);
                                } else {
                                    mCezhanData.setObserveType(Constants.BFFB);
                                }
                                mCezhanData.setQianshi(arrayJidianAndCedian[i + 1]);
                                mCezhanData.setHoushi(arrayJidianAndCedian[i]);
                                listCezhan.add(mCezhanData);
                            }

//                            for (CezhanData cezhanData : listCezhan) {
//                                KLog.e("Number::" + cezhanData.getNumber());
//                            }
                            for (int i = arrayJidianAndCedian.length - 1; i > 0; i--) {
                                intNumber++;
                                CezhanData mCezhanData = new CezhanData();
                                mCezhanData.setNumber(intNumber);
                                mCezhanData.setShuizhunxianID(mYusheshuizhunxianData.getId() + "");
                                mCezhanData.setMeasureDirection("反测");
                                //其实不是这样的，应该根据水准线的观测类型，还要根据奇数站和偶数站的时候不同的前后后前的顺序不一样就会显示不一样,后期再处理。
                                if (intNumber % 2 == 0) {
                                    mCezhanData.setObserveType(Constants.FBBF);
                                } else {
                                    mCezhanData.setObserveType(Constants.BFFB);
                                }
                                mCezhanData.setQianshi(arrayJidianAndCedian[i - 1]);
                                mCezhanData.setHoushi(arrayJidianAndCedian[i]);
                                listCezhan.add(mCezhanData);
                            }

                            for (CezhanData cezhanData : listCezhan) {
                                KLog.e("Number::" + cezhanData.getNumber());
                            }

                            DataSupport.deleteAll(CezhanData.class, "shuizhunxianID = ? ", String.valueOf(mYusheshuizhunxianData.getId()));
                            DataSupport.saveAll(listCezhan);
                            int rowsAffected = mYusheshuizhunxianData.update(mYusheshuizhunxianData.getId());

                            subscriber.onNext(rowsAffected);

                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }

                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<Integer>() {
                            @Override
                            public void _onNext(Integer rowsAffected) {
                                if (isViewAttached()) {
                                    getView().responseSave(rowsAffected);
                                }
                            }
                        })

        );
    }
}
