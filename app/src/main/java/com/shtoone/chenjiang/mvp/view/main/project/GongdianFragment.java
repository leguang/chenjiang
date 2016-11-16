package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class GongdianFragment extends BaseLazyFragment {

    private static final String TAG = GongdianFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;


    public static GongdianFragment newInstance() {
        return new GongdianFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gongdian, container, false);
        ButterKnife.bind(this, view);
        initStateBar(toolbar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("项目查看");
        initToolbarBackNavigation(toolbar);
    }

    /**
     * 懒加载
     *
     * @param savedInstanceState
     */
    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            initData();
        }
    }

    private void initData() {
        loadRootFragment(R.id.fl_menu_project_fragment, GongdianMenuFragment.newInstance());
        replaceLoadRootFragment(R.id.fl_content_project_fragment, GongdianContentFragment.newInstance(), false);
    }


    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(GongdianContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(GongdianContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }


}
