package com.shtoone.chenjiang.mvp.view.adapter;

import android.graphics.Color;
import android.util.SparseBooleanArray;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.socks.library.KLog;

import java.util.List;

public class GongdianMenuAdapter extends BaseQuickAdapter<GongdianData, BaseViewHolder> {
    private static final String TAG = GongdianMenuAdapter.class.getSimpleName();
    private SparseBooleanArray mBooleanArray = new SparseBooleanArray();
    private int mLastCheckedPosition = -1;

    public GongdianMenuAdapter() {
        super(R.layout.item_rv_project_menu_fragment, null);
    }

    @Override
    public void setNewData(List<GongdianData> data) {
        super.setNewData(data);
        mBooleanArray.clear();
        mBooleanArray = new SparseBooleanArray(data.size());
        mLastCheckedPosition = -1;
    }

    @Override
    protected void convert(BaseViewHolder holder, GongdianData item) {
        if (!mBooleanArray.get(holder.getLayoutPosition() - this.getHeaderLayoutCount())) {
            holder.setVisible(R.id.view_line_item_rv_project_menu_fragment, false)
                    .setTextColor(R.id.tv_item_rv_project_menu_fragment, Color.BLACK);
            holder.itemView.setBackgroundResource(R.color.light_gray);
        } else {
            holder.setVisible(R.id.view_line_item_rv_project_menu_fragment, true)
                    .setTextColor(R.id.tv_item_rv_project_menu_fragment, R.color.colorAccent);
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.setText(R.id.tv_item_rv_project_menu_fragment, item.getName());
    }

    public void setItemChecked(int position) {

        KLog.e("选中：：" + position);
        mBooleanArray.put(position, true);
        if (mLastCheckedPosition > -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
        }
        notifyDataSetChanged();
        mLastCheckedPosition = position;
    }
}
