package com.yuanopen.chatmudule;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.yuanopen.chatmudule.adapter.FriendAdapter;
import com.yuanopen.chatmudule.adapter.GroupAdapter;
import com.yuanopen.chatmudule.add.AddFriendAcivity;
import com.yuanopen.chatmudule.bean.UserFriend;
import com.yuanopen.chatmudule.bean.UserGroup;
import com.yuanopen.chatmudule.databinding.ActivityAddressBookBinding;
import com.yuanopen.chatmudule.group.AddGroupAcivity;
import com.yuanopen.chatmudule.group.CreateGroupActivity;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_User_Friend;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_User_Group;

/**
 * Created by yuanopen on 2018/7/24/024.
 */

public class AdressBookActivity extends AppCompatActivity{

ActivityAddressBookBinding addressBookBinding;
    List<UserGroup>groupList;
    List<UserFriend>friendList;
    GroupAdapter groupAdapter;
    FriendAdapter friendAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressBookBinding= DataBindingUtil.setContentView(this,R.layout.activity_address_book);
        init();
    }


   private void  init(){
       groupList=new ArrayList<>();
       friendList=new ArrayList<>();

       groupAdapter=new GroupAdapter(this,groupList);
       friendAdapter=new FriendAdapter(this,friendList);

       addressBookBinding.groupListView.setAdapter(groupAdapter);
       addressBookBinding.friendListView.setAdapter(friendAdapter);

       addressBookBinding.groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               RongIM.getInstance().startGroupChat(AdressBookActivity.this, groupList.get(i).getGroup_id(),  groupList.get(i).getGroup_name());
           }
       });

       addressBookBinding.friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               RongIM.getInstance().startPrivateChat(AdressBookActivity.this, friendList.get(i).getFriend_id(),  friendList.get(i).getFriend_name());
           }
       });

       getDataFromService();
    }

    private void getDataFromService() {
        // 获取群组列表
        Map<String,String>param=new HashMap<>();
        param.put("user_id", ChatConfig.USER_ID);

        CreateParam requestGroup=new CreateParam(UriConfig.UserServlet,RequestAction_Get_User_Group,param);
        requestGroup.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
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

        // 获取好友列表
        requestGroup.request(requestGroup.getUrl());


        CreateParam requestFriend=new CreateParam(UriConfig.UserServlet,RequestAction_Get_User_Friend,param);
        requestFriend.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {

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
        // 获取好友列表
        requestFriend.request(requestFriend.getUrl());
    }

    public void Back(View v)
    {
        finish();
    }

    public  void Search(View v)
    {
    }


    AlertDialog dialog;
    private void showChooseDialoog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(AdressBookActivity.this);
        View view=View.inflate(this,R.layout.dialog_add_choose,null);
        builder.setView(view);

        dialog=builder.create();
        Button btnAddFriend,btnAddGroup,btnCreateGroup,btnCancle;

        btnAddFriend=view.findViewById(R.id.btn_add_friend);
        btnAddGroup=view.findViewById(R.id.btn_add_group);
        btnCreateGroup=view.findViewById(R.id.btn_create_group);
        btnCancle=view.findViewById(R.id.btn_cancle);


        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdressBookActivity.this, AddFriendAcivity.class);
                startActivity(intent);
            }
        });
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdressBookActivity.this, AddGroupAcivity.class);
                startActivity(intent);
            }
        });
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdressBookActivity.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public  void addFriend(View v){
        showChooseDialoog();
    }
}
