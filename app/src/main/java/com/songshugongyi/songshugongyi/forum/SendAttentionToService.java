package com.songshugongyi.songshugongyi.forum;

import android.content.Context;
import android.util.Log;

import com.songshugongyi.songshugongyi.bean.Attention;
import com.songshugongyi.songshugongyi.util.ShowToast;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.utils.getCurrentTime;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Hot_Topic_Attention;

/**
 * Created by yuanopen on 2018/7/19/019.
 */

public class SendAttentionToService  {
  private  static  String ATG="SendAttentionToService";

    public  static  void sendAttentionToService(final Context context, String atttention_user_id, String user_id){
        Attention attention=new Attention();
        attention.setAttented_user_id(atttention_user_id);
        attention.setUser_id(user_id);
        attention.setCreate_time(getCurrentTime.getTime());
        attention.setUpdate_time(getCurrentTime.getTime());

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Hot_Topic_Attention);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                ShowToast.showToast(context,"关注失败");
                Log.d(ATG,"关注失败"+msg);
            }

            @Override
            public void onSuccess(String json) {
                ShowToast.showToast(context,"关注失败");
                Log.d(ATG,"关注失败"+json);
            }
        });

        request.PostRequest(UriConfig.UserServlet,request.getPostUrl(attention));
    }

}
