package com.shtoone.chenjiang.widget.bluetooth;

import android.content.Context;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.shtoone.chenjiang.widget.bluetooth.classic.ClassicBluetooth;
import com.shtoone.chenjiang.widget.bluetooth.le.LeBluetooth;

/**
 * Created by leguang on 2016/12/23 0023.
 * Email：langmanleguang@qq.com
 */
public class BluetoothManager {
    private static final String TAG = BluetoothManager.class.getSimpleName();

    private BluetoothManager() {
    }

    public static IBluetooth getBluetooth(Context mContext) {
        int intBluetoothType = (int) SharedPreferencesUtils.get(mContext, Constants.BLUETOOTH_TYPE, 0);
        if (intBluetoothType == Constants.BLUETOOTH_CLASSIC) {
            return ClassicBluetooth.newInstance(mContext);
        } else if (intBluetoothType == Constants.BLUETOOTH_LE) {
            return LeBluetooth.newInstance(mContext);
        } else {
            return ClassicBluetooth.newInstance(mContext);
        }
    }
}
