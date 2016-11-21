package com.shtoone.chenjiang.mvp.view.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.model.entity.db.JidianData;

public class JidianAdapter extends BaseQuickAdapter<JidianData, BaseViewHolder> {
    private static final String TAG = JidianAdapter.class.getSimpleName();

    public JidianAdapter() {
        super(R.layout.item_rv_project_jidian_fragment, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, JidianData mJidianData) {
        holder.setText(R.id.tv_bianhao_item_rv_project_jisian_fragment, "编号：" + mJidianData.getBianhao())
                .setText(R.id.tv_name_item_rv_project_jisian_fragment, Html.fromHtml("名称：<font color=black>" + mJidianData.getName() + "</font>"))
                .setText(R.id.tv_xiuzhenghougaochengzhi_item_rv_project_jisian_fragment, Html.fromHtml("修正后高程值：<font color=black>" + mJidianData.getXiuzhenghou() + "</font>"))
                .setText(R.id.tv_lichengguanhao_item_rv_project_jisian_fragment, Html.fromHtml("里程冠号：<font color=black>" + mJidianData.getGuanhao() + "</font>"))
                .setText(R.id.tv_lichengzhi_item_rv_project_jisian_fragment, Html.fromHtml("里程值：<font color=black>" + mJidianData.getLicheng() + "</font>"));
    }
}
