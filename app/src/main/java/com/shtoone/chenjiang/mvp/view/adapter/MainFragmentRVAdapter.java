package com.shtoone.chenjiang.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.utils.DrawableUtils;

public class MainFragmentRVAdapter extends BaseQuickAdapter<YusheshuizhunxianData, BaseViewHolder> {
    private static final String TAG = MainFragmentRVAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;

    public MainFragmentRVAdapter() {
        super(R.layout.item_rv_main_fragment, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, YusheshuizhunxianData yusheshuizhunxianData) {
        final int position = holder.getLayoutPosition() - this.getHeaderLayoutCount();
        holder.setText(R.id.tv_bianhao_item_rv_main_fragment, yusheshuizhunxianData.getXianlubianhao())
                .setText(R.id.tv_route_type_item_rv_main_fragment, yusheshuizhunxianData.getLeixing())
                .setText(R.id.tv_observe_type_item_rv_main_fragment, yusheshuizhunxianData.getJidianshu())
                .setText(R.id.tv_date_item_rv_main_fragment, yusheshuizhunxianData.getXiugaishijian())
                .setOnClickListener(R.id.iv_left_item_rv_main_fragment, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnItemClickListener == null) {
                            return;
                        }
                        mOnItemClickListener.onLeftClick(view, position);
                    }
                }).setOnClickListener(R.id.ll_middle_item_rv_main_fragment, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener == null) {
                    return;
                }
                mOnItemClickListener.onMiddleClick(view, position);
            }
        }).setOnClickListener(R.id.iv_right_item_rv_main_fragment, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener == null) {
                    return;
                }
                mOnItemClickListener.onRightClick(view, position);
            }
        });

        //默认下载的预设水准线都是未编辑状态，则把图标颜色都设置成灰色。
        ImageView mImageViewLeft = holder.getView(R.id.iv_left_item_rv_main_fragment);
        ImageView mImageViewRight = holder.getView(R.id.iv_right_item_rv_main_fragment);
        if (yusheshuizhunxianData.getStatus().equals(Constants.status_daibianji)) {
            mImageViewLeft.setImageDrawable(DrawableUtils.getTintDrawable(mImageViewLeft.getDrawable(), 0xff808080, true));
            mImageViewRight.setImageDrawable(DrawableUtils.getTintDrawable(mImageViewRight.getDrawable(), 0xff808080, true));
        } else {
            mImageViewLeft.setImageDrawable(DrawableUtils.getTintDrawable(mImageViewLeft.getDrawable(), 0xff4caf50, true));
            mImageViewRight.setImageDrawable(DrawableUtils.getTintDrawable(mImageViewRight.getDrawable(), 0xff4caf50, true));
        }
    }

    @Override
    public void remove(int position) {
        this.mData.remove(position);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onLeftClick(View view, int position);

        void onMiddleClick(View view, int position);

        void onRightClick(View view, int position);
    }
}
