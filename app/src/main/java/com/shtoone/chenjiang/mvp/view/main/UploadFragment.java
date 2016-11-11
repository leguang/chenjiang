package com.shtoone.chenjiang.mvp.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.UploadContract;
import com.shtoone.chenjiang.mvp.presenter.UploadPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadFragment extends BaseFragment<UploadContract.Presenter> implements UploadContract.View {

    private static final String TAG = UploadFragment.class.getSimpleName();

    public static UploadFragment newInstance() {
        return new UploadFragment();
    }

    @NonNull
    @Override
    protected UploadContract.Presenter createPresenter() {
        return new UploadPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
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
//        initToolbarBackNavigation(toolbar);
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