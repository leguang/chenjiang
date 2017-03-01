package com.shtoone.chenjiang.mvp.view.main.about;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.about.AboutFragmentViewPagerContract;
import com.shtoone.chenjiang.mvp.presenter.about.AboutFragmentViewPagerPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.DisplayUtils;
import com.shtoone.chenjiang.utils.NetworkUtils;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.socks.library.KLog;


import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by Administrator on 2016/11/22.
 */
public class AboutFragmentViewPagerFragment extends BaseFragment<AboutFragmentViewPagerContract.Presenter> implements AboutFragmentViewPagerContract.View{

    private static final String TAG = AboutFragmentViewPagerFragment.class.getSimpleName();
    @BindView(R.id.psl_view_pager_about_fragment)
    PageStateLayout mPageStateLayout;
    @BindView(R.id.ptr_view_pager_about_fragment)
    PtrFrameLayout mPtrFrameLayout;
    @BindView(R.id.wv_view_pager_about_fragment)
    WebView mWebView;
    private int aboutWhat;
    private WebSettings mWebSettings;
    private String url = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            aboutWhat = args.getInt(Constants.ABOUTWHAT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_about_fragment, container, false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    public static AboutFragmentViewPagerFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(Constants.ABOUTWHAT, position);
        AboutFragmentViewPagerFragment fragment = new AboutFragmentViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void initData() {
        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
        initWebView();

        if (aboutWhat == 0) {
            url = Constants.ABOUTAPP;
        } else if (aboutWhat == 1) {
            url = Constants.ABOUTCOMPANY;
        }
        mWebView.loadUrl(url);
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        KLog.e("mWebView.getScrollY():" + mWebView.getScrollY());
        if (mWebView.getScrollY() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void refresh() {
        mWebView.reload();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    private void initWebView() {
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);// 表示支持js
        mWebSettings.setUseWideViewPort(true);// 支持双击缩放
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
        mWebSettings.setDomStorageEnabled(true);
        String cacheDirPath = _mActivity.getCacheDir().getAbsolutePath() + "/webViewCache ";
        mWebSettings.setDatabasePath(cacheDirPath);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCachePath(cacheDirPath);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);

        if (Build.VERSION.SDK_INT >= 19) {
            mWebSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebSettings.setLoadsImagesAutomatically(false);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mPageStateLayout.showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPageStateLayout.showContent();
                mWebSettings.setLoadsImagesAutomatically(true);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mPageStateLayout.showError();

            }

            /**
             * 所有跳转的链接都会在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


    @NonNull
    @Override
    protected AboutFragmentViewPagerContract.Presenter createPresenter() {
        return new AboutFragmentViewPagerPresenter(this);
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



    public void initPageStateLayout(final PageStateLayout mPageStateLayout) {
        if (null == mPageStateLayout) return;

        mPageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        mPageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageStateLayout.showEmpty();
                NetworkUtils.openSetting(_mActivity);
            }
        });
    }

    public void initPtrFrameLayout(final PtrFrameLayout mPtrFrameLayout) {
        if (null == mPtrFrameLayout) return;

        // 下拉刷新头部
        final StoreHouseHeader ptrHeader = new StoreHouseHeader(_mActivity);
        final String[] mStringList = {Constants.DOMAIN_1, Constants.DOMAIN_2};
        ptrHeader.setTextColor(Color.BLACK);
        ptrHeader.setPadding(0, DisplayUtils.dp2px(15), 0, 0);
        ptrHeader.initWithString(mStringList[0]);
        mPtrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
            private int mLoadTime = 0;

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                ptrHeader.initWithString(string);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                String string = mStringList[mLoadTime % mStringList.length];
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });
        mPtrFrameLayout.setHeaderView(ptrHeader);
        mPtrFrameLayout.addPtrUIHandler(ptrHeader);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 100);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return isCanDoRefresh();
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                refresh();
                frame.refreshComplete();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.e("destroy");
    }
}
