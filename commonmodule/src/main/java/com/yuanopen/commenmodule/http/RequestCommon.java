package com.yuanopen.commenmodule.http;

import android.content.Context;
import android.util.Log;

import com.yuanopen.commenmodule.utils.ShowToast;

import java.util.Map;

/**
 * Created by yuanopen on 2018/7/19/019.
 */

public class RequestCommon {


    private static final String TAG = "RequestCommon";

    public static  <T> void sendDataToService(final Context context, String host,String action, final T model, final String disciption, final OnSendListener<T> listener) {

        CreateParam request = new CreateParam(host,action);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                Log.d(TAG, disciption+"数据发送到服务器失败");
                ShowToast.showToast(context, disciption+"失败");
                listener.onFail(msg);
            }

            @Override
            public void onSuccess(String json) {
                Log.d(TAG, disciption+"数据发送到服务器成功");
                ShowToast.showToast(context,disciption+ "成功");
                listener.onSuccess(model);
            }
        });

        String url = request.getUrl(model);

        System.out.println(TAG+"--->"+url);
        request.request(url);
    }

    public static  <T> void sendDataToService(final Context context,String host, String action, Map<String,String >param, final OnSendListener<T> listener) {

        CreateParam request = new CreateParam(host,action,param);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                Log.d(TAG, "数据发送到服务器失败");

                listener.onFail(msg);
            }

            @Override
            public void onSuccess(String json) {
                Log.d(TAG, "数据发送到服务器成功");
                listener.onSuccess((T) json);
            }
        });

        String url = request.getUrl();
        System.out.println(TAG+"--->"+url);
        request.request(url);
    }
}
