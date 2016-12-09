package com.shtoone.chenjiang.mvp.view.main.measure;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.AudioPlayer;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.Dialoghelper;
import com.shtoone.chenjiang.common.ToastUtils;
import com.shtoone.chenjiang.mvp.contract.measure.MeasureContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.measure.MeasurePresenter;
import com.shtoone.chenjiang.mvp.view.adapter.MeasureRVPAdapter;
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
public class MeasureRightFragment extends BaseFragment<MeasureContract.Presenter> implements MeasureContract.View {
    private static final String TAG = MeasureRightFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_jidian_measure_right_fragment)
    Button btJidian;
    @BindView(R.id.bt_zhuandian_measure_right_fragment)
    Button btZhuandian;
    @BindView(R.id.bt_cedian_measure_right_fragment)
    Button btCedian;
    @BindView(R.id.bt_chongce_measure_right_fragment)
    Button btChongce;
    @BindView(R.id.bt_fance_measure_right_fragment)
    Button btFance;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    protected RecyclerViewPager mRecyclerView;
    private List<String> listJidianBianhao;
    private MeasureRVPAdapter mAdapter;
    private YusheshuizhunxianData mYusheshuizhunxianData;
    private LinearLayoutManager mLinearLayoutManager;
    private Dialog progressDialog;
    private SmoothBluetooth mSmoothBluetooth;
    private Dialog dialogList;
    public static final int BT_REQUEST = 11;
    private ViewGroup viewGroup;

    public static MeasureRightFragment newInstance(YusheshuizhunxianData mYusheshuizhunxianData) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.YUSHESHUIZHUNXIAN, mYusheshuizhunxianData);

        MeasureRightFragment fragment = new MeasureRightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mYusheshuizhunxianData = (YusheshuizhunxianData) args.getSerializable(Constants.YUSHESHUIZHUNXIAN);
        }
    }

    @NonNull
    @Override
    protected MeasureContract.Presenter createPresenter() {
        return new MeasurePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure_right, container, false);
        ButterKnife.bind(this, view);
        //重设toolbar的paddingTop值，以填补状态栏高度
        initStateBar(toolbar);
        viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initRecyclerViewPager(view);
        initData();
        initBluetooth();
    }


    private void initToolbar() {
        toolbar.setTitle("测量");
        ((MeasureFragment) getParentFragment()).initToolbartoggle(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制侧滑的开与关
                ((MeasureFragment) getParentFragment()).toggle();
            }
        });

        toolbar.inflateMenu(R.menu.menu_measure_fragment);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_stop:
                        ToastUtils.showToast(_mActivity, "停止");
                        Dialoghelper.dialog(_mActivity, R.drawable.ic_error_outline_red_400_48dp,
                                R.string.dialog_stop_title, R.string.dialog_stop_content, R.string.dialog_positiveText,
                                R.string.dialog_negativeText, new Dialoghelper.Call() {
                                    @Override
                                    public void onNegative() {
                                    }

                                    @Override
                                    public void onPositive() {

                                    }
                                });
                        break;

                    case R.id.action_delete:
                        ToastUtils.showToast(_mActivity, "删除");
                        Dialoghelper.dialog(_mActivity, R.drawable.ic_error_outline_red_400_48dp,
                                R.string.dialog_delete_title, R.string.dialog_delete_content, R.string.dialog_positiveText,
                                R.string.dialog_negativeText, new Dialoghelper.Call() {
                                    @Override
                                    public void onNegative() {
                                    }

                                    @Override
                                    public void onPositive() {

                                    }
                                });

                        break;
                }
                return true;
            }
        });
    }

    private void initData() {
        mPresenter.requestJidianData();
        mPresenter.requestCezhanData(mYusheshuizhunxianData);
    }


    private void initBluetooth() {
        mSmoothBluetooth = new SmoothBluetooth(BaseApplication.mContext);
        mSmoothBluetooth.setListener(mListener);
    }

    protected void initRecyclerViewPager(View view) {
        mRecyclerView = (RecyclerViewPager) view.findViewById(R.id.viewpager);

        //数字越大，触发滚向下一页所需偏移就越大
        mRecyclerView.setTriggerOffset(0.1f);
        //数字越大，就越容易滚动
        mRecyclerView.setFlingFactor(0.0f);
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new MeasureRVPAdapter());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);


    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {
        Dialoghelper.warningSnackbar(viewGroup, "数据初始化失败", Dialoghelper.APPEAR_FROM_TOP_TO_DOWN);
    }


    @Override
    public void showLoading() {
    }

    @OnClick({R.id.bt_jidian_measure_right_fragment, R.id.bt_zhuandian_measure_right_fragment, R.id.bt_cedian_measure_right_fragment, R.id.bt_chongce_measure_right_fragment, R.id.bt_fance_measure_right_fragment, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_jidian_measure_right_fragment:
                Dialoghelper.dialogList(_mActivity, 0, R.string.dialog_select_jidian, listJidianBianhao, R.string.dialog_negativeText, 0, new Dialoghelper.ListCall() {
                    @Override
                    public void onSelection(Dialog dialog, View itemView, int which, CharSequence text) {

                    }
                });
                break;
            case R.id.bt_zhuandian_measure_right_fragment:

                break;
            case R.id.bt_cedian_measure_right_fragment:

                break;
            case R.id.bt_chongce_measure_right_fragment:

                break;
            case R.id.bt_fance_measure_right_fragment:
                mAdapter.measure(mRecyclerView.getCurrentPosition(), 2);

                break;
            case R.id.fab:
                //连接策略是：优先连接已配对的，当配对中没有的时候，再扫描。
                mSmoothBluetooth.tryConnection();

//                if (progressDialog == null) {
//                    progressDialog = Dialoghelper.progress(_mActivity, R.string.dialog_wait, R.string.dialog_measureing, true);
//                }
//                progressDialog.show();
//                mAdapter.measure(mRecyclerView.getCurrentPosition(), 1);
                break;
        }
    }

    @Override
    public void responseJidianData(List<String> listJidianBianhao) {
        this.listJidianBianhao = listJidianBianhao;
    }

    @Override
    public void responseCezhanData(List<CezhanData> listCezhan) {
        mAdapter.setNewData(listCezhan);
    }

    @Override
    public void onDestroy() {
        AudioPlayer.onDestroy();
        super.onDestroy();
    }

    private SmoothBluetooth.Listener mListener = new SmoothBluetooth.Listener() {
        @Override
        public void onBluetoothNotSupported() {
            Dialoghelper.errorSnackbar(viewGroup, "不支持蓝牙设备", Dialoghelper.APPEAR_FROM_TOP_TO_DOWN);
        }

        @Override
        public void onBluetoothNotEnabled() {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, BT_REQUEST);
        }

        @Override
        public void onConnecting(Device device) {
            Dialoghelper.loadingSnackbar(viewGroup, "正连接:" + device.getName(), Dialoghelper.APPEAR_FROM_TOP_TO_DOWN);
        }

        @Override
        public void onConnected(Device device) {
            Dialoghelper.successSnackbar(viewGroup, "已连接:" + device.getName(), Dialoghelper.APPEAR_FROM_TOP_TO_DOWN);
        }

        @Override
        public void onDisconnected() {
            Dialoghelper.warningSnackbar(viewGroup, "蓝牙连接已断开", Dialoghelper.APPEAR_FROM_TOP_TO_DOWN);
        }

        @Override
        public void onConnectionFailed(Device device) {
            Dialoghelper.errorSnackbar(viewGroup, "蓝牙连接失败", Dialoghelper.APPEAR_FROM_TOP_TO_DOWN);

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
            Dialoghelper.errorSnackbar(viewGroup, "未找到蓝牙设备", Dialoghelper.APPEAR_FROM_TOP_TO_DOWN);
        }

        @Override
        public void onDevicesFound(final List<Device> deviceList, final SmoothBluetooth.ConnectionCallback connectionCallback) {
            List<String> listDeviceInfo = new ArrayList<>();

            for (int i = 0; i < deviceList.size(); i++) {
                listDeviceInfo.add(deviceList.get(i).getName() + "(" + deviceList.get(i).getAddress() + ")");
            }

            if (dialogList == null) {
                dialogList = Dialoghelper.dialogList(_mActivity, 0, R.string.dialog_select_bluetooth, listDeviceInfo, R.string.dialog_negativeText, 0, new Dialoghelper.ListCall() {
                    @Override
                    public void onSelection(Dialog dialog, View itemView, int which, CharSequence text) {
                        connectionCallback.connectTo(deviceList.get(which));
                        dialog.dismiss();
                    }
                });
            } else {
                dialogList.show();
            }

        }

        @Override
        public void onDataReceived(int data, String str) {
            KLog.e("data::" + data);
            KLog.e("str::" + str);
        }
    };

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
