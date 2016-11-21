package com.shtoone.chenjiang.mvp.contract.upload;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface UploadContract {
    interface View extends BaseContract.View {
        void responseGongdianData(List<GongdianData> mGongdianData);

        void responseShuizhunxianData(List<ShuizhunxianData> mShuizhunxianData, int pagination);

        void onUploaded(int intMessageType, String strMessage);

    }

    interface Presenter extends BaseContract.Presenter {
        void requestShuizhunxianData(int pagination);

        void upload(List<ShuizhunxianData> listShuizhunxian);

    }
}
