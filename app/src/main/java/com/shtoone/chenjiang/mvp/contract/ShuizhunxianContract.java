package com.shtoone.chenjiang.mvp.contract;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;

import java.util.List;
import java.util.Map;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface ShuizhunxianContract {
    interface View extends BaseContract.View {
        void responseData(Map<String, String[]> map);

        void responseStaffData(List<String> mStaffData);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestStaffData();
    }
}
