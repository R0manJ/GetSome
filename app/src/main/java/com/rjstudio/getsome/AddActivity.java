package com.rjstudio.getsome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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

import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.widget.CnToolbar;

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
        String currentDate = intent.getStringExtra("Date");
        String[] dateToLong = currentDate.split("-");
        currentDate = new String();
        for (String s : dateToLong)
        {
            currentDate += s;
        }
//        Log.d(TAG,currentDate);
        date = Long.parseLong(currentDate);
//        Log.d(TAG,"long:"+Long.parseLong(currentDate));
        dataProvider = new DataProvider(this,date);
        //取到当前日期用于保存道sp中的当天消费记录,这里是新建项目，则修改List<ConsumeItem>里面即可；
        //传入一个日期 -> 如有这个日期有对应的消费数据集合 ，则抽取里面的list进行修改 -> 如果没有则新建一个list
        //当前步骤，已近新建了一个list集合了，现在测试存放数据
        //新建一个ConsumeItem
//        ConsumeItem consumeItem = new ConsumeItem();
//        consumeItem.setAmount(0.24f);
//        consumeItem.setConsumeName("Dinner");
//        consumeItem.setDate(Long.parseLong(currentDate));
//        consumeItem.setTypeIcon(1);
//        dataProvider.put(consumeItem);
        //到这一步，将存放了一个数据
        //写入数据没有问题，已经测试完成

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


    String TAG = "TEST";
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }




}
