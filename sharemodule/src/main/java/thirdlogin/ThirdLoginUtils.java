package thirdlogin;

import android.util.Log;

import com.yuanopen.commenmodule.ParamsConfig;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.User;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by yuanopen on 2018/6/24/024.
 */

public class ThirdLoginUtils {

    private String TAG="ThirdLoginUtils";

    private  static ThirdLoginUtils ThirdLoginIntance;

    public static ThirdLoginUtils getIntance(){
        if(ThirdLoginIntance==null)
            ThirdLoginIntance=new ThirdLoginUtils();

        return ThirdLoginIntance;
    }

    /**
     *QQ第三方登录
     */
    public void   ThirdQQLogin(){
        Log.d(TAG,"ThirdQQLogin");
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.i("hash:",hashMap.toString());
                //{is_yellow_vip=0, msg=, vip=0, nickname=源愿圆远,
                // figureurl_qq_1=http://thirdqq.qlogo.cn/qqapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/40,
                // city=, gender=男,
                // figureurl_1=http://qzapp.qlogo.cn/qzapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/50,
                // province=, is_yellow_year_vip=0,
                // year=1998, yellow_vip_level=0,922B3DB7B8B0CA707ACA0550DC58E239
                // figureurl=http://qzapp.qlogo.cn/qzapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/30,
                // figureurl_2=http://qzapp.qlogo.cn/qzapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/100,
                // is_lost=0, figureurl_qq_2=http://thirdqq.qlogo.cn/qqapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/100,
                // level=0, ret=0}
                PlatformDb db= platform.getDb();
                Log.i("hash:",db.exportData());

//                {"unionid":"","userID":"922B3DB7B8B0CA707ACA0550DC58E239",
// "icon":"http:\/\/thirdqq.qlogo.cn\/qqapp\/101480730\/922B3DB7B8B0CA707ACA0550DC58E239\/100",
// "expiresTime":1529804900156,
// "nickname":"源愿圆远",
// "token":"6EC41BFFEAC0B1203F07838946D23445",
// "secretType":"0","gender":"0",
// "pf":"desktop_m_qq-10000144-android-2002-"
// ,"pay_token":"769037AA23196B293C8BB49702020936",
// "secret":"","iconQzone":"http:\/\/qzapp.qlogo.cn\/qzapp\/101480730\/922B3DB7B8B0CA707ACA0550DC58E239\/100",
// "pfkey":"2536eba8ce8f08ba006cbe0959b8fa8b","expiresIn":7776000}

//                Log.i("hash:", platform.getDb().exportData());
//                Log.i("hash:", platform.getDb().exportData());
//
//                Log.i("hash:", platform.getDb().exportData());
//                Log.i("hash:", platform.getDb().exportData());
//                platform.getDb().exportData();
                CheckOnService(db);

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                      l.OnFail(throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });//授权回调监听，监听oncomplete，onerror，oncancel三种状态

        if(plat.isClientValid()){
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
        }
        if(plat.isAuthValid()){
//判断是否已经存在授权状态，可以根据自己的登录逻辑设置
//            Toast.makeText(this, "已经授权过了", Toast.LENGTH_SHORT).show();
            return;
        }
         //plat.authorize();	//要功能，不要数据
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面

    }
    /**
     *QQ第三方登录
     */
    public void   ThirdWeiXinLogin(){
        Log.i("ThirdQQLogin:","ThirdQQLogin");
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.i("hash:",hashMap.toString());
                //{is_yellow_vip=0, msg=, vip=0, nickname=源愿圆远,
                // figureurl_qq_1=http://thirdqq.qlogo.cn/qqapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/40,
                // city=, gender=男,
                // figureurl_1=http://qzapp.qlogo.cn/qzapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/50,
                // province=, is_yellow_year_vip=0,
                // year=1998, yellow_vip_level=0,922B3DB7B8B0CA707ACA0550DC58E239
                // figureurl=http://qzapp.qlogo.cn/qzapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/30,
                // figureurl_2=http://qzapp.qlogo.cn/qzapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/100,
                // is_lost=0, figureurl_qq_2=http://thirdqq.qlogo.cn/qqapp/101480730/922B3DB7B8B0CA707ACA0550DC58E239/100,
                // level=0, ret=0}
                PlatformDb db= platform.getDb();
                Log.i("hash:",db.exportData());

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });//授权回调监听，监听oncomplete，onerror，oncancel三种状态

        if(plat.isClientValid()){
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
        }
        if(plat.isAuthValid()){
//判断是否已经存在授权状态，可以根据自己的登录逻辑设置
//            Toast.makeText(this, "已经授权过了", Toast.LENGTH_SHORT).show();
            return;
        }
        //plat.authorize();	//要功能，不要数据
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面

    }

    /**
     * @fun 通过唯一标识到服务器检查是否已经登录过
     * @param db
     */
    private void CheckOnService(final PlatformDb db){

                User user=new User();
                user.setUserId(db.getUserId());
                user.setUserName(db.getUserName());
                user.setUserAvatar(db.getUserIcon());
                user.setUser_phone("无");
                // 默认123456
                user.setUserPassward("123456");
                if("m".equals(db.getUserGender()))
                    user.setUserSex(1);
                else
                    user.setUserSex(0);

          Login(user);

    }

    private void Login(final User user) {

        CreateParam request=new CreateParam(UriConfig.UserServlet,ParamsConfig.RequestAction_The_Third_Login);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                l.OnFail(msg);
            }

            @Override
            public void onSuccess(String json) {
            l.OnSuccess(GsonUtils.jsonToModule(json,User.class));
            }
        });

        request.PostRequest(UriConfig.UserServlet,request.getPostUrl(user));
        // 请求服务器
        Log.i(TAG,"请求服务器");

    }

   private OnThirdLoginListener l;

    public void setOnThirdLoginListener(OnThirdLoginListener l) {
        this.l = l;
    }

   public interface OnThirdLoginListener{
         void OnFail(String mrg);
         void OnSuccess(User user);
    }

}
