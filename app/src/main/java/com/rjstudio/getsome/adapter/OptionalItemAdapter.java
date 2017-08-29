package com.rjstudio.getsome.adapter;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjstudio.getsome.R;
import com.rjstudio.getsome.bean.OptionalItem;

import java.util.List;

/**
 * Created by r0man on 2017/8/19.
 */

public class OptionalItemAdapter extends BaseAdapter<OptionalItem> {

    private final Context mContext;
    private OnItemClickListener onItemClickListener;
    private final List<OptionalItem> mList;
    private int recordPosition = 0;

    private int itemNumber = Integer.MAX_VALUE;

    public OptionalItemAdapter(Context context, List<OptionalItem> list, int layoutId) {
        super(context, list, layoutId);
        mContext = context;
        mList = list;
    }

    @Override
    void convertData(final OptionalItem optionalItem, BaseViewHolder baseViewHolder, final int position) {
//        baseViewHolder.findView(R.id.iv_item).setBackground(mContext.getDrawable(R.drawable.ic_launcher_round));


        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) baseViewHolder.findView(R.id.iv_item);
        simpleDraweeView.setImageURI("res:///"+optionalItem.getImageId());
        baseViewHolder.findTextView(R.id.tv_item).setText(optionalItem.getTextId());

        if (optionalItem.isSelected())
        {
            itemNumber = optionalItem.getIndex();
            baseViewHolder.findView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.ahorroWhite2));
        }
        else
        {
            baseViewHolder.findView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.ahorroWhite));
        }

        baseViewHolder.findView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                itemNumber = position;
                notifyItemChanged(recordPosition);
                if (!optionalItem.isSelected())
                {
                    optionalItem.setSelected(!optionalItem.isSelected());
                    v.setBackgroundColor(mContext.getResources().getColor(R.color.ahorroWhite2));
                }
                else
                {
                    optionalItem.setSelected(!optionalItem.isSelected());
                    v.setBackgroundColor(mContext.getResources().getColor(R.color.ahorroWhite));
                }

                for (int i = 0; i < mList.size(); i++  )
                {
                    if (i != position){
                        mList.get(i).setSelected(false);
                    }
                }

                refreshSelected();
                if (onItemClickListener != null)
                {
                    onItemClickListener.onClick(v,position,optionalItem.isSelected(),optionalItem);
                }
                recordPosition = position;

            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view,int position,boolean isSelect,OptionalItem optionalItem);
    }

    private void refreshSelected()
    {
        //notifyItemRangeChanged(0,mList.size());
    }

    public int getItemNumber()
    {
        return itemNumber;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
