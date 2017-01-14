package com.shtoone.chenjiang.mvp.view.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ItemDragAdapter extends BaseItemDraggableAdapter<CharSequence, BaseViewHolder> {
    public ItemDragAdapter() {
        super(R.layout.item_rv_draggable, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CharSequence item) {
        helper.setText(R.id.tv_name_item_rv_addyusheshuizhunxian_fragment, item)
                .setText(R.id.tv_number_item_rv_addyusheshuizhunxian_fragment, (helper.getLayoutPosition() + 1) + "");
    }
}
