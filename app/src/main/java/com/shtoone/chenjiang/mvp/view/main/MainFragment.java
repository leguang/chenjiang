package com.shtoone.chenjiang.mvp.view.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qiangxi.checkupdatelibrary.bean.CheckUpdateInfo;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.DialogHelper;
import com.shtoone.chenjiang.common.ToastUtils;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.MainContract;
import com.shtoone.chenjiang.mvp.model.entity.bean.CheckUpdateBean;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.MainPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.Decoration;
import com.shtoone.chenjiang.mvp.view.adapter.ListDropDownLVAdapter;
import com.shtoone.chenjiang.mvp.view.adapter.MainFragmentRVAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.measure.MeasureFragment;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.socks.library.KLog;
import com.yyydjk.library.DropDownMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {
    private static final String TAG = MainFragment.class.getSimpleName();
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private long TOUCH_TIME = 0;
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private RecyclerView recyclerview;
    private PageStateLayout pagestatelayout;
    private PtrFrameLayout ptrframelayout;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private boolean isLoading;
    private MainFragmentRVAdapter mAdapter;
    private CheckUpdateInfo mCheckUpdateInfo;
    private View contentView;
    private List<String> listGongdian = new ArrayList<String>();
    private List<View> popupViews = new ArrayList<>();
    private ListView gongdianView, measureStatusView, timeTypeView;
    private ListDropDownLVAdapter gongdianAdapter;
    private String[] arrayHeaders, arrayMeasureStatus, arrayTimeType;
    private int pagination;
    private View mFooterLoading, mFooterNotLoading, mFooterError;
    private String strGongdianParam = "全部", strMeasureStatusParam = "全部", strTimeTypeParam = "全部";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @NonNull
    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        contentView = inflater.inflate(R.layout.recyclerview, null);
        initView(contentView);
        return view;
    }

    private void initView(View contentView) {
        pagestatelayout = (PageStateLayout) contentView.findViewById(R.id.pagestatelayout);
        ptrframelayout = (PtrFrameLayout) contentView.findViewById(R.id.ptrframelayout);
        recyclerview = (RecyclerView) contentView.findViewById(R.id.recyclerview);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) _mActivity).initToolBar(toolbar);
        toolbar.setTitle("水准线路列表");
        mFooterLoading = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_loading, (ViewGroup) recyclerview.getParent(), false);
        mFooterNotLoading = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_not_loading, (ViewGroup) recyclerview.getParent(), false);
        mFooterError = getLayoutInflater(savedInstanceState).inflate(R.layout.item_footer_error, (ViewGroup) recyclerview.getParent(), false);
        initData();
    }

    private void initData() {
        toolbar.inflateMenu(R.menu.menu_add);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        start(AddShuizhunxianFragment.newInstance());
                        break;
                }
                return true;
            }
        });

        setDropDownMenu();
        setRecyclerview();
        setLoadMore();
        initPtrFrameLayout(ptrframelayout);

    }

    private void setDropDownMenu() {
        Resources res = getResources();
        arrayHeaders = res.getStringArray(R.array.headers);
        arrayMeasureStatus = res.getStringArray(R.array.measure_status);
        arrayTimeType = res.getStringArray(R.array.time_type);

        gongdianView = new ListView(_mActivity);
        gongdianAdapter = new ListDropDownLVAdapter(_mActivity, listGongdian);
        gongdianView.setDividerHeight(0);
        gongdianView.setAdapter(gongdianAdapter);
        gongdianView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strGongdianParam = listGongdian.get(position);
                mPresenter.requestShuizhunxianData(0, strGongdianParam, strMeasureStatusParam, strTimeTypeParam);

                gongdianAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? arrayHeaders[0] : listGongdian.get(position));
                dropDownMenu.closeMenu();
            }
        });


        measureStatusView = new ListView(_mActivity);
        measureStatusView.setDividerHeight(0);
        final ListDropDownLVAdapter ageAdapter = new ListDropDownLVAdapter(_mActivity, Arrays.asList(arrayMeasureStatus));
        measureStatusView.setAdapter(ageAdapter);
        measureStatusView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strMeasureStatusParam = arrayMeasureStatus[position];
                mPresenter.requestShuizhunxianData(0, strGongdianParam, strMeasureStatusParam, strTimeTypeParam);

                ageAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? arrayHeaders[1] : arrayMeasureStatus[position]);
                dropDownMenu.closeMenu();
            }
        });

        timeTypeView = new ListView(_mActivity);
        timeTypeView.setDividerHeight(0);
        final ListDropDownLVAdapter sexAdapter = new ListDropDownLVAdapter(_mActivity, Arrays.asList(arrayTimeType));
        timeTypeView.setAdapter(sexAdapter);

        timeTypeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strTimeTypeParam = arrayTimeType[position];
                mPresenter.requestShuizhunxianData(0, strGongdianParam, strMeasureStatusParam, strTimeTypeParam);

                sexAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? arrayHeaders[2] : arrayTimeType[position]);
                dropDownMenu.closeMenu();
            }
        });

        //工点不一定要在最左边，可以放最右边
        popupViews.add(gongdianView);
        popupViews.add(measureStatusView);
        popupViews.add(timeTypeView);
        dropDownMenu.setDropDownMenu(Arrays.asList(arrayHeaders), popupViews, contentView);
    }

    private void setRecyclerview() {
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        recyclerview.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MainFragmentRVAdapter();
        mAdapter.removeAllFooterView();
        mAdapter.setOnItemClickListener(new MainFragmentRVAdapter.OnItemClickListener() {
            @Override
            public void onLeftClick(View view, final int position) {
                KLog.e("onLeftClick" + position);
                DialogHelper.dialog(_mActivity, R.drawable.ic_error_outline_red_400_48dp,
                        R.string.dialog_title_delete, R.string.dialog_delete_shuizhunxian, R.string.dialog_positiveText,
                        R.string.dialog_negativeText, new DialogHelper.Call() {
                            @Override
                            public void onNegative() {

                            }

                            @Override
                            public void onPositive() {
                                mAdapter.getData().get(position).delete();
                                mAdapter.remove(position);
                            }
                        });
            }

            @Override
            public void onMiddleClick(View view, int position) {
                start(EditShuizhunxianFragment.newInstance(mAdapter.getData().get(position)));
            }

            @Override
            public void onRightClick(View view, int position) {
                if (mAdapter.getData().get(position).getStatus().equals(Constants.status_daibianji)) {
                    ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
                    DialogHelper.warningSnackbar(viewGroup, "请先编辑水准线路再进行测量！", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                } else {
                    start(MeasureFragment.newInstance(mAdapter.getData().get(position)));
                }
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
                        mPresenter.requestShuizhunxianData(pagination, strGongdianParam, strMeasureStatusParam, strTimeTypeParam);
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
                mPresenter.requestShuizhunxianData(pagination, strGongdianParam, strMeasureStatusParam, strTimeTypeParam);
            }
        });
    }

    @Override
    public void responseShuizhunxianData(List<YusheshuizhunxianData> mYusheshuizhunxianData, int pagination) {
        KLog.e("mYusheshuizhunxianData.size()::" + mYusheshuizhunxianData.size());
        KLog.e("pagination::" + pagination);

        if (mYusheshuizhunxianData.size() > 0) {
            pagestatelayout.showContent();
            if (pagination == 0) {
                this.pagination = 0;//说明是第一页，或者是刷新,把页码重置为0，代表第一页。

                if (mYusheshuizhunxianData.size() > Constants.PAGE_SIZE) {
                    mAdapter.removeAllFooterView();
                    mAdapter.addFooterView(mFooterLoading);
                } else {
                    mAdapter.removeAllFooterView();
                }
                //重新刷新了，就不需要记录选中状态
                mAdapter.setNewData(mYusheshuizhunxianData);
            } else {
                mAdapter.addData(mYusheshuizhunxianData);
            }
            //靠这个参数控制最后不需要请求数据
            isLoading = false;
        } else {
            if (pagination == 0) {
                //当数据返回的数据条目为0时，说明是无数据，即页面是空状态，此时设置点击事件是跳转到下载页面。
                pagestatelayout.setOnRetryClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start(DownloadFragment.newInstance());
                    }
                });
                pagestatelayout.showEmpty();
            } else {
                //此处一定要先清除之前加载的FooterView，否则会报错。
                mAdapter.removeAllFooterView();
                mAdapter.addFooterView(mFooterNotLoading);
            }
        }
    }

    @Override
    public void responseGongdianData(List<GongdianData> mGongdianData) {
        KLog.e("mGongdianData::" + mGongdianData.size());

        listGongdian.clear();
        listGongdian.add("全部");
        if (mGongdianData != null && mGongdianData.size() > 0) {
            for (GongdianData gongdianData : mGongdianData) {
                listGongdian.add(gongdianData.getGdmc());
            }
        }
        gongdianAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
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

    /**
     * 当一进入界面或者下拉刷新的时候就调用此方法。默认父类中是调用的mPresenter.start();
     */
    @Override
    public void start() {
        mPresenter.requestGongdianData();
        mPresenter.requestShuizhunxianData(0, strGongdianParam, strMeasureStatusParam, strTimeTypeParam);
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventData event) {

        if (event.position == Constants.EVENT_REFRESH) {
            mPresenter.requestShuizhunxianData(0, strGongdianParam, strMeasureStatusParam, strTimeTypeParam);
            mPresenter.requestGongdianData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUpdateEvent(CheckUpdateBean.UpdateInfoBean mCheckUpdateInfo) {
        if (mCheckUpdateInfo.getIsForceUpdate() == 0) {
            UpdateDialog(mCheckUpdateInfo);
        } else {
            forceUpdateDialog(mCheckUpdateInfo);
        }
    }

    /**
     * 强制更新,checkupdatelibrary中提供的默认强制更新Dialog,您完全可以自定义自己的Dialog,
     */
    public void forceUpdateDialog(CheckUpdateBean.UpdateInfoBean mCheckUpdateInfo) {
        ForceUpdateDialog dialog = new ForceUpdateDialog(_mActivity);
        dialog.setAppSize(Math.round(((Float.valueOf(mCheckUpdateInfo.getNewAppSize())) / (1024 * 1024))))
                .setDownloadUrl(Constants.BASE_URL + mCheckUpdateInfo.getNewAppUrl().replace("\\", "/"))
                .setTitle(mCheckUpdateInfo.getAppName() + "有重大更新啦!")
                .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                .setFileName("cjgc.apk")
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib").show();

    }

    /**
     * 非强制更新,checkupdatelibrary中提供的默认非强制更新Dialog,您完全可以自定义自己的Dialog
     */
    public void UpdateDialog(CheckUpdateBean.UpdateInfoBean mCheckUpdateInfo) {
        UpdateDialog dialog = new UpdateDialog(_mActivity);
        dialog.setAppSize(Math.round(((Float.valueOf(mCheckUpdateInfo.getNewAppSize())) / (1024 * 1024))))
                .setDownloadUrl(Constants.BASE_URL + mCheckUpdateInfo.getNewAppUrl().replace("\\", "/"))
                .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦!")
                .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                .setFileName("cjgc.apk")
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib")
                //该方法需设为true,才会在通知栏显示下载进度,默认为false,即不显示
                //该方法只会控制下载进度的展示,当下载完成或下载失败时展示的通知不受该方法影响
                //即不管该方法是置为false还是true,当下载完成或下载失败时都会在通知栏展示一个通知
                .setShowProgress(true)
                .setIconResId(R.mipmap.ic_launcher)
                .setAppName(mCheckUpdateInfo.getAppName()).show();
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

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtils.showInfoToast(BaseApplication.mContext, Constants.PRESS_AGAIN);
        }
        return true;
    }

    @OnClick(R.id.fab)
    public void onClick() {
        recyclerview.smoothScrollToPosition(0);
    }
}
