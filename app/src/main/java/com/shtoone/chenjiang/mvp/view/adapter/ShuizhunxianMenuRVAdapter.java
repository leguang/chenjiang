package com.shtoone.chenjiang.mvp.view.adapter;

import android.graphics.Color;
import android.util.SparseBooleanArray;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ShuizhunxianMenuRVAdapter extends BaseQuickAdapter<YusheshuizhunxianData, BaseViewHolder> {
    private static final String TAG = ShuizhunxianMenuRVAdapter.class.getSimpleName();
    private SparseBooleanArray mBooleanArray = new SparseBooleanArray();
    private int mLastCheckedPosition = 0;

    public ShuizhunxianMenuRVAdapter() {
        super(R.layout.item_rv_project_menu_fragment, null);
    }

    @Override
    public void setNewData(List<YusheshuizhunxianData> data) {
        super.setNewData(data);
        mBooleanArray.clear();
        mBooleanArray = new SparseBooleanArray(data.size());
        mLastCheckedPosition = 0;
    }

    @Override
    protected void convert(BaseViewHolder holder, YusheshuizhunxianData item) {
        if (!mBooleanArray.get(holder.getLayoutPosition() - this.getHeaderLayoutCount())) {
            holder.setVisible(R.id.view_line_item_rv_project_menu_fragment, false)
                    .setTextColor(R.id.tv_item_rv_project_menu_fragment, Color.BLACK);
            holder.itemView.setBackgroundResource(R.color.light_gray);
        } else {
            holder.setVisible(R.id.view_line_item_rv_project_menu_fragment, true)
                    .setTextColor(R.id.tv_item_rv_project_menu_fragment, R.color.colorAccent);
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.setText(R.id.tv_item_rv_project_menu_fragment, item.getXianlubianhao());
    }

    public void setItemChecked(int position) {
        KLog.e("选中：：" + position);
        mBooleanArray.put(mLastCheckedPosition, false);
        mLastCheckedPosition = position;
        mBooleanArray.put(position, true);
        notifyDataSetChanged();
        EventBus.getDefault().post(mData.get(position));
    }
}
