package com.shtoone.chenjiang.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.entity.db.ResultData;
/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasuredResultDataRVAdapter extends BaseQuickAdapter<ResultData, BaseViewHolder> {
    private static final String TAG = MeasuredResultDataRVAdapter.class.getSimpleName();

    public MeasuredResultDataRVAdapter() {
        super(R.layout.item_rv_measured_result_data_fragment, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, ResultData mResultData) {
        holder.setText(R.id.tv_number_item_rv_measured_result_data_fragment, mResultData.getNumber())
                .setText(R.id.tv_state_item_rv_measured_result_data_fragment, mResultData.getState())
                .setText(R.id.tv_date_item_rv_measured_result_data_fragment, mResultData.getDate())
                .setText(R.id.tv_gongkuang_item_rv_measured_result_data_fragment, mResultData.getGongkuang())
                .setText(R.id.tv_xiuzhengzhi_item_rv_measured_result_data_fragment, mResultData.getXiuzhengzhi())
                .setText(R.id.tv_chengguozhi_item_rv_measured_result_data_fragment, mResultData.getChengguozhi())
                .setText(R.id.tv_yuyagaodu_item_rv_measured_result_data_fragment, mResultData.getYuyagaodu());
    }
}
