package com.shtoone.chenjiang.mvp.view.main.measure;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.AudioPlayer;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.DialogHelper;
import com.shtoone.chenjiang.mvp.contract.measure.MeasureContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.measure.MeasureRightPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.MeasureRVPAdapter;
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
    @BindView(R.id.tv_connect_measure_right_fragment)
    TextView tvConnect;
    private List<String> listJidianBianhao;
    private MeasureRVPAdapter mAdapter;
    private YusheshuizhunxianData mYusheshuizhunxianData;
    private LinearLayoutManager mLinearLayoutManager;
    private Dialog progressDialog;
    private ViewGroup viewGroup;
    private AlertDialog.Builder deviceListBuilder;
    private int measureIndex = 1;


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
        return new MeasureRightPresenter(this);
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
    }

    private void initToolbar() {
        mPresenter.start();
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
                    case R.id.action_pingcha:

                        break;

                    case R.id.action_delete:
                        DialogHelper.dialog(_mActivity, R.drawable.ic_error_outline_red_400_48dp,
                                R.string.dialog_delete_title, R.string.dialog_delete_content, R.string.dialog_positiveText,
                                R.string.dialog_negativeText, new DialogHelper.Call() {
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
        DialogHelper.warningSnackbar(viewGroup, "数据初始化失败", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
    }


    @Override
    public void showLoading() {
    }

    @OnClick({R.id.bt_jidian_measure_right_fragment, R.id.bt_zhuandian_measure_right_fragment,
            R.id.bt_cedian_measure_right_fragment, R.id.bt_chongce_measure_right_fragment,
            R.id.bt_fance_measure_right_fragment, R.id.fab, R.id.tv_connect_measure_right_fragment})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_connect_measure_right_fragment:
                if (tvConnect.getText().toString().equals("连接")) {
                    mPresenter.connectPaired(_mActivity);
                } else {
                    mPresenter.disconnect();
                    tvConnect.setText("连接");
                }
                break;

            case R.id.bt_jidian_measure_right_fragment:
                DialogHelper.dialogList(_mActivity, 0, R.string.dialog_select_jidian, listJidianBianhao, R.string.dialog_negativeText, 0, new DialogHelper.ListCall() {
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
//                mAdapter.measure(mRecyclerView.getCurrentPosition(), 2);

                break;
            case R.id.fab:
                break;
        }
    }

    @Override
    public void responseJidianData(List<String> listJidianBianhao) {
        this.listJidianBianhao = listJidianBianhao;
        //应该在这里初始化Dialog，不应该在点击那里写Dialog代码
    }

    @Override
    public void responseCezhanData(List<CezhanData> listCezhan) {
        mAdapter.setNewData(listCezhan);
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
        tvConnect.setText("断开");
    }

    @Override
    public void onDisconnected() {
        DialogHelper.errorSnackbar(viewGroup, "蓝牙连接已断开", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
        tvConnect.setText("连接");
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
        KLog.e("str::###############################################################" + str);

        mAdapter.measure(mRecyclerView.getCurrentPosition(), measureIndex, str);

        //调试用的，或者发送指令
        mPresenter.sendData((measureIndex + "\n").getBytes());
        measureIndex = measureIndex + 1;

        KLog.e("onDataReceived：：" + measureIndex);

        if (measureIndex >= 5) {
            measureIndex = 1;
            //此处还要判断是往测还是反测。
            KLog.e("onDataReceived滚动到下一页");


            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.smoothScrollToPosition(mRecyclerView.getCurrentPosition() + 1);
                }
            }, 500);
        }
    }

    @Override
    public void onDestroy() {
        AudioPlayer.onDestroy();
        DialogHelper.warningSnackbar(viewGroup, "蓝牙连接已断开", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
        super.onDestroy();
    }

    @Override
    public boolean onBackPressedSupport() {
        new AlertDialog.Builder(_mActivity)
                .setIcon(R.drawable.ic_error_outline_red_400_48dp)
                .setTitle(R.string.dialog_title_exit)
                .setMessage(R.string.dialog_content_exit)
                .setNegativeButton(R.string.dialog_positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getParentFragment() instanceof MeasureFragment) {
                            ((MeasureFragment) getParentFragment()).finish();
                        }
                    }
                })
                .setPositiveButton(R.string.dialog_negativeText, null)
                .show();

        return true;
    }
}
