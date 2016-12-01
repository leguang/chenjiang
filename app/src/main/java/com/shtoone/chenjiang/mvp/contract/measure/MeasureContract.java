package com.shtoone.chenjiang.mvp.contract.measure;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface MeasureContract {
    interface View extends BaseContract.View {
        void responseJidianData(List<String> listJidianBianhao);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestJidianData();
    }
}
