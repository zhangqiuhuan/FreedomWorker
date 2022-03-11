package com.work.freedomworker.utils;


import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xyin on 2016/10/12.
 * Gson工具类.
 */

public class GsonUtil {

    private static final String TAG = "GsonUtil";

    /**
     * 将json字符串转化成实体类.
     *
     * @param json json字符串
     * @param cls  实体类class对象
     * @param <T>  T类型,实体类类型
     * @return T类型表示的实体类对象
     */
    public static <T> T parseJson(String json, Class<T> cls) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "parseJson: "+e.getCause() );
        }
        return t;
    }

    /**
     * 解析json数组为ArrayList
     *
     * @param json  要解析的json
     * @param clazz 解析类
     * @return ArrayList
     */
    public static <T> ArrayList<T> parseToArrayList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 解析json数组成List.
     *
     * @param json  要解析的json
     * @param clazz 解析类
     * @return List
     */
    public static <T> List<T> parseToList(String json, Class<T[]> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            Gson gson = new Gson();
            T[] array = gson.fromJson(json, clazz);
            return Arrays.asList(array);
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 将对象转化成json字符串.
     *
     * @param src 需要转化的对象
     * @return 转化成json字符串
     */
    public static String toJson(Object src) {
        String result = null;
        if (src != null) {
            Gson gson = new Gson();
            result = gson.toJson(src);
        }
        return result;
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否可以转化为json对象
     * @param content
     * @return
     */
    public static boolean isJsonObject(String content) {
        // 此处应该注意，不要使用StringUtils.isEmpty(),因为当content为"  "空格字符串时，JSONObject.parseObject可以解析成功，
        // 实际上，这是没有什么意义的。所以content应该是非空白字符串且不为空，判断是否是JSON数组也是相同的情况。
        if(TextUtils.isEmpty(content))
            return false;
        try {
            JSONObject jsonObject=new JSONObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否可以转化为json对象
     * @param content
     * @return
     */
    public static boolean isJsonArriList(String content) {
        // 此处应该注意，不要使用StringUtils.isEmpty(),因为当content为"  "空格字符串时，JSONObject.parseObject可以解析成功，
        // 实际上，这是没有什么意义的。所以content应该是非空白字符串且不为空，判断是否是JSON数组也是相同的情况。
        if(TextUtils.isEmpty(content))
            return false;
        try {
            Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
            ArrayList<JsonObject> jsonObjects = new Gson().fromJson(content, type);
            return !(jsonObjects == null || jsonObjects.size() == 0);
        } catch (Exception e) {
            return false;
        }
    }


}
