package com.shtoone.chenjiang.widget.bluetooth;

import com.shtoone.chenjiang.widget.bluetooth.classic.Device;
import com.shtoone.chenjiang.widget.bluetooth.classic.SmoothBluetooth;

import java.util.List;

/**
 * Created by leguang on 2016/12/23 0023.
 * Email：langmanleguang@qq.com
 */
public interface BluetoothListener {
    
    void onBluetoothNotSupported();

    void onBluetoothNotEnabled();

    void onConnecting(Device device);

    void onConnected(Device device);

    void onDisconnected();

    void onConnectionFailed(Device device);

    void onDiscoveryStarted();

    void onDiscoveryFinished();

    void onNoDevicesFound();

    void onDevicesFound(List<Device> deviceList, SmoothBluetooth.ConnectionCallback connectionCallback);

    void onDataReceived(int data, String str);
}
