package com.shtoone.chenjiang.mvp.view.main.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseLazyFragment;
import com.shtoone.chenjiang.utils.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ShuizhunxianFragment extends BaseLazyFragment {
    private static final String TAG = ShuizhunxianFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_shuizhunxian_fragment)
    LinearLayout ll;

    public static ShuizhunxianFragment newInstance() {
        return new ShuizhunxianFragment();
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
        ll.setPadding(0, 0, 0, DensityUtils.dp2px(_mActivity, 56));
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
        loadRootFragment(R.id.fl_menu_shuizhunxian_fragment, ShuizhunxianMenuFragment.newInstance());
        replaceLoadRootFragment(R.id.fl_content_shuizhunxian_fragment, ShuizhunxianContentFragment.newInstance(), false);
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
    public void switchContentFragment(SupportFragment fragment) {
        SupportFragment contentFragment = findChildFragment(GongdianContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }
}
