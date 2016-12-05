package com.shtoone.chenjiang.mvp.view.main;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.Dialoghelper;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.DownloadContract;
import com.shtoone.chenjiang.mvp.model.entity.db.DuanmianData;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.presenter.DownloadPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.NetworkUtils;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DownloadFragment extends BaseFragment<DownloadContract.Presenter> implements DownloadContract.View {

    private static final String TAG = DownloadFragment.class.getSimpleName();
    @BindView(R.id.tv_date_download_fragment)
    TickerView tvDate;
    @BindView(R.id.bt_download_all_download_fragment)
    TextView tvDownloadAll;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.progress_bar_download_fragment)
    NumberProgressBar progressBar;
    @BindView(R.id.tv_gongdian_message_download_fragment)
    TextView tvGongdianMessage;
    @BindView(R.id.tv_gongdian_download_download_fragment)
    TextView tvGongdianDownload;
    @BindView(R.id.tv_duanmian_message_download_fragment)
    TextView tvDuanmianMessage;
    @BindView(R.id.tv_duanmian_download_download_fragment)
    TextView tvDuanmianDownload;
    @BindView(R.id.tv_cedian_message_download_fragment)
    TextView tvCedianMessage;
    @BindView(R.id.tv_cedian_download_download_fragment)
    TextView tvCedianDownload;
    @BindView(R.id.tv_yusheshuizhunxian_message_download_fragment)
    TextView tvYusheshuizhunxianMessage;
    @BindView(R.id.tv_yusheshuizhunxian_download_download_fragment)
    TextView tvYusheshuizhunxianDownload;
    @BindView(R.id.tv_jidian_message_download_fragment)
    TextView tvJidianMessage;
    @BindView(R.id.tv_jidian_download_download_fragment)
    TextView tvJidianDownload;
    @BindView(R.id.tv_Staff_message_download_fragment)
    TextView tvStaffMessage;
    @BindView(R.id.tv_staff_download_download_fragment)
    TextView tvStaffDownload;
    private int intFailedSum;
    private String message;
    private String progress;
    private TextView messageTextView;
    private TextView downloadTextView;
    private boolean isDownloadAll;
    private View[] arrayTextView = new View[7];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        ((MainActivity) _mActivity).closeDrawer();
        ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(appBar);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //开启抽屉菜单，使其可以滑动弹出。
        ((MainActivity) _mActivity).openDrawer();
    }

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @NonNull
    @Override
    protected DownloadContract.Presenter createPresenter() {
        return new DownloadPresenter(this);
    }

    private void initData() {
        initToolbarBackNavigation(toolbar);
        Typeface tf = Typeface.createFromAsset(_mActivity.getAssets(), "fonts/OpenSans-Light.ttf");
        tvDate.setTypeface(tf);
        tvDate.setCharacterList(TickerUtils.getDefaultNumberList());

        arrayTextView[0] = tvGongdianDownload;
        arrayTextView[1] = tvDuanmianDownload;
        arrayTextView[2] = tvCedianDownload;
        arrayTextView[3] = tvYusheshuizhunxianDownload;
        arrayTextView[4] = tvJidianDownload;
        arrayTextView[5] = tvStaffDownload;
        arrayTextView[6] = tvDownloadAll;

        String strUpdateTime = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.UPDATA_TIME, "");

        if (TextUtils.isEmpty(strUpdateTime)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            strUpdateTime = df.format(new Date());
            tvDate.setText(strUpdateTime);
            SharedPreferencesUtils.put(BaseApplication.mContext, Constants.UPDATA_TIME, strUpdateTime);
        } else {
            tvDate.setText(strUpdateTime);
        }

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

                if (percentage >= 0.9f) {
                    toolbarLayout.setTitle("更新数据");

                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {
        if (messageTextView == null || downloadTextView == null) {
            return;
        }
        downloadTextView.setText("下载");
        downloadTextView.setBackgroundResource(R.drawable.rect_bg_stroke);
        messageTextView.setTextColor(Color.RED);
        if (t instanceof ConnectException) {
            messageTextView.setText("网络异常,请检测网络");
        } else if (t instanceof HttpException) {
            messageTextView.setText("服务器异常，请重新下载");
        } else if (t instanceof SocketTimeoutException) {
            messageTextView.setText("连接超时，请重新下载");
        } else if (t instanceof JSONException) {
            messageTextView.setText("解析异常，请重新下载");
        } else {
            messageTextView.setText("数据异常，请重新下载");
        }

        for (int i = 0; i < arrayTextView.length; i++) {
            arrayTextView[i].setEnabled(true);
        }
        if (isDownloadAll) {
            tvDownloadAll.setBackgroundResource(R.drawable.rect_bg_down_all);
            tvDownloadAll.setText("全部下载");
            progressBar.setVisibility(View.GONE);
        }
        isDownloadAll = false;
    }


    @Override
    public void showLoading() {
    }

    @OnClick({R.id.bt_download_all_download_fragment, R.id.tv_gongdian_download_download_fragment,
            R.id.tv_duanmian_download_download_fragment, R.id.tv_cedian_download_download_fragment,
            R.id.tv_yusheshuizhunxian_download_download_fragment, R.id.tv_jidian_download_download_fragment,
            R.id.tv_staff_download_download_fragment})
    public void onClick(final View view) {
        if (BaseApplication.isShowDialog && NetworkUtils.isWifiConnected(BaseApplication.mContext)) {
            BaseApplication.isShowDialog = false;
            Dialoghelper.showDialog(_mActivity, R.drawable.ic_error_outline_red_400_48dp,
                    R.string.dialog_title, R.string.dialog_content, R.string.dialog_positiveText,
                    R.string.dialog_negativeText, new Dialoghelper.Call() {
                        @Override
                        public void onNegative() {

                        }

                        @Override
                        public void onPositive() {
                            onClickDownload(view);
                        }

                    });
        } else {
            onClickDownload(view);
        }
    }

    private void onClickDownload(View view) {

        //因为下载是只能单个下载，所以这个失败统计只要每次调用就行。
        intFailedSum = 0;
        KLog.e("onClick::intFailedSum::" + intFailedSum);
        for (int i = 0; i < arrayTextView.length; i++) {
            arrayTextView[i].setEnabled(false);
        }

        //更改时间
        DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        String strUpdateTime = df.format(new Date());
        tvDate.setText(strUpdateTime);
        SharedPreferencesUtils.put(BaseApplication.mContext, Constants.UPDATA_TIME, strUpdateTime);//保存到sp中

        switch (view.getId()) {
            case R.id.bt_download_all_download_fragment:
                downloadAll();
                break;
            case R.id.tv_gongdian_download_download_fragment:
                downloadGongdian();
                break;

            case R.id.tv_duanmian_download_download_fragment:
                downloadDuanmian();
                break;

            case R.id.tv_cedian_download_download_fragment:
                downloadCedian();
                break;

            case R.id.tv_yusheshuizhunxian_download_download_fragment:
                downloadYusheshuizhunxian();
                break;

            case R.id.tv_jidian_download_download_fragment:
                downloadJidian();
                break;

            case R.id.tv_staff_download_download_fragment:
                downloadStaff();
                break;
        }
    }

    private void downloadAll() {
        KLog.e("点击开始下载所有………………");

        isDownloadAll = true;
        tvDownloadAll.setText("正在下载…");
        tvDownloadAll.setBackgroundResource(R.drawable.rect_bg_downing_all);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(1);
        downloadGongdian();
        KLog.e("点击下载所有末尾………………");
    }

    private void downloadGongdian() {
        KLog.e("点击进入下载工点………………");
        if (BaseApplication.mUserInfoBean == null || TextUtils.isEmpty(BaseApplication.mUserInfoBean.getUserId())) {
            tvGongdianMessage.setTextColor(Color.RED);
            tvGongdianMessage.setText("缺少参数：userid");
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
            if (isDownloadAll) {
                isDownloadAll = false;
                tvDownloadAll.setBackgroundResource(R.drawable.rect_bg_down_all);
                tvDownloadAll.setText("全部下载");
                progressBar.setVisibility(View.GONE);
            }
            return;
        }
        tvGongdianDownload.setText("0%");
        tvGongdianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
        tvGongdianMessage.setText("开始下载……");
        tvGongdianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
        //这行代码必须在最底部。
        messageTextView = tvGongdianMessage;
        downloadTextView = tvGongdianDownload;
        mPresenter.downloadGongdian(BaseApplication.mUserInfoBean.getUserId());
        KLog.e("点击进入下载工点末尾………………");
    }

    private void downloadDuanmian() {
        KLog.e("点击开始下载断面……………");

        List<GongdianData> mGongdianData = DataSupport.findAll(GongdianData.class);
        if (mGongdianData.size() <= 0) {
            tvDuanmianMessage.setTextColor(Color.RED);
            tvDuanmianMessage.setText("缺少参数：工点ID，请先下载工点信息");
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
            if (isDownloadAll) {
                isDownloadAll = false;
                tvDownloadAll.setBackgroundResource(R.drawable.rect_bg_down_all);
                tvDownloadAll.setText("全部下载");
                progressBar.setVisibility(View.GONE);
            }
            return;
        }
        tvDuanmianDownload.setText("0%");
        tvDuanmianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
        tvDuanmianMessage.setText("开始下载……");
        tvDuanmianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
        //这行代码必须在最底部。
        messageTextView = tvDuanmianMessage;
        downloadTextView = tvDuanmianDownload;
        mPresenter.downloadDuanmian();
        KLog.e("点断面……………末尾");
    }

    private void downloadCedian() {
        KLog.e("点击测点下载开始………………………………");
        List<DuanmianData> mDuanmianData = DataSupport.findAll(DuanmianData.class);
        if (mDuanmianData.size() <= 0) {
            tvCedianMessage.setTextColor(Color.RED);
            tvCedianMessage.setText("缺少参数：断面ID，请先下载断面信息");
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
            if (isDownloadAll) {
                isDownloadAll = false;
                tvDownloadAll.setBackgroundResource(R.drawable.rect_bg_down_all);
                tvDownloadAll.setText("全部下载");
                progressBar.setVisibility(View.GONE);
            }
            return;
        }
        tvCedianDownload.setText("0%");
        tvCedianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
        tvCedianMessage.setText("开始下载……");
        tvCedianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
        //这行代码必须在最底部。
        messageTextView = tvCedianMessage;
        downloadTextView = tvCedianDownload;
        mPresenter.downloadCedian();
        KLog.e("点测点末尾………………");
    }

    private void downloadYusheshuizhunxian() {
        KLog.e("点击预设水准线开始………………………………");
        if (BaseApplication.mUserInfoBean == null || TextUtils.isEmpty(BaseApplication.mUserInfoBean.getDept().getOrgId())) {
            tvYusheshuizhunxianMessage.setTextColor(Color.RED);
            tvYusheshuizhunxianMessage.setText("缺少参数：部门ID");
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
            if (isDownloadAll) {
                isDownloadAll = false;
                tvDownloadAll.setBackgroundResource(R.drawable.rect_bg_down_all);
                tvDownloadAll.setText("全部下载");
                progressBar.setVisibility(View.GONE);
            }
            return;
        }
        tvYusheshuizhunxianDownload.setText("0%");
        tvYusheshuizhunxianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
        tvYusheshuizhunxianMessage.setText("开始下载……");
        tvYusheshuizhunxianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
        //这行代码必须在最底部。
        messageTextView = tvYusheshuizhunxianMessage;
        downloadTextView = tvYusheshuizhunxianDownload;
        mPresenter.downloadYusheshuizhunxian(BaseApplication.mUserInfoBean.getDept().getOrgId());
        KLog.e("点预设水准线…………末尾……");
    }

    private void downloadJidian() {
        KLog.e("点基点………开始………");
        tvJidianDownload.setText("0%");
        tvJidianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
        tvJidianMessage.setText("开始下载……");
        tvJidianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
        //这行代码必须在最底部。
        messageTextView = tvJidianMessage;
        downloadTextView = tvJidianDownload;
        mPresenter.downloadJidian();
        KLog.e("点基点………末尾………");
    }

    private void downloadStaff() {
        KLog.e("点人员…开始……………");
        tvStaffDownload.setText("0%");
        tvStaffDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
        tvStaffMessage.setText("开始下载……");
        tvStaffMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
        //这行代码必须在最底部。
        messageTextView = tvStaffMessage;
        downloadTextView = tvStaffDownload;
        mPresenter.downloadStaff();
        KLog.e("点人员…末尾……………");
    }

    @Override
    public void setGongdianMessage(Boolean mBoolean, int intSum, int intIndex) {
        KLog.e("intIndex::" + intIndex);
        if (!mBoolean) {
            intFailedSum += 1;
            tvGongdianMessage.setTextColor(Color.RED);
            message = String.format("%d号工点数据下载失败", intIndex);
        } else {
            tvGongdianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            message = String.format("%d号工点数据下载成功", intIndex);
        }
        tvGongdianMessage.setText(message);
        progress = (int) (((1.0 / intSum) * 100) * intIndex) + "%";
        tvGongdianDownload.setText(progress);
        if (isDownloadAll) {
            progressBar.setProgress((int) (((1.0 / intSum) * 100) * intIndex));
        }
        KLog.e(message);
    }

    @Override
    public void onGongdianCompleted() {
        KLog.e("onGongdianCompleted::intFailedSum::" + intFailedSum);
        if (intFailedSum > 0) {
            tvGongdianMessage.setTextColor(Color.RED);
            message = String.format("%d个工点数据下载失败", intFailedSum);
            tvGongdianMessage.setText(message);
        } else {
            tvGongdianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            tvGongdianMessage.setText("恭喜！下载完成。");
        }
        tvGongdianDownload.setText("下载");
        tvGongdianDownload.setBackgroundResource(R.drawable.rect_bg_stroke);
        KLog.e("111111111111");

        if (isDownloadAll) {
            downloadDuanmian();
            KLog.e("222222222222");

        } else {
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
        }
    }

    @Override
    public void setDuanmianMessage(Boolean mBoolean, int intSum, int intIndex) {
        KLog.e("intIndex::" + intIndex);
        if (!mBoolean) {
            intFailedSum += 1;
            tvDuanmianMessage.setTextColor(Color.RED);
            message = String.format("%d号断面数据下载失败", intIndex);
        } else {
            tvDuanmianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            message = String.format("%d号断面数据下载成功", intIndex);
        }
        tvDuanmianMessage.setText(message);
        progress = (int) (((1.0 / intSum) * 100) * intIndex) + "%";
        tvDuanmianDownload.setText(progress);
        if (isDownloadAll) {
            progressBar.setProgress((int) (((1.0 / intSum) * 100) * intIndex));
        }
        KLog.e(message);
    }

    @Override
    public void onDuanmianCompleted() {
        KLog.e("intFailedSum::" + intFailedSum);
        if (intFailedSum > 0) {
            tvDuanmianMessage.setTextColor(Color.RED);
            message = String.format("%d个断面数据下载失败", intFailedSum);
            tvDuanmianMessage.setText(message);
        } else {
            tvDuanmianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            tvDuanmianMessage.setText("恭喜！下载完成。");
        }
        tvDuanmianDownload.setText("下载");
        tvDuanmianDownload.setBackgroundResource(R.drawable.rect_bg_stroke);
        if (isDownloadAll) {
            downloadCedian();
        } else {
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
        }
    }

    @Override
    public void setCedianMessage(Boolean mBoolean, int intSum, int intIndex) {
        KLog.e("intIndex::" + intIndex);
        if (!mBoolean) {
            intFailedSum += 1;
            tvCedianMessage.setTextColor(Color.RED);
            message = String.format("%d号测点数据下载失败", intIndex);
        } else {
            tvCedianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            message = String.format("%d号测点数据下载成功", intIndex);
        }
        tvCedianMessage.setText(message);
        progress = (int) (((1.0 / intSum) * 100) * intIndex) + "%";
        tvCedianDownload.setText(progress);
        if (isDownloadAll) {
            progressBar.setProgress((int) (((1.0 / intSum) * 100) * intIndex));
        }
        KLog.e(message);
    }

    @Override
    public void onCedianCompleted() {
        KLog.e("intFailedSum::" + intFailedSum);
        if (intFailedSum > 0) {
            tvCedianMessage.setTextColor(Color.RED);
            message = String.format("%d个测点数据下载失败", intFailedSum);
            tvCedianMessage.setText(message);
        } else {
            tvCedianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            tvCedianMessage.setText("恭喜！下载完成。");
        }
        tvCedianDownload.setText("下载");
        tvCedianDownload.setBackgroundResource(R.drawable.rect_bg_stroke);
        if (isDownloadAll) {
            downloadYusheshuizhunxian();
        } else {
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
        }
    }

    @Override
    public void setYusheshuizhunxianMessage(Boolean mBoolean, int intSum, int intIndex) {
        KLog.e("intIndex::" + intIndex);
        if (!mBoolean) {
            intFailedSum += 1;
            tvYusheshuizhunxianMessage.setTextColor(Color.RED);
            message = String.format("%d号预设水准线数据下载失败", intIndex);
        } else {
            tvYusheshuizhunxianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            message = String.format("%d号预设水准线数据下载成功", intIndex);
        }
        tvYusheshuizhunxianMessage.setText(message);
        progress = (int) (((1.0 / intSum) * 100) * intIndex) + "%";
        tvYusheshuizhunxianDownload.setText(progress);
        if (isDownloadAll) {
            progressBar.setProgress((int) (((1.0 / intSum) * 100) * intIndex));
        }
        KLog.e(message);
    }

    @Override
    public void onYusheshuizhunxianCompleted() {
        KLog.e("intFailedSum::" + intFailedSum);
        if (intFailedSum > 0) {
            tvYusheshuizhunxianMessage.setTextColor(Color.RED);
            message = String.format("%d个预设水准线数据下载失败", intFailedSum);
            tvYusheshuizhunxianMessage.setText(message);
        } else {
            tvYusheshuizhunxianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            tvYusheshuizhunxianMessage.setText("恭喜！下载完成。");
        }
        tvYusheshuizhunxianDownload.setText("下载");
        tvYusheshuizhunxianDownload.setBackgroundResource(R.drawable.rect_bg_stroke);
        if (isDownloadAll) {
            downloadJidian();
        } else {
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
        }

        EventBus.getDefault().post(new EventData(Constants.EVENT_REFRESH));
    }

    @Override
    public void setJidianMessage(Boolean mBoolean, int intSum, int intIndex) {
        KLog.e("intIndex::" + intIndex);
        if (!mBoolean) {
            intFailedSum += 1;
            tvJidianMessage.setTextColor(Color.RED);
            message = String.format("%d号工点数据下载失败", intIndex);
        } else {
            tvJidianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            message = String.format("%d号工点数据下载成功", intIndex);
        }
        tvJidianMessage.setText(message);
        progress = (int) (((1.0 / intSum) * 100) * intIndex) + "%";
        tvJidianDownload.setText(progress);
        if (isDownloadAll) {
            progressBar.setProgress((int) (((1.0 / intSum) * 100) * intIndex));
        }
        KLog.e(message);
    }

    @Override
    public void onJidianCompleted() {
        KLog.e("intFailedSum::" + intFailedSum);
        if (intFailedSum > 0) {
            tvJidianMessage.setTextColor(Color.RED);
            message = String.format("%d个工点数据下载失败", intFailedSum);
            tvJidianMessage.setText(message);
        } else {
            tvJidianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            tvJidianMessage.setText("恭喜！下载完成。");
        }
        tvJidianDownload.setText("下载");
        tvJidianDownload.setBackgroundResource(R.drawable.rect_bg_stroke);
        if (isDownloadAll) {
            downloadStaff();
        } else {
            for (int i = 0; i < arrayTextView.length; i++) {
                arrayTextView[i].setEnabled(true);
            }
        }
    }

    @Override
    public void setStaffMessage(Boolean mBoolean, int intSum, int intIndex) {
        KLog.e("intIndex::" + intIndex);
        if (!mBoolean) {
            intFailedSum += 1;
            tvStaffMessage.setTextColor(Color.RED);
            message = String.format("%d号工点数据下载失败", intIndex);
        } else {
            tvStaffMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            message = String.format("%d号工点数据下载成功", intIndex);
        }
        tvStaffMessage.setText(message);
        progress = (int) (((1.0 / intSum) * 100) * intIndex) + "%";
        tvStaffDownload.setText(progress);
        if (isDownloadAll) {
            progressBar.setProgress((int) (((1.0 / intSum) * 100) * intIndex));
        }
        KLog.e(message);
    }

    @Override
    public void onStaffCompleted() {
        KLog.e("intFailedSum::" + intFailedSum);
        if (intFailedSum > 0) {
            tvStaffMessage.setTextColor(Color.RED);
            message = String.format("%d个工点数据下载失败", intFailedSum);
            tvStaffMessage.setText(message);
        } else {
            tvStaffMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
            tvStaffMessage.setText("恭喜！下载完成。");
        }
        tvStaffDownload.setText("下载");
        tvStaffDownload.setBackgroundResource(R.drawable.rect_bg_stroke);
        if (isDownloadAll) {
            isDownloadAll = false;
            tvDownloadAll.setBackgroundResource(R.drawable.rect_bg_down_all);
            tvDownloadAll.setText("全部下载");
            progressBar.setVisibility(View.GONE);
        }
        for (int i = 0; i < arrayTextView.length; i++) {
            arrayTextView[i].setEnabled(true);
        }
    }
}
