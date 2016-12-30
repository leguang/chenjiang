package com.shtoone.chenjiang.widget.bluetooth;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Observable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.shtoone.chenjiang.widget.bluetooth.classic.ClassicBluetooth;
import com.shtoone.chenjiang.widget.bluetooth.le.LeBluetooth;
import com.socks.library.KLog;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by leguang on 2016/12/23 0023.
 * Email：langmanleguang@qq.com
 */
public class BluetoothManager {
    private static final String TAG = BluetoothManager.class.getSimpleName();
    private static BluetoothManager INSTANCE;
    private IBluetooth mBluetooth;
    private BluetoothListener mListener;

    private BluetoothManager(Context mContext) {
        int intBluetoothType = (int) SharedPreferencesUtils.get(mContext, Constants.BLUETOOTH_TYPE, 0);
        if (intBluetoothType == Constants.BLUETOOTH_CLASSIC) {
            mBluetooth = ClassicBluetooth.newInstance(mContext);
        } else if (intBluetoothType == Constants.BLUETOOTH_LE) {
            mBluetooth = LeBluetooth.newInstance(mContext);
        } else {
            mBluetooth = ClassicBluetooth.newInstance(mContext);
        }
    }

    public static BluetoothManager newInstance(Context mContext) {
        if (INSTANCE == null) {
            synchronized (ClassicBluetooth.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BluetoothManager(mContext);
                }
            }
        }
        return INSTANCE;
    }

    public IBluetooth getBluetooth() {
        return mBluetooth;
    }

    public void switch2Classic() {
        this.mBluetooth = ClassicBluetooth.newInstance(BaseApplication.mContext);
    }

    public void switch2Le() {
        this.mBluetooth = LeBluetooth.newInstance(BaseApplication.mContext);
    }

    public void setListener(BluetoothListener mListener) {
        this.mListener = mListener;
        mBluetooth.setListener(mListener);
    }

    public void connectDefault() {
        if (!mBluetooth.isAvailable()) {
            if (mListener != null) {
                mListener.onBluetoothNotSupported();
            }
            return;
        }
        if (!mBluetooth.isOpened()) {
            mBluetooth.open();
        }

        final String strAddress = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.BLUETOOTH_ADDRESS, "");
        if (!TextUtils.isEmpty(strAddress)) {
            KLog.e("连接:" + strAddress);
            //要延迟连接，因为一进某个页面就打开蓝牙，蓝牙还没完全打开就连接会连接不上。
            rx.Observable.timer(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            mBluetooth.connect(strAddress);
                        }
                    });
        }
    }

    public void connectPaired(Activity mActivity) {
        String[] arrayDeviceInfo = new String[mBluetooth.getBondedDevices().size()];
        final String[] arrayAdress = new String[mBluetooth.getBondedDevices().size()];
        int i = 0;
        for (BluetoothDevice bluetoothDevice : mBluetooth.getBondedDevices()) {
            arrayDeviceInfo[i] = bluetoothDevice.getName() + "(" + bluetoothDevice.getAddress() + ")";
            arrayAdress[i] = bluetoothDevice.getAddress();
            i++;
        }
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.dialog_select_bluetooth)
                .setItems(arrayDeviceInfo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBluetooth.connect(arrayAdress[which]);
                        dialog.dismiss();
                    }
                }).setPositiveButton(R.string.dialog_negativeText, null)
                .setNeutralButton(R.string.dialog_scan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBluetooth.startScan();
                    }
                }).show();
    }

    public void connect(String address) {
        mBluetooth.connect(address);
    }

    public void disconnect() {
        mBluetooth.disconnect();
    }

    public void startScan() {
        mBluetooth.startScan();
    }

    public void sendData(byte[] data) {
        mBluetooth.sendData(data);
    }

    public void onDestroy() {
        mBluetooth.close();
    }

    public void open() {
        mBluetooth.open();
    }
}
