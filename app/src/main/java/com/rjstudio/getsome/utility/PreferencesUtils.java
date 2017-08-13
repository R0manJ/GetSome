package com.rjstudio.getsome.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by r0man on 2017/8/13.
 */

public class PreferencesUtils {

    public static String PREFERENCE_NAME = "GetSome_Pref_Commen";

    public static boolean putString (Context context, String key,String value)
    {
        SharedPreferences data = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(key,value);
        return editor.commit();
    }

    public static String getString (Context context , String key)
    {
        return getString(context,key,null);
    }

    //Important
    public static String getString(Context context,String key, String defaultValue)
    {
        SharedPreferences data = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        return data.getString(key,defaultValue);
    }

    //存储日期
    public static boolean putLong(Context context,String key,Long value)
    {
        SharedPreferences data = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putLong(key,value);
        return editor.commit();
    }

    public static Long getLong(Context context,String key )
    {
        return getLong(context,key,-1);
    }

    public static Long getLong(Context context,String key , int defaultValue)
    {
        SharedPreferences data = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        return data.getLong(key,defaultValue);
    }

}
