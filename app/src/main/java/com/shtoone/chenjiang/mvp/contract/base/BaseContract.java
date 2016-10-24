package com.shtoone.chenjiang.mvp.contract.base;

import android.support.annotation.UiThread;

/**
 * Author：leguang on 2016/10/10 0010 20:44
 * Email：langmanleguang@qq.com
 */
public interface BaseContract {
    interface View {
    }

    interface Presenter {

        @UiThread
        void detachView();
    }

    interface Model {
        void start();
    }
}
