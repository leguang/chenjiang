package com.shtoone.chenjiang.mvp.contract.measure;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface MeasureContract {
    interface View extends BaseContract.View {
        void responseJidianData(List<String> listJidianBianhao);

        void responseCezhanData(List<CezhanData> listCezhan);

        void setDialog(String tips);

        void onConnecting(BluetoothDevice device);

        void onConnected(BluetoothDevice device);

        void onDisconnected();

        void onDiscoveryStarted();

        void onDiscoveryFinished();

        void onDevicesFound(List<BluetoothDevice> deviceList);

        void onDataReceived(String str);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestJidianData();

        void requestCezhanData(YusheshuizhunxianData mYusheshuizhunxianData);

        void disconnect();

        void startScan();

        void connect(String address);

        void connectPaired(Activity mActivity);

        void sendData(byte[] data);
    }
}
