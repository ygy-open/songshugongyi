package like;

import android.content.Context;

import com.yuanopen.commenmodule.http.OnSendListener;
import com.yuanopen.commenmodule.http.RequestCommon;

import bean.Like;


/**
 * Created by yuanopen on 2018/7/15/015.
 */

public class LikeUtils {
    private  String TAG="LikeUtils";
    private Context mContext;
    private static LikeUtils LikeUtilsInstance;

    public LikeUtils(Context mContext) {
        this.mContext=mContext;
    }

    //获取实例接口
    //单利模式
    public static LikeUtils getIntance(Context mContext){
        if(LikeUtilsInstance==null)
            LikeUtilsInstance=new LikeUtils(mContext);

        return LikeUtilsInstance;
    }

   public  void sendLikeToService(String host,String action, Like like, OnSendListener<Like> listener){
       RequestCommon.sendDataToService(mContext,host,action,like,"点赞",listener);
//       CreateParam request=new CreateParam(action);
//       request.setOnResultListener(new BaseRequest.OnResultListener() {
//           @Override
//           public void onFail(int code, String msg) {
//               Log.d(TAG,"点赞数据发送到服务器失败");
//               ShowToast.showToast(mContext,"点赞失败");
//           }

//
//           @Override
//           public void onSuccess(String json) {
//               Log.d(TAG,"点赞数据发送到服务器成功");
//               ShowToast.showToast(mContext,"点赞成功");
//           }
//       });
//
//       String url=request.getUrl(like);
//       System.out.println(url);
//       request.request(url);
   }
}
