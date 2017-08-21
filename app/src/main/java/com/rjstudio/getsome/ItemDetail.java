package com.rjstudio.getsome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.widget.CnToolbar;

public class ItemDetail extends AppCompatActivity {

    private TextView tv_account;
    private TextView tv_remark;
    private TextView tv_type;
    private SimpleDraweeView iv_type;
    private TextView tv_amount;
    private ConsumeItem consumeItem;
    private CnToolbar cnToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();
        initData();
        setData();
    }

    private void initView()
    {
        cnToolbar = (CnToolbar) findViewById(R.id.toolbar);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        iv_type = (SimpleDraweeView) findViewById(R.id.iv_item);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        tv_account = (TextView) findViewById(R.id.tv_account);

        cnToolbar.hideMiddleButton();
        cnToolbar.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cnToolbar.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("ConsumeItem",consumeItem);
                intent.putExtra("isNewItem",false);
                startActivity(intent);

            }
        });
    }

    private void initData()
    {
        Intent intent = getIntent();
        consumeItem = (ConsumeItem) intent.getSerializableExtra("ConsumeItem");
    }

    private void setData()
    {
        tv_amount.setText(consumeItem.getAmount()+"  $");
        tv_remark.setText(consumeItem.getRemark());
        tv_account.setText("null");
//        tv_type.setText(consumeItem.getConsumeType());
//        iv_type.setImageURI();
    }
}
