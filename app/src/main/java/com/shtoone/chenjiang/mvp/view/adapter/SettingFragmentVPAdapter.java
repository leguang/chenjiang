package com.shtoone.chenjiang.mvp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.chenjiang.mvp.view.main.setting.DeviceSettingFragment;
import com.shtoone.chenjiang.mvp.view.main.setting.ParamSettingFragment;
import com.socks.library.KLog;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class SettingFragmentVPAdapter extends FragmentPagerAdapter {
    private static final String TAG = SettingFragmentVPAdapter.class.getSimpleName();
    private String[] arrTabTitle = {"设备设置", "参数设置"};

    public SettingFragmentVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        KLog.e("position::" + position);
        if (null != arrTabTitle && arrTabTitle.length > 0) {
            if (0 == position) {
                return DeviceSettingFragment.newInstance();
            } else if (1 == position) {
                return ParamSettingFragment.newInstance();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        if (null != arrTabTitle && arrTabTitle.length > 0) {
            return arrTabTitle.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != arrTabTitle && arrTabTitle.length > 0) {
            return arrTabTitle[position];
        }
        return null;
    }
}
