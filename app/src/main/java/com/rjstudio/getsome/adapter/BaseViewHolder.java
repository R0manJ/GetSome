package com.rjstudio.getsome.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by r0man on 2017/8/11.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final View view;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;

    }

    public View findView(int widgetId)
    {
        return view.findViewById(widgetId);
    }

    public TextView findTextView(int widgetId)
    {
        return (TextView) findView(widgetId);
    }

}
