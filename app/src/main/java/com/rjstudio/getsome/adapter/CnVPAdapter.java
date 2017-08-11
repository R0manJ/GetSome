package com.rjstudio.getsome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.rjstudio.getsome.fragment.ContentFragment;

import java.util.List;

/**
 * Created by r0man on 2017/8/11.
 */

public class CnVPAdapter extends FragmentPagerAdapter {

    private List<ContentFragment> mList;

    public CnVPAdapter(FragmentManager fm, List<ContentFragment> list)
    {
        super(fm);
        this.mList = list ;
    }

    @Override
    public Fragment getItem(int position)
    {

        return mList.get(position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
