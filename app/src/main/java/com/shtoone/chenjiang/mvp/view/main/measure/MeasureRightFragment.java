package com.shtoone.chenjiang.mvp.view.main.measure;

import android.app.Dialog;
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
    private ArrayList<CezhanData> listCezhan;
    private YusheshuizhunxianData mYusheshuizhunxianData;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initRecyclerViewPager(view);

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
                                        for (CezhanData cezhanData : listCezhan) {
                                            KLog.e(cezhanData.getNumber());
                                        }
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

    protected void initRecyclerViewPager(View view) {
        mRecyclerView = (RecyclerViewPager) view.findViewById(R.id.viewpager);

        //数字越大，触发滚向下一页所需偏移就越大
        mRecyclerView.setTriggerOffset(0.1f);
        //数字越大，就越容易滚动
        mRecyclerView.setFlingFactor(0.0f);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new MeasureRVPAdapter());

        listCezhan = new ArrayList<CezhanData>();

        for (int i = 0; i < 10; i++) {
            listCezhan.add(new CezhanData());
        }

        mAdapter.setNewData(listCezhan);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {
        ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
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

                AudioPlayer.play(8);
                break;
            case R.id.bt_cedian_measure_right_fragment:
                AudioPlayer.play(7);

                break;
            case R.id.bt_chongce_measure_right_fragment:
                AudioPlayer.play(6);

                break;
            case R.id.bt_fance_measure_right_fragment:
                AudioPlayer.play(5);

                break;
            case R.id.fab:
                AudioPlayer.play(4);

                break;
        }
    }

    @Override
    public void responseJidianData(List<String> listJidianBianhao) {
        this.listJidianBianhao = listJidianBianhao;
    }

    @Override
    public void responseCezhanData(List<CezhanData> listCezhan) {

    }

    @Override
    public void onDestroy() {
        AudioPlayer.onDestroy();
        super.onDestroy();
    }
}
