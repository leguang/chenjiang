package com.shtoone.chenjiang.mvp.view.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;

import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.common.ActivityManager;
import com.shtoone.chenjiang.utils.NetworkUtils;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public abstract class BaseActivity<P extends BaseContract.Presenter> extends SwipeBackActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initStateBar();
        netWorkTips();
        mPresenter = createPresenter();
    }

    private void initStateBar() {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @NonNull
    protected abstract P createPresenter();

    private void initActivity() {
        //把每一个Activity加入栈中
        ActivityManager.getInstance().addActivity(this);

        //一旦启动某个Activity就打印Log，方便找到该类
        KLog.e(getClass().getName());

        //左侧滑动退出设置
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
    }

    public void netWorkTips() {
        if (!NetworkUtils.isConnected(getApplicationContext())) {
            View view = getWindow().getDecorView();
            Snackbar mSnackbar = Snackbar.make(view, "当前网络已断开！", Snackbar.LENGTH_LONG)
                    .setAction("设置网络", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 跳转到系统的网络设置界面
                            NetworkUtils.openSetting(BaseActivity.this);
                        }
                    });
            View v = mSnackbar.getView();
            v.setBackgroundColor(Color.parseColor("#FFCC00"));
            mSnackbar.show();
        }
    }

    @Override
    public boolean swipeBackPriority() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {

            mPresenter.detachView();
            mPresenter = null;
        }
        //把每一个Activity弹出栈
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
//        return new FragmentAnimator(0, 0, 0, 0);
        return new FragmentAnimator(android.R.anim.fade_in, android.R.anim.fade_out, 0, 0);
    }
}
