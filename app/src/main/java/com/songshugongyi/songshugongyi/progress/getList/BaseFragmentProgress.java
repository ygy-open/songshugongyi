package com.songshugongyi.songshugongyi.progress.getList;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.Progress;
import com.songshugongyi.songshugongyi.progress.detail.ProgressDetail;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.songshugongyi.songshugongyi.util.ShowToast.showToast;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Progress;

/**
 * Created by yuanopen on 2018/5/9/009.
 */

public class BaseFragmentProgress extends Fragment {

    protected RecyclerView recyclerView;
    private List<Progress>progressList;
    AdapterProgress adapterProgress;
    private int  Type=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list_progress, container, false);
        init(view);
        return view;
    }

        private void init(View view) {
            SetProgressData();
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);

        refreshLayout.setPrimaryColorsId(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                SetProgressData();
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                adapterProgress.notifyDataSetChanged();
                refreshlayout.finishLoadmore(2000);
            }
        });

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
//设置全局的Footer构建器

//        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//
//                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
//            }
//        });



        recyclerView=view.findViewById(R.id.progress_recyclerview);
        progressList=new ArrayList<>();
            // 设置布局管理器
            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

        adapterProgress=new AdapterProgress(view.getContext(),progressList);
        recyclerView.setAdapter(adapterProgress);

            //recyclerView点击事件
            adapterProgress.setItemClickListener(new AdapterProgress.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent=new Intent(getContext(), ProgressDetail.class);
                    intent.putExtra("progress",progressList.get(position));
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
//                    startActivity(intent);
                }
            });
    }

    public  void SetProgressData(){

        Map<String ,String>param=new HashMap<>();
        param.put("type",getType()+"");

        CreateParam request=new CreateParam(UriConfig.ProgressServlet,RequestAction_GetList_Progress,param);

        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                showToast(getContext()," "+msg);
            }

            @Override
            public void onSuccess(String json) {

                Log.d(TAG,"数据请求成功");
                java.lang.reflect.Type commentListType = new TypeToken<ArrayList<Progress>>(){}.getType();

                List<Progress>list= GsonUtils.gson.fromJson(json,commentListType);

                progressList.clear();
                progressList.addAll(list);
                adapterProgress.notifyDataSetChanged();
            }
        });

        //请求服务器
        String requestUrl = request.getUrl();
        Log.i("BaseFragmentProgress:",requestUrl);
        request.request(requestUrl);

    }

    public void setType(int i){
        Type=i;
    }
    public int getType(){
        return Type;
    }

}
