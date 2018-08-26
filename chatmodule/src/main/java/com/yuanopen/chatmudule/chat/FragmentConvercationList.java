package com.yuanopen.chatmudule.chat;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanopen.chatmudule.R;
import com.yuanopen.chatmudule.bean.Friend;
import com.yuanopen.chatmudule.bean.UserGroup;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.User;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_FriendInfo_By_Id;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_GroupInfo_By_Id;


/**
 * Created by yuanopen on 2018/5/4/004.
 * @fun 对应圈子的聊天聊天信息列表
 */

public class FragmentConvercationList extends Fragment {
    private String TAG="FragmentList";
      ConversationListFragment fragment;
    static   FragmentConvercationList instance;

    public static FragmentConvercationList getInstance(){
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
       View view = inflater.inflate(R.layout.fragment_conserctionlist, container, false);

         fragment=new ConversationListFragment();
        instance=this;
        Uri uri = Uri.parse("rong://" + view.getContext().getPackageName()).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.PUSH_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(),"false") //
                .build();

        fragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();

        initInfo();

        return view;
    }

    public ConversationListFragment getFragment() {
        return fragment;
    }

    private void initInfo( ) {

        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                return findUserById(userId);
                //根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);

        /**
         * 刷新群组缓存数据。
         *
         * @param group 需要更新的群组缓存数据。
         */

        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                return findGroupById(s);
            }
        },true );


    }

    private UserInfo findUserById(final String userId) {

        Map<String,String> params=new HashMap<>();
        params.put("user_id", "null");
        params.put("friend_user_id",userId);

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Get_FriendInfo_By_Id,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {

            }

            @Override
            public void onSuccess(String json) {
                Friend friend= GsonUtils.jsonToModule(json,Friend.class);
                User user=friend.getUser();
                if(user!=null)
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(user.getUserId(),user.getUserName(),Uri.parse(user.getUserAvatar())));
            }
        });

        String url=request.getUrl();
        Log.d(TAG,url);
        request.request(url);

        return null;
    }

    private Group findGroupById(final String group_id) {
        Map<String,String> params=new HashMap<>();
        params.put("group_id", group_id);
        params.put("user_id", "null");

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Get_GroupInfo_By_Id,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {

            }

            @Override
            public void onSuccess(String json) {
                UserGroup group= GsonUtils.jsonToModule(json,UserGroup.class);
                RongIM.getInstance().refreshGroupInfoCache(new Group(group.getGroup_id(),group.getGroup_name(),Uri.parse(group.getGroup_avatar())));         }
        });

     String url=request.getUrl();

        Log.d(TAG,url);
        request.request(url);

        return null;
    }


}
