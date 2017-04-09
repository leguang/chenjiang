package com.shtoone.chenjiang.mvp.view.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.utils.DisplayUtils;
import com.shtoone.chenjiang.utils.NetworkUtils;
import com.shtoone.chenjiang.utils.ScreenUtils;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.socks.library.KLog;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public abstract class BaseFragment<P extends BaseContract.Presenter> extends SwipeBackFragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    public P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();

    }

    @NonNull
    protected abstract P createPresenter();

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void setPresenter(@NonNull P presenter) {
        this.mPresenter = presenter;
    }

    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();

        //监听Fragment的内存泄露
        BaseApplication.mRefWatcher.watch(this);
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
                start();
                frame.refreshComplete();
            }
        });
    }

    public void start() {
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    public boolean isCanDoRefresh() {
        return true;
    }

    public void initStateBar(View view) {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + ScreenUtils.getStatusBarHeight(BaseApplication.mContext),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    public void initPageStateLayout(final PageStateLayout mPageStateLayout) {
        if (null == mPageStateLayout) return;

        mPageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.start();
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

}
