package com.wanguo.quick_batch.utils;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * 描述：
 *
 * @author Badguy
 */
public class OkHttpUtil {

    private static OkHttpClient okHttpClient = new OkHttpClient();

//    public static OkHttpClient getInstance() {
//        if (null == instance) {
//            instance = new OkHttpClient();
//        }
//        return instance;
//    }

    public static String get(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return response.body().string();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream post(String url, String jsonParam) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParam);
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return response.body().byteStream();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
