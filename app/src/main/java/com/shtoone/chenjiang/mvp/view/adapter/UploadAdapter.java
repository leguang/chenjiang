package com.shtoone.chenjiang.mvp.view.adapter;


import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.view.adapter.base.OnItemClickListener;

import java.util.List;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadAdapter extends BaseQuickAdapter<ShuizhunxianData, BaseViewHolder> {

    private static final String TAG = UploadAdapter.class.getSimpleName();

    private List<ShuizhunxianData> listChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnItemClickListener mOnItemClickListener;


    public UploadAdapter(List<ShuizhunxianData> listChecked) {
        super(R.layout.item_rv_upload_fragment, null);
        this.listChecked = listChecked;
    }

    @Override
    protected void convert(final BaseViewHolder holder, ShuizhunxianData shuizhunxianData) {
        final int position = holder.getLayoutPosition() - this.getHeaderLayoutCount();
        holder.setText(R.id.tv_bianhao_item_rv_upload_fragment, shuizhunxianData.getBianhao())
                .setText(R.id.tv_biaoduan_item_rv_upload_fragment, shuizhunxianData.getDepartName())
                .setText(R.id.tv_edittime_item_rv_upload_fragment, shuizhunxianData.getEditDate())
                .setText(R.id.tv_observe_time_item_rv_upload_fragment, shuizhunxianData.getObserveDate())
                .setText(R.id.tv_route_type_item_rv_upload_fragment, shuizhunxianData.getRouteType())
                .setText(R.id.tv_observe_type_item_rv_upload_fragment, shuizhunxianData.getObserveType())
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


//                .setOnCheckedChangeListener(R.id.cb_item_rv_upload_fragment, new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        KLog.e("onCheckedChanged::"+"11111111111111");
//
//                    }
//                });

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

    //之所以要复写，是因为原框架的addData在加完数据之后自动
    @Override
    public void addData(List<ShuizhunxianData> newData) {
        super.addData(newData);
    }
}
