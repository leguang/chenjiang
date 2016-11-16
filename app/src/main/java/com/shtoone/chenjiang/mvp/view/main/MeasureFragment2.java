package com.shtoone.chenjiang.mvp.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.MeasureContract;
import com.shtoone.chenjiang.mvp.presenter.MeasurePresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.socks.library.KLog;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasureFragment2 extends BaseFragment<MeasureContract.Presenter> implements MeasureContract.View {

    private static final String TAG = MeasureFragment2.class.getSimpleName();

    public static MeasureFragment2 newInstance() {
        return new MeasureFragment2();
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
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initStateBar(toolbar);
        initData();
    }

    private void initData() {
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
