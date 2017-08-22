package com.rjstudio.getsome;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjstudio.getsome.bean.ConsumeItem;
import com.rjstudio.getsome.bean.DataProvider;
import com.rjstudio.getsome.widget.CnToolbar;

public class ItemDetail extends AppCompatActivity {

    private TextView tv_account;
    private TextView tv_remark;
    private TextView tv_type;
    private SimpleDraweeView iv_type;
    private TextView tv_amount;
    private ConsumeItem consumeItem;
    private CnToolbar cnToolbar;
    private Button bu_delete;
    private DataProvider dataProvider;
    private int index;

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
        bu_delete = (Button) findViewById(R.id.bu_delete);

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

        bu_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ItemDetail.this);
                dialogBuilder.setTitle(getString(R.string.Warning));
                dialogBuilder.setMessage(getString(R.string.isDelete));
                dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataProvider.deleteConsumeItem(index);
                        finish();
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.create().show();

            }
        });
    }

    private void initData()
    {
        Intent intent = getIntent();
        consumeItem = (ConsumeItem) intent.getSerializableExtra("ConsumeItem");
        long date = consumeItem.getDate();
        dataProvider = new DataProvider(this,date);
        index = intent.getIntExtra("index",999);
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
