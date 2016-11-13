package com.shtoone.chenjiang.mvp.view.main;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.AddShuizhunxianContract;
import com.shtoone.chenjiang.mvp.presenter.AddShuizhunxianPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.ToastUtils;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class AddShuizhunxianFragment extends BaseFragment<AddShuizhunxianContract.Presenter> implements AddShuizhunxianContract.View {

    private static final String TAG = AddShuizhunxianFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    private ImageView iv;

    public static AddShuizhunxianFragment newInstance() {
        return new AddShuizhunxianFragment();
    }

    @NonNull
    @Override
    protected AddShuizhunxianContract.Presenter createPresenter() {
        return new AddShuizhunxianPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_shuizhunxian, container, false);
        iv = (ImageView) inflater.inflate(R.layout.menu_item_sync, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("添加水准线");
        initToolbarBackNavigation(toolbar);
//        initPageStateLayout(pagestatelayout);

        toolbar.inflateMenu(R.menu.menu_save);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        KLog.e("111111111111");
                        startAnimation(item);
                        ToastUtils.showToast(getContext(), "保存中……………………");
                        break;
                }
                return true;
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KLog.e("iv^^^^^^^^^^^^^");
                iv.clearAnimation();
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu_save);
            }
        });
    }

    private void startAnimation(MenuItem item) {
        iv.setImageResource(R.drawable.ic_sync_white_24dp);
        item.setActionView(iv);
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
        animator.setDuration(1000);
        animator.setRepeatMode(Animation.RESTART);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
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
}
