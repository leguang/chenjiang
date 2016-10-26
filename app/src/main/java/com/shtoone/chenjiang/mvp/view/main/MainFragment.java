package com.shtoone.chenjiang.mvp.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.MainContract;
import com.shtoone.chenjiang.mvp.model.bean.LevelLineData;
import com.shtoone.chenjiang.mvp.presenter.MainPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.ListDropDownAdapter;
import com.shtoone.chenjiang.mvp.view.adapter.YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.ToastUtils;
import com.shtoone.chenjiang.widget.PageStateLayout;
import com.yyydjk.library.DropDownMenu;

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
    private YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter mAdapter;

    private String headers[] = {"工点", "测量", "时间"};
    private String citys[] = {"不限", "武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉武汉", "北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京北京", "上海上海上海上海上海上海上海上海上海上海上海上海上海上海上海上海上海上海上海上海上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州", "不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "待测量", "待平差", "已删除"};
    private String sexs[] = {"不限", "近一周", "近一月"};
    private List<View> popupViews = new ArrayList<>();

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
        return view;
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
                        ToastUtils.showToast(getContext(), "添加……………………");
                        break;
                }
                return true;
            }
        });

        //init city menu
        ListView cityView = new ListView(_mActivity);
        final ListDropDownAdapter cityAdapter = new ListDropDownAdapter(_mActivity, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        ListView ageView = new ListView(_mActivity);
        ageView.setDividerHeight(0);
        final ListDropDownAdapter ageAdapter = new ListDropDownAdapter(_mActivity, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        ListView sexView = new ListView(_mActivity);
        sexView.setDividerHeight(0);
        final ListDropDownAdapter sexAdapter = new ListDropDownAdapter(_mActivity, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);


        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                dropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                dropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                dropDownMenu.closeMenu();
            }
        });

        //init context view
        TextView contentView = new TextView(_mActivity);

        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


        View view = View.inflate(_mActivity, R.layout.recyclerview, null);
        pagestatelayout = (PageStateLayout) view.findViewById(R.id.pagestatelayout);
        ptrframelayout = (PtrFrameLayout) view.findViewById(R.id.ptrframelayout);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);

        //init dropdownview
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view);

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
    public void showContent() {
        pagestatelayout.showContent();
    }

    @Override
    public void showError() {
        pagestatelayout.showError();
    }

    @Override
    public void showLoading() {
        pagestatelayout.showLoading();
    }

    @Override
    public void refresh(List<LevelLineData> levelLineDatas) {
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter(_mActivity, levelLineDatas));
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        recyclerview.setAdapter(mScaleInAnimationAdapter);
    }

}
