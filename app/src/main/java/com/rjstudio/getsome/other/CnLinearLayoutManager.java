package com.rjstudio.getsome.other;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by r0man on 2017/8/22.
 */

public class CnLinearLayoutManager extends LinearLayoutManager {
    public CnLinearLayoutManager(Context context) {
        super(context);
    }

    public CnLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CnLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try
        {
            super.onLayoutChildren(recycler, state);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
