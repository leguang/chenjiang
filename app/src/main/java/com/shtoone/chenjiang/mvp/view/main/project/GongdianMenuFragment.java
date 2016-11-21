package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.project.GongdianMenuContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.presenter.project.GongdianMenuPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.GongdianMenuAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.DensityUtils;
import com.shtoone.chenjiang.utils.ToastUtils;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.socks.library.KLog;

import java.net.ConnectException;
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
    private GongdianMenuAdapter mAdapter;
    private int mCurrentPosition = -1;
    private static final String SAVE_STATE_POSITION = "save_state_position";
    private int pagination = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private View mFooterLoading, mFooterNotLoading, mFooterError;
    private boolean isLoading;

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
        pagestatelayout.setPadding(0, 0, 0, DensityUtils.dp2px(_mActivity, 56));
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        recyclerview.setLayoutManager(mLinearLayoutManager);
        mFooterLoading = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_loading, (ViewGroup) recyclerview.getParent(), false);
        mFooterNotLoading = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_not_loading, (ViewGroup) recyclerview.getParent(), false);
        mFooterError = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_error, (ViewGroup) recyclerview.getParent(), false);
        mFooterError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeAllFooterView();
                mAdapter.addFooterView(mFooterLoading);
                mPresenter.queryData(pagination);
            }
        });
        setAdapter();
        setLoadMore();
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showToast(_mActivity, Integer.toString(position));
                showProjectContent(position);
            }
        });

        recyclerview.setAdapter(mAdapter);
        initPageStateLayout(pagestatelayout);
        initPtrFrameLayout(ptrframelayout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //处理缓存
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(SAVE_STATE_POSITION);
            mAdapter.setItemChecked(mCurrentPosition);
        }
    }

    private void setAdapter() {
        mAdapter = new GongdianMenuAdapter();
        mAdapter.removeAllFooterView();
    }

    private void setLoadMore() {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mAdapter == null) {
                    return;
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == mAdapter.getItemCount()
                        //目的是判断第一页数据条数是否满足一整页。
                        && mAdapter.getItemCount() >= Constants.PAGE_SIZE) {
                    if (!isLoading) {
                        isLoading = true;
                        pagination += 1;
                        mPresenter.queryData(pagination);
                        KLog.e("进来了…………………………");
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLinearLayoutManager == null) {
                    return;
                }
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void refresh(List<GongdianData> mGongdianData, int pagination) {
        if (mGongdianData.size() > 0) {
            if (pagination == 0) {
                //刷明是第一页，或者是刷新,把页码重置为0，代表第一页。
                if (mGongdianData.size() >= Constants.PAGE_SIZE) {
                    mAdapter.removeAllFooterView();
                    mAdapter.addFooterView(mFooterLoading);
                }
                this.pagination = 0;
                mCurrentPosition = 0;
                mAdapter.setNewData(mGongdianData);
                mAdapter.setItemChecked(0);
                //设置一下会重新刷新整个item的位置，即使不是第一个item位置刷新，也会重新刷新定位到第一个。
                recyclerview.setAdapter(mAdapter);
                //刷新右边
                showProjectContent(0);
            } else {
                mAdapter.addData(mGongdianData);
            }
            //靠这个参数控制最后不需要请求数据
            isLoading = false;
        } else {
            if (pagination == 0) {
                pagestatelayout.showEmpty();
            } else {
                //此处一定要先清除之前加载的FooterView，否则会报错。
                mAdapter.removeAllFooterView();
                mAdapter.addFooterView(mFooterNotLoading);
            }
        }
    }

    @NonNull
    @Override
    protected GongdianMenuContract.Presenter createPresenter() {
        return new GongdianMenuPresenter(this);
    }

    @Override
    public void showContent() {
        pagestatelayout.showContent();
    }

    @Override
    public void showError(Throwable t) {
        if (pagination == 0) {
            if (t instanceof ConnectException) {
                pagestatelayout.showNetError();
            } else {
                pagestatelayout.showError();
            }
        } else {
            mAdapter.removeAllFooterView();
            mAdapter.addFooterView(mFooterError);
        }
    }

    @Override
    public void showLoading() {
        if (pagination == 0) {
            pagestatelayout.showLoading();
        }
    }

    private void showProjectContent(int position) {
        KLog.e("切换：：" + position);

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
        recyclerview.setAdapter(mAdapter = null);
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        if (pagestatelayout.isShowContent) {
            //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
            if (null != recyclerview) {
                if (recyclerview.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerview.getLayoutManager();
                    int position = lm.findFirstVisibleItemPosition();
                    if (position >= 0) {
                        if (lm.findViewByPosition(position).getTop() >= 0 && position >= 0) {
                            return true;
                        }
                    }
                }
            } else {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }
}
