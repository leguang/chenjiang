package com.shtoone.chenjiang.mvp.view.main.upload;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.upload.UploadContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.upload.UploadPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.Decoration;
import com.shtoone.chenjiang.mvp.view.adapter.ListDropDownAdapter;
import com.shtoone.chenjiang.mvp.view.adapter.UploadAdapter;
import com.shtoone.chenjiang.mvp.view.adapter.base.OnItemClickListener;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;
import com.shtoone.chenjiang.utils.ToastUtils;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;
import com.yyydjk.library.DropDownMenu;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadFragment extends BaseFragment<UploadContract.Presenter> implements UploadContract.View {

    private static final String TAG = UploadFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R.id.cb_check_all_upload_fragment)
    CheckBox cbCheckAll;
    @BindView(R.id.iv_upload_upload_fragment)
    ImageView ivUpload;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private View contentView;
    private RecyclerView recyclerview;
    private PageStateLayout pagestatelayout;
    private PtrFrameLayout ptrframelayout;
    private UploadAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private View mFooterLoading, mFooterNotLoading, mFooterError;
    private boolean isLoading;
    private List<View> popupViews = new ArrayList<>();
    private ListView gongdianView, measureStatusView, timeTypeView;
    private String[] arrayHeaders, arrayUploadStatus, arrayTimeType;
    private List<String> listGongdian = new ArrayList<String>();
    private ListDropDownAdapter gongdianAdapter;
    private List<ShuizhunxianData> listChecked = new ArrayList<>();
    private int pagination = 0;
    private ObjectAnimator animator;

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
        contentView = inflater.inflate(R.layout.recyclerview, null);
        initView(contentView);
        return attachToSwipeBack(view);
    }


    private void initView(View contentView) {
        pagestatelayout = (PageStateLayout) contentView.findViewById(R.id.pagestatelayout);
        ptrframelayout = (PtrFrameLayout) contentView.findViewById(R.id.ptrframelayout);
        recyclerview = (RecyclerView) contentView.findViewById(R.id.recyclerview);
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
        setDropDownMenu();
        setRecyclerview();
        setLoadMore();
        initPageStateLayout(pagestatelayout);
        initPtrFrameLayout(ptrframelayout);
    }

    private void setDropDownMenu() {
        Resources res = getResources();
        arrayHeaders = res.getStringArray(R.array.headers);
        arrayUploadStatus = res.getStringArray(R.array.upload_status);
        arrayTimeType = res.getStringArray(R.array.time_type);

        gongdianView = new ListView(_mActivity);
        gongdianAdapter = new ListDropDownAdapter(_mActivity, listGongdian);
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

        measureStatusView = new ListView(_mActivity);
        measureStatusView.setDividerHeight(0);
        final ListDropDownAdapter ageAdapter = new ListDropDownAdapter(_mActivity, Arrays.asList(arrayUploadStatus));
        measureStatusView.setAdapter(ageAdapter);
        measureStatusView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? arrayHeaders[1] : arrayUploadStatus[position]);
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

        //工点不一定要在最左边，可以放最右边
        popupViews.add(gongdianView);
        popupViews.add(measureStatusView);
        popupViews.add(timeTypeView);
        dropDownMenu.setDropDownMenu(Arrays.asList(arrayHeaders), popupViews, contentView);
    }

    private void setRecyclerview() {
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        recyclerview.setLayoutManager(mLinearLayoutManager);

        mAdapter = new UploadAdapter(listChecked);
        mAdapter.setOnCheckedChangeListener(new UploadAdapter.OnCheckedChangeListener() {
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
                start(CheckMeasureFragment.newInstance());
            }
        });
        mAdapter.removeAllFooterView();
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
    public void responseGongdianData(List<GongdianData> mGongdianData) {
        if (mGongdianData == null || mGongdianData.size() == 0) {
            return;
        }

        listGongdian.clear();
        if (mGongdianData.size() > 0) {
            for (GongdianData gongdianData : mGongdianData) {
                listGongdian.add(gongdianData.getZxlc());
            }
        }
        gongdianAdapter.notifyDataSetChanged();
    }

    @Override
    public void responseShuizhunxianData(List<ShuizhunxianData> mShuizhunxianData, int pagination) {
        if (mShuizhunxianData.size() > 0) {
            if (pagination == 0) {
                //刷明是第一页，或者是刷新,把页码重置为0，代表第一页。
                if (mShuizhunxianData.size() >= Constants.PAGE_SIZE) {
                    mAdapter.removeAllFooterView();
                    mAdapter.addFooterView(mFooterLoading);
                }
                this.pagination = 0;
                //重新刷新了，就不需要记录选中状态
                listChecked.clear();
                mAdapter.setNewData(mShuizhunxianData);
            } else {
                if (cbCheckAll.isChecked()) {
                    listChecked.addAll(mShuizhunxianData);
                }
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
            }
        }
    }

    @Override
    public void onUploaded(int intMessage, String strMessage) {
        animator.end();
        ivUpload.setImageResource(R.drawable.ic_backup_white_24dp);

        ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
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

    //只能用OnClickListener不能用OnCheckedChangeListener，因为最终会onBindViewHolder中调用notifyDataSetChanged ，
    // 因为我是在convert通过接口的形式传出数据，然后OnCheckedChangeListener这个会随着状态的改变自动调用onCheckedChanged，所以会循环调用自己
    // 所以会报Cannot call this method while RecyclerView is computing a layout or scrolling
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

                ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();


                TSnackbar snackBar = TSnackbar.make(viewGroup, "上传中，请稍后...", TSnackbar.LENGTH_LONG, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);
                snackBar.setPromptThemBackground(Prompt.SUCCESS);
                snackBar.addIconProgressLoading(0, true, false);
                snackBar.show();

                //开启旋转动画
                ivUpload.setImageResource(R.drawable.ic_sync_white_24dp);
                //不需要重复创建ObjectAnimator对象。
                if (animator == null) {
                    animator = ObjectAnimator.ofFloat(ivUpload, "rotation", 360f, 0f);
                    animator.setDuration(1000);
                    animator.setRepeatMode(Animation.RESTART);
                    animator.setRepeatCount(Animation.INFINITE);
                    animator.setInterpolator(new LinearInterpolator());
                }
                animator.start();
                break;
        }
    }
}
