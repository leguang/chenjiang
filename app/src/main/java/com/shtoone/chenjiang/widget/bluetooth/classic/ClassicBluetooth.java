package com.shtoone.chenjiang.widget.bluetooth.classic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.util.Log;

import com.shtoone.chenjiang.widget.bluetooth.BluetoothListener;
import com.shtoone.chenjiang.widget.bluetooth.IBluetooth;
import com.socks.library.KLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ClassicBluetooth implements IBluetooth {
    private static final String TAG = ClassicBluetooth.class.getSimpleName();
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

    public ClassicBluetooth(Context context) {
        this(context, null);
    }

    public ClassicBluetooth(Context context, BluetoothListener listener) {
        mContext = context;
        mListener = listener;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
    }

    public void setListener(BluetoothListener listener) {
        mListener = listener;
    }

    @Override
    public boolean isAvailable() {
        try {
            if (mBluetoothAdapter == null || mBluetoothAdapter.getAddress().equals(null))
                return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean isOpened() {
        return mBluetoothAdapter.isEnabled();
    }

    @Override
    public void open() {
        mBluetoothAdapter.enable();
    }


    /**
     * 看是否要转换成list列表
     *
     * @return
     */
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
        Log.e(TAG, "doDiscovery()");

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
            mCurrentDevice = device;
            mConnectThread = new ConnectThread(device);
            mConnectThread.start();
        }

        mState = STATE_CONNECTING;
    }

    @Override
    public void disconnect() {
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
        Log.e(TAG, " mState-> " + mState);
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
            if (mState != STATE_CONNECTED) return;
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
                Log.e(TAG, "Device found: " + device.getName() + " " + device.getAddress());
                if (!deviceExist(device)) {
                    mDevices.add(device);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d(TAG, "Discovery finished: " + mDevices.size());
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
        private String mSocketType;

        public ConnectThread(final BluetoothDevice device) {
            KLog.e("创建连接线程");
            mmDevice = device;
            BluetoothSocket tmp = null;

            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord(UUID_DEVICE);
            } catch (IOException e) {
                KLog.e("Socket Type: " + mSocketType + "create() failed", e);
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

            }
            mmSocket = tmp;
        }

        public void run() {
            KLog.e("BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread");
            mBluetoothAdapter.cancelDiscovery();
            try {
                KLog.e("开始阻塞的方式进行连接");
                mmSocket.connect();
                KLog.e("结束阻塞的方式进行连接");

                KLog.e("mmSocket.connect();");
            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    KLog.e("unable to close() " + mSocketType + " socket during connection failure", e2);
                }
                mListener.onConnectionFailed(mmDevice);
                return;
            }

            synchronized (this) {
                mConnectThread = null;
            }

            KLog.e("connected(mmSocket, mmDevice, mSocketType);");
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
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

//        Message msg = mHandler.obtainMessage(MESSAGE_DEVICE_NAME);
//        mHandler.sendMessage(msg);

        mState = STATE_CONNECTED;
        Log.e(TAG, " mState-> " + mState);
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
            KLog.e("BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            while (mState == STATE_CONNECTED) {
                KLog.e("进入循环**********");
                try {

                    KLog.e("开始读取流…………………………………………………………");
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    KLog.e("结束读取流…………………………………………………………");
                    final String readMessage = new String(buffer, 0, bytes);
                    KLog.e(bytes);
                    KLog.e(readMessage);


                    final int finalBytes = bytes;
                    Observable.just(readMessage)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String receiveData) {
                                    KLog.e("currentThreadName::" + Thread.currentThread().getName());
                                    mListener.onDataReceived(finalBytes, readMessage);

                                }
                            });


                    // Send the obtained bytes to the UI Activity
//                    mHandler.obtainMessage(BluetoothChat.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    KLog.e("进入异常");
                    KLog.e(e);
                    //连接断开通知UI
                    mListener.onDisconnected();
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         *
         * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);
                KLog.e("write::" + new String(buffer));
                KLog.e(mmOutStream);
                // Share the sent message back to the UI Activity
//                mHandler.obtainMessage(BluetoothChat.MESSAGE_WRITE, -1, -1, buffer)
//                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
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
