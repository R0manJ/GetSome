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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rjstudio.getsome.adapter.OptionalItemAdapter;
import com.rjstudio.getsome.adapter.VPItemAdapter;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.bean.OptionalItem;
import com.rjstudio.getsome.widget.CnToolbar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private Handler popWindowsHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            cMWindows.showAtLocation(calculateLayout, Gravity.BOTTOM,10,0);
            super.handleMessage(msg);
        }

    };

    private Handler handlerCalculateLogic = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_amount.setText(msg.obj+"");
            if (tv_amount.getText().toString().equals(msg.obj+"") && isDecimal)
            {
                tv_amount.setText(msg.obj+"");
            }
        }
    };
//    private View contentView;
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
    private TextView tv_amount;
    private double tempSava;
    private int optionKey;

    //Keyboard args
    private double number = 0;
    private boolean isDecimal = false;
    private double decimalPart= 0f;
    private int decimalBit = 1;
    private int integerBit = 1;


    private ConsumeItem consumeItem;
    private List<OptionalItem> optionalItemList;
    private LinearLayout ll_setRemark;
    private int recordPosition;

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
        recordPosition = intent.getIntExtra("index",99);
//        Log.d(TAG, "initData: date "+date+" -- position = "+recordPosition);
//        Log.d(TAG,"long:"+Long.parseLong(currentDate));

        dataProvider = new DataProvider(this,date);
        consumeItem = new ConsumeItem();

    }
    private void initView()
    {
        //ToolBar
        CnToolbar cnToolbar = (CnToolbar) findViewById(R.id.toolbar);
        cnToolbar.setLeftButtonToBack()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });



        cnToolbar.setRightButtonToSave()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO : consmueItem set;
                        if (setConsumeItemToDataProvider())
                        {
                            finish();
                        }
                    }
                });

        //TODO : getApplication & this ?
        calculateLayout = LayoutInflater.from(this).inflate(R.layout.calculate_layout,null);
        cMWindows = new PopupWindow(calculateLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        cMWindows.setTouchable(true);
        cMWindows.setOutsideTouchable(true);
        tv_amount = (TextView) findViewById(R.id.tv_setAmount);
        tv_amount.setText("0.0");
        tv_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cMWindows.showAtLocation(calculateLayout, Gravity.BOTTOM,10,0);

            }
        });

        tv_setRemark = (TextView) findViewById(R.id.tv_setRemark);
        ll_setRemark = (LinearLayout) findViewById(R.id.ll_remark);

        ll_setRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Remark", Toast.LENGTH_SHORT).show();
                final EditText et_newSetRemark = initRemarkSetLayout();
                PopupWindow popupWindow = new PopupWindow(et_newSetRemark, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(false);
                popupWindow.showAsDropDown(tv_amount);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tv_setRemark.setText(et_newSetRemark.getText());
                    }
                });
            }
        });

        initOptionalItemLayout();

        if (!intent.getBooleanExtra("isNewItem",true))
        {
            consumeItem = (ConsumeItem) intent.getSerializableExtra("ConsumeItem");
            number = consumeItem.getAmount();
            tv_amount.setText(number+"  $");
            //TODO : Type
            Log.d(TAG, "initView: consume type :"+consumeItem.getConsumeType());
            optionalItemList.get(consumeItem.getConsumeType()).setSelected(true);
        }
        tv_setRemark.setText(consumeItem.getRemark());
        initRemarkSetLayout();
        keyboardLogic();

    }
    private EditText initRemarkSetLayout()
    {
        EditText et_remark = new EditText(this);
//        et_remark.setBackgroundColor(getResources().getColor(R.color.BurlyWood));
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(500,500);
        et_remark.setBackgroundColor(getResources().getColor(R.color.ahorroWhite2));
        et_remark.setLayoutParams(layoutParams);
        et_remark.setTextColor(getResources().getColor(R.color.ahorroBlack));
//        et_remark.setText("null");
//        Log.d(TAG, "initRemarkSetLayout: "+et_remark.getText());
        return et_remark;
    }

    private void initOptionalItemLayout()
    {
        //Test data
        optionalItemList = new ArrayList<>();
        OptionalItem optionalItem = new OptionalItem();
        optionalItem.setImageId(R.drawable.breakfast);
        optionalItem.setTextId(R.string.breakfast);
        optionalItem.setIndex(0);
        optionalItemList.add(optionalItem);

        optionalItem = new OptionalItem();
        optionalItem.setImageId(R.drawable.lunch);
        optionalItem.setTextId(R.string.lunch);
        optionalItem.setIndex(1);
        optionalItemList.add(optionalItem);

        optionalItem = new OptionalItem();
        optionalItem.setImageId(R.drawable.dinner);
        optionalItem.setTextId(R.string.dinner);
        optionalItem.setIndex(2);
        optionalItemList.add(optionalItem);

        optionalItem = new OptionalItem();
        optionalItem.setImageId(R.drawable.shopping);
        optionalItem.setTextId(R.string.shopping);
        optionalItem.setIndex(3);
        optionalItemList.add(optionalItem);

        optionalItem = new OptionalItem();
        optionalItem.setImageId(R.drawable.other);
        optionalItem.setTextId(R.string.other);
        optionalItem.setIndex(4);
        optionalItemList.add(optionalItem);

        optionalItem = new OptionalItem();
        optionalItem.setImageId(R.drawable.transfer);
        optionalItem.setTextId(R.string.transfer);
        optionalItem.setIndex(5);
        optionalItemList.add(optionalItem);



        rv_optionalItem = (RecyclerView) findViewById(R.id.rv_chooseItem);
        optionalItemAdapter = new OptionalItemAdapter(this, optionalItemList, R.layout.item_layout_2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        gridLayoutManager.canScrollHorizontally();
        rv_optionalItem.setAdapter(optionalItemAdapter);
        optionalItemAdapter.setOnItemClickListener(new OptionalItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isSelect, OptionalItem optionalItem) {
                consumeItem.setConsumeType(position);
                consumeItem.setTypeIcon(optionalItem.getImageId());
//                consumeItem.setIndex((int)(Math.random()*1000000+100 ));
                consumeItem.setConsumeName(getString(optionalItem.getTextId()));
                Log.d(TAG, "onClick: "+optionalItem.getImageId()+"---"+optionalItem.getTextId()+"---text: "+getString(optionalItem.getTextId()));
            }
        });

        rv_optionalItem.setLayoutManager(gridLayoutManager);
    }

    String TAG = "TEST";


    private void keyboardLogic()
    {
        Button bu_1 = (Button) calculateLayout.findViewById(R.id.bu_1);
        Button bu_2 = (Button) calculateLayout.findViewById(R.id.bu_2);
        Button bu_3 = (Button) calculateLayout.findViewById(R.id.bu_3);
        Button bu_4 = (Button) calculateLayout.findViewById(R.id.bu_4);
        Button bu_5 = (Button) calculateLayout.findViewById(R.id.bu_5);
        Button bu_6 = (Button) calculateLayout.findViewById(R.id.bu_6);
        Button bu_7 = (Button) calculateLayout.findViewById(R.id.bu_7);
        Button bu_8 = (Button) calculateLayout.findViewById(R.id.bu_8);
        Button bu_9 = (Button) calculateLayout.findViewById(R.id.bu_9);
        Button bu_0 = (Button) calculateLayout.findViewById(R.id.bu_0);
        Button bu_dop = (Button) calculateLayout.findViewById(R.id.bu_dop);
        Button bu_del = (Button) calculateLayout.findViewById(R.id.bu_del);
        Button bu_ac = (Button) calculateLayout.findViewById(R.id.bu_ac);
        Button bu_equals = (Button) calculateLayout.findViewById(R.id.bu_equals);
        Button bu_sub = (Button) calculateLayout.findViewById(R.id.bu_sub);
        Button bu_add = (Button) calculateLayout.findViewById(R.id.bu_add);
        Button bu_mul = (Button) calculateLayout.findViewById(R.id.bu_mul);
        Button bu_div = (Button) calculateLayout.findViewById(R.id.bu_div);
        bu_1.setOnClickListener(this);
        bu_0.setOnClickListener(this);
        bu_2.setOnClickListener(this);
        bu_3.setOnClickListener(this);
        bu_4.setOnClickListener(this);
        bu_5.setOnClickListener(this);
        bu_6.setOnClickListener(this);
        bu_7.setOnClickListener(this);
        bu_8.setOnClickListener(this);
        bu_9.setOnClickListener(this);
        bu_del.setOnClickListener(this);
        bu_div.setOnClickListener(this);
        bu_dop.setOnClickListener(this);
        bu_sub.setOnClickListener(this);
        bu_ac.setOnClickListener(this);
        bu_add.setOnClickListener(this);
        bu_equals.setOnClickListener(this);
        bu_mul.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bu_0:
                calculateLogic(0);
                break;
            case R.id.bu_1:
                calculateLogic(1);
//                Toast.makeText(context, "Onclick + 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bu_2:
                calculateLogic(2);
                break;
            case R.id.bu_3:
                calculateLogic(3);
                break;
            case R.id.bu_4:
                calculateLogic(4);
                break;
            case R.id.bu_5:
                calculateLogic(5);
                break;
            case R.id.bu_6:
                calculateLogic(6);
                break;
            case R.id.bu_7:
                calculateLogic(7);
                break;
            case R.id.bu_8:
                calculateLogic(8);
                break;
            case R.id.bu_9:
                calculateLogic(9);
                break;

            case R.id.bu_ac:
                calculateLogic(-1);
                break;
            case R.id.bu_dop:
//                Toast.makeText(context, "dop", Toast.LENGTH_SHORT).show();
                calculateLogic(-2);
                break;
            case R.id.bu_del:
                calculateLogic(-3);
                break;
            case R.id.bu_add:
                calculateLogic(-4);
                break;
            case R.id.bu_sub:
                calculateLogic(-5);
                break;
            case R.id.bu_mul:
                calculateLogic(-6);
                break;
            case R.id.bu_div:
                calculateLogic(-7);
                break;
            case R.id.bu_equals:
                calculateLogic(-8);
                break;
        }
    }


    public void calculateLogic(int key)
    {
        if (key == -2)
        {
            //Clicked the dop key
            isDecimal = true;

            return;
        }
        else if (key == -3)
        {
            if (!isDecimal)
            {
                if (integerBit>1)
                {

//                    number /= 10;
                    int integer = (int) number;
                    integer /= 10;
                    number = integer;
                    integerBit --;
                }
            }
            else
            {
//                Log.d(TAG, "calculateLogic: decimalBit = "+decimalBit);
                int bit10 = 10;
                for (int i = 1 ; i < (decimalBit-1) ; i ++)
                {
                    bit10 *= 10;
                }
                if (decimalBit > 1)
                {
                    int integer = (int) (number * bit10);
                    integer /= 10;
                    number = (double)integer/bit10*10;
                    decimalBit--;
                    if (decimalBit == 1) isDecimal=false;
                }
            }
            Message msg = new Message();
            msg.obj = number;
            handlerCalculateLogic.sendMessage(msg);
            return;
        }
        else if (key == -4)
        {
            //加法逻辑
            tempSava = number;
            number = 0;
            optionKey = 4;

            isDecimal = false;
            decimalBit = 1;
            integerBit = 1;
            Message msg = new Message();
            msg.obj = number;
            handlerCalculateLogic.sendMessage(msg);
            return;

        }
        else if (key == -5)
        {
            tempSava = number;
            number = 0;
            optionKey = 5;

            isDecimal = false;
            decimalBit = 1;
            integerBit = 1;
            Message msg = new Message();
            msg.obj = number;
            handlerCalculateLogic.sendMessage(msg);
            return;


        }
        else if (key == -6)
        {
            tempSava = number;
            number = 0;
            optionKey = 6;

            isDecimal = false;
            decimalBit = 1;
            integerBit = 1;
            Message msg = new Message();
            msg.obj = number;
            handlerCalculateLogic.sendMessage(msg);
            return;

        }
        else if (key == -7)
        {
            tempSava = number;
            number = 0;
            optionKey = 7;

            isDecimal = false;
            decimalBit = 1;
            integerBit = 1;

            Message msg = new Message();
            msg.obj = number;
            handlerCalculateLogic.sendMessage(msg);
            return;
        }
        else if (key == -8)
        {
            switch (optionKey)
            {
                case 4:
//                    Log.d(TAG, "calculateLogic: add = "+number+"--"+tempSava);
                    number = BigDecimal.valueOf(tempSava).add(BigDecimal.valueOf(number)).doubleValue();
                    break;
                case 5:
                    number = BigDecimal.valueOf(tempSava).subtract(BigDecimal.valueOf(number)).doubleValue();
                    break;
                case 6:
                    number = BigDecimal.valueOf(tempSava).multiply(BigDecimal.valueOf(number)).doubleValue();
                    break;
                case 7:
                    //除法
                    number = BigDecimal.valueOf(tempSava).divide(BigDecimal.valueOf(number)).doubleValue();
                    break;
            }
            Message msg = new Message();
            msg.obj = number;
            handlerCalculateLogic.sendMessage(msg);
            return;
        }
        else if (key == -1)
        {
            //AC
            number = 0.0f;
            decimalPart = 0f;
            decimalBit = 1;
            integerBit = 1;
            isDecimal = false;

            optionKey = 0;
            tempSava = 0;
            Message msg = new Message();
            msg.obj = number;
            handlerCalculateLogic.sendMessage(msg);
            return;

        }

        if (!isDecimal )
        {

            if ( integerBit >= 9) return;
            number = (number * 10) + key ;
            integerBit ++;

        }
        else
        {

            //小数的表达方式
            int beforeChangeToDecimal = 0;
            //0.1 , 0.01 , 0.001
            if (decimalBit <= 3)
            {

                int bit10 = 10;
//                Log.d(TAG, "calculateLogic: bit :"+decimalBit);
                for (int i = 1 ; i < decimalBit ;i++)
                {
                    bit10 *= 10;
//                    Log.d(TAG, "calculateLogic: bit = "+bit10);
                }
                beforeChangeToDecimal = (int) (number * bit10)/bit10;
//                Log.d(TAG, "calculateLogic: beforeChangeToDEcimal (1)+"+beforeChangeToDecimal);
                beforeChangeToDecimal = beforeChangeToDecimal * bit10 + key;
//                Log.d(TAG, "calculateLogic: beforeChangeToDEcimal (2)+"+beforeChangeToDecimal);
                decimalPart = (double)(beforeChangeToDecimal % bit10) / bit10;
//                Log.d(TAG, "calculateLogic: bigDecimal:"+bigDecimal);
//                Log.d(TAG, "calculateLogic: beforeChangeToDEcimal (3)+"+decimalPart);
//                Log.d(TAG, "calculateLogic: "+BigDecimal.valueOf(decimalPart).doubleValue());
                number = BigDecimal.valueOf(decimalPart).add(BigDecimal.valueOf(number)).doubleValue();
//                Log.d(TAG, "calculateLogic: number "+number);
                decimalBit ++;
            }

        }

        Message msg = new Message();
        msg.obj = number;
        handlerCalculateLogic.sendMessage(msg);
//        bit ++;
    }


    private boolean setConsumeItemToDataProvider()
    {
        if (!tv_amount.getText().equals("0.0"))
        {

            consumeItem.setAmount(Double.valueOf(number));

        }
        else
        {
            Toast.makeText(context, "请输入金额", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (optionalItemAdapter.getItemNumber() > optionalItemList.size())
        {

            Toast.makeText(context, "请选择项目"+optionalItemAdapter.getItemNumber(), Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            consumeItem.setConsumeType(optionalItemAdapter.getItemNumber());
        }


        consumeItem.setRemark(tv_setRemark.getText()+"");
        consumeItem.setDate(date);

        consumeItem.setIndex((int)(Math.random()*10000+100 ));
        Toast.makeText(context, ""+consumeItem.getIndex(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "setConsumeItemToDataProvider: "+consumeItem.getIndex());
        if (intent.getBooleanExtra("isNewItem",true))
        {
            dataProvider.put(consumeItem);
        }
        else
        {
            dataProvider.upDate(recordPosition,consumeItem);
            intent.putExtra("ConsumeItem",consumeItem);
            setResult(909,intent);
        }
        Log.d(TAG, "setConsumeItemToDataProvider: "+consumeItem.getAmount()+"--"+consumeItem.getConsumeName()+"--"+consumeItem.getTypeIcon());
        return true;

    }
}
