package com.rjstudio.getsome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rjstudio.getsome.adapter.OptionalItemAdapter;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.bean.OptionalItem;
import com.rjstudio.getsome.widget.CnToolbar;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private Handler popWindowsHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            cMWindows.showAtLocation(calculateLayout, Gravity.BOTTOM,10,0);
            super.handleMessage(msg);
        }

    };
    private View contentView;
    private View calculateLayout;
    private PopupWindow cMWindows;
    private Context context;
    private TextView tv_setRemark;
    private Intent intent;
    private long date;
    private DataProvider dataProvider;
    private RecyclerView rv_setItem;
    private RecyclerView rv_optionalItem;
    private OptionalItemAdapter optionalItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);



        initData();
        initView();

    }
    private void initData()
    {
        context = this;
        intent = getIntent();
        date = intent.getLongExtra("Date",0);
//        Log.d(TAG,"long:"+Long.parseLong(currentDate));
        dataProvider = new DataProvider(this,date);


    }
    private void initView()
    {
        //ToolBar
        CnToolbar cnToolbar = (CnToolbar) findViewById(R.id.toolbar);
        cnToolbar.setLeftButtonTexts(getResources().getString(R.string.back))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        cnToolbar.setRightButtonTexts(getResources().getString(R.string.save))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO : consmueItem set;
                        ConsumeItem consumeItem = new ConsumeItem();
                        consumeItem.setConsumeName("xxx"+"");
                        consumeItem.setTypeIcon(1);
                        consumeItem.setAmount(22.77f);
                        consumeItem.setConsumeType(1);
                        consumeItem.setDate(date);
                        dataProvider.put(consumeItem);
                        finish();
                    }
                });
        //TODO : getApplication & this ?
        calculateLayout = LayoutInflater.from(this).inflate(R.layout.calculate_layout,null);
        cMWindows = new PopupWindow(calculateLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        cMWindows.setTouchable(true);
        cMWindows.setOutsideTouchable(true);
        TextView tv_amount = (TextView) findViewById(R.id.tv_setAmount);
        tv_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cMWindows.showAtLocation(calculateLayout, Gravity.BOTTOM,10,0);

            }
        });

        tv_setRemark = (TextView) findViewById(R.id.tv_setRemark);

        tv_setRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Remark", Toast.LENGTH_SHORT).show();
                TextView tv_newSetRemark = initRemarkSetLayout();
                PopupWindow popupWindow = new PopupWindow(tv_newSetRemark, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(false);
                popupWindow.showAsDropDown(v);
            }
        });
        initRemarkSetLayout();
        initOptionalItemLayout();


    }
    private EditText initRemarkSetLayout()
    {
        EditText et_remark = new EditText(this);
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(800,800);
        et_remark.setLayoutParams(layoutParams);
        et_remark.setText("xxx");
        Log.d(TAG, "initRemarkSetLayout: "+et_remark.getText());
        return et_remark;
    }

    private void initOptionalItemLayout()
    {
        //Test data
        List<OptionalItem> optionalItemList = new ArrayList<>();
        for (int i = 0 ; i < 20; i++)
        {
            OptionalItem optionalItem = new OptionalItem();
            optionalItem.setImageId(R.drawable.ic_launcher_round);
            optionalItem.setTextId(R.string.add);
            optionalItemList.add(optionalItem);
        }

        rv_optionalItem = (RecyclerView) findViewById(R.id.rv_chooseItem);
        optionalItemAdapter = new OptionalItemAdapter(this,optionalItemList, R.layout.item_layout_2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        gridLayoutManager.canScrollHorizontally();
        rv_optionalItem.setAdapter(optionalItemAdapter);
        rv_optionalItem.setLayoutManager(gridLayoutManager);
    }

    String TAG = "TEST";




}
