package com.shtoone.chenjiang.mvp.view.main.upload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.measure.LayoutAdapter;
import com.shtoone.chenjiang.mvp.view.main.measure.MeasureFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class CheckMeasureRightFragment extends BaseFragment {

    private static final String TAG = CheckMeasureRightFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvp_check_measure_fragment)
    RecyclerViewPager rvp;
    @BindView(R.id.fab_check_measure_fragment)
    FloatingActionButton fab;


    public static CheckMeasureRightFragment newInstance() {
        return new CheckMeasureRightFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_measure_right, container, false);
        ButterKnife.bind(this, view);
        //重设toolbar的paddingTop值，以填补状态栏高度
        initStateBar(toolbar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initViewPager();

    }

    private void initToolbar() {
        toolbar.setTitle("测量");
//        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制侧滑的开与关
                ((MeasureFragment) getParentFragment()).toggle();
            }
        });
    }

    private void initData() {

    }

    protected void initViewPager() {
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false);
        //数字越大，触发滚向下一页所需偏移就越大
        rvp.setTriggerOffset(0.1f);
        //数字越大，就越容易滚动
        rvp.setFlingFactor(0.0f);
        rvp.setLayoutManager(layout);
        rvp.setAdapter(new LayoutAdapter(_mActivity, rvp));
        rvp.setHasFixedSize(true);
        rvp.setLongClickable(true);
    }

    @OnClick(R.id.fab_check_measure_fragment)
    public void onClick() {
        //获取fab在屏幕上的绝对坐标。
        int[] location = new int[2];
        fab.getLocationOnScreen(location);
        int radius = fab.getWidth() / 2;
        //将坐标传给下一个fragment。
        ((CheckMeasureFragment) getParentFragment()).startFragment(MeasuredDataFragment.newInstance(location[0] + radius, location[1] + radius));
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }
}
