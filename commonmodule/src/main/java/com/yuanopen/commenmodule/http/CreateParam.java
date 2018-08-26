package com.yuanopen.commenmodule.http;

import android.util.Log;

import com.yuanopen.commenmodule.utils.BASE64Encoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;


/**
 * Created by yuanopen on 2018/7/13/013.
 */

public class CreateParam  extends BaseRequest{

    private static String TAG="CreateParam";
    private   String Host;
    private   String Action ;
    private Map<String,?> params;

    public CreateParam(String host, String action ) {
        Host = host;
        Action = action;
    }
    public CreateParam(String host, String action, Map<String, ?> params ) {
        Host = host;
        Action = action;
        this.params = params;
    }

    public CreateParam(String action, Map<String, String> params) {
        Action = action;
        this.params = params;
    }

    public CreateParam(String action) {
        Action = action;
    }


    public String getUrl() {

        String p="";
        for (String key:params.keySet())
        {
            p+= "&" + key + "=" + params.get(key).toString();
        }
        return Host+"?action="+Action+p;
    }

    public <T> String getUrl(T module) {
       String data= GsonUtils.ModuleTojosn(module);
        Log.d(TAG,data);

        data=new BASE64Encoder().encode(data.getBytes());
        Log.d(TAG,data);

        return Host+"?action="+Action+"&data="+data;
    }

    public <T> FormBody getPostUrl(T module) {
        String data= GsonUtils.ModuleTojosn(module);
        Log.d(TAG,data);

        data=new BASE64Encoder().encode(data.getBytes());
        Log.d(TAG,data);

        FormBody formBody = new FormBody
                .Builder()
                .add("action",Action)
                .add("data",data)//设置参数名称和参数值
                .build();

        return formBody;
    }

    public FormBody getPostUrl() {

        FormBody.Builder builder=new FormBody.Builder();

        for (String key:params.keySet())
        {
            builder.add(key,params.get(key).toString());

        }

        FormBody formBody = builder
                .add("action",Action)
                .build();

        return formBody;
    }
    @Override
    protected void onFail(IOException e) {
        sendFailMsg(-100, e.getMessage());
    }

    @Override
    protected void onResponseFail(int code) {
        sendFailMsg(code, "服务出现异常");
    }

    @Override
    protected  void onResponseSuccess(String body) {
        Log.d(TAG,body);
        String data;
        String code="-1";
        String errCode="-1";
        String errMsg="null";


        JSONObject object= null;
        try {
            object = new JSONObject(body);
            code=object.get("code").toString();
            Log.d(TAG,code);
            data=object.get("data").toString();
            errMsg=object.getString("errMsg");
            Log.d(TAG,data);

            if ("-1".equals(code)) {
                sendFailMsg(-101, "数据格式错误");
                Log.d(TAG,"数据格式错误");
                return;
            }
            if (code.equals(ResponseObject.CODE_SUCCESS)) {
                sendSuccMsg(data);
                Log.d(TAG,"data不为空");
            } else if (code.equals(ResponseObject.CODE_FAIL)) {
                Log.d(TAG,errMsg);
                sendFailMsg(Integer.valueOf(errCode),errMsg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
