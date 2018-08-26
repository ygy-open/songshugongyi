package share;

import android.content.Context;
import android.util.Log;

import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.OnSendListener;
import com.yuanopen.commenmodule.utils.ShowToast;
import com.yuanopen.sharemodule.R;

import java.util.HashMap;

import bean.Share;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.mob.tools.utils.Strings.getString;

/**
 * Created by yuanopen on 2018/6/24/024.
 * 实现分享功能
 */

public class ShareUtils {
  private  String TAG="ShareUtils";
    private Context mContext;
    private static ShareUtils ShareModuleInstance;

    public ShareUtils(Context mContext) {
        this.mContext=mContext;
    }

    //获取实例接口
    //单利模式
    public static ShareUtils getIntance(Context mContext){
        if(ShareModuleInstance==null)
            ShareModuleInstance=new ShareUtils(mContext);

        return ShareModuleInstance;
    }

    /**
     * 分享方法
     */
    public <T> void showShare(final String host,final String action , final Share share , final OnSendListener listener) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d(TAG,"分享成功");
                share.setShare_platform(platform.getName());
                sendTosService(host,action,share);
                listener.onSuccess(share);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d(TAG,"失败");
                listener.onFail(i + "");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.d(TAG,"分享成取消");
            }
        });
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(mContext);

    }

    private void sendTosService(String host,String action,Share share) {


        CreateParam request=new CreateParam(host,action);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                Log.d(TAG,"分享数据发送到服务器失败");
                ShowToast.showToast(mContext,"分享失败");
            }

            @Override
            public void onSuccess(String json) {
                Log.d(TAG,"分享数据发送到服务器失败");
                ShowToast.showToast(mContext,"分享成功");
            }
        });

        String url=request.getUrl(share);
        System.out.println(url);
        request.request(url);

    }


}
