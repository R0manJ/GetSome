package com.rjstudio.getsome.adapter;

import android.content.Context;

import com.rjstudio.getsome.R;
import com.rjstudio.getsome.bean.ConsumeItem;

import java.util.List;

/**
 * Created by r0man on 2017/8/12.
 */

public class VPItemAdapter extends BaseAdapter<ConsumeItem> {

    public VPItemAdapter(Context context, List<ConsumeItem> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    void convertData(ConsumeItem consumeItem, BaseViewHolder baseViewHolder) {
        baseViewHolder.findTextView(R.id.tv_typeName).setText(consumeItem.getConsumeName());
        baseViewHolder.findTextView(R.id.tv_typeAmount).setText(consumeItem.getAmount() + " $ ");

    }
}
