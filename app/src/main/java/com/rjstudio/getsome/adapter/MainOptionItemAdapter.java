package com.rjstudio.getsome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjstudio.getsome.MainActivity;
import com.rjstudio.getsome.R;
import com.rjstudio.getsome.bean.MainOptionalItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r0man on 2017/8/21.
 */

public class MainOptionItemAdapter extends BaseAdapter<MainOptionalItem> {

    private Context mContext;


    public MainOptionItemAdapter(Context context, List<MainOptionalItem> list) {
        super(context, list, R.layout.item_layout_3);
        mContext = context;

    }


    @Override
    void convertData(MainOptionalItem mainOptionalItem, BaseViewHolder baseViewHolder, final int position) {
//            baseViewHolder.findView(R.id.iv_item);

//        MainOptionalItem mainOptionalItem = mList.get(position);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) baseViewHolder.findView(R.id.iv_item);
//        simpleDraweeView.setImageURI();
        baseViewHolder.findTextView(R.id.tv_item).setText(mContext.getString(mainOptionalItem.getTextId()));
        baseViewHolder.findView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 1:
                        Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
                        //Expenditure
                        //NotifyDataChangeRange
                        break;
                    case 2:
                        Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();
                        //NotifyDataChangeRange
                        //Income
                        break;
                    case 3:
                        Toast.makeText(mContext, "3", Toast.LENGTH_SHORT).show();

                        //Statistics
                        break;
                    case 0:
                        Toast.makeText(mContext, "0", Toast.LENGTH_SHORT).show();
                        //Setting
                        break;
                }
            }
        });

    }

}
