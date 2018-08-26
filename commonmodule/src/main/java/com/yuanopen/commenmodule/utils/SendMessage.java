package com.yuanopen.commenmodule.utils;

import android.content.Context;

import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.message.FriendMessage;
import com.yuanopen.commenmodule.message.GroupMessage;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Add_Friend;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Join_Group;

/**
 * Created by yuanopen on 2018/7/30/030.
 */

public class SendMessage {
    public  static  void sendMessage(final Context context, FriendMessage message){
        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Add_Friend);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                ShowToast.showToast(context,"请求失败！");
            }

            @Override
            public void onSuccess(String json) {
                ShowToast.showToast(context,"请求成功！等待验证");
            }
        });
        request.PostRequest(UriConfig.UserServlet,request.getPostUrl(message));
    }

    public  static  void sendAddGroupMessage(final Context context, GroupMessage message){
        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Join_Group);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                ShowToast.showToast(context,"请求失败！");
            }

            @Override
            public void onSuccess(String json) {
                ShowToast.showToast(context,"请求成功！");
            }
        });
        request.PostRequest(UriConfig.UserServlet,request.getPostUrl(message));
    }
}
