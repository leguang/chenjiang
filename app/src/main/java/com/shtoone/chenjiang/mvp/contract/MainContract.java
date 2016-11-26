package com.shtoone.chenjiang.mvp.contract;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface MainContract {
    interface View extends BaseContract.View {
        void responseGongdianData(List<GongdianData> mGongdianData);

        void responseShuizhunxianData(List<YusheshuizhunxianData> mShuizhunxianData, int pagination);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestGongdianData();

        void requestShuizhunxianData(int pagination, String strGongdianParam, String strMeasureStatusParam, String strTimeTypeParam);
    }
}
