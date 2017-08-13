package com.rjstudio.getsome;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

        initView();

    }
    private void initView()
    {
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
