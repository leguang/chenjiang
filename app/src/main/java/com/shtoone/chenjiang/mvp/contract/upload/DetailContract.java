package com.shtoone.chenjiang.mvp.contract.upload;

import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface DetailContract {
    interface View extends BaseContract.View {
        void responseCezhanData(List<CezhanData> listCezhan);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestCezhanData(ShuizhunxianData mShuizhunxianData);
    }
}
