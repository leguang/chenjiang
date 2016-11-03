package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.StaffContract;
import com.shtoone.chenjiang.mvp.model.bean.StaffData;
import com.shtoone.chenjiang.mvp.presenter.project.StaffPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseLazyFragment;
import com.shtoone.chenjiang.widget.PageStateLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class StaffFragment extends BaseLazyFragment<StaffContract.Presenter> implements StaffContract.View {

    private static final String TAG = StaffFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pagestatelayout)
    PageStateLayout pagestatelayout;
    @BindView(R.id.ptrframelayout)
    PtrFrameLayout ptrframelayout;


    public static StaffFragment newInstance() {
        return new StaffFragment();
    }

    @NonNull
    @Override
    protected StaffContract.Presenter createPresenter() {
        return new StaffPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("项目查看");
        initToolbarBackNavigation(toolbar);
        initPageStateLayout(pagestatelayout);
    }

    /**
     * 懒加载
     *
     * @param savedInstanceState
     */
    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            initData();
        }
    }

    private void initData() {
        //下拉刷新必须得在懒加载里设置，因为下拉刷新是一进来就刷新，启动start()。
        initPtrFrameLayout(ptrframelayout);
    }

    @Override
    public void showContent() {
        pagestatelayout.showContent();
    }

    @Override
    public void showError(Throwable t) {
        pagestatelayout.showError();
    }

    @Override
    public void showLoading() {
        pagestatelayout.showLoading();
    }

    @Override
    public void refresh(List<StaffData> mStaffData) {

    }
}
