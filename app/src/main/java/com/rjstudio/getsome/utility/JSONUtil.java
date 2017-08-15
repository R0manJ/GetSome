package com.rjstudio.getsome.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by r0man on 2017/8/13.
 */

public class JSONUtil {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson()
    {
        return gson;
    }

    public static <T> T fromJson(String json,Class<T> clz)
    {
        return gson.fromJson(json,clz);
    }

    public static <T> T fromJson(String json , Type type)
    {
        if (json != null)
            return gson.fromJson(json,type);
        return (T)new ArrayList<T>();
    }

    public static String toJson(Object obj)
    {
        return gson.toJson(obj);
    }

}
