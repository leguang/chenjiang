package com.shtoone.chenjiang.mvp.view.adapter;


import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.view.adapter.base.OnItemClickListener;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadRVAdapter extends BaseQuickAdapter<YusheshuizhunxianData, BaseViewHolder> {
    private static final String TAG = UploadRVAdapter.class.getSimpleName();
    private List<YusheshuizhunxianData> listChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnItemClickListener mOnItemClickListener;

    public UploadRVAdapter(List<YusheshuizhunxianData> listChecked) {
        super(R.layout.item_rv_upload_fragment, null);
        this.listChecked = listChecked;
    }

    @Override
    protected void convert(final BaseViewHolder holder, YusheshuizhunxianData mYusheshuizhunxianData) {
        final int position = holder.getLayoutPosition() - this.getHeaderLayoutCount();
        holder.setText(R.id.tv_bianhao_item_rv_upload_fragment, mYusheshuizhunxianData.getBiaoshi())
                .setText(R.id.tv_route_type_item_rv_upload_fragment, "路线类型：" + mYusheshuizhunxianData.getRouteType())
                .setText(R.id.tv_observe_type_item_rv_upload_fragment, "观测类型：" + mYusheshuizhunxianData.getObserveType())
                .setText(R.id.tv_observe_time_item_rv_upload_fragment, "观测时间：" + mYusheshuizhunxianData.getXiugaishijian())
                .setChecked(R.id.cb_item_rv_upload_fragment, listChecked.contains(mData.get(position)))
                .setOnClickListener(R.id.cb_item_rv_upload_fragment, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnCheckedChangeListener == null) {
                            return;
                        }
                        mOnCheckedChangeListener.onCheckedChanged((CompoundButton) holder.getView(R.id.cb_item_rv_upload_fragment), position);
                    }
                });

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton compoundButton, int position);
    }

    public void checkAllChanged(boolean b) {
        listChecked.clear();
        if (b) {
            listChecked.addAll(mData);
        }
        notifyDataSetChanged();
    }
}
