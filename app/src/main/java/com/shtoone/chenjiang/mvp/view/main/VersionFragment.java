package com.shtoone.chenjiang.mvp.view.main;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.VersionContract;
import com.shtoone.chenjiang.mvp.model.bean.CheckUpdateBean;
import com.shtoone.chenjiang.mvp.presenter.VersionPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.AppUtils;
import com.shtoone.chenjiang.utils.NetworkUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class VersionFragment extends BaseFragment<VersionContract.Presenter> implements VersionContract.View {

    private static final String TAG = VersionFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_version_version_fragment)
    TextView tvVersion;
    @BindView(R.id.bt_update_version_fragment)
    CircularProgressButton btUpdate;

    public static VersionFragment newInstance() {
        return new VersionFragment();
    }

    @NonNull
    @Override
    protected VersionContract.Presenter createPresenter() {
        return new VersionPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_version, container, false);
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

    private void initData() {
        initToolbarBackNavigation(toolbar);
        toolbar.setTitle("版本更新");
        tvVersion.setText(AppUtils.getVersionName(_mActivity));
    }


    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {
        if (t instanceof ConnectException) {
            setErrorMessage("网络异常,点击进入设置");
            btUpdate.setOnClickListener(new go2SettingOnClickListener());
        } else if (t instanceof HttpException) {
            setErrorMessage("服务器异常");
        } else if (t instanceof SocketTimeoutException) {
            setErrorMessage("连接超时");
        } else if (t instanceof JSONException) {
            setErrorMessage("解析异常");
        } else {
            setErrorMessage("数据异常");
        }
    }


    @Override
    public void showLoading() {
        btUpdate.setIndeterminateProgressMode(true);
        btUpdate.setProgress(0);
        btUpdate.setProgress(50);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //开启抽屉菜单，使其可以滑动弹出。
        ((MainActivity) _mActivity).openDrawer();
    }

    @OnClick(R.id.bt_update_version_fragment)
    public void onClick() {
        mPresenter.checkUpdate();
    }


    @Override
    public void setErrorMessage(String message) {
        btUpdate.setErrorText(message);
        btUpdate.setProgress(-1);
    }

    /**
     * 强制更新,checkupdatelibrary中提供的默认强制更新Dialog,您完全可以自定义自己的Dialog,
     */
    @Override
    public void showForceUpdateDialog(final CheckUpdateBean.UpdateInfoBean mUpdateInfoBean) {
        btUpdate.setCompleteText("检测到新版本");
        btUpdate.setProgress(100);

        btUpdate.postDelayed(new Runnable() {
            @Override
            public void run() {
                ForceUpdateDialog dialog = new ForceUpdateDialog(_mActivity);
                dialog.setAppSize(mUpdateInfoBean.getNewAppSize())
                        .setDownloadUrl(mUpdateInfoBean.getNewAppUrl())
                        .setTitle(mUpdateInfoBean.getAppName() + "有更新啦")
                        .setReleaseTime(mUpdateInfoBean.getNewAppReleaseTime())
                        .setVersionName(mUpdateInfoBean.getNewAppVersionName())
                        .setUpdateDesc(mUpdateInfoBean.getNewAppUpdateDesc())
                        .setFileName("这是QQ.apk")
                        .setFilePath(Constants.PATH_APK_CACHE).show();
            }
        }, 500);

    }

    /**
     * 非强制更新,checkupdatelibrary中提供的默认非强制更新Dialog,您完全可以自定义自己的Dialog
     */
    @Override
    public void showUpdateDialog(final CheckUpdateBean.UpdateInfoBean mUpdateInfoBean) {
        btUpdate.setCompleteText("检测到新版本");
        btUpdate.setProgress(100);
        btUpdate.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateDialog dialog = new UpdateDialog(_mActivity);
                dialog.setAppSize(mUpdateInfoBean.getNewAppSize())
                        .setDownloadUrl(mUpdateInfoBean.getNewAppUrl())
                        .setTitle(mUpdateInfoBean.getAppName() + "有更新啦")
                        .setReleaseTime(mUpdateInfoBean.getNewAppReleaseTime())
                        .setVersionName(mUpdateInfoBean.getNewAppVersionName())
                        .setUpdateDesc(mUpdateInfoBean.getNewAppUpdateDesc())
                        .setFileName("这是QQ.apk")
                        .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib")
                        //该方法需设为true,才会在通知栏显示下载进度,默认为false,即不显示
                        //该方法只会控制下载进度的展示,当下载完成或下载失败时展示的通知不受该方法影响
                        //即不管该方法是置为false还是true,当下载完成或下载失败时都会在通知栏展示一个通知
                        .setShowProgress(true)
                        .setIconResId(R.mipmap.ic_launcher)
                        .setAppName(mUpdateInfoBean.getAppName()).show();

            }
        }, 500);
    }

    public class CheckUpdateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mPresenter.checkUpdate();
        }
    }

    public class go2SettingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!NetworkUtils.isConnected(_mActivity)) {
                NetworkUtils.openSetting(_mActivity);
            }
            btUpdate.setProgress(0);
            btUpdate.setOnClickListener(new CheckUpdateOnClickListener());
        }
    }


}
