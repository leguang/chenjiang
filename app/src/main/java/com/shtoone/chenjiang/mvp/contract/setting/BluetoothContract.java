package com.shtoone.chenjiang.mvp.contract.setting;


import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import com.shtoone.chenjiang.mvp.contract.base.BaseContract;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface BluetoothContract {
    interface View extends BaseContract.View {
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
        void disconnect();

        void startScan();

        void connect(String address);

        void connectPaired(Activity mActivity);

        void sendData(byte[] data);

    }
}
