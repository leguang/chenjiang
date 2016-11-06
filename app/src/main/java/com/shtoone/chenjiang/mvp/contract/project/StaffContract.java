package com.shtoone.chenjiang.mvp.contract.project;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.bean.StaffData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface StaffContract {
    interface View extends BaseContract.View {
        void refresh(List<StaffData> mStaffData, int pagination);

    }

    interface Presenter extends BaseContract.Presenter {
        void queryData(int pagination);
    }
}
