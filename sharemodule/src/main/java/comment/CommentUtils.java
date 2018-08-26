package comment;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.http.OnSendListener;

import org.reactivestreams.Subscriber;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import bean.Comment;
import bean.CommentWitnUser;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by yuanopen on 2018/7/15/015.
 */

public class CommentUtils<T> {
    private  String TAG="CommentUtils";
    private Context mContext;
    private static CommentUtils CommentUtilsInstance;

    public CommentUtils(Context mContext) {
        this.mContext=mContext;
    }

    //获取实例接口
    //单利模式
    public static CommentUtils getIntance(Context mContext){
        if(CommentUtilsInstance==null)
            CommentUtilsInstance=new CommentUtils(mContext);

        return CommentUtilsInstance;
    }

   public  void sendComentToService(String host,String action, final Comment comment, final OnSendListener listener){

       CreateParam request=new CreateParam(host,action);
       request.setOnResultListener(new BaseRequest.OnResultListener() {
           @Override
           public void onFail(int code, String msg) {
               Log.d(TAG,"评论数据发送到服务器失败");
               Toast.makeText(mContext,"评论失败，请检查网络！",Toast.LENGTH_SHORT).show();
               listener.onFail(msg);
           }

           @Override
           public void onSuccess(String json) {
               Log.d(TAG,"评论数据发送到服务器成功");
               Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
               listener.onSuccess(comment);
           }
       });

       request.PostRequest(host,request.getPostUrl(comment) );
   }


    public void GetComentFormService(String host,String action, final Subscriber<List<CommentWitnUser>> subscriber, Map<String ,String >params){
        final CreateParam request=new CreateParam(host,action,params);

        final Flowable observable=Flowable.create(new FlowableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final FlowableEmitter emitter) throws Exception {
                //请求网络
                request.setOnResultListener(new BaseRequest.OnResultListener() {
                    @Override
                    public void onFail(int code, String msg) {
                        Log.d(TAG,"获取评论数据失败");
                        subscriber.onError(new Throwable(msg));
                    }

                    @Override
                    public void onSuccess(String json) {

                        Type  commentListType = new TypeToken<List<CommentWitnUser>>(){}.getType();
                        List<CommentWitnUser>  comments= GsonUtils.gson.fromJson(json,commentListType);
//                               emitter.onNext(count);
                         Log.d(TAG,"获取评论数据成功，共有："+comments.size()+"条");
                        subscriber.onNext(comments);
                        subscriber.onComplete();
//                               emitter.onComplete();

                    }
                });

                String url=request.getUrl();
                System.out.println(url);
                request.request(url);

            }
        }, BackpressureStrategy.BUFFER );

        observable.subscribe(subscriber);

    }
}
