package com.songshugongyi.songshugongyi.Loginandregister;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.songshugongyi.songshugongyi.activity.AtyMain;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.Config;
import com.yuanopen.commenmodule.utils.User;

import java.util.HashMap;
import java.util.Map;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Login;


/**
 * Created by Administrator.
 */

public class UserLoginRequest  {

    Context context;

    public UserLoginRequest(Context context) {
        this.context = context;
    }

    private static UserLoginRequest userLoginRequest;
    public static UserLoginRequest getIntance(Context context){
        if(userLoginRequest==null)
            userLoginRequest=new UserLoginRequest(context);
        return userLoginRequest;
    }

  public void  userLogin(String phone,String pass){
        Map<String ,String> params=new HashMap<>();
        params.put("user_phone",phone);
        params.put("user_password",pass);

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Login,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                toLogin();
            }
            @Override
            public void onSuccess(String json) {
                Config.currentUser= GsonUtils.jsonToModule(json, User.class);
                listener.onSuccess(json);
                // 更新融云回话界面
//                ConversationListFragmentManager.getConversationListFragment(context).onRestoreUI();
                toMain();
            }
        });
        //请求服务器
        String requestUrl = request.getUrl();
        Log.i("LoginActivity",requestUrl);
        request.request(requestUrl);
    }

    public void  toLogin(){
        Intent intent=new Intent(context, LoginAcitivity.class);
        intent.putExtra("fromType",1);
        context.startActivity(intent);
    }

    public void toMain() {
        Intent intent=new Intent(context, AtyMain.class);
        context.startActivity(intent);
    }

    public interface OnLoginListener{
        void onFail(String mrg);
        void onSuccess(String msg);
    }


    OnLoginListener listener;
   public void setOnLoginListener( OnLoginListener listener){
       this.listener=listener;
   }

}
