package com.rjstudio.getsome.other;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by r0man on 2017/8/31.
 */

public class MyDataBaseOpenHelper extends SQLiteOpenHelper {

    public static final String CONSUME_ITEM_RECORD = "create table CONSUME_ITEM_RECORD (" +
            "Date int," +
            "IndexRecord int," +
            "ConsumeName text," +
            "ConsumeType int," +
            "TypeIcon int," +
            "Amount double," +
            "Remark text)";
    public static final String USER_RECORD = "create table USER_RECORD(" +
            "UserName text," +
            "UserType int ," +
            "UserBudget double)";

    private Context mContext;

    public MyDataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version,null);
    }

    public MyDataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONSUME_ITEM_RECORD);
        db.execSQL(USER_RECORD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
