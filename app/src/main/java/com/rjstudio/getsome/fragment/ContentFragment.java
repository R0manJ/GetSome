package com.rjstudio.getsome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjstudio.getsome.R;
import com.rjstudio.getsome.adapter.VPItemAdapter;
import com.rjstudio.getsome.bean.ConsumeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r0man on 2017/8/11.
 */

public class ContentFragment extends Fragment {

    private View view;

    private TextView tv_currency;
    private TextView tv_totalAmount;
    private RecyclerView rc_content;
    private List<ConsumeItem> mList;
    String TAG = "ContentFragment";
    // HashMap
    // Date -- List
    public ContentFragment() {
        super();
        mList = new ArrayList<ConsumeItem>();
    }

    public void setData(List<ConsumeItem> list)
    {
        mList.addAll(list);

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: complete");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.content_fragment_layout,null);
        initView();
        return view;
    }

    private void initView()
    {
        tv_currency = (TextView) view.findViewById(R.id.tv_currencyType);
        tv_totalAmount = (TextView) view.findViewById(R.id.tv_amount);
        rc_content = (RecyclerView) view.findViewById(R.id.rv_content);
        initRecyclerView();
    }

    public void initRecyclerView()
    {
        VPItemAdapter vpItemAdapter = new VPItemAdapter(getContext(),mList,R.layout.item_layout_1);
        rc_content.setAdapter(vpItemAdapter);
        rc_content.setLayoutManager(new LinearLayoutManager(getContext()));
        //Adapter

        //setAdapter

    }
}
