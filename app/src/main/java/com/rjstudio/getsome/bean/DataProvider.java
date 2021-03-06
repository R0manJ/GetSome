package com.rjstudio.getsome.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.rjstudio.getsome.other.MyDataBaseOpenHelper;
import com.rjstudio.getsome.utility.JSONUtil;
import com.rjstudio.getsome.utility.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by r0man on 2017/8/13.
 */

public class DataProvider {
    //data -> json
    //recordDate,list<consumeItem>
    public static final String CONSUMEITEM_JSON = "item_json";
    private SparseArray<DateReocord> datas = null;
    private Context mContext;
    private List<ConsumeItem> consumeItemList = null;

    private Long date;
    Calendar calendar = Calendar.getInstance();

    String TAG = "TEST";
    private final MyDataBaseOpenHelper myDataBaseOpenHelper;
    private final SQLiteDatabase sqLiteDatabase;

    public DataProvider(Context context,Long date)
    {
        myDataBaseOpenHelper = new MyDataBaseOpenHelper(context,"Usr_data",null,1);
        sqLiteDatabase = myDataBaseOpenHelper.getWritableDatabase();

        mContext = context;
//        datas = new SparseArray<>();
        //装载数据
//        listToSparse();
        Log.d(TAG, "DataProvider: "+date);
        this.date = date;
        consumeItemList = getDataFromLoacl(date);
    }

    private void listToSparse()
    {
        //从本地获取数据
        List<DateReocord> items = getDataFromLocal();
        if (items != null && items.size() > 0)
        {
            for (DateReocord dateReocord: items)
            {
                Long id = dateReocord.getDate();
                datas.put(id.intValue(),dateReocord);
            }
        }
    }

//    private void ConsumeItemListToSparse(Long date)
//    {
//        List<ConsumeItem> items = getDataFromLoacl(date);
//        if (items != null && items.size() > 0)
//        {
//            for (ConsumeItem consumeItem : items)
//            {
//                Long id = consumeItem.getDate();
//            }
//        }
//    }

    //只获取指定日期的json数据
    private List<ConsumeItem> getDataFromLoacl(Long date)
    {
        String json = PreferencesUtils.getString(mContext,date+"");
        List<ConsumeItem> dateConsumeItem = null;
        if (json != null)
        {
            dateConsumeItem = JSONUtil.fromJson(json,new TypeToken<List<ConsumeItem>>(){}.getType());
        }
        else
        {
            dateConsumeItem = new ArrayList<>();
        }
        return dateConsumeItem;
    }
    //本地获取数据的方法
    private List<DateReocord> getDataFromLocal()
    {
        //preferences - >json - > lisy -> return
        String json = PreferencesUtils.getString(mContext,CONSUMEITEM_JSON);
        List<DateReocord> dateReocords = null;
        if (json != null)
        {
            //json -> list
            dateReocords = JSONUtil.fromJson(json,new TypeToken<List<DateReocord>>(){}.getType());
        }

        return dateReocords;
    }


    //新建一个对象 -> 进新数据 -> 保存到Preference
    private ConsumeItem convertData(ConsumeItem consumeItem)
    {
        //获取到 DateRecord 的 List<ConsumeItem> -> add ConsumeItem
        ConsumeItem item = new ConsumeItem();
        item.setAmount(consumeItem.getAmount());
        item.setConsumeName(consumeItem.getConsumeName());
        item.setTypeIcon(consumeItem.getTypeIcon());
        item.setConsumeType(consumeItem.getConsumeType());
        item.setDate(consumeItem.getDate());
        return item;
    }

    //增加数据
    private void AddConsumeItem(ConsumeItem consumeItem)
    {
        consumeItemList.add(convertData(consumeItem));
        commit();
    }
    //删除数据
    public void deleteConsumeItem(int index)
    {
        Log.d(TAG, "deleteConsumeItem: "+consumeItemList.get(index).getDate()+"----"+consumeItemList.get(index).getIndex());
        sqLiteDatabase.delete("CONSUME_ITEM_RECORD","Date=? and IndexRecord=?",new String[]{consumeItemList.get(index).getDate()+"",consumeItemList.get(index).getIndex()+""});
        consumeItemList.remove(index);
        commit();
    }
    //修改数据
    private void editCounsumeItem(int position,ConsumeItem newConsumeItem)
    {
        consumeItemList.remove(position);
        consumeItemList.add(position,newConsumeItem);
        commit();
    }
    //提交
    private void commit()
    {
        PreferencesUtils.putString(mContext,date+"",JSONUtil.toJson(consumeItemList));
        Log.d("TEST",date+"的数据已被修改"+JSONUtil.toJson(consumeItemList));
        sqLiteDatabase.close();
    }

    public void put(ConsumeItem consumeItem)
    {
//        Long date = consumeItem.getDate();
//        ConsumeItem item = convertData(consumeItem);
//        DateReocord dateReocords = datas.get(date.intValue());
//        List<ConsumeItem> consumeItems = dateReocords.getmList();
        consumeItemList.add(consumeItem);

        ContentValues values = new ContentValues();
//        "Date int," +
//                "Index int," +
//                "ConsumeName text," +
//                "ConsumeType int," +
//                "TypeIcon int," +
//                "Amount double," +
//                "Remark text)";
        values.put("Date",consumeItem.getDate());
        values.put("IndexRecord",consumeItem.getIndex());
        values.put("ConsumeName",consumeItem.getConsumeName());
//        values.put("ConsumeType",consumeItem.getConsumeType());
        values.put("TypeIcon",consumeItem.getTypeIcon());
        values.put("Amount",consumeItem.getAmount());
        values.put("Remark",consumeItem.getRemark());

        sqLiteDatabase.insert("CONSUME_ITEM_RECORD",null,values);
        commit();
    }

//    Update
    public void upDate(int index,ConsumeItem newConsumeItem)
    {

        ContentValues values = new ContentValues();

        values.put("Date",newConsumeItem.getDate());
        values.put("IndexRecord",newConsumeItem.getIndex());
        values.put("ConsumeName",newConsumeItem.getConsumeName());
        values.put("TypeIcon",newConsumeItem.getTypeIcon());
        values.put("Amount",newConsumeItem.getAmount());
        values.put("Remark",newConsumeItem.getRemark());

        sqLiteDatabase.update("CONSUME_ITEM_RECORD",values,"Date = ? and IndexRecord = ?",new String[]{consumeItemList.get(index).getDate()+"",consumeItemList.get(index).getIndex()+""});
        consumeItemList.remove(index);
        consumeItemList.add(index,newConsumeItem);

        commit();
//        return ;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public List<ConsumeItem> get(Date date)
    {
        List mList = new ArrayList();
        long dateIndex = Long.parseLong(sdf.format(date));
        mList = JSONUtil.fromJson(PreferencesUtils.getString(mContext,dateIndex+""),new TypeToken<List<ConsumeItem>>(){}.getType());
        return mList;
    }
    public List<ConsumeItem> get(long date)
    {
        List mList = new ArrayList();
        String json = PreferencesUtils.getString(mContext,date+"");
        Log.d(TAG, date + " get: json is "+json);
        mList = JSONUtil.fromJson(json,new TypeToken<List<ConsumeItem>>(){}.getType());
        return mList;
    }

//    private void commit ()
//    {
//        List<DateReocord> dateReocords = sparseToList();
//        PreferencesUtils.putString(mContext,CONSUMEITEM_JSON, JSONUtil.toJson(dateReocords));
//        //把当天的数据存进去，key为日期，value 为 consumeItem
//        Long date = 20180813l;
//        PreferencesUtils.putString(mContext,date+"",JSONUtil.toJson());
//    }

    private List<DateReocord> sparseToList()
    {
        int size = datas.size();
        List<DateReocord> list = new ArrayList<>(size);
        for (int i  =0 ; i <size ; i ++)
        {
            list.add(datas.valueAt(i));
        }
        return list;
    }


}
