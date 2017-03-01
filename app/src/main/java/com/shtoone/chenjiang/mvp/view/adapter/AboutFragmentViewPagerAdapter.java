package com.shtoone.chenjiang.mvp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.chenjiang.mvp.view.main.about.AboutFragmentViewPagerFragment;


/**
 * Created by leguang on 2016/6/9 0009.
 */
public class AboutFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = AboutFragmentViewPagerAdapter.class.getSimpleName();
    private String[] title = {"关于APP", "关于我们"};
    private FragmentManager fragmentManager;
    private AboutFragmentViewPagerFragment mfragment;

    public AboutFragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager=fm;
    }

    @Override
    public Fragment getItem(int position) {
        if (null != title && title.length > 0) {
            mfragment= AboutFragmentViewPagerFragment.newInstance(position);
            return mfragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        if (null != title && title.length > 0) {
            return title.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != title && title.length > 0) {
            return title[position];
        }
        return null;
    }


}
