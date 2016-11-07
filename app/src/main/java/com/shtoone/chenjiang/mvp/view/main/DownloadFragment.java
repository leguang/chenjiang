package com.shtoone.chenjiang.mvp.view.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.DownloadContract;
import com.shtoone.chenjiang.mvp.presenter.DownloadPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DownloadFragment extends BaseFragment<DownloadContract.Presenter> implements DownloadContract.View {

    private static final String TAG = DownloadFragment.class.getSimpleName();
    @BindView(R.id.tv_date_download_fragment)
    TextView tvDate;
    @BindView(R.id.bt_download_download_fragment)
    Button btDownload;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.progress_bar_download_fragment)
    NumberProgressBar progressBar;

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @NonNull
    @Override
    protected DownloadContract.Presenter createPresenter() {
        return new DownloadPresenter(this);
    }

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

    private void initData() {
        initToolbarBackNavigation(toolbar);
        Typeface tf = Typeface.createFromAsset(_mActivity.getAssets(), "fonts/OpenSans-Light.ttf");
        tvDate.setTypeface(tf);

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
        if (t instanceof ConnectException) {
        } else if (t instanceof HttpException) {
        } else if (t instanceof SocketTimeoutException) {
        } else if (t instanceof JSONException) {
        } else {
        }
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //开启抽屉菜单，使其可以滑动弹出。
        ((MainActivity) _mActivity).openDrawer();
    }

}
