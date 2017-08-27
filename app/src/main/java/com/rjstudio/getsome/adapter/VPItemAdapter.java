package com.rjstudio.getsome.adapter;

import android.content.Context;
import android.view.View;

import com.rjstudio.getsome.R;
import com.rjstudio.getsome.bean.ConsumeItem;

import java.util.List;

/**
 * Created by r0man on 2017/8/12.
 */

public class VPItemAdapter extends BaseAdapter<ConsumeItem> {

    List<ConsumeItem> list;
    OnItemClickListener onItemClickListener;
    public VPItemAdapter(Context context, List<ConsumeItem> list, int layoutId) {
        super(context, list, layoutId);
        this.list = list;
    }

    @Override
    void convertData(final ConsumeItem consumeItem, BaseViewHolder baseViewHolder, final int position) {
        baseViewHolder.findTextView(R.id.tv_typeName).setText(consumeItem.getConsumeName());
        baseViewHolder.findTextView(R.id.tv_typeAmount).setText(consumeItem.getAmount() + " $ ");
        baseViewHolder.findView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                {
                    onItemClickListener.onClick(v,position,consumeItem);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }
    public void refreshData(List<ConsumeItem > newData)
    {
        list.clear();
        list.addAll(newData);
        if (newData.size() == 0)
        {
            notifyItemChanged(0);
        }
        else
        {
            notifyItemRangeChanged(0,newData.size());
        }
    }

    public interface OnItemClickListener{
        void onClick(View v,int position,ConsumeItem consumeItem);
    }
}
