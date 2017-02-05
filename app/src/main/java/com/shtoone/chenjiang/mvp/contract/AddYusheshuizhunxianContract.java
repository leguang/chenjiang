package com.shtoone.chenjiang.mvp.contract;

import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;

import java.util.List;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface AddYusheshuizhunxianContract {
    interface View extends BaseContract.View {
        void responseJidian(String[] mJidianData);

        void responseCedian(String[] mCedianData);

        void responseStaff(List<String> mStaffData);

        void responseSave(int rowsAffected);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestJidian();

        void requestCedian();

        void requestStaff();

        void save(YusheshuizhunxianData mYusheshuizhunxianData, String dateTime);
    }
}
