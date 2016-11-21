package com.shtoone.chenjiang.mvp.view.main.measure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.measure.MeasureContract;
import com.shtoone.chenjiang.mvp.presenter.measure.MeasurePresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasureFragment extends BaseFragment<MeasureContract.Presenter> implements MeasureContract.View {

    private static final String TAG = MeasureFragment.class.getSimpleName();
    @BindView(R.id.sliding_pane_layout_measure_fragment)
    SlidingPaneLayout slidingPaneLayout;

    public static MeasureFragment newInstance() {
        return new MeasureFragment();
    }

    @NonNull
    @Override
    protected MeasureContract.Presenter createPresenter() {
        return new MeasurePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure, container, false);
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        ((MainActivity) _mActivity).closeDrawer();
        //禁止Fragment侧滑退出
        setSwipeBackEnable(false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    private void initData() {
        //设置滑动视差 可选
        slidingPaneLayout.setParallaxDistance(400);
        //菜单滑动的颜色渐变设置 可选
        slidingPaneLayout.setCoveredFadeColor(ContextCompat.getColor(BaseApplication.mContext, R.color.base_color));
        //主视图滑动的颜色渐变设置 可选
        slidingPaneLayout.setSliderFadeColor(0);
    }


    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {
    }

    public void toggle() {
        if (slidingPaneLayout.isOpen()) {
            slidingPaneLayout.closePane();
        } else {
            slidingPaneLayout.openPane();
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

    @Override
    public boolean onBackPressedSupport() {
        if (slidingPaneLayout.isOpen()) {
            slidingPaneLayout.closePane();
        }
        return super.onBackPressedSupport();
    }
}
