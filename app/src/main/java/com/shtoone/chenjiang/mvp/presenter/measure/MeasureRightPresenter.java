package com.shtoone.chenjiang.mvp.presenter.measure;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.common.AudioPlayer;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.measure.MeasureContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.shtoone.chenjiang.widget.bluetooth.BluetoothListener;
import com.shtoone.chenjiang.widget.bluetooth.BluetoothManager;
import com.socks.library.KLog;

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
public class MeasureRightPresenter extends BasePresenter<MeasureContract.View> implements MeasureContract.Presenter {
    private static final String TAG = MeasureRightPresenter.class.getSimpleName();
    private BluetoothManager mBluetoothManager;

    public MeasureRightPresenter(MeasureContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        mBluetoothManager = BluetoothManager.newInstance(BaseApplication.mContext);
        mBluetoothManager.setListener(mListener);
        if (!mBluetoothManager.isConnected()) {
            mBluetoothManager.connectDefault();
        }
    }

    @Override
    public void disconnect() {
        mBluetoothManager.disconnect();
    }

    @Override
    public void startScan() {
        mBluetoothManager.startScan();
    }

    @Override
    public void connect(String address) {
        mBluetoothManager.connect(address);
    }

    @Override
    public void connectPaired(Activity mActivity) {
        mBluetoothManager.connectPaired(mActivity);
    }

    @Override
    public void sendData(byte[] data) {
        mBluetoothManager.sendData(data);
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
                            List<CezhanData> listCezhan = DataSupport.where("shuizhunxianID = ? ", String.valueOf(mYusheshuizhunxianData.getId()))
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

    /**
     * ********************蓝牙所有状态的回调都在这里*********************************
     */
    private BluetoothListener mListener = new BluetoothListener() {
        @Override
        public void onBluetoothNotSupported() {
            if (isViewAttached()) {
                getView().setDialog("未找到蓝牙设备");
            }
        }

        @Override
        public void onBluetoothNotEnabled() {
//            DialogHelper.successSnackbar(viewGroup, "蓝牙未打开，请打开手机蓝牙", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
        }

        @Override
        public void onConnecting(BluetoothDevice device) {
            if (isViewAttached()) {
                getView().onConnecting(device);
            }
        }

        @Override
        public void onConnected(BluetoothDevice device) {
            KLog.e("已经连接：" + device.getName());
            SharedPreferencesUtils.put(BaseApplication.mContext, Constants.BLUETOOTH_ADDRESS, device.getAddress());
            if (isViewAttached()) {
                getView().onConnected(device);
            }
            AudioPlayer.play(Constants.AUDIO_CONNECT);
        }

        @Override
        public void onDisconnected() {
            if (isViewAttached()) {
                getView().onDisconnected();
                AudioPlayer.play(Constants.AUDIO_DISCONNECT);
            }
        }

        @Override
        public void onConnectionFailed(BluetoothDevice device) {
            if (isViewAttached()) {
                getView().setDialog("蓝牙连接失败");
            }
        }

        @Override
        public void onDiscoveryStarted() {
            if (isViewAttached()) {
                getView().onDiscoveryStarted();
            }
        }

        @Override
        public void onDiscoveryFinished() {
            if (isViewAttached()) {
                getView().onDiscoveryFinished();
            }
        }

        @Override
        public void onNoDevicesFound() {
            if (isViewAttached()) {
                getView().setDialog("未发现蓝牙设备");
            }
        }

        @Override
        public void onDevicesFound(List<BluetoothDevice> deviceList) {
            if (isViewAttached()) {
                getView().onDevicesFound(deviceList);
            }
        }

        @Override
        public void onDataReceived(String str) {
            if (isViewAttached()) {
                getView().onDataReceived(str);
            }
            KLog.e("currentThreadName::" + Thread.currentThread().getName());
        }
    };

    @Override
    public void detachView() {
        mBluetoothManager.onDestroy();
        mBluetoothManager = null;
        mListener = null;
        super.detachView();
    }
}
