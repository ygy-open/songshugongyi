package com.yuanopen.chatmudule.group;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.yuanopen.chatmudule.adapter.GroupAdapter;
import com.yuanopen.chatmudule.add.AddBaseAcivity;
import com.yuanopen.chatmudule.bean.UserGroup;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.ShowToast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Search_Group_By_Name;
import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/7/25/025.
 */

public class AddGroupAcivity extends AddBaseAcivity {

    private List<UserGroup>groupList;
    GroupAdapter groupAdapter;


    @Override
    public void initData() {
        groupList=new ArrayList<>();
        groupAdapter=new GroupAdapter(this,groupList);
        listModel.setAdapter(groupAdapter);
        searchTitle.setText("添加群组");

        listModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(AddGroupAcivity.this, ShowGroupInfoAcivity.class);
                intent.putExtra("group_id",groupList.get(i).getGroup_id());
                intent.putExtra("group",groupList.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public void Search() {
        Map<String,String>params=new HashMap<>();
        params.put("name",etSearchContent.getText().toString());
        params.put("user_id",currentUser.getUserId());
        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Search_Group_By_Name,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                ShowToast.showToast(AddGroupAcivity.this,"服务器出错！");
            }
            @Override
            public void onSuccess(String json) {
                Type commentListType = new TypeToken<List<UserGroup>>(){}.getType();
                List<UserGroup>  list= GsonUtils.gson.fromJson(json,commentListType);

                groupList.clear();
                groupList.addAll(list);
                groupAdapter.notifyDataSetChanged();
            }
        });

        request.PostRequest(UriConfig.UserServlet,request.getPostUrl());

    }
}
