package com.rjstudio.getsome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.rjstudio.getsome.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r0man on 2017/8/11.
 */

public class CnVPAdapter extends FragmentPagerAdapter {

    private List<ContentFragment> mList;
    public static int maxPage = Integer.MAX_VALUE - 1;
    public static int centerPosition = 1073741850;
    public static int postionIn5x = ( (Integer.MAX_VALUE/2)/5 ) * 5;
    public static int positionIn10x = ( (Integer.MAX_VALUE/2)/10 ) * 10;
    public static int positionIn3th = ( (Integer.MAX_VALUE/2)/10 ) * 10 + 2;
    // 0x7fffffff hx / 34359738367 -1

    private String TAG = "CnVPAdapter";
    public CnVPAdapter(FragmentManager fm, List<ContentFragment> list)
    {

        super(fm);

        this.mList = new ArrayList<ContentFragment>();
        this.mList.addAll(list);
//        this.mList = list;
        Log.d(TAG, "CnVPAdapter: "+centerPosition);
        Log.d(TAG, "CnVPAdapter: initializing completed! Max page amount is "+ maxPage +"--- list size "+ list.size());
    }


    @Override
    public Fragment getItem(int position)
    {
        return mList.get(position % mList.size());
//        int index = position % 10;
//        if (index == 0)
//        {
//            return mList.get(5);
//        }
//        else if (index > 5)
//        {
//            if (index == 9) return mList.get(4);
//            return mList.get(index%5);
//        }
//        else if (index < 5)
//        {
//            if (index == 0) return mList.get(5);
//            return mList.get(5 + index);
//        }

//        return null;


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position%10);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public void refreshData(List<ContentFragment> data)
    {
        Log.d(TAG, "1refreshData: list size :"+mList.size()+"--data size: "+data.size());

        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
        Log.d(TAG, "2refreshData: list size :"+mList.size()+"--data size: "+data.size());
    }
}
