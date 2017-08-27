package com.rjstudio.getsome.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rjstudio.getsome.MainActivity;
import com.rjstudio.getsome.R;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.bean.DateReocord;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by r0man on 2017/8/11.
 */

public class CnButtomBar extends RelativeLayout {


    private double bugdet;
    private Date date;
    private Context context;
    private Calendar calendar;
    private SimpleDateFormat sf;
    private DataProvider dataProvider;
    private int currentMonth;
    private SimpleDateFormat sfForMonth;

    private double expenditureNumer = 0 ;
    private BigDecimal bigDecimal;
    private TextView tv_budget;
    private TextView tv_expenditure;
    private Context mContext;

    public CnButtomBar(Context context) {
        this(context,null);
//        initView();

    }

    public CnButtomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
//        initView();

    }

    public CnButtomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cn_buttombar,this);

        tv_expenditure = (TextView) view.findViewById(R.id.tv_surplus);
        tv_budget = (TextView) view.findViewById(R.id.tv_budget);
        tv_expenditure.setText("xxxx");
        tv_budget.setText("yyyy");
        tv_budget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "sss", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setCurrentDate(Date date, Context context)
    {
        this.date = date;
        this.context = context;
        calendar = Calendar.getInstance();
        sfForMonth = new SimpleDateFormat("MM");
//        currentMonth = Integer.valueOf(sfForMonth.format(date));


        sf = new SimpleDateFormat("yyyyMMdd");

        dataProvider = new DataProvider(context,Long.parseLong(sf.format(calendar.getTime())));
//        countExpenditure();
//        tv_expenditure.setText(expenditureNumer + "");
    }

    public void countExpenditure()
    {
        bigDecimal = BigDecimal.valueOf(expenditureNumer);
//        Log.d(TAG, "CurrentMonth "+currentMonth);
        calendar.set(Calendar.MONTH,currentMonth-1);
//        Log.d(TAG, "countExpenditure: "+calendar);
        calendar.set(Calendar.DATE,1);
        for (int i = 0 ; i < 32 ; i++)
        {
//            Log.d(TAG, "countExpenditure: "+sf.format(calendar.getTime()));
            List<ConsumeItem> list = dataProvider.get(calendar.getTime());
//            Log.d(TAG, "countExpenditure: list size "+ list.size());
            for (ConsumeItem consumeItem : list)
            {
//                Log.d(TAG, "countExpenditure: amount "+consumeItem.getAmount());
                bigDecimal = bigDecimal.add(BigDecimal.valueOf(consumeItem.getAmount()));
//                Log.d(TAG, "countExpenditure: "+bigDecimal);
            }
            calendar.add(Calendar.DAY_OF_YEAR,1);
//            calendar.add(Calendar.DAY_OF_MONTH,1);
            if (Integer.valueOf(sfForMonth.format(calendar.getTime()))> currentMonth)
            {
//                Log.d(TAG, "countExpenditure: int = "+Integer.valueOf(sfForMonth.format(calendar.getTime()))+"--"+currentMonth);
                expenditureNumer = bigDecimal.doubleValue();
                break;
            }
//            Log.d(TAG, "countExpenditure: "+expenditureNumber);

        }
//        Log.d(TAG, "countExpenditure: "+expenditureNumer);
        expenditureNumer = BigDecimal.valueOf(1200).subtract(BigDecimal.valueOf(expenditureNumer)).doubleValue();
        calendar.add(Calendar.MONTH,-1);
    }

    public void setTotalBudget(int number)
    {
        bugdet = number;
        tv_budget.setText(number+"");
    }

    public double getTotalBugdet()
    {
        return bugdet;
    }

    public void refreshAmount(Calendar calendar)
    {
        expenditureNumer = 0;
//        Log.d(TAG, "refreshAmount: Calander "+calendar.getTime());
        currentMonth = Integer.valueOf(sfForMonth.format(calendar.getTime()));
//        this.calendar = calendar;
        countExpenditure();
        tv_expenditure.setText(expenditureNumer+" / ");
//        Log.d(TAG, "refreshAmount: +"+expenditureNumer);
    }

}

