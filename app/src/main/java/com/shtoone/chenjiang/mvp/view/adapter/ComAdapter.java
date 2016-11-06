//package com.shtoone.chenjiang.mvp.view.adapter;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.SparseBooleanArray;
//
//import com.shtoone.chenjiang.R;
//import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
//import com.zhy.adapter.recyclerview.CommonAdapter;
//import com.zhy.adapter.recyclerview.base.ViewHolder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ComAdapter extends CommonAdapter<GongdianData> {
//    private static final String TAG = ComAdapter.class.getSimpleName();
//    private SparseBooleanArray mBooleanArray = new SparseBooleanArray();
//    private int mLastCheckedPosition = -1;
//
//    public ComAdapter(Context context, List<GongdianData> datas) {
//        super(context, R.layout.item_rv_project_menu_fragment, datas);
//    }
//
//    @Override
//    protected void convert(ViewHolder holder, GongdianData gongdianData, int position) {
//        if (!mBooleanArray.get(position)) {
//            holder.setVisible(R.id.view_line_item_rv_project_menu_fragment, false)
//                    .setTextColor(R.id.tv_item_rv_project_menu_fragment, Color.BLACK);
//            holder.itemView.setBackgroundResource(R.color.light_gray);
//        } else {
//
//            holder.setVisible(R.id.view_line_item_rv_project_menu_fragment, true)
//                    .setTextColor(R.id.tv_item_rv_project_menu_fragment, R.color.colorAccent);
//            holder.itemView.setBackgroundColor(Color.WHITE);
//        }
//
//        holder.setText(R.id.tv_item_rv_project_menu_fragment, gongdianData.getName());
//    }
//
//    public void setNewData(List<GongdianData> data) {
//        this.mDatas = data == null ? new ArrayList<GongdianData>() : data;
//        notifyDataSetChanged();
//        mBooleanArray.clear();
//        mBooleanArray = new SparseBooleanArray(data.size());
//    }
//
//
//    public void setItemChecked(int position) {
//        mBooleanArray.put(position, true);
//        if (mLastCheckedPosition > -1) {
//            mBooleanArray.put(mLastCheckedPosition, false);
//        }
//        notifyDataSetChanged();
//        mLastCheckedPosition = position;
//    }
//
//    public void addData(List<GongdianData> newData) {
//        this.mDatas.addAll(newData);
//        notifyDataSetChanged();
//    }
//}
