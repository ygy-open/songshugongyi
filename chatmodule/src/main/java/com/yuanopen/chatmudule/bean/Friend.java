package com.yuanopen.chatmudule.bean;

import com.yuanopen.commenmodule.utils.User;

/**
 * Created by yuanopen on 2018/7/26/026.
 */
public class Friend {
    private User user;
    private boolean isFriend;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
