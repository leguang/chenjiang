package com.shtoone.chenjiang.mvp.contract;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.model.bean.YusheshuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface MainContract {
    interface View extends BaseContract.View {
        void responseGongdianData(List<GongdianData> mGongdianData);

        void responseShuizhunxianData(List<YusheshuizhunxianData> mShuizhunxianData);
    }

    interface Presenter extends BaseContract.Presenter {


    }

}
