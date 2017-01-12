package com.shtoone.chenjiang.mvp.contract.project;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface ShuizhunxianMenuContract {
    interface View extends BaseContract.View {
        void responseShuizhunxian(List<YusheshuizhunxianData> mYusheshuizhunxianData, int pagination);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestShuizhunxian(int pagination);
    }
}
