package com.shtoone.chenjiang.mvp.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.SettingContract;
import com.shtoone.chenjiang.mvp.presenter.SettingPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.SettingFragmentVPAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class SettingFragment extends BaseFragment<SettingContract.Presenter> implements SettingContract.View {

    private static final String TAG = SettingFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar_tablayout)
    Toolbar toolbar;
    @BindView(R.id.vp_setting_fragment)
    ViewPager vp;
    @BindView(R.id.tablayout_toolbar_tablayout)
    TabLayout tablayout;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @NonNull
    @Override
    protected SettingContract.Presenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        ((MainActivity) _mActivity).closeDrawer();
        ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.e("onViewCreated");
        initData();
    }

    private void initData() {
        initToolbarBackNavigation(toolbar);
        toolbar.setTitle("系统设置");

        vp.setAdapter(new SettingFragmentVPAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(vp);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //开启抽屉菜单，使其可以滑动弹出。
        KLog.e("onDestroyView");
        ((MainActivity) _mActivity).openDrawer();
    }

}
