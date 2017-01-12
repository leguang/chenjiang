package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class ProjectActivity extends BaseActivity {
    private static final String TAG = ProjectActivity.class.getSimpleName();
    @BindView(R.id.bottom_navigation_project_activity)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.fl_container_project_activity)
    FrameLayout flContainer;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private int bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            mFragments[0] = ShuizhunxianFragment.newInstance();
            mFragments[1] = StaffFragment.newInstance();
            mFragments[2] = JidianFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_project_activity, 0, mFragments[0], mFragments[1], mFragments[2]);
        } else {
            mFragments[0] = findFragment(ShuizhunxianFragment.class);
            mFragments[1] = findFragment(StaffFragment.class);
            mFragments[2] = findFragment(JidianFragment.class);
        }
        initView();
        initData();
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    private void initView() {
    }

    public void initData() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.shuizhunxian, R.drawable.ic_statistic, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.renyuan, R.drawable.ic_friends, R.color.material_yellow_100);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.jidian, R.drawable.ic_nearby, R.color.material_green_100);
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
}
