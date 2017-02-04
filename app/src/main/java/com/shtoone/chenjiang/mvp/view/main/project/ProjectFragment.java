package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ProjectFragment extends BaseFragment {
    private static final String TAG = ProjectFragment.class.getSimpleName();
    @BindView(R.id.bottom_navigation_project_fragment)
    AHBottomNavigation bottomNavigation;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private int bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments = new SupportFragment[3];

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            mFragments[0] = ShuizhunxianFragment.newInstance();
            mFragments[1] = StaffFragment.newInstance();
            mFragments[2] = JidianFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_project_fragment, 0, mFragments[0], mFragments[1], mFragments[2]);
        } else {
            mFragments[0] = findFragment(ShuizhunxianFragment.class);
            mFragments[1] = findFragment(StaffFragment.class);
            mFragments[2] = findFragment(JidianFragment.class);
        }
        initData();
        return view;
    }

    public void initData() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.shuizhunxian, R.drawable.ic_statistic, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.renyuan, R.drawable.ic_friends, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.jidian, R.drawable.ic_nearby, R.color.white);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setColored(true);
        bottomNavigation.setForceTint(false);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.base_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));
        bottomNavigation.setForceTitlesDisplay(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, final boolean wasSelected) {

                showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
                bottomNavigationPreposition = position;

            }
        });
        bottomNavigation.setCurrentItem(0);
    }

    public void startFragment(SupportFragment toFragment) {
        start(toFragment);
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SupportFragment toFragment) {
        if (toFragment != null) {
            start(toFragment);
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
}
