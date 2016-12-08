package com.shtoone.chenjiang.mvp.presenter.measure;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.common.AudioPlayer;
import com.shtoone.chenjiang.mvp.contract.measure.MeasureContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasurePresenter extends BasePresenter<MeasureContract.View> implements MeasureContract.Presenter {
    private static final String TAG = MeasurePresenter.class.getSimpleName();

    public MeasurePresenter(MeasureContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestJidianData() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<JidianData>>() {
                    @Override
                    public void call(Subscriber<? super List<JidianData>> subscriber) {
                        List<JidianData> mJidianData = null;
                        try {
                            //借用一下这个异步线程初始化SoundPool，因为有检测到这个操作耗时。
                            AudioPlayer.init(BaseApplication.mContext);

                            mJidianData = DataSupport.findAll(JidianData.class);
                            subscriber.onNext(mJidianData);

                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                })
                        .map(new Func1<List<JidianData>, List<String>>() {
                            @Override
                            public List<String> call(List<JidianData> jidianDatas) {
                                List<String> listJidianBianhao = new ArrayList<String>();
                                for (JidianData jidianData : jidianDatas) {
                                    listJidianBianhao.add(jidianData.getBianhao());
                                }

                                return listJidianBianhao;
                            }
                        })
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<List<String>>() {
                            @Override
                            public void _onNext(List<String> strings) {
                                getView().responseJidianData(strings);
                            }
                        })
        );
    }

    @Override
    public void requestCezhanData(final YusheshuizhunxianData mYusheshuizhunxianData) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<CezhanData>>() {
                    @Override
                    public void call(Subscriber<? super List<CezhanData>> subscriber) {
                        //生成测站并存到数据库中这种操作应考虑放到编辑水准线，保存的那里。后面点击测量的时候应该读取数据库，而产生数据后应该是修改相应测站的行。
                        try {
                            String[] arrayJidianAndCedian = mYusheshuizhunxianData.getXianluxinxi().split(",");
                            List<CezhanData> listCezhan = new ArrayList<CezhanData>();
                            for (int i = 0; i < arrayJidianAndCedian.length - 1; i++) {
                                CezhanData mCezhanData = new CezhanData();
                                mCezhanData.setShuizhunxianID(mYusheshuizhunxianData.getId());
                                mCezhanData.setMeasureDirection("往测");
                                //其实不是这样的，应该根据水准线的观测类型，还要根据奇数站和偶数站的时候不同的前后后前的顺序不一样就会显示不一样
                                mCezhanData.setObserveType(mYusheshuizhunxianData.getObserveType());

                                mCezhanData.setQianshi(arrayJidianAndCedian[i]);
                                mCezhanData.setQianshi(arrayJidianAndCedian[(i + 1)]);
                                listCezhan.add(mCezhanData);
                            }
                            subscriber.onNext(listCezhan);

                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }

                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<List<CezhanData>>() {
                            @Override
                            public void _onNext(List<CezhanData> mCezhanData) {
                                getView().refresh(mJidianData, pagination);
                            }
                        })

        );
    }
}
