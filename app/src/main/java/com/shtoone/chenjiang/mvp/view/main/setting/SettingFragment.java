package com.shtoone.chenjiang.mvp.view.main.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.adapter.SettingFragmentVPAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;
import com.shtoone.chenjiang.utils.KeyBoardUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class SettingFragment extends BaseFragment {
    private static final String TAG = SettingFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar_tablayout)
    Toolbar toolbar;
    @BindView(R.id.vp_setting_fragment)
    ViewPager vp;
    @BindView(R.id.tablayout_toolbar_tablayout)
    TabLayout tablayout;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        //关闭抽屉菜单，使其无法滑动弹出，避免左划退出fragment时造成滑动冲突。
        ((MainActivity) _mActivity).closeDrawer();
        ButterKnife.bind(this, view);
        initStateBar(toolbar);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        initToolbar();
        initViewPager();
    }

    private void initToolbar() {
        if (toolbar == null) {
            return;
        }
        initToolbarBackNavigation(toolbar);
        toolbar.setTitle("系统设置");

        toolbar.inflateMenu(R.menu.menu_setting);
        toolbar.getMenu().findItem(R.id.action_save_param).setVisible(false);
        toolbar.getMenu().findItem(R.id.action_reset_param).setVisible(false);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_bluetooth_setting:
                        start(BluetoothFragment.newInstance());
                        break;
                    case R.id.action_save_param:
                        EventBus.getDefault().post(new EventData(Constants.EVENT_SAVE_PARAM));
                        KeyBoardUtils.hideKeybord(toolbar, BaseApplication.mContext);

                        break;
                    case R.id.action_reset_param:
                        EventBus.getDefault().post(new EventData(Constants.EVENT_RESET_SECOND_CLASS));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeybord(v,BaseApplication.mContext);
                _mActivity.onBackPressedSupport();
            }
        });

    }

    private void initViewPager() {
        vp.setAdapter(new SettingFragmentVPAdapter(getChildFragmentManager()));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbar.invalidate();
                if (position == 1) {
                    toolbar.getMenu().findItem(R.id.action_bluetooth_setting).setVisible(false);
                    toolbar.getMenu().findItem(R.id.action_save_param).setVisible(true);
                    toolbar.getMenu().findItem(R.id.action_reset_param).setVisible(true);
                } else if (position == 0) {
                    toolbar.getMenu().findItem(R.id.action_save_param).setVisible(false);
                    toolbar.getMenu().findItem(R.id.action_reset_param).setVisible(false);
                    toolbar.getMenu().findItem(R.id.action_bluetooth_setting).setVisible(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tablayout.setupWithViewPager(vp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //开启抽屉菜单，使其可以滑动弹出。
        KLog.e("onDestroyView");
        ((MainActivity) _mActivity).openDrawer();
    }


    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }
}
