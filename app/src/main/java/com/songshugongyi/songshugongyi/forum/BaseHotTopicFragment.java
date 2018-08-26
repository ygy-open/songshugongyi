package com.songshugongyi.songshugongyi.forum;

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
import com.songshugongyi.songshugongyi.bean.HotTopic;
import com.songshugongyi.songshugongyi.forum.detail.HotTopicDetail;
import com.songshugongyi.songshugongyi.util.ShowToast;
import com.yuanopen.commenmodule.ParamsConfig;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yuanopen on 2018/5/4/004.
 */

public class BaseHotTopicFragment extends Fragment {

    private   String TAG="BaseHotTopicFragment";
    RecyclerView recyclerView;
    AdapterHotTopic adapterHotTopic;
    List<HotTopic>hotTopicList;
    RefreshLayout refreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list_hot_topic, container, false);
        TAG=getTAG();
        type=getType();
       init(view);
        return view;
    }


    private void init( View view) {

        recyclerView=view.findViewById(R.id.lv_hot_topic_list);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        hotTopicList=new ArrayList<>();
        adapterHotTopic=new AdapterHotTopic(view.getContext(),hotTopicList,type);

        recyclerView.setAdapter(adapterHotTopic);

        // 请求数据
        getHotTopicsFromService();
        // 设置上拉与下拉 刷新
         refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);

        refreshLayout.setPrimaryColorsId(R.color.colorPrimary);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getHotTopicsFromService();
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                adapterHotTopic.notifyDataSetChanged();
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


        //recyclerView点击事件
        adapterHotTopic.setItemClickListener(new AdapterHotTopic.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), HotTopicDetail.class);
                intent.putExtra("hot_topic",  hotTopicList.get(position));
                intent.putExtra("type",getType());
                startActivity(intent);
            }
        });

    }

    void getHotTopicsFromService(){

        Map<String ,String>param=new HashMap<>();
        param.put("page_size","10");
        if(Config.currentUser!=null)
        param.put("user_id",Config.currentUser.getUserId());
        else
            param.put("user_id","null");

        CreateParam request=new CreateParam(UriConfig.HotTopicServlet, ParamsConfig.RequestAction_GetList_Hot_Topic,param);

        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                Log.d(TAG,"数据请求生成");
                ShowToast.showToast(getContext(),"数据请求生成,请检查网络！");
            }

            @Override
            public void onSuccess(String json) {
                Log.d(TAG,"数据请求成功");
               Type commentListType = new TypeToken<ArrayList<HotTopic>>(){}.getType();

                List<HotTopic>list= GsonUtils.gson.fromJson(json,commentListType);

                hotTopicList.clear();
                hotTopicList.addAll(list);
                adapterHotTopic.notifyDataSetChanged();

                refreshLayout.finishRefresh(0);
            }
        });

      String url=request.getUrl();
        Log.d(TAG,url);
        request.request(url);

    }
    private int type=1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
}
