/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shtoone.chenjiang.mvp.view.main.measure;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class LayoutAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 100;

    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;


    public LayoutAdapter(Context context, RecyclerView recyclerView) {
        this(context, recyclerView, DEFAULT_ITEM_COUNT);
    }

    public LayoutAdapter(Context context, RecyclerView recyclerView, int itemCount) {
        mContext = context;
        mItems = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            addItem(i);
        }

        mRecyclerView = recyclerView;
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_rvp_measure_right_fragment, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final View itemView = holder.itemView;
//        itemView.setBackgroundResource(position % 2 == 0 ? R.color.material_red_50 : R.color.material_green_50);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(BaseApplication.mContext, position + "");
            }
        });
        final int itemId = mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ItemViewHolder extends ViewHolder {
        TextView tv_material_name;
        TextView tv_reality;
        TextView tv_matching;
        TextView tv_deviation;
        TextView tv_deviation_rate;

        public ItemViewHolder(View view) {
            super(view);
//            tv_material_name = (TextView) view.findViewById(R.id.tv_material_name_item_recyclerview_produce_query_detal_activity);
//            tv_reality = (TextView) view.findViewById(R.id.tv_reality_item_recyclerview_produce_query_detal_activity);
//            tv_matching = (TextView) view.findViewById(R.id.tv_matching_item_recyclerview_produce_query_detal_activity);
//            tv_deviation = (TextView) view.findViewById(R.id.tv_deviation_item_recyclerview_produce_query_detal_activity);
//            tv_deviation_rate = (TextView) view.findViewById(R.id.tv_deviation_rate_item_recyclerview_produce_query_detal_activity);
        }
    }
}
