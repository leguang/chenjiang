package com.shtoone.chenjiang.mvp.view.main.upload;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.DialogHelper;
import com.shtoone.chenjiang.common.ToastUtils;
import com.shtoone.chenjiang.mvp.contract.upload.UploadContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.upload.UploadPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.Decoration;
import com.shtoone.chenjiang.mvp.view.adapter.UploadRVAdapter;
import com.shtoone.chenjiang.mvp.view.adapter.base.OnItemClickListener;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import org.litepal.crud.DataSupport;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadFragment extends BaseFragment<UploadContract.Presenter> implements UploadContract.View {
    private static final String TAG = UploadFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.cb_check_all_upload_fragment)
    CheckBox cbCheckAll;
    @BindView(R.id.iv_upload_upload_fragment)
    ImageView ivUpload;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.pagestatelayout)
    PageStateLayout pagestatelayout;
    @BindView(R.id.ptrframelayout)
    PtrFrameLayout ptrframelayout;
    private UploadRVAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private View mFooterLoading, mFooterNotLoading, mFooterError;
    private boolean isLoading;
    private List<YusheshuizhunxianData> listChecked = new ArrayList<>();
    private int pagination = 0;
    private ViewGroup viewGroup;

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
        viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        ((MainActivity) _mActivity).closeDrawer();
        ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(toolbar);
        toolbar.setTitle("上传");
        initToolbarBackNavigation(toolbar);
        mFooterLoading = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_loading, (ViewGroup) recyclerview.getParent(), false);
        mFooterNotLoading = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_not_loading, (ViewGroup) recyclerview.getParent(), false);
        mFooterError = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_error, (ViewGroup) recyclerview.getParent(), false);
        initData();
    }

    private void initData() {
        setRecyclerview();
        setLoadMore();
        initPageStateLayout(pagestatelayout);
        initPtrFrameLayout(ptrframelayout);
    }

    private void setRecyclerview() {
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        recyclerview.setLayoutManager(mLinearLayoutManager);

        mAdapter = new UploadRVAdapter(listChecked);
        mAdapter.removeAllFooterView();

        mAdapter.setOnCheckedChangeListener(new UploadRVAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, int position) {
                if (compoundButton.isChecked()) {
                    listChecked.add(mAdapter.getData().get(position));
                    if (listChecked.size() == mAdapter.getData().size()) {
                        cbCheckAll.setChecked(true);
                    }
                } else {
                    listChecked.remove(mAdapter.getData().get(position));
                    cbCheckAll.setChecked(false);
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(BaseApplication.mContext, position + "");
                YusheshuizhunxianData mYusheshuizhunxianData = mAdapter.getData().get(position);
                ShuizhunxianData mShuizhunxianData = DataSupport.where("yusheshuizhunxianID = ? and chuangjianshijian = ? "
                        , String.valueOf(mYusheshuizhunxianData.getId()), mYusheshuizhunxianData.getXiugaishijian())
                        .findFirst(ShuizhunxianData.class);
                start(DetailFragment.newInstance(mShuizhunxianData));
            }
        });
        recyclerview.addItemDecoration(new Decoration(_mActivity, Decoration.VERTICAL_LIST));
        recyclerview.setAdapter(mAdapter);
    }

    private void setLoadMore() {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mAdapter == null) {
                    return;
                }

                //判断分页加载的时机是滑动到底部。
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == mAdapter.getItemCount()
                        //目的是判断第一页数据条数是否满足一整页。
                        && mAdapter.getItemCount() >= Constants.PAGE_SIZE) {
                    if (!isLoading) {
                        isLoading = true;
                        pagination += 1;
                        mPresenter.requestShuizhunxianData(pagination);
                    }
                }

                //滑动到顶部之后就可以隐藏掉FAB了。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLinearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    fab.hide();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mLinearLayoutManager == null) {
                    return;
                }
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();

                if (dy > 5) {
                    fab.hide();
                } else if (dy < -5) {
                    fab.show();
                }
            }
        });

        mFooterError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeAllFooterView();
                mAdapter.addFooterView(mFooterLoading);
                mPresenter.requestShuizhunxianData(pagination);
            }
        });
    }

    @Override
    public void responseShuizhunxianData(List<YusheshuizhunxianData> mYusheshuizhunxianData, int pagination) {
        if (mYusheshuizhunxianData.size() > 0) {
            if (pagination == 0) {
                //刷明是第一页，或者是刷新,把页码重置为0，代表第一页。
                if (mYusheshuizhunxianData.size() >= Constants.PAGE_SIZE) {
                    mAdapter.removeAllFooterView();
                    mAdapter.addFooterView(mFooterLoading);
                }
                this.pagination = 0;
                //重新刷新了，就不需要记录选中状态
                listChecked.clear();
                mAdapter.setNewData(mYusheshuizhunxianData);
            } else {
                if (cbCheckAll.isChecked()) {
                    listChecked.addAll(mYusheshuizhunxianData);
                }
                mAdapter.addData(mYusheshuizhunxianData);
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
    public void onUploaded(int intMessage, String strMessage) {
        TSnackbar snackBar = TSnackbar.make(viewGroup, strMessage, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);
        if (intMessage == Constants.UPLAND_SUCCESS) {
            snackBar.setPromptThemBackground(Prompt.SUCCESS);
        } else {
            snackBar.setPromptThemBackground(Prompt.ERROR);
        }
        snackBar.show();
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新。
        if (null != recyclerview && null != mLinearLayoutManager) {
            int position = mLinearLayoutManager.findFirstVisibleItemPosition();
            if (position >= 0) {
                if (mLinearLayoutManager.findViewByPosition(position).getTop() >= 0 && position == 0) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
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
    public void onDestroyView() {
        super.onDestroyView();
        //开启抽屉菜单，使其可以滑动弹出。
        ((MainActivity) _mActivity).openDrawer();
    }

    //只能用OnClickListener不能用OnCheckedChangeListener，因为最终会onBindViewHolder中调用notifyDataSetChanged ，
    //因为我是在convert通过接口的形式传出数据，然后OnCheckedChangeListener这个会随着状态的改变自动调用onCheckedChanged，所以会循环调用自己
    //所以会报Cannot call this method while RecyclerView is computing a layout or scrolling
    @OnClick({R.id.cb_check_all_upload_fragment, R.id.iv_upload_upload_fragment, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_check_all_upload_fragment:
                mAdapter.checkAllChanged(cbCheckAll.isChecked());
                break;
            case R.id.fab:
                recyclerview.smoothScrollToPosition(0);
                break;
            case R.id.iv_upload_upload_fragment:
                if (listChecked == null || listChecked.size() == 0) {
                    Snackbar mSnackbar = Snackbar.make(view, "请选择后上传", Snackbar.LENGTH_LONG)
                            .setAction("全选", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cbCheckAll.setChecked(true);
                                    mAdapter.checkAllChanged(true);
                                }
                            });
                    View v = mSnackbar.getView();
                    v.setBackgroundColor(Color.parseColor("#FFCC00"));
                    mSnackbar.show();
                    return;
                }
                mPresenter.upload(listChecked);
                DialogHelper.loadingSnackbar(viewGroup, "正在努力上传，请稍后…", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                break;
        }
    }
}
