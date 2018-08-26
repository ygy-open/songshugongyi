package com.yuanopen.chatmudule.add;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.yuanopen.chatmudule.adapter.FriendAdapter;
import com.yuanopen.chatmudule.bean.UserFriend;
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

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Search_User_By_User_Nmae;
import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/7/25/025.
 */

public class AddFriendAcivity extends AddBaseAcivity{

    String TAG="AddFriendAcivity";
    private List<UserFriend> friendList;
    FriendAdapter friendAdapter;


    @Override
    public void initData() {
        friendList=new ArrayList<>();
        friendAdapter=new FriendAdapter(this,friendList);
        listModel.setAdapter(friendAdapter);
        searchTitle.setText("添加好友");

        listModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(AddFriendAcivity.this, ShowUserInfoAcivity.class);
                intent.putExtra("user_id",friendList.get(i).getFriend_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public void Search() {
        Map<String,String> params=new HashMap<>();
        params.put("name",etSearchContent.getText().toString());
        params.put("user_id",currentUser.getUserId());
        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Search_User_By_User_Nmae,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                ShowToast.showToast(AddFriendAcivity.this,"服务器出错！");
            }
            @Override
            public void onSuccess(String json) {
                Type commentListType = new TypeToken<List<UserFriend>>(){}.getType();
                List<UserFriend>  list= GsonUtils.gson.fromJson(json,commentListType);

                friendList.clear();
                friendList.addAll(list);
                friendAdapter.notifyDataSetChanged();
            }
        });

        request.PostRequest(UriConfig.UserServlet,request.getPostUrl());

    }
}
