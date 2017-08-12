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
    public static int centerPosition = 1073741820;
    // 0x7fffffff hx / 34359738367 -1

    private String TAG = "CnVPAdapter";
    public CnVPAdapter(FragmentManager fm, List<ContentFragment> list)
    {
        super(fm);
        this.mList = new ArrayList<ContentFragment>();
        this.mList.addAll(list);
        Log.d(TAG, "CnVPAdapter: "+centerPosition);
        Log.d(TAG, "CnVPAdapter: initializing completed! Max page amount is "+ maxPage +"--- list size "+ list.size());
    }

    @Override
    public Fragment getItem(int position)
    {

        int offSet;
        Log.d(TAG, "getItem: list size - " + mList.size() + " current position :"+position);
        if (position > centerPosition)
        {
            // slide right
            offSet = (position - centerPosition)%3;
            return mList.get(2 - offSet);
        }
        else if (position < centerPosition)
        {
            // slide left
            offSet = (centerPosition - position)%3 ;
            return mList.get(2+offSet);
        }
        else if (position%3 == 0)
        {
            return mList.get(2);
        }
        return null;
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
