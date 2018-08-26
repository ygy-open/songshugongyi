package com.yuanopen.sharemodule.utils;

import android.util.Log;

import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;

import org.reactivestreams.Subscriber;

import java.util.HashMap;
import java.util.Map;

import bean.ModelStatus;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by yuanopen on 2018/7/20/020.
 */

public class GetModelStatus {
    private static String TAG="GetModelStatus";
    public static  void getModelStatus(String host,String action, String model_id,String user_id, final Subscriber<ModelStatus> subscriber){

        Map<String ,String > params=new HashMap<>();
        params.put("model_id",model_id);
        params.put("user_id",user_id);

        Log.d(TAG,params.get("user_id"));

        final CreateParam request=new CreateParam(host,action,params);

        final Flowable observable=Flowable.create(new FlowableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final FlowableEmitter emitter) throws Exception {
                //请求网络
                request.setOnResultListener(new BaseRequest.OnResultListener() {
                    @Override
                    public void onFail(int code, String msg) {
                        Log.d(TAG,"获取Counts数据失败");
                        subscriber.onError(new Throwable(msg));
                    }

                    @Override
                    public void onSuccess(String json) {

                        Log.d(TAG,"获取Counts数据成功");
                        ModelStatus status= GsonUtils.jsonToModule(json, ModelStatus.class);
                        subscriber.onNext(status);
                        subscriber.onComplete();

                    }
                });

                String url=request.getUrl();
                 Log.d(TAG,url);
                request.request(url);
            }
        }, BackpressureStrategy.BUFFER );

        observable.subscribe(subscriber);


    }
}
