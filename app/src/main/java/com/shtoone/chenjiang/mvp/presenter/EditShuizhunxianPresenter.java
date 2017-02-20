package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.ShuizhunxianContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.StaffData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

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
public class EditShuizhunxianPresenter extends BasePresenter<ShuizhunxianContract.View> implements ShuizhunxianContract.Presenter {
    private static final String TAG = EditShuizhunxianPresenter.class.getSimpleName();

    public EditShuizhunxianPresenter(ShuizhunxianContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        requestStaffData();
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
    public void save(final YusheshuizhunxianData mYusheshuizhunxianData, final String dateTime) {
        if (mYusheshuizhunxianData == null) {
            return;
        }
        mRxManager.add(Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        try {
                            ShuizhunxianData mShuizhunxianData;
                            if (mYusheshuizhunxianData.getStatus().equals(Constants.status_daibianji)) {

                                //****************以下是生成测量这一次水准线的数据信息***************************
                                mYusheshuizhunxianData.setXiugaishijian(dateTime);
                                mShuizhunxianData = new ShuizhunxianData();
                                mShuizhunxianData.setChuangjianshijian(dateTime);
                                mShuizhunxianData.save();

                                //****************以下是生成测站的数据信息***************************

                                String[] arrayJidianAndCedian = mYusheshuizhunxianData.getXianluxinxi().split(",");


                                JidianData mStartJidian = DataSupport.where("name = ? ", arrayJidianAndCedian[0])
                                        .findFirst(JidianData.class);

                                KLog.e("mStartJidian::" + mStartJidian.getChushigaocheng());

                                JidianData mEndJidian = DataSupport.where("name = ? ", arrayJidianAndCedian[arrayJidianAndCedian.length - 1])
                                        .findFirst(JidianData.class);
                                KLog.e("mEndJidian::" + mEndJidian.getChushigaocheng());

                                int intNumber = 0;
                                List<CezhanData> listCezhan = new ArrayList<>();
                                //生成往测数据
                                for (int i = 0; i < arrayJidianAndCedian.length - 1; i++) {
                                    intNumber++;
                                    CezhanData mCezhanData = new CezhanData();
                                    mCezhanData.setNumber(intNumber);
                                    if (i == 0) {
                                        mCezhanData.setFirst(true);
                                    }

                                    mCezhanData.setShuizhunxianID(mShuizhunxianData.getId());
                                    mCezhanData.setMeasureDirection(Constants.wangce);
                                    if (intNumber % 2 == 0) {
                                        mCezhanData.setObserveType(Constants.FBBF);
                                    } else {
                                        mCezhanData.setObserveType(Constants.BFFB);
                                    }
                                    mCezhanData.setQianshi(arrayJidianAndCedian[i + 1]);
                                    mCezhanData.setHoushi(arrayJidianAndCedian[i]);
                                    if (mStartJidian != null) {
                                        mCezhanData.setChushigaochengzhi(mStartJidian.getChushigaocheng());
                                    }
                                    listCezhan.add(mCezhanData);
                                }
                                //生成返测数据
                                for (int i = arrayJidianAndCedian.length - 1; i > 0; i--) {
                                    intNumber++;
                                    CezhanData mCezhanData = new CezhanData();
                                    mCezhanData.setNumber(intNumber);
                                    if (i == arrayJidianAndCedian.length - 1) {
                                        mCezhanData.setFirst(true);
                                    }
                                    mCezhanData.setShuizhunxianID(mShuizhunxianData.getId());
                                    mCezhanData.setMeasureDirection(Constants.fance);
                                    if (intNumber % 2 == 0) {
                                        mCezhanData.setObserveType(Constants.BFFB);
                                    } else {
                                        mCezhanData.setObserveType(Constants.FBBF);
                                    }
                                    mCezhanData.setQianshi(arrayJidianAndCedian[i - 1]);
                                    mCezhanData.setHoushi(arrayJidianAndCedian[i]);
                                    if (mEndJidian != null) {
                                        mCezhanData.setChushigaochengzhi(mEndJidian.getChushigaocheng());
                                    }
                                    listCezhan.add(mCezhanData);
                                }
                                DataSupport.deleteAll(CezhanData.class, "shuizhunxianID = ? ", String.valueOf(mShuizhunxianData.getId()));
                                DataSupport.saveAll(listCezhan);

                                for (CezhanData cezhanData : listCezhan) {
                                    KLog.e(cezhanData.isFirst());
                                }

                            } else {
                                mShuizhunxianData = DataSupport.where("yusheshuizhunxianID = ? and chuangjianshijian = ? "
                                        , String.valueOf(mYusheshuizhunxianData.getId()), mYusheshuizhunxianData.getXiugaishijian())
                                        .findFirst(ShuizhunxianData.class);
                            }

                            if (mShuizhunxianData != null) {
                                mShuizhunxianData.setYusheshuizhunxianID(mYusheshuizhunxianData.getId());
                                mShuizhunxianData.setBiaoshi(mYusheshuizhunxianData.getBiaoshi());
                                mShuizhunxianData.setYsszxid(mYusheshuizhunxianData.getYsszxid());
                                mShuizhunxianData.setXianlubianhao(mYusheshuizhunxianData.getXianlubianhao());
                                mShuizhunxianData.setCedianshu(mYusheshuizhunxianData.getCedianshu());
                                mShuizhunxianData.setLeixing(mYusheshuizhunxianData.getLeixing());
                                mShuizhunxianData.setShezhiren(mYusheshuizhunxianData.getShezhiren());
                                mShuizhunxianData.setXianlumingcheng(mYusheshuizhunxianData.getXianlumingcheng());
                                mShuizhunxianData.setDepartId(mYusheshuizhunxianData.getDepartId());
                                mShuizhunxianData.setJidianshu(mYusheshuizhunxianData.getJidianshu());
                                mShuizhunxianData.setXianluxinxi(mYusheshuizhunxianData.getXianluxinxi());
                                mShuizhunxianData.setRouteType(mYusheshuizhunxianData.getRouteType());
                                mShuizhunxianData.setObserveType(mYusheshuizhunxianData.getObserveType());
                                mShuizhunxianData.setWeather(mYusheshuizhunxianData.getWeather());
                                mShuizhunxianData.setPressure(mYusheshuizhunxianData.getPressure());
                                mShuizhunxianData.setTemperature(mYusheshuizhunxianData.getTemperature());
                                mShuizhunxianData.setStaff(mYusheshuizhunxianData.getStaff());
                                mShuizhunxianData.setXiugaishijian(dateTime);
                                mShuizhunxianData.save();
                            }

                            mYusheshuizhunxianData.setStatus(Constants.status_daiceliang);
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
