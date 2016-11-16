package com.shtoone.chenjiang.mvp.view.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qiangxi.checkupdatelibrary.bean.CheckUpdateInfo;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.MainContract;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.model.bean.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.MainPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.ListDropDownAdapter;
import com.shtoone.chenjiang.mvp.view.adapter.MainFragmentRVAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.measure.MeasureFragment;
import com.shtoone.chenjiang.utils.ToastUtils;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.socks.library.KLog;
import com.yyydjk.library.DropDownMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {

    private static final String TAG = MainFragment.class.getSimpleName();
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private RecyclerView recyclerview;
    private PageStateLayout pagestatelayout;
    private PtrFrameLayout ptrframelayout;
    private LinearLayoutManager mLinearLayoutManager;
    private ScaleInAnimationAdapter mScaleInAnimationAdapter;
    private int lastVisibleItemPosition;
    private boolean isLoading;
    private MainFragmentRVAdapter mAdapter;
    private CheckUpdateInfo mCheckUpdateInfo;
    private View contentView;

    private List<View> popupViews = new ArrayList<>();
    private ListView gongdianView, measureStatusView, timeTypeView;
    private String[] arrayHeaders;
    private String[] arrayMeasureStatus;
    private String[] arrayTimeType;

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
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        recyclerview.setLayoutManager(mLinearLayoutManager);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {

                    if (!isLoading) {
                        isLoading = true;
                        recyclerview.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                loadMore();
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();


            }
        });

        initPageStateLayout(pagestatelayout);
        initPtrFrameLayout(ptrframelayout);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) _mActivity).initToolBar(toolbar);
        toolbar.setTitle("水准线路列表");
        initData();
    }

    private void initData() {

        toolbar.inflateMenu(R.menu.menu_add);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        start(MeasureFragment.newInstance());
                        break;
                }
                return true;
            }
        });

        Resources res = getResources();
        arrayHeaders = res.getStringArray(R.array.headers);
        arrayMeasureStatus = res.getStringArray(R.array.measure_status);
        arrayTimeType = res.getStringArray(R.array.time_type);


        measureStatusView = new ListView(_mActivity);
        measureStatusView.setDividerHeight(0);
        final ListDropDownAdapter ageAdapter = new ListDropDownAdapter(_mActivity, Arrays.asList(arrayMeasureStatus));
        measureStatusView.setAdapter(ageAdapter);
        measureStatusView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? arrayHeaders[1] : arrayMeasureStatus[position]);
                dropDownMenu.closeMenu();
            }
        });


        timeTypeView = new ListView(_mActivity);
        timeTypeView.setDividerHeight(0);
        final ListDropDownAdapter sexAdapter = new ListDropDownAdapter(_mActivity, Arrays.asList(arrayTimeType));
        timeTypeView.setAdapter(sexAdapter);

        timeTypeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? arrayHeaders[2] : arrayTimeType[position]);
                dropDownMenu.closeMenu();
            }
        });

    }

    @Override
    public boolean isCanDoRefresh() {
        //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
        if (null != recyclerview && null != mLinearLayoutManager) {
            int position = mLinearLayoutManager.findFirstVisibleItemPosition();
            KLog.e(position);
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
//        pagestatelayout.showContent();
    }

    @Override
    public void showError(Throwable t) {
//        pagestatelayout.showError();
    }

    @Override
    public void showLoading() {
//        pagestatelayout.showLoading();
    }

    @Override
    public void responseShuizhunxianData(List<YusheshuizhunxianData> mShuizhunxianData) {
        //mAdapter的实例化要放到最开始，因为在没有数据的时候，滑动会空指针异常，因为  if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new MainFragmentRVAdapter(_mActivity, mShuizhunxianData));
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(0.5f));
        mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        recyclerview.setAdapter(mScaleInAnimationAdapter);
    }

    @Override
    public void responseGongdianData(List<GongdianData> mGongdianData) {
        final List<String> listGongdian = new ArrayList<>();

        if (mGongdianData.size() > 0) {
            for (GongdianData gongdianData : mGongdianData) {
                listGongdian.add(gongdianData.getZxlc());
            }
        }

        gongdianView = new ListView(_mActivity);
        final ListDropDownAdapter gongdianAdapter = new ListDropDownAdapter(_mActivity, listGongdian);
        gongdianView.setDividerHeight(0);
        gongdianView.setAdapter(gongdianAdapter);
        gongdianView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gongdianAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? arrayHeaders[0] : listGongdian.get(position));
                dropDownMenu.closeMenu();
            }
        });

        //工点不一定要在最左边，可以放最右边
        popupViews.add(gongdianView);
        popupViews.add(measureStatusView);
        popupViews.add(timeTypeView);
        dropDownMenu.setDropDownMenu(Arrays.asList(arrayHeaders), popupViews, contentView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventData event) {

//        if (event.position == Constants.CHECKUPDATE) {
//
//            mCheckUpdateInfo = new CheckUpdateInfo();
//            mCheckUpdateInfo.setAppName("android检查更新库")
//                    .setIsForceUpdate(1)//设置是否强制更新,该方法的参数只要和服务端商定好什么数字代表强制更新即可
//                    .setNewAppReleaseTime("2016-10-14 12:37")//软件发布时间
//                    .setNewAppSize(12.3f)//单位为M
//                    .setNewAppUrl("http://shouji.360tpcdn.com/160914/c5164dfbbf98a443f72f32da936e1379/com.tencent.mobileqq_410.apk")
//                    .setNewAppVersionCode(20)//新app的VersionCode
//                    .setNewAppVersionName("1.0.2")
//                    .setNewAppUpdateDesc("1,优化下载逻辑\n2,修复一些bug\n3,完全实现强制更新与非强制更新逻辑\n4,非强制更新状态下进行下载,默认在后台进行下载\n5,当下载成功时,会在通知栏显示一个通知,点击该通知,进入安装应用界面\n6,当下载失败时,会在通知栏显示一个通知,点击该通知,会重新下载该应用\n7,当下载中,会在通知栏显示实时下载进度,但前提要dialog.setShowProgress(true).");
//
//            UpdateDialogClick();
//
//        }
    }


    /**
     * 强制更新,checkupdatelibrary中提供的默认强制更新Dialog,您完全可以自定义自己的Dialog,
     */
    public void forceUpdateDialogClick() {
        mCheckUpdateInfo.setIsForceUpdate(0);
        if (mCheckUpdateInfo.getIsForceUpdate() == 0) {
            ForceUpdateDialog dialog = new ForceUpdateDialog(_mActivity);
            dialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                    .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                    .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                    .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                    .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                    .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                    .setFileName("这是QQ.apk")
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib").show();
        }
    }

    /**
     * 非强制更新,checkupdatelibrary中提供的默认非强制更新Dialog,您完全可以自定义自己的Dialog
     */
    public void UpdateDialogClick() {
        mCheckUpdateInfo.setIsForceUpdate(1);
        if (mCheckUpdateInfo.getIsForceUpdate() == 1) {
            UpdateDialog dialog = new UpdateDialog(_mActivity);
            dialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                    .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                    .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                    .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                    .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                    .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                    .setFileName("这是QQ.apk")
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib")
                    //该方法需设为true,才会在通知栏显示下载进度,默认为false,即不显示
                    //该方法只会控制下载进度的展示,当下载完成或下载失败时展示的通知不受该方法影响
                    //即不管该方法是置为false还是true,当下载完成或下载失败时都会在通知栏展示一个通知
                    .setShowProgress(true)
                    .setIconResId(R.mipmap.ic_launcher)
                    .setAppName(mCheckUpdateInfo.getAppName()).show();
        }
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
}
