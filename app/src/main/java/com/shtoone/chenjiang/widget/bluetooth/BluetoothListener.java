package com.shtoone.chenjiang.widget.bluetooth;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * Created by leguang on 2016/12/23 0023.
 * Email：langmanleguang@qq.com
 */
public interface BluetoothListener {

    void onBluetoothNotSupported();

    void onBluetoothNotEnabled();

    void onConnecting(BluetoothDevice device);

    void onConnected(BluetoothDevice device);

    void onDisconnected();

    void onConnectionFailed(BluetoothDevice device);

    void onDiscoveryStarted();

    void onDiscoveryFinished();

    void onNoDevicesFound();

    void onDevicesFound(List<BluetoothDevice> deviceList);

    void onDataReceived(String str);
}
