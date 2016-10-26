package com.shtoone.chenjiang.mvp.contract.base;

import android.support.annotation.UiThread;

/**
 * Author：leguang on 2016/10/10 0010 20:44
 * Email：langmanleguang@qq.com
 */
public interface BaseContract {
    interface View {
        void showContent();

        void showError();

        void showLoading();
    }

    interface Presenter {

        @UiThread
        void detachView();

        void start();

    }

    interface Model {
        void start();
    }
}
