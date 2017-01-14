package com.shtoone.chenjiang.mvp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.chenjiang.mvp.view.main.upload.MeasuredOriginalDataFragment;
import com.shtoone.chenjiang.mvp.view.main.upload.MeasuredResultDataFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasuredDataFragmentVPAdapter extends FragmentPagerAdapter {
    private static final String TAG = MeasuredDataFragmentVPAdapter.class.getSimpleName();
    private String[] arrTabTitle = {"原始数据", "成果数据"};

    public MeasuredDataFragmentVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (null != arrTabTitle && arrTabTitle.length > 0) {
            if (position==0){
                return MeasuredOriginalDataFragment.newInstance();
            }else {
                return MeasuredResultDataFragment.newInstance();

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
