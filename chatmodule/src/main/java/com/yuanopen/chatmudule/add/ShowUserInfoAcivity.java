package com.yuanopen.chatmudule.add;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yuanopen.chatmudule.R;
import com.yuanopen.chatmudule.bean.Friend;
import com.yuanopen.chatmudule.databinding.ActivityShowUserinfoBinding;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.message.FriendMessage;
import com.yuanopen.commenmodule.utils.User;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;

import static com.yuanopen.chatmudule.adapter.ShowAvatar.showIavatar;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_FriendInfo_By_Id;
import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static com.yuanopen.commenmodule.utils.SendMessage.sendMessage;

/**
 * Created by yuanopen on 2018/7/25/025.
 */

public class ShowUserInfoAcivity extends AppCompatActivity {
    private String TAG="ShowUserInfoAcivity";
    ActivityShowUserinfoBinding showUserinfoBinding;
    String userId;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showUserinfoBinding= DataBindingUtil.setContentView(this, R.layout.activity_show_userinfo);
        userId=getIntent().getStringExtra("user_id");
        if(userId!=null)
        init();
    }

    private void init() {

        Map<String,String> params=new HashMap<>();
        params.put("friend_user_id",userId);
        params.put("user_id", currentUser.getUserId());

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Get_FriendInfo_By_Id,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {

            }

            @Override
            public void onSuccess(String json) {
                Friend friend= GsonUtils.jsonToModule(json,Friend.class);
                 user=friend.getUser();
                showInfo(friend.isFriend());
            }
        });

        String url=request.getUrl();
        Log.d(TAG,url);
        request.request(url);
    }

   public void showInfo( boolean i){

       showIavatar(user.getUserAvatar(),showUserinfoBinding.userImage);
       showUserinfoBinding.userName.set(R.drawable.ic_user_icon,"昵称",user.getUserName());
       if("".equals(user.getUser_signature()))
           showUserinfoBinding.userSignature.set(R.drawable.ic_user_icon,"签名","无");
       else
           showUserinfoBinding.userSignature.set(R.drawable.ic_user_icon,"签名",user.getUser_signature());

       showUserinfoBinding.userAge.set(R.drawable.ic_user_icon,"年龄",user.getUserAge()+"");
       showUserinfoBinding.userSex.set(R.drawable.ic_user_icon,"性别",(user.getUserSex()==1)?"男":"女");

       if(!i){
           showUserinfoBinding.btnAddFriend.setVisibility(View.VISIBLE);
       }else{
           showUserinfoBinding.btnSendMessage.setVisibility(View.VISIBLE);
       }

    }

 public  void   toTalk(View view){
     RongIM.getInstance().startPrivateChat(ShowUserInfoAcivity.this,user.getUserId(), user.getUserName());
    }

    public void AddFriend(View v){
        showUserinfoBinding.btnAddFriend.setClickable(false);
        FriendMessage message=new FriendMessage();
        message.setContent("添加");
        message.setType(1);
        message.setUser_id(currentUser.getUserId());
        message.setUser_name(currentUser.getUserName());
        message.setTarget_id(user.getUserId());
        message.setTarget_name(user.getUserName());
        message.setUser_avatar(user.getUserAvatar());
        sendMessage(this,message);
    }
    public void Back(View view)
    {
        finish();
    }
}
