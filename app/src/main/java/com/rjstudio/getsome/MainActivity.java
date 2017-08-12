package com.rjstudio.getsome;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rjstudio.getsome.adapter.CnVPAdapter;
import com.rjstudio.getsome.bean.Consume;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.fragment.ContentFragment;
import com.rjstudio.getsome.widget.CnToolbar;

import java.text.DateFormat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTestData();
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
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        list = new ArrayList<ContentFragment>();
        for (int i = 0 ; i < 5 ; i++)
        {
            ContentFragment contentFragment = new ContentFragment();
            if (i < 2)contentFragment.setData(testData1);
            else if (i < 4) contentFragment.setData(testData2);
            else contentFragment.setData(testData3);
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
                int curerentDateNumber = date.getDate();
                Log.d(TAG, "onPageSelected: "+position % 10+"--position:"+position);
                currentDate = (1900 + date.getYear())+"/"+(date.getMonth()+1)+"/"+(curerentDateNumber + (position%10)) ;

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
        Calendar calendar = Calendar.getInstance();

        String TAG = "Date";
        long currentTime = System.currentTimeMillis();
        date = new Date(currentTime);

        currentDate = (1900 + date.getYear())+"/"+(date.getMonth()+1)+"/"+ date.getDate();
        toolbar.setLeftButtonText(currentDate);
        Log.d(TAG, "setDate: "+ currentDate);
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
}
