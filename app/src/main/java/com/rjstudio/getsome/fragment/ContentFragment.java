package com.rjstudio.getsome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rjstudio.getsome.ItemDetail;
import com.rjstudio.getsome.R;
import com.rjstudio.getsome.adapter.VPItemAdapter;
import com.rjstudio.getsome.bean.Consume;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.other.CnLinearLayoutManager;
import com.rjstudio.getsome.other.DividerItemDecoration;

import java.math.BigDecimal;
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
    private double totalAmount = 0;

    // HashMap
    // Date -- List
    public ContentFragment( ) {
        super();
        mList = new ArrayList<ConsumeItem>();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate: complete");
        dataProvider = new DataProvider(getContext(),this.date);
        mList.clear();
    }

    public void setDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String da = sdf.format(date);
        this.date = Long.parseLong(da);
//        Log.d(TAG, "setDate: "+this.date+"---");
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
//                Log.d(TAG, "onClick: "+getDate());
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

        vpItemAdapter.setOnItemClickListener(new VPItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position,ConsumeItem consumeItem) {
                Intent intent = new Intent(getContext(), ItemDetail.class);
                intent.putExtra("isNewItem",true);
                intent.putExtra("index",position);
                Log.d(TAG, "onClick: recyclerView item "+date);
                intent.putExtra("Date",date);
                Log.d(TAG, "onClick: Position"+position);
                intent.putExtra("ConsumeItem",consumeItem);
                startActivity(intent);
            }
        });
        rc_content.setAdapter(vpItemAdapter);
        rc_content.setLayoutManager(new CnLinearLayoutManager(getContext()));
        //Adapter
        rc_content.addItemDecoration(new DividerItemDecoration(getContext()));

    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume: Fragment --- "+mList.size());
//        Log.d(TAG, "onResume: list.size = "+dataProvider.get(date).size());
        vpItemAdapter.refreshData(dataProvider.get(date));
        calculateTotalAmount();
        vpItemAdapter.notifyItemRangeChanged(0,mList.size());

    }


    private void calculateTotalAmount()
    {
        Log.d(TAG, "calculateTotalAmount: --"+date);
        mList = dataProvider.get(date);
        if (mList != null && mList.size() >0)
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(0);
            for (ConsumeItem consumeItem : mList)
            {
//                Log.d(TAG, "calculateTotalAmount: "+bigDecimal);
                bigDecimal = bigDecimal.add(BigDecimal.valueOf(consumeItem.getAmount()));
            }
            totalAmount = bigDecimal.doubleValue();
        }
        tv_totalAmount.setText(totalAmount+"");
    }

    public int getConsmeItemListSize()
    {
        return mList.size();
    }
}
