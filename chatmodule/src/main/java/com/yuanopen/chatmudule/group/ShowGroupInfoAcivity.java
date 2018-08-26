package com.yuanopen.chatmudule.group;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yuanopen.chatmudule.R;
import com.yuanopen.chatmudule.bean.UserGroup;
import com.yuanopen.chatmudule.databinding.ActivityShowGroupBinding;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.message.GroupMessage;
import com.yuanopen.commenmodule.utils.ShowToast;

import java.util.HashMap;
import java.util.Map;

import static com.yuanopen.chatmudule.adapter.ShowAvatar.showIavatar;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_GroupInfo_By_Id;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Quit_Group;
import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static com.yuanopen.commenmodule.utils.SendMessage.sendAddGroupMessage;

/**
 * Created by yuanopen on 2018/7/25/025.
 */

public class ShowGroupInfoAcivity extends AppCompatActivity {
    private String TAG="ShowGroupInfoAcivity";

    ActivityShowGroupBinding showGroupBinding;
    String groupId;
    UserGroup group;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showGroupBinding= DataBindingUtil.setContentView(this, R.layout.activity_show_group);
        groupId=getIntent().getStringExtra("group_id");
        group= (UserGroup) getIntent().getSerializableExtra("group");
        if(group!=null)
            showInfo(group);
        if(groupId!=null)
         init();
    }

    private void init() {

        Map<String,String> params=new HashMap<>();
        params.put("group_id", groupId);
        params.put("user_id", currentUser.getUserId());

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Get_GroupInfo_By_Id,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {

            }

            @Override
            public void onSuccess(String json) {
                group = GsonUtils.jsonToModule(json,UserGroup.class);
                showInfo(group);
            }
        });

        String url=request.getUrl();
        Log.d(TAG,url);
        request.request(url);
    }

   public void showInfo(UserGroup group){
       showIavatar(group.getGroup_avatar(),showGroupBinding.groupImage);
       showGroupBinding.tvGroupName.setText(group.getGroup_name());
       showGroupBinding.groupSignature.setText(group.getGroup_signature());
        if(!group.isJoin_group()){
            showGroupBinding.btnJoinGroup.setVisibility(View.VISIBLE);
        }else
       showGroupBinding.btnQuitGroup.setVisibility(View.VISIBLE);

    }

    public void Back(View view)
    {
        finish();
    }

   public void  addGroup(View v){
       showGroupBinding.btnJoinGroup.setClickable(false);
       sendAgreeGroupMessage(1);
    }
    public void quitGroup(View view)
    {
        Map<String,String>params=new HashMap<>();
        params.put("group_id",group.getGroup_id());
        params.put("user_id",currentUser.getUserId());
        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Quit_Group,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                ShowToast.showToast(ShowGroupInfoAcivity.this,"请求失败！");
                showGroupBinding.btnJoinGroup.setClickable(true);
            }

            @Override
            public void onSuccess(String json) {
                ShowToast.showToast(ShowGroupInfoAcivity.this,"请求成功！");
                showGroupBinding.btnJoinGroup.setVisibility(View.INVISIBLE);
            }
        });
        request.request(request.getUrl());
    }

    private void   sendAgreeGroupMessage(int type){
        GroupMessage message=new GroupMessage();
        message.setContent("申请");
        message.setType(type);
        message.setUser_id(currentUser.getUserId());
        message.setUser_name(currentUser.getUserName());
        message.setTarget_id(group.getUser_id());
        message.setGroup_name(group.getGroup_name());
        message.setGroup_id(group.getGroup_id());
        message.setUser_avatar(currentUser.getUserAvatar());

        sendAddGroupMessage(this,message);
    }
}
