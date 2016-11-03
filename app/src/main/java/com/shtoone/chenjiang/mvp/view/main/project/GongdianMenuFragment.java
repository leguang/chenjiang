package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.project.GongdianMenuContract;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.presenter.project.GongdianMenuPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.ProjectMenuFragmentRVAdapter;
import com.shtoone.chenjiang.mvp.view.adapter.base.OnItemClickListener;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class GongdianMenuFragment extends BaseFragment<GongdianMenuContract.Presenter> implements GongdianMenuContract.View {

    private static final String TAG = GongdianMenuFragment.class.getSimpleName();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pagestatelayout)
    PageStateLayout pagestatelayout;
    @BindView(R.id.ptrframelayout)
    PtrFrameLayout ptrframelayout;
    private ProjectMenuFragmentRVAdapter mAdapter;
    private int mCurrentPosition = -1;
    private static final String SAVE_STATE_POSITION = "save_state_position";


    public static GongdianMenuFragment newInstance() {
        return new GongdianMenuFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPageStateLayout(pagestatelayout);
        initPtrFrameLayout(ptrframelayout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerview.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new ProjectMenuFragmentRVAdapter(_mActivity);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showProjectContent(position);
            }
        });
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(SAVE_STATE_POSITION);
            mAdapter.setItemChecked(mCurrentPosition);
        }
    }

    @Override
    public void refresh(List<GongdianData> mGongdianData) {
        mAdapter.setDatas(mGongdianData);
        mCurrentPosition = 0;
        mAdapter.setItemChecked(0);
        showProjectContent(0);
    }

    @NonNull
    @Override
    protected GongdianMenuContract.Presenter createPresenter() {
        return new GongdianMenuPresenter(this);
    }

    @Override
    public void showContent() {
        KLog.e("showContent::");
        pagestatelayout.showContent();
    }

    @Override
    public void showError(Throwable t) {
        KLog.e("showError::" + t);
        pagestatelayout.showContent();
    }

    @Override
    public void showLoading() {
        pagestatelayout.showLoading();
    }

    private void showProjectContent(int position) {
        if (position == mCurrentPosition) {
            return;
        }

        mCurrentPosition = position;
        mAdapter.setItemChecked(position);
        GongdianContentFragment fragment = GongdianContentFragment.newInstance();
        ((GongdianFragment) getParentFragment()).switchContentFragment(fragment);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_STATE_POSITION, mCurrentPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerview.setAdapter(null);
    }
}
