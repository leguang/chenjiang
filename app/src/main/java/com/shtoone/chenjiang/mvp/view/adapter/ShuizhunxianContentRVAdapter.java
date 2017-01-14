package com.shtoone.chenjiang.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ShuizhunxianContentRVAdapter extends BaseQuickAdapter<ShuizhunxianData, BaseViewHolder> {
    private static final String TAG = ShuizhunxianContentRVAdapter.class.getSimpleName();

    public ShuizhunxianContentRVAdapter() {
        super(R.layout.item_rv_project_content_fragment, null);
    }

    @Override
    public void setNewData(List<ShuizhunxianData> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ShuizhunxianData item) {
        holder.setText(R.id.tv_observe_time_item_rv_project_content_fragment, item.getChuangjianshijian())
                .setText(R.id.tv_route_type_item_rv_project_content_fragment, "路线类型：" + item.getRouteType())
                .setText(R.id.tv_observe_type_item_rv_project_content_fragment, "观测类型：" + item.getObserveType());
    }
}
