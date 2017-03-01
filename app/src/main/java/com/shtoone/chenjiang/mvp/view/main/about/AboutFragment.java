package com.shtoone.chenjiang.mvp.view.main.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.about.AboutContract;
import com.shtoone.chenjiang.mvp.presenter.about.AboutPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.AboutFragmentViewPagerAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/22.
 */
public class AboutFragment extends BaseFragment<AboutContract.Presenter> implements AboutContract.View {


    @BindView(R.id.toolbar_toolbar_tablayout)
    Toolbar toolbarToolbarTablayout;
    @BindView(R.id.tablayout_toolbar_tablayout)
    TabLayout tablayoutToolbarTablayout;
    @BindView(R.id.appbar_toolbar_tablayout)
    AppBarLayout appbarToolbarTablayout;
    @BindView(R.id.vp_about_activity)
    ViewPager vpAboutActivity;

    private FragmentManager fragmentManager;
    private AboutFragmentViewPagerAdapter madapter;

    @NonNull
    @Override
    protected AboutContract.Presenter createPresenter() {
        return new AboutPresenter(this);
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        ((MainActivity) _mActivity).closeDrawer();
        ButterKnife.bind(this, view);
        initStateBar(toolbarToolbarTablayout);
        return attachToSwipeBack(view);
    }

    @Override
    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Field field = madapter.getClass().getDeclaredField("mfragment");
                    field.setAccessible(true);
                    Fragment mFragment = (Fragment) field.get(madapter);
                    if (mFragment != null) {
                        madapter.destroyItem(vpAboutActivity, 0, mFragment);
                        madapter.finishUpdate(vpAboutActivity);//提交事务
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // fragmentManager.popBackStackImmediate(1,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                _mActivity.onBackPressedSupport();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
    }

    private void initDate() {
        fragmentManager = getChildFragmentManager();
        madapter = new AboutFragmentViewPagerAdapter(fragmentManager);
        toolbarToolbarTablayout.setTitle("关于");
        initToolbarBackNavigation(toolbarToolbarTablayout);
        vpAboutActivity.setAdapter(madapter);
        tablayoutToolbarTablayout.setupWithViewPager(vpAboutActivity);
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {

    }

    @Override
    public void showLoading() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

//        _mActivity.logFragmentStackHierarchy("0000000000000000000");
//        _mActivity.showFragmentStackHierarchyView();
    }
}
