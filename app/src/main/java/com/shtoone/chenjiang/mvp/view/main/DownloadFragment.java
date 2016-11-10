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
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.DownloadContract;
import com.shtoone.chenjiang.mvp.model.bean.DuanmianData;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.presenter.DownloadPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.socks.library.KLog;

import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
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
    Button btDownloadAll;
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
        tvDate.setText("08-08 04:65");

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
        downloadTextView.setEnabled(true);
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
    }


    @Override
    public void showLoading() {
    }

    @OnClick({R.id.bt_download_all_download_fragment, R.id.tv_gongdian_download_download_fragment,
            R.id.tv_duanmian_download_download_fragment, R.id.tv_cedian_download_download_fragment,
            R.id.tv_yusheshuizhunxian_download_download_fragment, R.id.tv_jidian_download_download_fragment,
            R.id.tv_staff_download_download_fragment})
    public void onClick(View view) {
        //因为下载是只能单个下载，所以这个失败统计只要每次调用就行。
        intFailedSum = 0;
        KLog.e("intFailedSum::" + intFailedSum);
        switch (view.getId()) {
            case R.id.bt_download_all_download_fragment:
                tvDate.setText("01-01 01:01");

                tvDate.setEnabled(false);
                break;
            case R.id.tv_gongdian_download_download_fragment:
                if (BaseApplication.mUserInfoBean == null || TextUtils.isEmpty(BaseApplication.mUserInfoBean.getUserId())) {
                    tvGongdianMessage.setTextColor(Color.RED);
                    tvGongdianMessage.setText("缺少参数：userid");
                    return;
                }
                tvGongdianDownload.setEnabled(false);
                tvGongdianDownload.setText("0%");
                tvGongdianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
                tvGongdianMessage.setText("开始下载……");
                tvGongdianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
                //这行代码必须在最底部。
                mPresenter.downloadGongdian(BaseApplication.mUserInfoBean.getUserId());
                messageTextView = tvGongdianMessage;
                downloadTextView = tvGongdianDownload;
                KLog.e("点工点………………");
                break;

            case R.id.tv_duanmian_download_download_fragment:
                List<GongdianData> mGongdianData = DataSupport.findAll(GongdianData.class);
                if (mGongdianData.size() <= 0) {
                    tvDuanmianMessage.setTextColor(Color.RED);
                    tvDuanmianMessage.setText("缺少参数：工点ID，请先下载工点信息");
                    return;
                }
                tvDuanmianDownload.setEnabled(false);
                tvDuanmianDownload.setText("0%");
                tvDuanmianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
                tvDuanmianMessage.setText("开始下载……");
                tvDuanmianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
                //这行代码必须在最底部。
                mPresenter.downloadDuanmian();
                messageTextView = tvDuanmianMessage;
                downloadTextView = tvDuanmianDownload;
                KLog.e("点断面……………");
                break;

            case R.id.tv_cedian_download_download_fragment:
                KLog.e("点击测点………………………………");
                List<DuanmianData> mDuanmianData = DataSupport.findAll(DuanmianData.class);
                if (mDuanmianData.size() <= 0) {
                    tvCedianMessage.setTextColor(Color.RED);
                    tvCedianMessage.setText("缺少参数：断面ID，请先下载断面信息");
                    return;
                }
                tvCedianDownload.setEnabled(false);
                tvCedianDownload.setText("0%");
                tvCedianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
                tvCedianMessage.setText("开始下载……");
                tvCedianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
                //这行代码必须在最底部。
                mPresenter.downloadCedian();
                messageTextView = tvCedianMessage;
                downloadTextView = tvCedianDownload;
                KLog.e("点测点………………");
                break;

            case R.id.tv_yusheshuizhunxian_download_download_fragment:
                KLog.e("点击预设水准线………………………………");
                if (BaseApplication.mUserInfoBean == null || TextUtils.isEmpty(BaseApplication.mUserInfoBean.getDept().getOrgId())) {
                    tvYusheshuizhunxianMessage.setTextColor(Color.RED);
                    tvYusheshuizhunxianMessage.setText("缺少参数：部门ID");
                    return;
                }
                tvYusheshuizhunxianDownload.setEnabled(false);
                tvYusheshuizhunxianDownload.setText("0%");
                tvYusheshuizhunxianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
                tvYusheshuizhunxianMessage.setText("开始下载……");
                tvYusheshuizhunxianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
                //这行代码必须在最底部。
                mPresenter.downloadYusheshuizhunxian(BaseApplication.mUserInfoBean.getDept().getOrgId());
                messageTextView = tvYusheshuizhunxianMessage;
                downloadTextView = tvYusheshuizhunxianDownload;
                KLog.e("点预设水准线………………");
                break;

            case R.id.tv_jidian_download_download_fragment:
                tvJidianDownload.setEnabled(false);
                tvJidianDownload.setText("0%");
                tvJidianDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
                tvJidianMessage.setText("开始下载……");
                tvJidianMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
                //这行代码必须在最底部。
                mPresenter.downloadJidian();
                messageTextView = tvJidianMessage;
                downloadTextView = tvJidianDownload;
                KLog.e("点基点………………");
                break;

            case R.id.tv_staff_download_download_fragment:

                tvStaffDownload.setEnabled(false);
                tvStaffDownload.setText("0%");
                tvStaffDownload.setBackgroundResource(R.drawable.oval_bg_with_stroke_only);
                tvStaffMessage.setText("开始下载……");
                tvStaffMessage.setTextColor(_mActivity.getResources().getColor(R.color.base_color));
                //这行代码必须在最底部。
                mPresenter.downloadStaff();
                messageTextView = tvStaffMessage;
                downloadTextView = tvStaffDownload;
                KLog.e("点人员………………");
                break;
        }
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
        KLog.e(message);
    }

    @Override
    public void onGongdianCompleted() {
        KLog.e("intFailedSum::" + intFailedSum);
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
        tvGongdianDownload.setEnabled(true);
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
        tvDuanmianDownload.setEnabled(true);
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
        tvCedianDownload.setEnabled(true);
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
        tvYusheshuizhunxianDownload.setEnabled(true);
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
        tvJidianDownload.setEnabled(true);
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
        tvStaffDownload.setEnabled(true);
    }
}
