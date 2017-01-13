package com.shtoone.chenjiang.mvp.presenter.upload;

import com.shtoone.chenjiang.mvp.contract.upload.DetailContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.shtoone.chenjiang.widget.bluetooth.BluetoothManager;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {
    private static final String TAG = DetailPresenter.class.getSimpleName();

    public DetailPresenter(DetailContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
    }


    @Override
    public void requestCezhanData(final ShuizhunxianData mShuizhunxianData) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<CezhanData>>() {
                    @Override
                    public void call(Subscriber<? super List<CezhanData>> subscriber) {
                        //生成测站并存到数据库中这种操作应考虑放到编辑水准线，保存的那里。后面点击测量的时候应该读取数据库，而产生数据后应该是修改相应测站的行。
                        try {

                            List<CezhanData> listCezhan = DataSupport.where("shuizhunxianID = ? ", String.valueOf(mShuizhunxianData.getId()))
                                    .order("number").find(CezhanData.class);
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
                                getView().responseCezhanData(mCezhanData);
                            }
                        })
        );
    }
}
