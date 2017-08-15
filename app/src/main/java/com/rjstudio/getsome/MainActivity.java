package com.rjstudio.getsome;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rjstudio.getsome.adapter.CnVPAdapter;
import com.rjstudio.getsome.bean.Consume;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.bean.DateReocord;
import com.rjstudio.getsome.fragment.ContentFragment;
import com.rjstudio.getsome.utility.JSONUtil;
import com.rjstudio.getsome.utility.PreferencesUtils;
import com.rjstudio.getsome.widget.CnToolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager vp_content;
    private CnToolbar toolbar;
    private Date date;
    private String currentDate;
    private List<ContentFragment> list;

    private String TAG = "MainActivity";
    private CnVPAdapter cnVPAdapter;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int lastPosition;
    private DataProvider dataProvider;
    private List<List<ConsumeItem>> fragmentData;
    private ContentFragment contentFragment;
    private String initCurrentDate;
    private Calendar ca;

    private Handler DateHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: "+msg.obj);
            //oolbar.getLeftButton().setText(msg.obj+"");
        }
    };
    private String editDate;
    private Calendar editCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

        initData();
        initView();


    }

    private void initData()
    {
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        lastPosition = CnVPAdapter.centerPosition;
        calendar = Calendar.getInstance();
        initCurrentDate = simpleDateFormat.format(calendar.getTime());

        long currentDateL = Long.parseLong(initCurrentDate);
        Log.d(TAG, "long currentDate"+(currentDateL-2));
        dataProvider = new DataProvider(this,currentDateL);
        //抽取5个集合，当前天数 -/+ 2
        fragmentData = new ArrayList<>();

        Log.d(TAG, "Load completed! -- "+ fragmentData.size());
        editCalendar = Calendar.getInstance();

    }

    private void initView()
    {
        toolbar = (CnToolbar) findViewById(R.id.toolbar);
        toolbar.setLeftButtonTexts(initCurrentDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        toolbar.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("Date",currentDate);
                Log.d(TAG, "onClick: "+currentDate);
                startActivity(intent);
            }
        });


        vp_content = (ViewPager) findViewById(R.id.vp_content);

        list = new ArrayList<ContentFragment>();
        ca = Calendar.getInstance();
        cnVPAdapter = new CnVPAdapter(getSupportFragmentManager(), list);

        reloadDataInTwoWay(1);



        vp_content.setAdapter(cnVPAdapter);
        vp_content.setCurrentItem(cnVPAdapter.centerPosition ,true);
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                editDate = simpleDateFormat.format(editCalendar.getTime());
                Log.d(TAG, "onPageSelected: "+ editDate);
                if (position % 10 == 1)
                {

                    reloadDataInTwoWay(1);
                }
                else if (position % 10 == 3)
                {
                    reloadDataInTwoWay(2);
                }
                else if (position % 10 == 6)
                {
                    reloadDataInTwoWay(3);
                }
                else if (position % 10 ==8)
                {
                    reloadDataInTwoWay(4);
                }
                Log.d(TAG, "onPageSelected: selected is "+position%10);
                Log.d(TAG, "onPageSelected: lastPosition"+lastPosition +"--- position"+position);

//
                if (position - lastPosition > 0)
                {
                    ca.add(Calendar.DAY_OF_YEAR,1);
                    editDate = simpleDateFormat.format(ca.getTime());
                    Log.d(TAG, "onPageSelected: ++"+editDate);

                }
                else
                {
                    ca.add(Calendar.DAY_OF_YEAR,-1);
                    editDate = simpleDateFormat.format(ca.getTime());
                    Log.d(TAG, "onPageSelected: --"+editDate);

                }
                lastPosition = position;


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        toolbar.setLeftButtonText(editDate);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void reloadDataInTwoWay(int i)
    {
        Log.d(TAG, "ca - currentDate :" + simpleDateFormat.format(ca.getTime()));
        //这个ca显示的是 当前的日期20170815，只做数据重载用
        list.clear();
        if (i == 1)
        {
            //分段式
            //////////
            for(int d =  0 ; d < 5 ; d++)
            {
                contentFragment = new ContentFragment();
                contentFragment.setDate(ca.getTime());
                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            Log.d(TAG, "reload one ca is"+simpleDateFormat.format(ca.getTime()));
            ca.add(Calendar.DAY_OF_YEAR,-2);
            for (int d = 0;d < 5;d++)
            {
                contentFragment = new ContentFragment();
                contentFragment.setDate(ca.getTime());
                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-8);
            Log.d(TAG, "reload one over ca is"+simpleDateFormat.format(ca.getTime()));

            //////////
        }
        else if (i == 2)
        {
            //直连式
            Log.d(TAG, "reload two current date is "+simpleDateFormat.format(ca.getTime()));
            ca.add(Calendar.DAY_OF_YEAR,-2);
            for (int d = 0 ; d < 10 ; d++)
            {
                contentFragment = new ContentFragment();
                Log.d(TAG, "reloadDataInTwoWay: Add date is "+simpleDateFormat.format(ca.getTime()));
                contentFragment.setDate(ca.getTime());
                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-8);
            Log.d(TAG, "reload two over date is "+simpleDateFormat.format(ca.getTime()));
        }
        else if (i == 3)
        {
            ca.add(Calendar.DAY_OF_YEAR,-5);
            for (int d = 0 ; d < 10 ; d++)
            {
                contentFragment = new ContentFragment();
                contentFragment.setDate(ca.getTime());
                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-5);
            Log.d(TAG, "reload.3 over date is "+simpleDateFormat.format(ca.getTime()));
        }
        else if (i == 4)
        {
            ca.add(Calendar.DAY_OF_YEAR,3);
            for (int d = 0 ; d < 5 ;d++)
            {
                Log.d(TAG, "reload.4 date is "+simpleDateFormat.format(ca.getTime()));
                contentFragment = new ContentFragment();
                contentFragment.setDate(ca.getTime());
                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-10);
            for (int d = 0 ; d < 5 ; d++)
            {
                Log.d(TAG, "reload.4 date is "+simpleDateFormat.format(ca.getTime()));
                contentFragment = new ContentFragment();
                contentFragment.setDate(ca.getTime());
                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-3);
            Log.d(TAG, "reload.4 over date is "+simpleDateFormat.format(ca.getTime()));

        }
        cnVPAdapter.refreshData(list);
        Log.d(TAG, "After reloading ca's date is "+simpleDateFormat.format(ca.getTime()));
    }
    public void reloadData(boolean isSlideToRight)
    {
        list.clear();
        Log.d(TAG, "Record date : "+simpleDateFormat.format(ca.getTime()));
        if (isSlideToRight)
        {
            ca.add(Calendar.DAY_OF_YEAR, -5);
            for (int i = 0 ; i < 10 ; i ++)
            {
                contentFragment = new ContentFragment();
                Log.d(TAG, "reloadData: "+ simpleDateFormat.format(ca.getTime()));
                contentFragment.setDate(ca.getTime());
                contentFragment.setData(null,DateHandler);

                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
        }
        else
        {
            ca.add(Calendar.DAY_OF_YEAR,5);
            for (int i = 0 ; i < 10 ; i++)
            {
                contentFragment = new ContentFragment();
                Log.d(TAG,"left"+simpleDateFormat.format(ca.getTime()));
                contentFragment.setDate(ca.getTime());
                contentFragment.setData(null,DateHandler);
                list.add(contentFragment);
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
        }
        cnVPAdapter.refreshData(list);
    }

    public void resetContentFragmentDataList()
    {
        list.clear();
        for (int i = 0 ; i < 5; i++)
        {
            ContentFragment contentFragment = new ContentFragment();
            list.add(contentFragment);
        }
    }



    public List<ConsumeItem> testData1;
    public List<ConsumeItem> testData2;
    public List<ConsumeItem> testData3;

    public void initTestData()
    {
        testData1 = new ArrayList<>();
        testData2 = new ArrayList<>();
        testData3 = new ArrayList<>();
        ConsumeItem testItem;
        for (int i = 0 ; i < 5; i++)
        {
            testItem = new ConsumeItem();
            testItem.setTypeIcon(Consume.TypeIconId.LUNCH);
            testItem.setConsumeName(getResources().getText(Consume.TypeTextId.LUNCH) +"-"+i);
            testItem.setAmount(i + 55 );
            testData1.add(testItem);
            if (i > 2) testData2.add(testItem);
            if (i == 4) testData3.add(testItem);
        }
    }


    public void initJsonData()
    {
        //ConsumeItem -> DateRecord -> toJson -> SharaPrefences
        List<DateReocord> totalData = new ArrayList<>();
        List<ConsumeItem> data1 = new ArrayList<>();
        DateReocord dateReocord = new DateReocord();
        for (int i = 0 ; i < 10 ; i++)
        {
            ConsumeItem consumeItem = new ConsumeItem();
            consumeItem.setConsumeType(i + i *4);
            consumeItem.setDate(20170813l);
            consumeItem.setConsumeName(i+"consumeItem");
            consumeItem.setAmount(1010f);
            data1.add(consumeItem);
        }
//        dateReocord.setDate(20170813l);
//        dateReocord.setmList(data1);
//        totalData.add(dateReocord);
        List<ConsumeItem> data2 = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++)
        {
            ConsumeItem consumeItem = new ConsumeItem();
            consumeItem.setConsumeType(i + i *4);
            consumeItem.setDate(20170814l);
            consumeItem.setConsumeName(i+"consumeItem");
            consumeItem.setAmount(1010f);
            data2.add(consumeItem);
        }
//        dateReocord = new DateReocord();
//        dateReocord.setmList(data2);
//        dateReocord.setDate(20170814l);
//        totalData.add(dateReocord);
        String TEST = "TEST";
        //当前有一个DateRecord ，里面有两天的消费记录，每天共10条
        //测试一： json 转换
//        Log.d(TEST,JSONUtil.toJson(totalData));
//      测试数据结果： [{"date":20170813,"mList":[{"Amount":1010.0,"ConsumeName":"0consumeItem","ConsumeType":0,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"1consumeItem","ConsumeType":5,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"2consumeItem","ConsumeType":10,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"3consumeItem","ConsumeType":15,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"4consumeItem","ConsumeType":20,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"5consumeItem","ConsumeType":25,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"6consumeItem","ConsumeType":30,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"7consumeItem","ConsumeType":35,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"8consumeItem","ConsumeType":40,"TypeIcon":0,"date":20170813},{"Amount":1010.0,"ConsumeName":"9consumeItem","ConsumeType":45,"TypeIcon":0,"date":20170813}]},{"date":20170814,"mList":[{"Amount":1010.0,"ConsumeName":"0consumeItem","ConsumeType":0,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"1consumeItem","ConsumeType":5,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"2consumeItem","ConsumeType":10,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"3consumeItem","ConsumeType":15,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"4consumeItem","ConsumeType":20,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"5consumeItem","ConsumeType":25,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"6consumeItem","ConsumeType":30,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"7consumeItem","ConsumeType":35,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"8consumeItem","ConsumeType":40,"TypeIcon":0,"date":20170814},{"Amount":1010.0,"ConsumeName":"9consumeItem","ConsumeType":45,"TypeIcon":0,"date":20170814}]}]

        //测试SharePreference ， key为Data，数据为json数据
        PreferencesUtils.putString(getApplicationContext(),20170813+"",JSONUtil.toJson(data1));
        PreferencesUtils.putString(getApplicationContext(),20170814+"",JSONUtil.toJson(data2));
        Log.d(TEST,"13"+PreferencesUtils.getString(getApplicationContext(),20170813+""));

        Log.d(TEST,"14"+PreferencesUtils.getString(getApplicationContext(),20170814+""));




    }
}
