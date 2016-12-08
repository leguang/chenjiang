package com.shtoone.chenjiang.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;

public class MeasureRVPAdapter extends BaseQuickAdapter<CezhanData, BaseViewHolder> {
    private static final String TAG = MeasureRVPAdapter.class.getSimpleName();

    public MeasureRVPAdapter() {
        super(R.layout.item_rvp_measure_right_fragment, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, CezhanData mCezhanData) {
        int position = holder.getLayoutPosition() - this.getHeaderLayoutCount();
        mCezhanData.setNumber((position + 1) + "");
        holder.setText(R.id.tv_number_item_rvp_measure_right_fragment, ++position + "");
        holder.setText(R.id.tv_measure_direction_item_rvp_measure_right_fragment, mCezhanData.getMeasureDirection())
                .setText(R.id.tv_qianshi_item_rvp_measure_right_fragment, "前视:" + mCezhanData.getQianshi())
                .setText(R.id.tv_houshi_item_rvp_measure_right_fragment, "后视:" + mCezhanData.getHoushi())
                .setText(R.id.tv_observe_type_item_rvp_measure_right_fragment, mCezhanData.getObserveType())
                .setText(R.id.tv_b1hd, mCezhanData.getB1hd())
                .setText(R.id.tv_houshijuhe, mCezhanData.getHoushi())
                .setText(R.id.tv_b2hd, mCezhanData.getB2hd())
                .setText(R.id.tv_qianshijuhe, mCezhanData.getQianshijuhe())
                .setText(R.id.tv_f1hd, mCezhanData.getF1hd())
                .setText(R.id.tv_gaocha1, mCezhanData.getGaocha1())
                .setText(R.id.tv_f2hd, mCezhanData.getF2hd())
                .setText(R.id.tv_gaocha2, mCezhanData.getGaocha2())
                .setText(R.id.tv_b1r, mCezhanData.getB1r())
                .setText(R.id.tv_cezhangaocha, mCezhanData.getCezhangaocha())
                .setText(R.id.tv_b2r, mCezhanData.getB2r())
                .setText(R.id.tv_gaochengzhi, mCezhanData.getGaochengzhi())
                .setText(R.id.tv_f1r, mCezhanData.getF1r())
                .setText(R.id.tv_frdushucha, mCezhanData.getFrdushucha())
                .setText(R.id.tv_f2r, mCezhanData.getF2r())
                .setText(R.id.tv_brdushucha, mCezhanData.getBrdushucha());
    }
}
