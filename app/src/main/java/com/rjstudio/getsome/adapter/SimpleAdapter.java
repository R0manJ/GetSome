package com.rjstudio.getsome.adapter;

import android.content.Context;
import android.widget.TextView;

import com.rjstudio.getsome.R;

import java.util.List;

/**
 * Created by r0man on 2017/8/11.
 */

public class SimpleAdapter<T> extends BaseAdapter<String> {
    public SimpleAdapter(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    void convertData(String s, BaseViewHolder baseViewHolder) {

    }
}
