package com.work.freedomworker.net;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiUtils {


    private static ApiUtils instance;

    private ApiUtils() {
    }

    public static ApiUtils getInstance() {
        if (null == instance) {
            instance = new ApiUtils();
        }
        return instance;
    }

    public void postJson(String  method, Map<String,Object> paramsMap, StringCallback stringCallback){


        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        JSONObject jsonObject=new JSONObject();
        JSONObject json=new JSONObject(paramsMap);
//        try {
//            jsonObject.put("method",method);
//            jsonObject.put("options",json);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Log.e("jsonObject",json.toString());
        PostRequest postRequest= OkGo.post(ApiConstant.localUrl+method);
        postRequest.upJson(json);

        // 执行
        postRequest.execute(stringCallback);

    }


    //post方式访问后台
    private void postJson(StringCallback okGoCallBack, String apiUrl, String methods, Map<String, Object> paramsMap) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        PostRequest postRequest = OkGo.post(apiUrl);

        JSONObject jsonObject=new JSONObject();
        JSONObject json=new JSONObject(paramsMap);
        try {
            jsonObject.put("method",methods);
            jsonObject.put("options",json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("TAG",jsonObject.toString());
        postRequest.upJson(jsonObject);

        // 执行
        postRequest.execute(okGoCallBack);
    }


    //post方式访问后台
    public void post(String apiUrl, Map<String, Object> paramsMap,StringCallback okGoCallBack) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        PostRequest postRequest = OkGo.post(ApiConstant.localUrl+apiUrl);



        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                if (entry.getKey().equals("file")) {
                    postRequest.addFileParams(entry.getKey(), (List<File>) entry.getValue());
                } else {
                    postRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

                }
            }
        }
        // 执行
        postRequest.execute(okGoCallBack);
    }

    //post方式访问后台
    public void postHeader(String apiUrl, Map<String, Object> paramsMap,Map<String, Object> headerMap,StringCallback okGoCallBack) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        PostRequest postRequest = OkGo.post(ApiConstant.localUrl+apiUrl);



        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                if (entry.getKey().equals("file")) {
                    postRequest.addFileParams(entry.getKey(), (List<File>) entry.getValue());
                } else {
                    postRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

                }
            }
        }

        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                postRequest.headers(entry.getKey(), String.valueOf(entry.getValue()));

            }
        }

        // 执行
        postRequest.execute(okGoCallBack);
    }

    //post方式访问后台
    public void postToken(String apiUrl, Map<String, Object> paramsMap,StringCallback okGoCallBack) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        PostRequest postRequest = OkGo.post(apiUrl);


        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                if (entry.getKey().equals("file")) {
                    postRequest.addFileParams(entry.getKey(), (List<File>) entry.getValue());
                } else {
                    postRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

//                    postRequest.params(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }

        // 执行
        postRequest.execute(okGoCallBack);
    }


    //post方式访问后台
    private void postCookie(StringCallback okGoCallBack, String apiUrl, String cookies, Map<String, Object> paramsMap) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        PostRequest postRequest = OkGo.post(apiUrl);
        postRequest.headers("Cookie",cookies);

        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                if (entry.getKey().equals("file")) {
                    postRequest.addFileParams(entry.getKey(), (List<File>) entry.getValue());
                } else {
                    postRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

//                    postRequest.params(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }

        // 执行
        postRequest.execute(okGoCallBack);
    }

    //put方式访问后台
    private void putCookie(StringCallback okGoCallBack, String apiUrl, String cookies, Map<String, Object> paramsMap) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        PutRequest putRequest = OkGo.put(apiUrl);
        putRequest.headers("Cookie",cookies);

        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                if (entry.getKey().equals("file")) {
                    putRequest.addFileParams(entry.getKey(), (List<File>) entry.getValue());
                } else {
                    putRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

//                    postRequest.params(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }

        // 执行
        putRequest.execute(okGoCallBack);
    }


    //get方式访问后台
    public void get(String apiUrl, Map<String, Object> paramsMap,StringCallback okGoCallBack) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        GetRequest getRequest = OkGo.get(ApiConstant.localUrl+apiUrl);


        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                getRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

            }
        }


        // 执行
        getRequest.execute(okGoCallBack);
    }

    //post方式访问后台
    public void getHeader(String apiUrl, Map<String, Object> paramsMap,Map<String, Object> headerMap,StringCallback okGoCallBack) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        GetRequest getRequest = OkGo.get(ApiConstant.localUrl+apiUrl);


        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                getRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

            }
        }

        for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                getRequest.headers(entry.getKey(), String.valueOf(entry.getValue()));

            }
        }

        // 执行
        getRequest.execute(okGoCallBack);
    }

    //get方式访问后台
    private void getCookie(StringCallback okGoCallBack, String apiUrl, String cookies, Map<String, Object> paramsMap) {
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }

        GetRequest getRequest = OkGo.get(apiUrl);
        getRequest.headers("Cookie",cookies);

        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                getRequest.params(entry.getKey(), String.valueOf(entry.getValue()));

            }
        }

        // 执行
        getRequest.execute(okGoCallBack);
    }




}
