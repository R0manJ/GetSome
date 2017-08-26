package com.rjstudio.getsome;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.rjstudio.getsome.adapter.CnVPAdapter;
import com.rjstudio.getsome.adapter.MainOptionItemAdapter;
import com.rjstudio.getsome.bean.Consume;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.bean.DateReocord;
import com.rjstudio.getsome.bean.MainOptionalItem;
import com.rjstudio.getsome.fragment.ContentFragment;
import com.rjstudio.getsome.other.DividerItemDecoration;
import com.rjstudio.getsome.utility.JSONUtil;
import com.rjstudio.getsome.utility.PreferencesUtils;
import com.rjstudio.getsome.widget.CnButtomBar;
import com.rjstudio.getsome.widget.CnToolbar;

import java.math.BigDecimal;
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
    private ContentFragment contentFragment;
    private List<Date> dateList;
    private List<ContentFragment> fragmentList;
    private DrawerLayout drawerLayout;
    private CnButtomBar cnButtomBar;

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
        lastPosition = CnVPAdapter.positionIn3th;
        ca = Calendar.getInstance();
        dataProvider = new DataProvider(this,ca.getTime().getTime());
        //装载完10个fragment ，等待转载日期
        dateList = loadDateToFragment(2);

        fragmentList = new ArrayList<>();
        ContentFragment contentFragment;
        for (int i = 0 ; i <  10 ; i++)
        {
            contentFragment = new ContentFragment();
            contentFragment.setDate(dateList.get(i));
            fragmentList.add(contentFragment);
        }
    }

    private List<MainOptionalItem> initOptionalItem()
    {
        MainOptionalItem mainOptionalItem;
        List<MainOptionalItem> mList = new ArrayList<>();

        mainOptionalItem = new MainOptionalItem();
        mainOptionalItem.setImageId(0);
        mainOptionalItem.setTextId(R.string.expenditure);
        mList.add(mainOptionalItem);

        mainOptionalItem = new MainOptionalItem();
        mainOptionalItem.setImageId(0);
        mainOptionalItem.setTextId(R.string.income);
        mList.add(mainOptionalItem);

        mainOptionalItem = new MainOptionalItem();
        mainOptionalItem.setImageId(0);
        mainOptionalItem.setTextId(R.string.statistics);
        mList.add(mainOptionalItem);

        mainOptionalItem = new MainOptionalItem();
        mainOptionalItem.setImageId(0);
        mainOptionalItem.setTextId(R.string.setting);
        mList.add(mainOptionalItem);
        Log.d(TAG, "initOptionalItem: list size "+mList.size());
        return mList;
    }

    private void initView()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        toolbar = (CnToolbar) findViewById(R.id.toolbar);
//        toolbar.getLeftButton().setBackground(getDrawable(R.drawable.menu));
        toolbar.setLeftButtonTexts(simpleDateFormat.format(ca.getTime())).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        toolbar.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        toolbar.getRightButton().setBackground(getDrawable(R.drawable.edit));
        toolbar.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                //当前日期是ca，ca的日期时间传过去

                intent.putExtra("Date",Long.parseLong(simpleDateFormat.format(ca.getTime())));
                Log.d(TAG, "onClick: "+currentDate);
                startActivity(intent);
            }
        });


        vp_content = (ViewPager) findViewById(R.id.vp_content);


        cnVPAdapter = new CnVPAdapter(getSupportFragmentManager(), fragmentList);


        vp_content.setAdapter(cnVPAdapter);
        //设置页面到第二项
        vp_content.setCurrentItem(CnVPAdapter.positionIn3th ,true);

//        CnViewPageAdapter cnViewPageAdapter = new CnViewPageAdapter();

        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Toast.makeText(MainActivity.this, position%10+"", Toast.LENGTH_SHORT).show();
                if (position > lastPosition)
                {
                    ca.add(Calendar.DAY_OF_YEAR,1);
                }
                else
                {
                    ca.add(Calendar.DAY_OF_YEAR,-1);
                }
                lastPosition = position;

                //20180601
                //20170821
                String indexOfRefreshExpenditure = simpleDateFormat.format(ca.getTime()).substring(6,8);
                Log.d(TAG, "onPageSelected: +  "+indexOfRefreshExpenditure);
                if (Integer.valueOf(indexOfRefreshExpenditure) == 1)
                {
                    cnButtomBar.refreshAmount(ca);
                }

                if (position % 10 == 2)
                {
                    dateList = loadDateToFragment(2);
                    Log.d(TAG, "DataList size "+ dateList.size());

                }
                else if (position % 10 == 5)
                {
                    dateList = loadDateToFragment(5);
                    Log.d(TAG, "DataList size "+ dateList.size());
                }
                else if (position % 10 == 7)
                {
                    dateList = loadDateToFragment(7);
                    Log.d(TAG, "DataList size "+ dateList.size());
                }

                int index = 0;
                for (ContentFragment contentFragment : fragmentList)
                {
                    Log.d(TAG, "onPageSelected: +fl = "+fragmentList.size()+"--"+dateList.size());
                    contentFragment.setDate(dateList.get(index ++));
                }


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        toolbar.setLeftButtonText(editDate);
                        toolbar.setLeftButtonText(simpleDateFormat.format(ca.getTime()));
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //OptionMenu
        MainOptionItemAdapter mainOptionItemAdapter = new MainOptionItemAdapter(this,initOptionalItem());
        RecyclerView rv_OptionalMenu = (RecyclerView) findViewById(R.id.rv_chooseItem);
        rv_OptionalMenu.setAdapter(mainOptionItemAdapter);
        rv_OptionalMenu.setLayoutManager(new LinearLayoutManager(this));
        rv_OptionalMenu.addItemDecoration(new DividerItemDecoration(this));
        //ButtomBar
        cnButtomBar = (CnButtomBar) findViewById(R.id.buttomBar);
        cnButtomBar.setCurrentDate(ca.getTime(),this);
        cnButtomBar.setTotalBudget(1200);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cnButtomBar.refreshAmount(ca);
//        Log.d(TAG, "onResume: ");
//        cnVPAdapter.notifyDataSetChanged();
//        cnVPAdapter.refreshData(list);
    }


    //Date load
    public List<Date> loadDateToFragment(int indication)
    {
        List<Date> mList = new ArrayList<>();
        mList.clear();
        if (indication == 2)
        {
            ca.add(Calendar.DAY_OF_YEAR,-2);
            for (int i = 0 ; i < 5 ;i ++)
            {
                mList.add(ca.getTime());
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-8);
            for (int i = 0 ; i < 5; i ++)
            {
                mList.add(ca.getTime());
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
//            Log.d(TAG, "loadDateToFragment: type == 1 "+ simpleDateFormat.format(ca.getTime())+"--isTrue: "+(mList.get(0)==ca.getTime())+"---mList.size = "+mList.size());
        }
        else if (indication == 5)
        {
            ca.add(Calendar.DAY_OF_YEAR,-5);
            for (int i = 0 ; i < 10; i ++)
            {
                mList.add(ca.getTime());
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-5);
//            Log.d(TAG, "loadDateToFragment: type == 2 "+ simpleDateFormat.format(ca.getTime())+"--isTrue: "+(mList.get(2)==ca.getTime()));
        }
        else if (indication == 7)
        {
            ca.add(Calendar.DAY_OF_YEAR, +3);
            for (int i = 0 ; i < 5;i ++)
            {
                mList.add(ca.getTime());
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-10);
            for (int i = 0 ; i < 5 ; i ++)
            {
                mList.add(ca.getTime());
                ca.add(Calendar.DAY_OF_YEAR,1);
            }
            ca.add(Calendar.DAY_OF_YEAR,-3);
//            Log.d(TAG, "loadDateToFragment: type == 7 "+ simpleDateFormat.format(ca.getTime())+"--isTrue: "+(mList.get(6)==ca.getTime()));
        }

//
//        for (Date date : mList)
//        {
//            Log.d(TAG, "Check date : "+ simpleDateFormat.format(date));
//        }


        return mList;

    }
}

