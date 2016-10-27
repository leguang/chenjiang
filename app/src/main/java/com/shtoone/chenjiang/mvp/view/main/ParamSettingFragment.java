package com.shtoone.chenjiang.mvp.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;

import butterknife.BindView;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ParamSettingFragment extends BaseFragment {

    private static final String TAG = ParamSettingFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar_tablayout)
    Toolbar toolbar;
    @BindView(R.id.vp_setting_fragment)
    ViewPager vp;

    public static ParamSettingFragment newInstance() {
        return new ParamSettingFragment();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_param_setting, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {

    }


    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }
}
