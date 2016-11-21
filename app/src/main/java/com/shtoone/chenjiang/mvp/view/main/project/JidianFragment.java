package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.project.JidianContract;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;
import com.shtoone.chenjiang.mvp.presenter.project.JidianPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.JidianAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseLazyFragment;
import com.shtoone.chenjiang.utils.DensityUtils;
import com.shtoone.chenjiang.utils.ToastUtils;
import com.shtoone.chenjiang.widget.PageStateLayout;

import java.net.ConnectException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class JidianFragment extends BaseLazyFragment<JidianContract.Presenter> implements JidianContract.View {

    private static final String TAG = JidianFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pagestatelayout)
    PageStateLayout pagestatelayout;
    @BindView(R.id.ptrframelayout)
    PtrFrameLayout ptrframelayout;
    private int pagination = 0;
    private JidianAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private View mFooterLoading;
    private View mFooterNotLoading;
    private View mFooterError;
    private boolean isLoading;

    public static JidianFragment newInstance() {
        return new JidianFragment();
    }

    @NonNull
    @Override
    protected JidianContract.Presenter createPresenter() {
        return new JidianPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        ButterKnife.bind(this, view);
        initStateBar(toolbar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("项目查看");
        initToolbarBackNavigation(toolbar);
        initPageStateLayout(pagestatelayout);
        pagestatelayout.setPadding(0, 0, 0, DensityUtils.dp2px(_mActivity, 56));
    }

    /**
     * 懒加载
     *
     * @param savedInstanceState
     */
    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            initData(savedInstanceState);
        }
    }

    private void initData(Bundle savedInstanceState) {
        //下拉刷新必须得在懒加载里设置，因为下拉刷新是一进来就刷新，启动start()。
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
            }
        });

        recyclerview.setAdapter(mAdapter);
        initPageStateLayout(pagestatelayout);
        initPtrFrameLayout(ptrframelayout);
    }


    private void setAdapter() {
        mAdapter = new JidianAdapter();
        mAdapter.removeAllFooterView();
    }

    private void setLoadMore() {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == mAdapter.getItemCount()
                        //目的是判断第一页数据条数是否满足一整页。
                        && mAdapter.getItemCount() >= Constants.PAGE_SIZE) {
                    if (!isLoading) {
                        isLoading = true;
                        pagination += 1;
                        mPresenter.queryData(pagination);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    @Override
    public void showContent() {
        pagestatelayout.showContent();
    }

    @Override
    public void showError(Throwable t) {
        //第一页加载出错，显示方式和第一页以后的部分出错显示不同
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

    @Override
    public void refresh(List<JidianData> mJidianData, int pagination) {
        if (mJidianData.size() > 0) {
            if (pagination == 0) {
                //刷明是第一页，或者是刷新,把页码重置为0，代表第一页。
                if (mJidianData.size() >= Constants.PAGE_SIZE) {
                    mAdapter.removeAllFooterView();
                    mAdapter.addFooterView(mFooterLoading);
                }
                this.pagination = 0;
                mAdapter.setNewData(mJidianData);
                //设置一下会重新刷新整个item的位置，即使不是第一个item位置刷新，也会重新刷新定位到第一个。
                recyclerview.setAdapter(mAdapter);
            } else {
                mAdapter.addData(mJidianData);
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
