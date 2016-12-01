package com.shtoone.chenjiang.mvp.presenter.measure;

import com.shtoone.chenjiang.mvp.contract.measure.MeasureContract;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
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
                        .subscribe(new Subscriber<List<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<String> strings) {
                                getView().responseJidianData(strings);
                            }
                        })
        );
    }


}
