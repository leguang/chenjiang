package com.shtoone.chenjiang.mvp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.view.adapter.base.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class GongdianMenuFragmentRVAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = GongdianMenuFragmentRVAdapter.class.getSimpleName();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<GongdianData> itemsData = new ArrayList<GongdianData>();
    private SparseBooleanArray mBooleanArray;
    private int mLastCheckedPosition = -1;


    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }

    public GongdianMenuFragmentRVAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void setDatas(List<GongdianData> items) {
        itemsData.clear();
        itemsData.addAll(items);

        mBooleanArray = new SparseBooleanArray(items.size());
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (itemsData != null && itemsData.size() > 0) {
            //这里的10是根据分页查询，一页该显示的条数
            if (itemsData.size() > 4) {
                return itemsData.size() + 1;
            } else {
                return itemsData.size();
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 4 && position + 1 == getItemCount()) {
            return ITEM_TYPE.TYPE_FOOTER.ordinal();
        } else {
            return ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            if (!mBooleanArray.get(position)) {
                mItemViewHolder.viewLine.setVisibility(View.INVISIBLE);
                mItemViewHolder.itemView.setBackgroundResource(R.color.light_gray);
                mItemViewHolder.tv.setTextColor(Color.BLACK);
            } else {
                mItemViewHolder.viewLine.setVisibility(View.VISIBLE);
                mItemViewHolder.itemView.setBackgroundColor(Color.WHITE);
                mItemViewHolder.tv.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            }

            mItemViewHolder.tv.setText(itemsData.get(position).getName());

            if (mOnItemClickListener != null) {
                mItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_project_menu_fragment, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new FootViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer_loading, parent, false));
        }
        return null;
    }

    static class ItemViewHolder extends ViewHolder {
        View viewLine;
        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            viewLine = view.findViewById(R.id.view_line_item_rv_project_menu_fragment);
            tv = (TextView) view.findViewById(R.id.tv_item_rv_project_menu_fragment);
        }
    }

    static class FootViewHolder extends ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    public void setItemChecked(int position) {
        mBooleanArray.put(position, true);
        if (mLastCheckedPosition > -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
        }
        notifyDataSetChanged();
        mLastCheckedPosition = position;
    }
}
