package com.shtoone.chenjiang.widget.bluetooth.le;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.shtoone.chenjiang.common.RxManager;
import com.shtoone.chenjiang.widget.bluetooth.BluetoothListener;
import com.shtoone.chenjiang.widget.bluetooth.IBluetooth;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.bluetooth.BluetoothDevice.TRANSPORT_LE;

/**
 * Created by leguang on 2016/12/27 0027.
 * Email：langmanleguang@qq.com
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class LeBluetooth implements IBluetooth {
    private static final String TAG = LeBluetooth.class.getSimpleName();
    private static LeBluetooth INSTANCE;
    public static final int STATE_NONE = 0;        // we're doing nothing
    public static final int STATE_LISTEN = 1;        // now listening for incoming connections
    public static final int STATE_CONNECTING = 2;    // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;    // now connected to a remote device
    private final Context mContext;
    private int mState;
    private static final UUID UUID_SERVICE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final String UUID_BLEGATTCHAR = "00001102-0000-1000-8000-00805f9b34fb";
    public RxManager mRxManager = new RxManager();
    private BluetoothListener mListener;
    private BluetoothAdapter mBluetoothAdapter;
    private static final long SCAN_PERIOD = 10000;
    private ArrayList<BluetoothDevice> mDevices = new ArrayList<>();
    private BluetoothDevice mCurrentDevice;
    private boolean isScanning;
    private BluetoothGatt mBluetoothGatt;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private BluetoothGattCharacteristic mBleGattChar;
    int number = 0;

    public LeBluetooth(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
    }

    public static LeBluetooth newInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LeBluetooth.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LeBluetooth(context);
                }
            }
        }
        return INSTANCE;
    }

    private static boolean isAndroidMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void runOnUiThread(Runnable runnable) {
        if (isAndroidMainThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    @Override
    public void setListener(BluetoothListener listener) {
        mListener = listener;
    }

    @Override
    public boolean isAvailable() {
        return (mBluetoothAdapter != null && mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE));
    }

    @Override
    public boolean isOpened() {
        return mBluetoothAdapter.isEnabled();
    }

    @Override
    public boolean open() {
        return mBluetoothAdapter.enable();
    }

    @Override
    public Set<BluetoothDevice> getBondedDevices() {
        return mBluetoothAdapter.getBondedDevices();
    }

    @Override
    public boolean startScan() {
        Log.e(TAG, "scanBleDevices: 扫描蓝牙");
        mCurrentDevice = null;
        mDevices.clear();
        mBluetoothAdapter.startLeScan(mBleScanCallback);
        isScanning = true;
        if (mListener != null) {
            mListener.onDiscoveryStarted();
        }
        Observable.timer(SCAN_PERIOD, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        stopScan();
                    }
                });
        return true;
    }

    @Override
    public boolean isScaning() {
        return isScanning;
    }

    @Override
    public boolean stopScan() {
        if (!isScaning()) {
            return true;
        }
        if (mBluetoothAdapter == null) {
            return true;
        }
        mBluetoothAdapter.stopLeScan(mBleScanCallback);
        isScanning = false;
        KLog.e("isScanning::" + isScanning);
        if (mListener != null) {
            mListener.onDiscoveryFinished();
            if (mDevices.isEmpty()) {
                mListener.onNoDevicesFound();
            } else {
                mListener.onDevicesFound(mDevices);
            }
        }
        return true;
    }

    @Override
    public void connect(String address) {
        if (mBluetoothAdapter == null || address == null) {
            return;
        }

        if (mCurrentDevice != null && address.equals(mCurrentDevice.getAddress()) && mBluetoothGatt != null) {
            Log.e(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mState = STATE_CONNECTING;
                if (mListener != null) {
                    mListener.onConnecting(mCurrentDevice);
                }
                return;
            }
        }

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (mListener != null && device == null) {
            mListener.onConnectionFailed(device);
        }

        if (mGattCallback != null && device != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mBluetoothGatt = device.connectGatt(mContext, true, mGattCallback, TRANSPORT_LE);
            } else {
                mBluetoothGatt = device.connectGatt(mContext, true, mGattCallback);
            }
        }

        mCurrentDevice = device;
        if (mListener != null) {
            mListener.onConnecting(mCurrentDevice);
        }
        mState = STATE_CONNECTING;
    }

    @Override
    public void disconnect() {
        if (mState != STATE_CONNECTED) {
            return;
        }
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            return;
        }
        stopScan();
        mBluetoothGatt.disconnect();
        mCurrentDevice = null;
        mState = STATE_NONE;
        KLog.e(TAG, " mState-> " + mState);
    }

    @Override
    public boolean close() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }

        if (mState != STATE_NONE) {
            disconnect();
        }
        mRxManager.clear();
        return mBluetoothAdapter.disable();
    }

    @Override
    public void sendData(byte[] data) {
        KLog.e(new String(data));

        if (mState != STATE_CONNECTED) {
            return;
        }

        if (mBluetoothGatt != null && mBleGattChar != null) {
            mBleGattChar.setValue(data);
            boolean isSend = mBluetoothGatt.writeCharacteristic(mBleGattChar);
            KLog.e(data.toString());
            KLog.e(new String(data));
            Log.e(TAG, "发送：" + (isSend ? "成功" : "失败"));
        }

    }

    @Override
    public boolean isConnected() {
        return mState == STATE_CONNECTED;
    }

    /**
     * 搜索到蓝牙设备后的回调
     */
    private BluetoothAdapter.LeScanCallback mBleScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bleDevice, int rssi, byte[] scanRecord) {
            KLog.e(" found: " + bleDevice.getName() + "::" + bleDevice.getAddress());
            KLog.e("rssi::" + rssi);
            if (!deviceExist(bleDevice)) {
                mDevices.add(bleDevice);
            }
        }
    };

    private boolean deviceExist(BluetoothDevice device) {
        for (BluetoothDevice mDevice : mDevices) {
            if (mDevice.getAddress().contains(device.getAddress())) {
                return true;
            }
        }
        return false;
    }


    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                KLog.e("device connect success!");
                mState = STATE_CONNECTED;
                if (isScaning()) {
                    stopScan();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onConnected(mCurrentDevice);
                        }
                    }
                });

                if (gatt.getDevice().getBondState() != BluetoothDevice.BOND_BONDING) {
                    mBluetoothGatt.discoverServices();
                }

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                KLog.e(TAG, "device disconnect.");
                mState = STATE_NONE;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onDisconnected();
                        }
                    }
                });
            }
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                KLog.d("success with find services discovered .");
                List<BluetoothGattService> serviceList = gatt.getServices();
                Observable.from(serviceList)
                        .flatMap(new Func1<BluetoothGattService, Observable<BluetoothGattCharacteristic>>() {
                            @Override
                            public Observable<BluetoothGattCharacteristic> call(BluetoothGattService bleGattService) {
                                KLog.e("Service::" + bleGattService.getUuid().toString());
                                return Observable.from(bleGattService.getCharacteristics());
                            }
                        })
                        .filter(new Func1<BluetoothGattCharacteristic, Boolean>() {
                            @Override
                            public Boolean call(BluetoothGattCharacteristic bleGattChar) {
                                KLog.e("bleGattCharUUID::" + bleGattChar.getUuid().toString());
//                                return bleGattChar.getUuid().toString().equals(UUID_DEVICE);
                                return true;

                            }
                        })
                        .subscribe(new Action1<BluetoothGattCharacteristic>() {
                            @Override
                            public void call(BluetoothGattCharacteristic bleGattChar) {
                                KLog.e("最后::" + bleGattChar.getUuid().toString());
                                if (bleGattChar.getUuid().toString().equals(UUID_BLEGATTCHAR)) {
                                    gatt.setCharacteristicNotification(bleGattChar, true);//设置开启接受蓝牙数据


                                    //明天再遍历一遍看看descriptor有哪些
                                    //实在不行就都设置
//                                    BluetoothGattDescriptor descriptor = bleGattChar
//                                            .getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
//                                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);


                                    List<BluetoothGattDescriptor> descriptors = bleGattChar.getDescriptors();
                                    for (BluetoothGattDescriptor descriptor : descriptors) {

                                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                        mBluetoothGatt.writeDescriptor(descriptor);
                                    }

                                }
                            }
                        });


            } else if (status == BluetoothGatt.GATT_FAILURE) {
                KLog.e("failure find services discovered.");
            }
        }

//        @Override
//        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            super.onCharacteristicRead(gatt, characteristic, status);
//             String receiveData = new String(characteristic.getValue());
//            KLog.e("收到蓝牙发来数据：" + receiveData);
//        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            BluetoothGattCharacteristic charat = gatt.getService(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"))
                    .getCharacteristic(UUID.fromString("00001102-0000-1000-8000-00805f9b34fb"));

            KLog.e("临时：" + charat.getUuid().toString());
            KLog.e("临时：" + characteristic.getUuid().toString());


            //******************************************
            if (characteristic.getUuid().toString().equals(UUID_BLEGATTCHAR)) {
                final String receiveData = new String(characteristic.getValue());
                KLog.e("收到蓝牙发来数据：" + receiveData);
            }


            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.e(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.e(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.e(TAG, String.format("Received heart rate: %d", heartRate));

            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for (byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
//                    intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            }
            if (data[0] == 'A') {
                return;
            } else {

                Log.i("yq", "data字符串：" + new String(data));
            }


            //*******************


            number++;
            KLog.e("number::" + number);
//            KLog.e("onCharacteristicChanged");
//            KLog.e("characteristic.getValue()::" + characteristic.getValue());
//            KLog.e("characteristic.getValue()::" + characteristic.getValue().toString());
//            final String receiveData = new String(characteristic.getValue());
//            KLog.e("收到蓝牙发来数据：" + receiveData);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mListener != null) {
//                        mListener.onDataReceived(receiveData);
//                    }
//                }
//            });
//
//            //*****************************************
//            try {
//                long timestamp = System.currentTimeMillis();
//                String fileName = "ble-" + timestamp + ".log";
//                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    String path = Environment.getExternalStorageDirectory().getPath() + "/BLE/";
//                    Log.d(TAG, "path=" + path);
//                    File dir = new File(path);
//                    if (!dir.exists()) {
//                        dir.mkdirs();
//                    }
//                    FileOutputStream fos = new FileOutputStream(path + fileName);
//                    fos.write(characteristic.getValue());
//                    fos.close();
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "an error occured while writing file...", e);
//            }
//            //**************************************

        }
    };
}
