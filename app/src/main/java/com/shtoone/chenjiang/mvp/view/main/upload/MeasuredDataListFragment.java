package com.shtoone.chenjiang.mvp.view.main.upload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.upload.MeasuredDataListContract;
import com.shtoone.chenjiang.mvp.presenter.upload.MeasuredDataListPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.widget.PageStateLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasuredDataListFragment extends BaseFragment<MeasuredDataListContract.Presenter> implements MeasuredDataListContract.View {

    private static final String TAG = MeasuredDataListFragment.class.getSimpleName();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pagestatelayout)
    PageStateLayout pagestatelayout;
    @BindView(R.id.ptrframelayout)
    PtrFrameLayout ptrframelayout;
    private int intFromMeasuredDataFragment;

    public static MeasuredDataListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Constants.FROMTO_MEASURED_DATA_FRAGMENT, position);

        MeasuredDataListFragment fragment = new MeasuredDataListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            intFromMeasuredDataFragment = args.getInt(Constants.FROMTO_MEASURED_DATA_FRAGMENT);
        }
    }

    @NonNull
    @Override
    protected MeasuredDataListContract.Presenter createPresenter() {
        return new MeasuredDataListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measured_data_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void response(List<Class> mListData, int pagination) {

    }
}
