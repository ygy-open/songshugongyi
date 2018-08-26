package com.songshugongyi.songshugongyi.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.util.ImageUtils;
import com.yuanopen.chatmudule.bean.UserGroup;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.message.DBMessage;
import com.yuanopen.commenmodule.message.FriendMessage;
import com.yuanopen.commenmodule.message.GroupMessage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yuanopen on 2018/7/30/030.
 */

public class AdapterVerifyMessage extends BaseAdapter {

    Context context;
    List<DBMessage> messageList;

    public AdapterVerifyMessage(Context context, List<DBMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View view1=View.inflate(context, R.layout.item_add_message,null);
        CircleImageView  user_avatar=view1.findViewById(R.id.avatar_friend);
        TextView content=view1.findViewById(R.id.content);


        final DBMessage dbMessage=messageList.get(i);

        if(dbMessage.getType()==1){
            FriendMessage friendMessage=GsonUtils.jsonToModule(dbMessage.getExtra(),FriendMessage.class);
            ImageUtils.showIavatar(friendMessage.getUser_avatar(),user_avatar);
            content.setText(friendMessage.getUser_name()+" 申请添加你为好友");
        }
        else {
            GroupMessage groupMessage=GsonUtils.jsonToModule(dbMessage.getExtra(),GroupMessage.class);
            ImageUtils.showIavatar(groupMessage.getUser_avatar(),user_avatar);
            content.setText(groupMessage.getUser_name()+" 申请加入群组("+groupMessage.getGroup_name()+")");
        }

        return view1;
    }

    public static UserGroup getGroup(String json){

        return GsonUtils.jsonToModule(json,UserGroup.class);
    }


}
