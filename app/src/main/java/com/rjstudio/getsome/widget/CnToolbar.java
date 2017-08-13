package com.rjstudio.getsome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rjstudio.getsome.R;

/**
 * Created by r0man on 2017/8/11.
 */

public class CnToolbar extends Toolbar {

    private LayoutInflater mInflater;
    private View mView;

    private Button bu_left;
    private Button bu_middle;
    private Button bu_right;

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


    private void initView()
    {
        if (mView == null)
        {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.cn_toolbar_layout,null);

            bu_left = (Button) mView.findViewById(R.id.bu_left);
            bu_middle = (Button) mView.findViewById(R.id.bu_middle);
            bu_right = (Button) mView.findViewById(R.id.bu_right);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView,lp);
        }
    }

    public void setLeftButtonToBack()
    {
        bu_left.setBackground(null);
        bu_left.setText(getContext().getResources().getString(R.string.back));
    }
    public void setLeftButtonText(String text)
    {
//        bu_left.setBackground();
        bu_left.setText(text);
    }
    public Button setLeftButtonTexts(String text)
    {
        setLeftButtonText(text);
        return bu_left;
    }

    public Button getLeftButton()
    {
        return bu_left;
    }


    public void hideMiddleButton()
    {
        bu_middle.setVisibility(INVISIBLE);
    }
    public void showMiddleButton()
    {
        bu_middle.setVisibility(VISIBLE);
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
}
