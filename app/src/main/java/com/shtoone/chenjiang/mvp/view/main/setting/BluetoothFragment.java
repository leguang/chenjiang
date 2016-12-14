package com.shtoone.chenjiang.mvp.view.main.setting;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Dialoghelper;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.widget.bluetooth.Device;
import com.shtoone.chenjiang.widget.bluetooth.SmoothBluetooth;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BluetoothFragment extends BaseFragment {
    private static final String TAG = BluetoothFragment.class.getSimpleName();
    public static final int BT_REQUEST = 1;
    @BindView(R.id.tv_disconnect)
    TextView tvDisconnect;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.tv_paired)
    TextView tvPaired;
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_responses)
    ListView lvResponses;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.cb_carrage)
    CheckBox cbCarrage;
    @BindView(R.id.bt_send)
    Button btSend;
    private SmoothBluetooth mSmoothBluetooth;
    private Dialog progressDialog;
    private List<String> listResponse = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private AlertDialog.Builder deviceListBuilder;

    public static BluetoothFragment newInstance() {
        return new BluetoothFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth_setting, container, false);
        ButterKnife.bind(this, view);
        initStateBar(toolbar);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        initToolbarBackNavigation(toolbar);
        toolbar.setTitle("蓝牙调试");
        mSmoothBluetooth = new SmoothBluetooth(BaseApplication.mContext);
        mSmoothBluetooth.setListener(mListener);
        mAdapter = new ArrayAdapter<>(_mActivity, android.R.layout.simple_list_item_1, listResponse);
        lvResponses.setAdapter(mAdapter);
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }


    private SmoothBluetooth.Listener mListener = new SmoothBluetooth.Listener() {
        @Override
        public void onBluetoothNotSupported() {
            toolbar.setTitle("未找到蓝牙设备");
        }

        @Override
        public void onBluetoothNotEnabled() {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, BT_REQUEST);
        }

        @Override
        public void onConnecting(Device device) {
            toolbar.setTitle("正连接:" + device.getName());
        }

        @Override
        public void onConnected(Device device) {
            toolbar.setTitle("已连接:" + device.getName());
            tvPaired.setVisibility(View.GONE);
            tvScan.setVisibility(View.GONE);
            tvDisconnect.setVisibility(View.VISIBLE);
        }

        @Override
        public void onDisconnected() {
            toolbar.setTitle("蓝牙连接已断开");

            tvPaired.setVisibility(View.VISIBLE);
            tvScan.setVisibility(View.VISIBLE);
            tvDisconnect.setVisibility(View.GONE);
        }

        @Override
        public void onConnectionFailed(Device device) {
            toolbar.setTitle("蓝牙连接失败");
            if (device != null && device.isPaired()) {
                mSmoothBluetooth.doDiscovery();
            }
        }

        @Override
        public void onDiscoveryStarted() {
            progressDialog = Dialoghelper.progressDialog(_mActivity, "正在玩命查找设备...", 0);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        public void onDiscoveryFinished() {
            progressDialog.dismiss();
        }

        @Override
        public void onNoDevicesFound() {
            toolbar.setTitle("未找到蓝牙设备");
        }

        @Override
        public void onDevicesFound(final List<Device> deviceList, final SmoothBluetooth.ConnectionCallback connectionCallback) {
            KLog.e("onDevicesFound^^^^^^^^^^^^^^^^^^^^^");
            String[] arrayDeviceInfo = new String[deviceList.size()];

            for (int i = 0; i < deviceList.size(); i++) {
                arrayDeviceInfo[i] = deviceList.get(i).getName() + "(" + deviceList.get(i).getAddress() + ")";
            }

            if (deviceListBuilder == null) {
                deviceListBuilder = new AlertDialog.Builder(_mActivity)
                        .setTitle(R.string.dialog_select_bluetooth)
                        .setItems(arrayDeviceInfo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                connectionCallback.connectTo(deviceList.get(which));
                                dialog.dismiss();
                            }
                        }).setPositiveButton(R.string.dialog_negativeText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setNeutralButton("扫描", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSmoothBluetooth.doDiscovery();
                            }
                        });
            } else {
                deviceListBuilder.setItems(arrayDeviceInfo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        connectionCallback.connectTo(deviceList.get(which));
                        dialog.dismiss();
                    }
                });
            }
            deviceListBuilder.show();
        }

        @Override
        public void onDataReceived(int data, String str) {
            if (data > 0) {
                listResponse.add(0, str);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @OnClick({R.id.tv_disconnect, R.id.tv_scan, R.id.tv_paired, R.id.bt_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_disconnect:
                mSmoothBluetooth.disconnect();
                listResponse.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_scan:
                mSmoothBluetooth.doDiscovery();
                break;
            case R.id.tv_paired:
                mSmoothBluetooth.tryConnection();
                break;
            case R.id.bt_send:
                mSmoothBluetooth.send(etMessage.getText().toString(), cbCarrage.isChecked());
                etMessage.setText("");
                break;
        }
    }

    @Override
    public void onDestroy() {
        mSmoothBluetooth.stop();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BT_REQUEST) {
            if (resultCode == RESULT_OK) {
                mSmoothBluetooth.tryConnection();
            }
        }
    }
}
