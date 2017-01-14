package com.shtoone.chenjiang.mvp.contract;

import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface AddYusheshuizhunxianContract {
    interface View extends BaseContract.View {
        void responseJidian(String[] mJidianData);

        void responseCedian(String[] mCedianData);

        void responseSave(int rowsAffected);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestJidian();

        void requestCedian();

        void save(YusheshuizhunxianData mYusheshuizhunxianData, String dateTime);
    }
}
