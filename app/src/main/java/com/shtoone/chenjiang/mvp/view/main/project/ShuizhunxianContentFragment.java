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
import com.shtoone.chenjiang.mvp.contract.project.ShuizhunxianContentContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.project.ShuizhunxianContentPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.Decoration;
import com.shtoone.chenjiang.mvp.view.adapter.ShuizhunxianContentRVAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.upload.DetailFragment;
import com.shtoone.chenjiang.widget.PageStateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.ConnectException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ShuizhunxianContentFragment extends BaseFragment<ShuizhunxianContentContract.Presenter> implements ShuizhunxianContentContract.View {
    private static final String TAG = ShuizhunxianContentFragment.class.getSimpleName();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pagestatelayout)
    PageStateLayout pagestatelayout;
    @BindView(R.id.ptrframelayout)
    PtrFrameLayout ptrframelayout;
    private ShuizhunxianContentRVAdapter mAdapter;
    private int pagination = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private View mFooterLoading, mFooterNotLoading, mFooterError;
    private boolean isLoading;
    private String yusheshuizhunxianID;

    public static ShuizhunxianContentFragment newInstance() {
        return new ShuizhunxianContentFragment();
    }

    @NonNull
    @Override
    protected ShuizhunxianContentContract.Presenter createPresenter() {
        return new ShuizhunxianContentPresenter(this);
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
                mPresenter.requestShuizhunxian(pagination, yusheshuizhunxianID);
            }
        });
        setAdapter();
        setLoadMore();
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                EventBus.getDefault().post(DetailFragment.newInstance(mAdapter.getData().get(position)));
            }
        });
        recyclerview.addItemDecoration(new Decoration(_mActivity, Decoration.VERTICAL_LIST));
        recyclerview.setAdapter(mAdapter);
        initPageStateLayout(pagestatelayout);
        initPtrFrameLayout(ptrframelayout);
    }

    private void setAdapter() {
        mAdapter = new ShuizhunxianContentRVAdapter();
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
                        mPresenter.requestShuizhunxian(pagination, yusheshuizhunxianID);
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
    public void responseShuizhunxian(List<ShuizhunxianData> mShuizhunxianData, int pagination) {
        if (mShuizhunxianData.size() > 0) {
            if (pagination == 0) {
                //刷明是第一页，或者是刷新,把页码重置为0，代表第一页。
                if (mShuizhunxianData.size() >= Constants.PAGE_SIZE) {
                    mAdapter.removeAllFooterView();
                    mAdapter.addFooterView(mFooterLoading);
                }
                this.pagination = 0;
                mAdapter.setNewData(mShuizhunxianData);
                //设置一下会重新刷新整个item的位置，即使不是第一个item位置刷新，也会重新刷新定位到第一个。
                recyclerview.setAdapter(mAdapter);
            } else {
                mAdapter.addData(mShuizhunxianData);
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
                mAdapter.notifyDataSetChanged();//这里必须要notify一下，否则会报错，因为我修改了footer。
            }
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(YusheshuizhunxianData mYusheshuizhunxianData) {
        yusheshuizhunxianID = mYusheshuizhunxianData.getId() + "";
        mPresenter.requestShuizhunxian(0, yusheshuizhunxianID);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
