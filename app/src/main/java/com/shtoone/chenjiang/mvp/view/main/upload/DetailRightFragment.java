package com.shtoone.chenjiang.mvp.view.main.upload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.project.JidianFragment;
import com.shtoone.chenjiang.mvp.view.main.project.ShuizhunxianFragment;
import com.shtoone.chenjiang.mvp.view.main.project.StaffFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DetailRightFragment extends BaseFragment {
    private static final String TAG = DetailRightFragment.class.getSimpleName();
    @BindView(R.id.bn_detail_right_fragment)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private int bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private ShuizhunxianData mShuizhunxianData;

    public static DetailRightFragment newInstance(ShuizhunxianData mShuizhunxianData) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.YUSHESHUIZHUNXIAN, mShuizhunxianData);

        DetailRightFragment fragment = new DetailRightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mShuizhunxianData = (ShuizhunxianData) args.getSerializable(Constants.YUSHESHUIZHUNXIAN);
        }
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_right, container, false);
        ButterKnife.bind(this, view);
        //重设toolbar的paddingTop值，以填补状态栏高度
        initStateBar(toolbar);
        if (savedInstanceState == null) {
            mFragments[0] = DetailDataFragment.newInstance(mShuizhunxianData);
            mFragments[1] = OriginalDataFragment.newInstance();
            mFragments[2] = ResultDataFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_detail_right_fragment, 0, mFragments[0], mFragments[1], mFragments[2]);
        } else {
            mFragments[0] = findFragment(ShuizhunxianFragment.class);
            mFragments[1] = findFragment(StaffFragment.class);
            mFragments[2] = findFragment(JidianFragment.class);
        }
        initData();
        return view;
    }

    public void initData() {
        toolbar.setTitle("详情");
        ((DetailFragment) getParentFragment()).initToolbartoggle(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制侧滑的开与关
                ((DetailFragment) getParentFragment()).toggle();
            }
        });

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.detail_data, R.drawable.ic_statistic, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.original_data, R.drawable.ic_friends, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.result_data, R.drawable.ic_nearby, R.color.white);
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
