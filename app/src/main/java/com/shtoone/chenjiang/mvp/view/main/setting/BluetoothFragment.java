package com.shtoone.chenjiang.mvp.view.main.setting;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
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

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.DialogHelper;
import com.shtoone.chenjiang.mvp.contract.setting.BluetoothContract;
import com.shtoone.chenjiang.mvp.presenter.setting.BluetoothPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 * 该界面留作调试指令用
 */
public class BluetoothFragment extends BaseFragment<BluetoothContract.Presenter> implements BluetoothContract.View {
    private static final String TAG = BluetoothFragment.class.getSimpleName();
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
    @BindView(R.id.bt_send1)
    Button btSend1;
    @BindView(R.id.bt_send2)
    Button btSend2;
    private Dialog progressDialog;
    private List<String> listResponse = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private AlertDialog.Builder deviceListBuilder;
    private ViewGroup viewGroup;

    public static BluetoothFragment newInstance() {
        return new BluetoothFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth_setting, container, false);
        ButterKnife.bind(this, view);
        initStateBar(toolbar);
        viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
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
        mAdapter = new ArrayAdapter<>(_mActivity, android.R.layout.simple_list_item_1, listResponse);
        lvResponses.setAdapter(mAdapter);
        mPresenter.start();
    }


    @OnClick({R.id.tv_disconnect, R.id.tv_scan, R.id.tv_paired, R.id.bt_send, R.id.bt_send1, R.id.bt_send2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_disconnect:
                mPresenter.disconnect();
                listResponse.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_scan:
                mPresenter.startScan();
                break;
            case R.id.tv_paired:
                mPresenter.connectPaired(_mActivity);
                break;
            case R.id.bt_send:
                //看此处的bytes编码   会不会引起乱码
                mPresenter.sendData(etMessage.getText().toString().getBytes());
                etMessage.setText("");
                break;

            case R.id.bt_send1:
                mPresenter.sendData("GET/I/WI12".getBytes());
                break;
            case R.id.bt_send2:
                mPresenter.sendData("GET/M/WI32/WI330\n".getBytes());

                break;
        }
    }

    @NonNull
    @Override
    protected BluetoothContract.Presenter createPresenter() {
        return new BluetoothPresenter(this);
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
        DialogHelper.successSnackbar(viewGroup, "已连接:" + device.getName(), DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
        tvPaired.setVisibility(View.GONE);
        tvScan.setVisibility(View.GONE);
        tvDisconnect.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDisconnected() {
        DialogHelper.successSnackbar(viewGroup, "蓝牙连接已断开", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
        tvPaired.setVisibility(View.VISIBLE);
        tvScan.setVisibility(View.VISIBLE);
        tvDisconnect.setVisibility(View.GONE);
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

    @Override
    public void onDataReceived(String str) {
        KLog.e(str);
        if (str.length() > 0) {
            listResponse.add(0, str);
            mAdapter.notifyDataSetChanged();
        }
    }
}
