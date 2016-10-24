package com.shtoone.chenjiang.event;

import android.view.View;

import java.util.Calendar;

/**
 * Created by leguang on 2016/8/15 0015.
 * 其实这个防止多点击事件处理在我们项目里是没有必要的，
 * 因为我们项目当中点击都是为了访问网络，
 * 而下拉刷新里本事就有判断正在下拉下刷新的时候是不允许重复。
 */
public abstract class NoMultiClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoMultiClick(view);
        }
    }

    public abstract void onNoMultiClick(View view);
}
