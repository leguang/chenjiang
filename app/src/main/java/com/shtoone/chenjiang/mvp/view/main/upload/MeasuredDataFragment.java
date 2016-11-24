package com.shtoone.chenjiang.mvp.view.main.upload;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.adapter.MeasuredDataFragmentVPAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasuredDataFragment extends BaseFragment {

    private static final String TAG = MeasuredDataFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar_tablayout)
    Toolbar toolbar;
    @BindView(R.id.tablayout_toolbar_tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp_measured_data_fragment)
    ViewPager vp;
    @BindView(R.id.ll_measured_data_fragment)
    CoordinatorLayout ll;
    @BindView(R.id.appbar_toolbar_tablayout)
    AppBarLayout appbar;
    private int x;
    private int y;

    public static MeasuredDataFragment newInstance(int x, int y) {
        Bundle args = new Bundle();
        args.putInt(Constants.FROMTO_X, x);
        args.putInt(Constants.FROMTO_Y, y);

        MeasuredDataFragment fragment = new MeasuredDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            x = args.getInt(Constants.FROMTO_X);
            y = args.getInt(Constants.FROMTO_Y);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //显示上一个fragment。这样才会让动画看起来是在上一个fragment的基础上reveal出一个view来。
        getFragmentManager().beginTransaction()
                .show(getPreFragment())
                .commit();
        View view = inflater.inflate(R.layout.fragment_measured_data, container, false);
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        ((MainActivity) _mActivity).closeDrawer();
        ButterKnife.bind(this, view);
        initStateBar(appbar);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        initToolbarBackNavigation(toolbar);
        toolbar.setTitle("数据详情");

        vp.setAdapter(new MeasuredDataFragmentVPAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(vp);
        revealShow();
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    private void revealShow() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ll.setVisibility(View.VISIBLE);
            return;
        }
        ll.post(new Runnable() {
            @Override
            public void run() {
                int w = ll.getWidth();
                int h = ll.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);
                Animator anim = ViewAnimationUtils.createCircularReveal(ll, x, y, 0, finalRadius);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(1000);
                anim.start();
            }
        });
    }
}
