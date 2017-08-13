package com.rjstudio.getsome;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rjstudio.getsome.adapter.CnVPAdapter;
import com.rjstudio.getsome.bean.Consume;
import com.rjstudio.getsome.bean.ConsumeItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        lastPosition = CnVPAdapter.centerPosition;
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = simpleDateFormat.format(calendar.getTime());
//        initJsonData();

//        initTestData();
        initView();


    }

    private void initView()
    {
        toolbar = (CnToolbar) findViewById(R.id.toolbar);
        toolbar.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }
        });
        toolbar.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });


        vp_content = (ViewPager) findViewById(R.id.vp_content);

        list = new ArrayList<ContentFragment>();
        for (int i = 0 ; i < 5 ; i++)
        {
            ContentFragment contentFragment = new ContentFragment();
//            if (i < 2)contentFragment.setData(testData1);
//            else if (i < 4) contentFragment.setData(testData2);
//            else contentFragment.setData(testData3);
            list.add(contentFragment);
        }


        cnVPAdapter = new CnVPAdapter(getSupportFragmentManager(), list);
        vp_content.setAdapter(cnVPAdapter);
        vp_content.setCurrentItem(cnVPAdapter.centerPosition ,true);
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > lastPosition)
                {
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                    currentDate = simpleDateFormat.format(calendar.getTime());
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_YEAR,-1);
                    currentDate = simpleDateFormat.format(calendar.getTime());
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        toolbar.setLeftButtonText(currentDate);
                    }
                });


                if( (position - cnVPAdapter.centerPosition)%2 == 1)
                {
                    reloadData();

                }
                lastPosition = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDate();
    }
    //获取当前日期
    public void setDate()
    {

        toolbar.setLeftButtonText(currentDate);
    }

    public void reloadData()
    {
        list.clear();
        for (int i = 0 ; i < 5; i ++)
        {
            ContentFragment f = new ContentFragment();
            list.add(f);
        }
        Log.d(TAG, "reloadData: list size:"+list.size());
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
