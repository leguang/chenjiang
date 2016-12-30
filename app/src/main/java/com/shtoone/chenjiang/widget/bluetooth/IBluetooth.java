package com.shtoone.chenjiang.widget.bluetooth;

import android.bluetooth.BluetoothDevice;

import java.util.Set;

/**
 * Created by leguang on 2016/12/23 0023.
 * Email：langmanleguang@qq.com
 */
public interface IBluetooth {

    boolean isAvailable();

    boolean isOpened();

    boolean open();

    Set<BluetoothDevice> getBondedDevices();

    boolean startScan();

    boolean isScaning();

    boolean stopScan();

    void connect(String address);

    void disconnect();

    boolean close();

    void sendData(byte[] data);

    void setListener(BluetoothListener listener);
}
