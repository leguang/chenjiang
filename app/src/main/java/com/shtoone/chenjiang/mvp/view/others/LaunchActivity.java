package com.shtoone.chenjiang.mvp.view.others;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseActivity;
import com.shtoone.chenjiang.utils.ToastUtils;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class LaunchActivity extends BaseActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_launch_activity, SplashFragment.newInstance());
        }

        checkUpdate();
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }


    private void checkUpdate() {

    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }

    @Override
    public void onBackPressedSupport() {

        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtils.showInfoToast(getApplicationContext(), Constants.PRESS_AGAIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
