package com.shtoone.chenjiang.mvp.presenter;


import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.MainContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CedianData;
import com.shtoone.chenjiang.mvp.model.entity.db.DuanmianData;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

import org.litepal.crud.DataSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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

    @Override
    public void start() {
        requestGongdianData();
        requestShuizhunxianData(0, "全部", "全部", "全部");
    }

    @Override
    public void requestGongdianData() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<GongdianData>>() {
            @Override
            public void call(Subscriber<? super List<GongdianData>> subscriber) {
                try {
                    List<GongdianData> mGongdianData = DataSupport.findAll(GongdianData.class);

                    KLog.e("mGongdianData::" + mGongdianData.size());
                    subscriber.onNext(mGongdianData);
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GongdianData>>() {

                               @Override
                               public void onStart() {
                                   super.onStart();
                                   KLog.e("onStart");
                                   getView().showLoading();
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(List<GongdianData> gongdianDatas) {
                                   getView().responseGongdianData(gongdianDatas);
                               }

                               @Override
                               public void onCompleted() {

                               }
                           }
                ));
    }

    @Override
    public void requestShuizhunxianData(final int pagination, final String strGongdianParam, final String strStatusParam, final String strTypeParam) {

        //由于框架支持的原生sql查询老是报错，因此只能用最笨的方式。
        KLog.e("pagination::" + pagination);
        KLog.e("strGongdianParam::" + strGongdianParam);
        KLog.e("strStatusParam::" + strStatusParam);
        KLog.e("strTypeParam::" + strTypeParam);

//这一部分是用于过滤状态参数和时间参数的
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<YusheshuizhunxianData>>() {
            @Override
            public void call(Subscriber<? super List<YusheshuizhunxianData>> subscriber) {
                List<YusheshuizhunxianData> mShuizhunxianData = null;
                try {
                    if (strStatusParam.equals("全部")) {

                        if (strTypeParam.equals("全部")) {
                            mShuizhunxianData = DataSupport.order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(YusheshuizhunxianData.class);

                        } else if (strTypeParam.equals("近一周")) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar mCalendar = Calendar.getInstance();
                            String strEndTime = df.format(mCalendar.getTime());
                            mCalendar.add(Calendar.DAY_OF_MONTH, -7);
                            String strStartTime = df.format(mCalendar.getTime());

                            mShuizhunxianData = DataSupport.where("xiugaishijian Between ? and ?", strStartTime, strEndTime)
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(YusheshuizhunxianData.class);

                        } else if (strTypeParam.equals("近一月")) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar mCalendar = Calendar.getInstance();
                            String strEndTime = df.format(mCalendar.getTime());
                            mCalendar.add(Calendar.MONTH, -1);
                            String strStartTime = df.format(mCalendar.getTime());

                            mShuizhunxianData = DataSupport.where("xiugaishijian Between ? and ?", strStartTime, strEndTime)
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(YusheshuizhunxianData.class);
                        }
                    } else {

                        if (strTypeParam.equals("全部")) {
                            mShuizhunxianData = DataSupport.where("measureState = ?", strStatusParam)
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(YusheshuizhunxianData.class);


                        } else if (strTypeParam.equals("近一周")) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar mCalendar = Calendar.getInstance();
                            String strEndTime = df.format(mCalendar.getTime());
                            mCalendar.add(Calendar.DAY_OF_MONTH, -7);
                            String strStartTime = df.format(mCalendar.getTime());

                            mShuizhunxianData = DataSupport.where("measureState = ? and xiugaishijian Between ? and ?", strStatusParam, strStartTime, strEndTime)
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(YusheshuizhunxianData.class);

                        } else if (strTypeParam.equals("近一月")) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar mCalendar = Calendar.getInstance();
                            String strEndTime = df.format(mCalendar.getTime());
                            mCalendar.add(Calendar.MONTH, -1);
                            String strStartTime = df.format(mCalendar.getTime());

                            mShuizhunxianData = DataSupport.where("measureState = ? and xiugaishijian Between ? and ?", strStatusParam, strStartTime, strEndTime)
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(YusheshuizhunxianData.class);
                        }
                    }

                    subscriber.onNext(mShuizhunxianData);
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<YusheshuizhunxianData>>() {
                               @Override
                               public void call(List<YusheshuizhunxianData> yusheshuizhunxianDatas) {
                                   KLog.e("333333333");

                                   filterAsGongdian(pagination, strGongdianParam, yusheshuizhunxianDatas);
                               }
                           }
                ));
    }

    public void filterAsGongdian(final int pagination, final String strGongdianParam, List<YusheshuizhunxianData> yusheshuizhunxianDatas) {
        final List<YusheshuizhunxianData> filteredShuizhunxianData = new ArrayList<YusheshuizhunxianData>();
        KLog.e("222222222222");


        Observable.from(yusheshuizhunxianDatas).filter(new Func1<YusheshuizhunxianData, Boolean>() {
            @Override
            public Boolean call(YusheshuizhunxianData yusheshuizhunxianData) {
                String[] arrayCedian = yusheshuizhunxianData.getXianluxinxi().split(",");

//                CedianData mCedianData = DataSupport.where("bianhao = ?", arrayCedian[1]).findFirst(CedianData.class);
                CedianData mCedianData = DataSupport.where("bianhao = ?", "cd18").findFirst(CedianData.class);


                DuanmianData mDuanmianData = DataSupport.where("dmid = ?", mCedianData.getDuanmianid()).findFirst(DuanmianData.class);
                GongdianData mGongdianData = DataSupport.where("gdid = ?", mDuanmianData.getGongdianid()).findFirst(GongdianData.class);
                KLog.e("44444444");


                KLog.e(mGongdianData.getGdmc() + "::::::::::::" + strGongdianParam);
                return mGongdianData.getGdmc().equals(strGongdianParam);
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YusheshuizhunxianData>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        KLog.e("onStart");
                        getView().showLoading();
                    }

                    @Override
                    public void onNext(YusheshuizhunxianData yusheshuizhunxianData) {
                        KLog.e("5555555555");
                        filteredShuizhunxianData.add(yusheshuizhunxianData);
                    }

                    @Override
                    public void onCompleted() {
                        KLog.e("11111111111");
                        KLog.e("filteredShuizhunxianData.size()::" + filteredShuizhunxianData.size());
                        KLog.e("pagination::" + pagination);
                        getView().responseShuizhunxianData(filteredShuizhunxianData, pagination);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //此处不考虑错误类型，笼统的以错误来介绍
                        KLog.e("onError::" + e);
                        getView().showError(e);
                    }
                });
    }

}
