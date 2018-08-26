package com.songshugongyi.songshugongyi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.jaeger.library.StatusBarUtil;
import com.songshugongyi.songshugongyi.Loginandregister.UserLoginRequest;
import com.songshugongyi.songshugongyi.R;
import com.yuanopen.chatmudule.bean.Friend;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;

import java.util.HashMap;
import java.util.Map;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_FriendInfo_By_Id;
import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/5/5/005.
 */

public class LaunchActivity extends Activity {
    String TAG="LaunchActivity";
    UserLoginRequest userLoginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);
        userLoginRequest=UserLoginRequest.getIntance(this);
        StatusBarUtil.setTranslucent(this);
        final Integer time = 2000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initCurrentUser();
            }
        }, time);
    }

    private void initCurrentUser() {
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isFirstLogin=sp.getBoolean("isFirstLogin",true);
        String LoginType=sp.getString("LoginType","phone");

        if(isFirstLogin)
            userLoginRequest.toLogin();
        else {

            if("third".equals(LoginType)){
                String user_id = sp.getString("user_id", "null");
                Log.d(TAG,"user_id:"+user_id);
                if("null".equals(user_id))
                    userLoginRequest.toLogin();
                else
                    getUser(user_id);
            }else{

            String name = sp.getString("username", "null");
            String pass = sp.getString("password", "null");

            if (!"null".equals(name) && (!"null".equals(pass))) {
                userLoginRequest.userLogin(name,pass);
            }else{
                userLoginRequest.toLogin();
            }

            finish();
            }

        }

        userLoginRequest.setOnLoginListener(new UserLoginRequest.OnLoginListener() {
            @Override
            public void onFail(String mrg) {

            }

            @Override
            public void onSuccess(String msg) {
                finish();
            }
        });

    }

    private void getUser(String userId) {

        Map<String,String> params=new HashMap<>();
        params.put("friend_user_id",userId);
       params.put("user_id", "null");

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Get_FriendInfo_By_Id,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                userLoginRequest.toLogin();
            }

            @Override
            public void onSuccess(String json) {
                Friend friend= GsonUtils.jsonToModule(json,Friend.class);
                currentUser=friend.getUser();
                Intent intent=new Intent(LaunchActivity.this, AtyMain.class);
                LaunchActivity.this. startActivity(intent);
                LaunchActivity.this.finish();
            }
        });


        String url=request.getUrl();
        Log.d(TAG,url);
        request.request(url);
    }
}
