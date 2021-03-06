package com.rjstudio.getsome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjstudio.getsome.R;

/**
 * Created by r0man on 2017/8/11.
 */

public class CnToolbar extends Toolbar {

    private LayoutInflater mInflater;
    private View mView;

    private Button bu_middle;
    private Button bu_right;
    private LinearLayout bu_left;
    private TextView tv_left;
    private SimpleDraweeView sv_left;

    public CnToolbar(Context context) {
        this(context,null);
    }

    public CnToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CnToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null)
        {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CnToolbar,defStyleAttr,0);
            int n = a.getIndexCount();
            for (int i = 0;i < n ; i++)
            {
                //TODO : 属性逻辑还没有添加
            }
            a.recycle();

        }

        //初始化界面
        initView();

        //TODO : 这个方法不知道什么意思？
        setContentInsetsRelative(10,10);
    }


    String TAG ="action test";

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG, "onTouchEvent: down");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onTouchEvent: up");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "onTouchEvent: mmmmm");
//                break;
//            default:
//                break;
//        }
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_UP:
//                Log.d("***", "ACTION_UP");
//                break;
//            case MotionEvent.ACTION_DOWN:
//                Log.d("***", "ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d("***", "ACTION_MOVE");
//                break;
//            default:
//                break;
//        }
//
//        return super.onTouchEvent(ev);
//
//    }

    private void initView()
    {
        if (mView == null)
        {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.cn_toolbar_layout,null);

            bu_left = (LinearLayout) mView.findViewById(R.id.bu_left);
            sv_left = (SimpleDraweeView) mView.findViewById(R.id.iv_leftIcon);
            tv_left = (TextView) mView.findViewById(R.id.tv_left);
            sv_left.setImageURI("res:///"+R.drawable.menu);

            bu_middle = (Button) mView.findViewById(R.id.bu_middle);
            bu_right = (Button) mView.findViewById(R.id.bu_right);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView,lp);
        }
    }




    public void setLeftButtonText(String text)
    {
//        bu_left.setBackground();
//        bu_left.setText(text);
        tv_left.setText(text);
    }
    public LinearLayout setLeftButtonTexts(String text)
    {
        setLeftButtonText(text);
        return bu_left;
    }

    public LinearLayout getLeftButton()
    {
        return bu_left;
    }


    public CnToolbar hideMiddleButton()
    {
        bu_middle.setVisibility(INVISIBLE);
        //TODO : new Instance
        return null;
    }
    public Button showMiddleButton()
    {
        bu_middle.setVisibility(VISIBLE);
        return bu_middle;
    }

    public Button getRightButton()
    {
        return bu_right;
    }
    public void setRightButtonText(String text)
    {
        bu_right.setText(text);
    }
    public Button setRightButtonTexts(String text)
    {
        setRightButtonText(text);
        return bu_right;
    }

    public LinearLayout setLeftButtonToMenu(String date)
    {
        sv_left.setImageURI("res:///"+R.drawable.menu);
        tv_left.setText(date);
        return bu_left;
    }

    public LinearLayout setLeftButtonToBack()
    {
        sv_left.setImageURI("res:///"+R.drawable.back);
        tv_left.setText(getContext().getResources().getString(R.string.back));
        return bu_left;
    }

    public Button setRightButtonToSave()
    {
        bu_middle.setVisibility(View.INVISIBLE);
        bu_right.setBackground(getResources().getDrawable(R.drawable.save));
        return bu_right;
    }

    public Button setRightButtonToAdd()
    {
        bu_middle.setVisibility(View.VISIBLE);
        bu_right.setBackground(getResources().getDrawable(R.drawable.edit));
        return bu_right;
    }


}
