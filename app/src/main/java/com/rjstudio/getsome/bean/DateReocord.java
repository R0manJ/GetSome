package com.rjstudio.getsome.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by r0man on 2017/8/13.
 */

public class DateReocord {
    //记录每天的消费记录
    //装载List<ConsumeItem>,记录时间
    private List<ConsumeItem> mList;
    //20170813
    private Long date;

    public List<ConsumeItem> getmList() {
        return mList;
    }

    public void setmList(List<ConsumeItem> mList) {
        this.mList = mList;
    }

    public long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
