package com.rjstudio.getsome.adapter;

import android.content.Context;

import com.rjstudio.getsome.R;
import com.rjstudio.getsome.bean.ConsumeItem;

import java.util.List;

/**
 * Created by r0man on 2017/8/12.
 */

public class VPItemAdapter extends BaseAdapter<ConsumeItem> {

    List<ConsumeItem> list;
    public VPItemAdapter(Context context, List<ConsumeItem> list, int layoutId) {
        super(context, list, layoutId);
        this.list = list;
    }

    @Override
    void convertData(ConsumeItem consumeItem, BaseViewHolder baseViewHolder,int position) {
        baseViewHolder.findTextView(R.id.tv_typeName).setText(consumeItem.getConsumeName());
        baseViewHolder.findTextView(R.id.tv_typeAmount).setText(consumeItem.getAmount() + " $ ");

    }

    public void refreshData(List<ConsumeItem > newData)
    {
        list.clear();
        list.addAll(newData);
        notifyItemRangeChanged(0,newData.size());
    }
}
