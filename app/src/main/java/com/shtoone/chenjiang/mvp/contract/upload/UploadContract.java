package com.shtoone.chenjiang.mvp.contract.upload;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface UploadContract {
    interface View extends BaseContract.View {
        void responseShuizhunxianData(List<YusheshuizhunxianData> mShuizhunxianData, int pagination);

        void onUploaded(int intMessageType, String strMessage);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestShuizhunxianData(int pagination);

        void upload(List<YusheshuizhunxianData> listShuizhunxian);
    }
}
