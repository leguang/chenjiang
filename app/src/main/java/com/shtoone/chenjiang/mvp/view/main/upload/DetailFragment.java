package com.shtoone.chenjiang.mvp.view.main.upload;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;
import com.shtoone.chenjiang.mvp.view.main.measure.MeasureLeftFragment;
import com.shtoone.chenjiang.mvp.view.main.measure.MeasureRightFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DetailFragment extends BaseFragment {
    private static final String TAG = DetailFragment.class.getSimpleName();
    @BindView(R.id.sliding_pane_layout_measure_fragment)
    SlidingPaneLayout slidingPaneLayout;
    private ShuizhunxianData mShuizhunxianData;
    private DrawerArrowDrawable indicator;

    public static DetailFragment newInstance(ShuizhunxianData mShuizhunxianData) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.YUSHESHUIZHUNXIAN, mShuizhunxianData);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mShuizhunxianData = (ShuizhunxianData) args.getSerializable(Constants.YUSHESHUIZHUNXIAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        if (_mActivity instanceof MainActivity) {
            ((MainActivity) _mActivity).closeDrawer();
        }
        //禁止Fragment侧滑退出
        setSwipeBackEnable(false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_left_detail_fragment, DetailLeftFragment.newInstance(mShuizhunxianData));
            loadRootFragment(R.id.fl_right_detail_fragment, DetailRightFragment.newInstance(mShuizhunxianData));
        }
        initData();
    }

    private void initData() {
        indicator = new DrawerArrowDrawable(_mActivity);
        indicator.setColor(Color.WHITE);
        //设置滑动视差 可选
        slidingPaneLayout.setParallaxDistance(400);
        //菜单滑动的颜色渐变设置 可选
        slidingPaneLayout.setCoveredFadeColor(ContextCompat.getColor(BaseApplication.mContext, R.color.base_color));
        //主视图滑动的颜色渐变设置 可选
        slidingPaneLayout.setSliderFadeColor(0);

        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                indicator.setProgress(slideOffset);
            }

            @Override
            public void onPanelOpened(View panel) {
            }

            @Override
            public void onPanelClosed(View panel) {
            }
        });
    }

    public void initToolbartoggle(Toolbar mToolbar) {
        mToolbar.setNavigationIcon(indicator);
    }

    public void toggle() {
        if (slidingPaneLayout.isOpen()) {
            slidingPaneLayout.closePane();
        } else {
            slidingPaneLayout.openPane();
        }
    }


    public void startFragment(SupportFragment toFragment) {
        start(toFragment);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (slidingPaneLayout.isOpen()) {
            slidingPaneLayout.closePane();
        }
        return super.onBackPressedSupport();
    }


    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }
}
