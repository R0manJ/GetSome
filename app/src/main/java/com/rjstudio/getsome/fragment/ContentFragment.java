package com.rjstudio.getsome.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.rjstudio.getsome.R;
import com.rjstudio.getsome.adapter.VPItemAdapter;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private long date;
    private VPItemAdapter vpItemAdapter;
    private DataProvider dataProvider;

    // HashMap
    // Date -- List
    public ContentFragment( ) {
        super();
        mList = new ArrayList<ConsumeItem>();
    }

    public void setData(List<ConsumeItem> list,Handler handler)
    {
        Log.d(TAG, "setData: +"+list.size()+"---+m");
        if (list != null)
        {
//            Log.d(TAG, "setData: not null ");
            Log.d(TAG, "setData: +"+list.size()+"---+m");
            mList.addAll(list);
        }
        else 
        {
            Log.d(TAG, "setData: null");
        }

        //test Data
        for (ConsumeItem consumeItem : mList)
        {
            Log.d("XXX",consumeItem.getDate()+"--"+consumeItem.getConsumeName()+"");
        }
//        this.handler = handler;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: complete");
        dataProvider = new DataProvider(getContext(),this.date);
        mList.clear();
        mList = dataProvider.get(date);
    }

    public void setDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String da = sdf.format(date);
        this.date = Long.parseLong(da);
        Log.d(TAG, "setDate: "+this.date+"---");
    }

    public long getDate()
    {
        return date;
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
        tv_totalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+getDate());
                Toast.makeText(getContext(), getDate()+"", Toast.LENGTH_SHORT).show();
            }
        });
        rc_content = (RecyclerView) view.findViewById(R.id.rv_content);
//        Message msg = new Message();
//        msg.obj = date;
//        handler.sendMessage(msg);
        initRecyclerView();

    }

    public void initRecyclerView()
    {
//        Log.d(TAG, "initRecyclerView: list size "+mList.size());
        vpItemAdapter = new VPItemAdapter(getContext(),mList, R.layout.item_layout_1);
//        for (ConsumeItem c : mList)
//        {
//            Log.d(TAG, c.getDate()+"----"+"initRecyclerView: "+c.getConsumeName());
//        }
        rc_content.setAdapter(vpItemAdapter);
        rc_content.setLayoutManager(new LinearLayoutManager(getContext()));
        //Adapter

        //setAdapter

    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume: Fragment --- "+mList.size());
        vpItemAdapter.refreshData(dataProvider.get(date));
        vpItemAdapter.notifyItemRangeChanged(0,mList.size());

    }
}
