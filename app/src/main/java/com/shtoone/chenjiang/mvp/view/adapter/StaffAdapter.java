package com.shtoone.chenjiang.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.bean.StaffData;

public class StaffAdapter extends BaseQuickAdapter<StaffData, BaseViewHolder> {
    private static final String TAG = StaffAdapter.class.getSimpleName();

    public StaffAdapter() {
        super(R.layout.item_rv_project_staff_fragment, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, StaffData staffData) {

        holder.setText(R.id.tv_name_item_rv_project_staff_fragment, "姓名：" + staffData.getName())
                .setText(R.id.tv_type_item_rv_project_staff_fragment, "类别：" + staffData.getType())
                .setText(R.id.tv_phone_number_item_rv_project_staff_fragment, "电话：" + staffData.getPhtoneNumber());
    }
}
