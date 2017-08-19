package com.rjstudio.getsome.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rjstudio.getsome.R;
import com.rjstudio.getsome.bean.ConsumeItem;

import java.util.List;

/**
 * Created by r0man on 2017/8/18.
 */

public class CnViewPageAdapter extends PagerAdapter {

    private final List<View> mList;

    private static int PositionIn5x = ((Integer.MAX_VALUE/2)/5)*5;
    private final Context mContext;
    private List<ConsumeItem> mData;
    private RecyclerView recyclerView;
    private VPItemAdapter vpItemAdapter;

    public CnViewPageAdapter(Context context,List<View> list)
    {
        super();
        mContext = context;
        mList = list;
    }

    public void setData(List<ConsumeItem> data)
    {
        mData = data;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        View view = mList.get(position % 5);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_content);
        vpItemAdapter = new VPItemAdapter(mContext,mData, R.layout.item_layout_1);
        recyclerView.setAdapter(vpItemAdapter);
        return view;
    }

    public void refreshData(List<ConsumeItem> list)
    {
        vpItemAdapter.refreshData(list);
    }
}
