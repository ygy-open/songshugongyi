package collect;

import android.content.Context;

import com.yuanopen.commenmodule.http.OnSendListener;
import com.yuanopen.commenmodule.http.RequestCommon;


/**
 * Created by yuanopen on 2018/7/15/015.
 */

public class CollectUtils {
    private  String TAG="CollectUtils";
    private Context mContext;
    private static CollectUtils CollectUtilsInstance;

    public CollectUtils(Context mContext) {
        this.mContext=mContext;
    }

    //获取实例接口
    //单利模式
    public static CollectUtils getIntance(Context mContext){
        if(CollectUtilsInstance==null)
            CollectUtilsInstance=new CollectUtils(mContext);

        return CollectUtilsInstance;
    }

   public  <T> void sendCollectToService(String host,String action,T model,OnSendListener< T> listener){

       RequestCommon.sendDataToService(mContext,host,action,model ,"收藏",  listener);

//       CreateParam request=new CreateParam(action);
//       request.setOnResultListener(new BaseRequest.OnResultListener() {
//           @Override
//           public void onFail(int code, String msg) {
//               Log.d(TAG,"收藏数据发送到服务器失败");
//               ShowToast.showToast(mContext,"收藏失败");
//           }
//
//           @Override
//           public void onSuccess(String json) {
//               Log.d(TAG,"收藏数据发送到服务器失败");
//               ShowToast.showToast(mContext,"收藏成功");
//           }
//       });
//
//       String url=request.getUrl(model);
//
//       System.out.println(url);
//       request.request(url);
   }
}
