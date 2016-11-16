package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.AddShuizhunxianContract;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.model.bean.JidianData;
import com.shtoone.chenjiang.mvp.model.bean.StaffData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

import org.litepal.crud.DataSupport;

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
public class AddShuizhunxianPresenter extends BasePresenter<AddShuizhunxianContract.View> implements AddShuizhunxianContract.Presenter {
    private static final String TAG = AddShuizhunxianPresenter.class.getSimpleName();

    public AddShuizhunxianPresenter(AddShuizhunxianContract.View mView) {
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
}
