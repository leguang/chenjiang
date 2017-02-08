package com.shtoone.chenjiang.mvp.view.main.setting;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.dd.CircularProgressButton;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.DialogHelper;
import com.shtoone.chenjiang.mvp.contract.setting.SettingContract;
import com.shtoone.chenjiang.mvp.presenter.setting.SettingPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DeviceSettingFragment extends BaseFragment<SettingContract.Presenter> implements SettingContract.View {
    private static final String TAG = DeviceSettingFragment.class.getSimpleName();
    @BindView(R.id.rg_bluetooth_type_device_setting_fragment)
    RadioGroup rgBluetoothType;
    @BindView(R.id.bt_connect_device_setting_fragment)
    CircularProgressButton btConnect;
    private Dialog progressDialog;
    private ViewGroup viewGroup;
    private AlertDialog.Builder deviceListBuilder;

    public static DeviceSettingFragment newInstance() {
        return new DeviceSettingFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_setting, container, false);
        ButterKnife.bind(this, view);
        viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        mPresenter.start();
        rgBluetoothType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_classic_device_setting_fragment) {
                    mPresenter.switch2Classic();
                } else if (i == R.id.rb_le_device_setting_fragment) {
                    mPresenter.switch2Le();
                }
            }
        });

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {

    }

    @Override
    public void showLoading() {

    }

    @NonNull
    @Override
    protected SettingContract.Presenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    public void checkClassicBluetooth() {
        rgBluetoothType.check(R.id.rb_classic_device_setting_fragment);
    }

    @Override
    public void checkLeBluetooth() {
        rgBluetoothType.check(R.id.rb_le_device_setting_fragment);
    }

    @Override
    public void setDialog(String tips) {
        DialogHelper.warningSnackbar(viewGroup, tips, DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
    }

    @Override
    public void onConnecting(BluetoothDevice device) {
        DialogHelper.loadingSnackbar(viewGroup, "正连接:" + device.getName(), DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
    }

    @Override
    public void onConnected(BluetoothDevice device) {
        KLog.e("蓝牙已连接…………………………………………………………");
        DialogHelper.successSnackbar(viewGroup, "已连接:" + device.getName(), DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
    }

    @Override
    public void onDisconnected() {
        DialogHelper.errorSnackbar(viewGroup, "蓝牙连接已断开", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
    }

    @Override
    public void onDiscoveryStarted() {
        progressDialog = DialogHelper.progressDialog(_mActivity, "正在玩命查找设备...", 0);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDiscoveryFinished() {
        progressDialog.dismiss();

    }

    @Override
    public void onDevicesFound(final List<BluetoothDevice> deviceList) {
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
                            mPresenter.connect(deviceList.get(which).getAddress());
                            dialog.dismiss();
                        }
                    }).setPositiveButton(R.string.dialog_negativeText, null)
                    .setNeutralButton(R.string.dialog_scan, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.startScan();
                        }
                    });
        } else {
            deviceListBuilder.setItems(arrayDeviceInfo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.connect(deviceList.get(which).getAddress());
                    dialog.dismiss();
                }
            });
        }
        deviceListBuilder.show();
    }


    @OnClick(R.id.bt_connect_device_setting_fragment)
    public void onClick() {
        mPresenter.connectPaired(_mActivity);
    }
}
