package com.shtoone.chenjiang.mvp.contract.upload;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface MeasuredDataListContract {
    interface View extends BaseContract.View {
        void response(List<Class> mListData, int pagination);
    }

    interface Presenter extends BaseContract.Presenter {
        void request(Class modelClass, int pagination);
    }
}
