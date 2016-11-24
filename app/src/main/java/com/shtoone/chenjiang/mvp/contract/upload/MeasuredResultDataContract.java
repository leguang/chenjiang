package com.shtoone.chenjiang.mvp.contract.upload;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ResultData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface MeasuredResultDataContract {
    interface View extends BaseContract.View {
        void response(List<ResultData> mResultData, int pagination);
    }

    interface Presenter extends BaseContract.Presenter {
        void request(int pagination);
    }
}
