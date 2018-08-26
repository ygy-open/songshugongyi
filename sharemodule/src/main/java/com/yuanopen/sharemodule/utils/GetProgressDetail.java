package com.yuanopen.sharemodule.utils;

import android.util.Log;

import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;

import org.reactivestreams.Subscriber;

import java.util.HashMap;
import java.util.Map;

import bean.ModelDetailCount;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by yuanopen on 2018/7/15/015.
 */

public class GetProgressDetail {

    private static String TAG="GetProgressDetail";

    public static  void getDetailCounts(String host,String action, String progressId, final Subscriber<ModelDetailCount> subscriber){


        Map<String ,String >params=new HashMap<>();
        params.put("model_id",progressId);

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
                                ModelDetailCount count= GsonUtils.jsonToModule(json, ModelDetailCount.class);
//                               emitter.onNext(count);
                                subscriber.onNext(count);
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
