package com.rjstudio.getsome.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

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
        return gson.fromJson(json,type);
    }

    public static String toJson(Object obj)
    {
        return gson.toJson(obj);
    }

}
