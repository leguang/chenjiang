package com.shtoone.chenjiang.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.bean.LevelLineData;
import com.shtoone.chenjiang.mvp.view.adapter.base.OnItemClickListener;

import java.util.List;


public class MainFragmentRVAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = MainFragmentRVAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<LevelLineData> itemsData;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }

    public MainFragmentRVAdapter(Context context, List<LevelLineData> itemsData) {
        super();
        this.context = context;
        this.itemsData = itemsData;
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
            mItemViewHolder.tv_bianhao.setText("编      号：" + itemsData.get(position).getId() + "");
            mItemViewHolder.tv_route.setText("路线类型：" + itemsData.get(position).getRouteType() + "");
            mItemViewHolder.tv_observe.setText("观测类型：" + itemsData.get(position).getObserveType() + "");
            mItemViewHolder.tv_date.setText("观测时间：" + itemsData.get(position).getObserveDate() + "");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_main_fragment, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.item_foot_recyclerview, parent, false));
        }
        return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_left;
        ImageView iv_right;
        TextView tv_bianhao;
        TextView tv_route;
        TextView tv_observe;
        TextView tv_date;

        public ItemViewHolder(View view) {
            super(view);
            iv_left = (ImageView) view.findViewById(R.id.iv_left_item_rv_main_fragment);
            iv_right = (ImageView) view.findViewById(R.id.iv_right_item_rv_main_fragment);
            tv_bianhao = (TextView) view.findViewById(R.id.tv_bianhao_item_rv_main_fragment);
            tv_route = (TextView) view.findViewById(R.id.tv_route_type_item_rv_main_fragment);
            tv_observe = (TextView) view.findViewById(R.id.tv_observe_type_item_rv_main_fragment);
            tv_date = (TextView) view.findViewById(R.id.tv_date_item_rv_main_fragment);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
