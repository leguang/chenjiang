package com.shtoone.chenjiang.widget.bluetooth.classic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.util.Log;

import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.RxManager;
import com.shtoone.chenjiang.widget.bluetooth.BluetoothListener;
import com.shtoone.chenjiang.widget.bluetooth.IBluetooth;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ClassicBluetooth implements IBluetooth {
    private static final String TAG = ClassicBluetooth.class.getSimpleName();
    private static ClassicBluetooth INSTANCE;
    private final Context mContext;
    private BluetoothListener mListener;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> mDevices = new ArrayList<>();
    private BluetoothDevice mCurrentDevice;
    private static final UUID UUID_DEVICE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    public static final int STATE_NONE = 0;        // we're doing nothing
    public static final int STATE_LISTEN = 1;        // now listening for incoming connections
    public static final int STATE_CONNECTING = 2;    // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;    // now connected to a remote device
    private int mState;

    private ClassicBluetooth(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
    }

    public static ClassicBluetooth newInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ClassicBluetooth.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClassicBluetooth(context);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void setListener(BluetoothListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public boolean isAvailable() {
        return mBluetoothAdapter != null;
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
        if (!checkBluetooth()) {
            return false;
        }
        mCurrentDevice = null;
        mDevices.clear();
        if (mListener != null) {
            mListener.onDiscoveryStarted();
        }
        KLog.e("doDiscovery()");
        if (isScaning()) {
            mContext.unregisterReceiver(mReceiver);
            mBluetoothAdapter.cancelDiscovery();
        }
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(mReceiver, filter);
        // 开始搜索
        return mBluetoothAdapter.startDiscovery();

    }

    @Override
    public boolean isScaning() {
        return mBluetoothAdapter.isDiscovering();
    }

    @Override
    public boolean stopScan() {
        return mBluetoothAdapter.cancelDiscovery();
    }

    @Override
    public boolean isConnected() {
        return mState == STATE_CONNECTED;
    }

    @Override
    public synchronized void connect(String address) {
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (BluetoothAdapter.checkBluetoothAddress(address)) {
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            if (mListener != null) {
                mListener.onConnecting(device);
            }
            mCurrentDevice = device;
            mConnectThread = new ConnectThread(device);
            mConnectThread.start();
        } else {
            KLog.e("地址格式不对");
            mListener.onDisconnected();
        }

        mState = STATE_CONNECTING;
        KLog.e(" mState-> " + mState);
    }

    @Override
    public void disconnect() {
        if (mState != STATE_CONNECTED) {
            return;
        }
        mCurrentDevice = null;
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        mState = STATE_NONE;
        KLog.e(TAG, " mState-> " + mState);
    }

    @Override
    public synchronized boolean close() {
        if (mState != STATE_NONE) {
            disconnect();
        }
        return mBluetoothAdapter.disable();
    }

    @Override
    public void sendData(byte[] data) {
        ConnectedThread r;
        synchronized (this) {
            if (mState != STATE_CONNECTED) {
                return;
            }
            r = mConnectedThread;
        }
        r.write(data);
    }

    private boolean checkBluetooth() {
        if (!isBluetoothAvailable()) {
            if (mListener != null) {
                mListener.onBluetoothNotSupported();
            }
            return false;
        }

        if (!isOpened()) {
            if (mListener != null) {
                mListener.onBluetoothNotEnabled();
            }
            return false;
        }
        return true;
    }

    public boolean isBluetoothAvailable() {
        try {
            if (mBluetoothAdapter == null || mBluetoothAdapter.getAddress().equals(null))
                return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                KLog.e("found: " + device.getName() + " " + device.getAddress());
                if (!deviceExist(device)) {
                    mDevices.add(device);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                KLog.e(TAG, "Discovery finished: " + mDevices.size());
                mContext.unregisterReceiver(mReceiver);
                if (mListener != null) {
                    mListener.onDiscoveryFinished();
                    if (mDevices.isEmpty()) {
                        mListener.onNoDevicesFound();
                    } else {
                        mListener.onDevicesFound(mDevices);
                    }
                }
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

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(final BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord(UUID_DEVICE);
            } catch (IOException e) {
                KLog.e(e);
                e.printStackTrace();
                //此处也要放到主线程当中
                Observable.just("1")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String receiveData) {
                                KLog.e("currentThreadName::" + Thread.currentThread().getName());
                                mListener.onConnectionFailed(device);
                            }
                        });
                disconnect();
            }
            mmSocket = tmp;
        }

        public void run() {
            setName("ConnectThread");
            mBluetoothAdapter.cancelDiscovery();
            try {
                KLog.e("开始阻塞的方式进行连接");
                if (mmSocket != null) {
                    mmSocket.connect();
                }
                KLog.e("结束阻塞的方式进行连接");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    KLog.e(e2);
                    e.printStackTrace();
                }
                KLog.e("连接失败………………………………………………");
                Observable.just("1")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String receiveData) {
                                KLog.e("提示接口连接失败");
                                KLog.e("currentThreadName::" + Thread.currentThread().getName());
                                mListener.onConnectionFailed(mmDevice);
                            }
                        });
                disconnect();
                return;
            }
            synchronized (this) {
                mConnectThread = null;
            }
            mState = STATE_CONNECTED;
            KLog.e(" mState-> " + mState);
            if (mListener != null) {
                Observable.just("1")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                mListener.onConnected(mmDevice);
                            }
                        });
            }
            connected(mmSocket);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                KLog.e(e);
            }
        }
    }

    public synchronized void connected(BluetoothSocket socket) {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            KLog.e("这是一个客户端socket");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
                KLog.e("获取客户端的socket的输入输出流");
            } catch (IOException e) {
                KLog.e("temp sockets not created", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            setName("ConnectedThread");
            byte[] buffer = new byte[1024];
            int length;

            while (mState == STATE_CONNECTED) {
                KLog.e("进入循环**********");
                try {
                    KLog.e("开始读取流…………………………………………………………");
                    length = mmInStream.read(buffer);
                    KLog.e("结束读取流…………………………………………………………");
                    final String readMessage = new String(buffer, 0, length);
//                    final String readMessage = new String(buffer, 0, length, "GBK");
//                    String readMessage1 = new String(readMessage.getBytes("GBK"), "UTF-8");
                    KLog.e(length);
                    KLog.e(readMessage);
                    Observable.just(readMessage)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String receiveData) {
                                    mListener.onDataReceived(readMessage);
                                }
                            });
                } catch (IOException e) {
                    KLog.e(e);
                    e.printStackTrace();
                    //连接断开通知UI
                    Observable.just("1")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    mListener.onDisconnected();
                                }
                            });
                    disconnect();
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);
                KLog.e("write::" + new String(buffer));
            } catch (IOException e) {
                e.printStackTrace();
                KLog.e("Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}
