package com.rjstudio.getsome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by r0man on 2017/8/11.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> mList;
    private final int mLayoutId;
    private final Context mContext;

    public BaseAdapter(Context context,List<T> list, int layoutId)
    {
        super();
        this.mContext = context;
        this.mList = list;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = mList.get(position);
        convertData(t,holder);
    }

    abstract void convertData(T t,BaseViewHolder baseViewHolder);



}
