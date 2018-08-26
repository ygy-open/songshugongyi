package com.yuanopen.chatmudule.bean;

/**
 * Created by yuanopen on 2018/7/24/024.
 */
public class UserFriend {
    private String user_id;
    private String friend_id;
    private String friend_name;
    private String friend_avatar;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_avatar() {
        return friend_avatar;
    }

    public void setFriend_avatar(String friend_avatar) {
        this.friend_avatar = friend_avatar;
    }
}
