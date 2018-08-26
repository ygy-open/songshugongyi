package com.yuanopen.chatmudule.bean;

import java.io.Serializable;

/**
 * Created by yuanopen on 2018/7/24/024.
 */

public class UserGroup implements Serializable{
    private String user_id;
    private String group_id;
    private String group_name;
    private String group_avatar;
    private String group_signature;

    private boolean join_group;

    public boolean isJoin_group() {
        return join_group;
    }

    public void setJoin_group(boolean join_group) {
        this.join_group = join_group;
    }

    public String getGroup_signature() {
        return group_signature;
    }

    public void setGroup_signature(String group_signature) {
        this.group_signature = group_signature;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_avatar() {
        return group_avatar;
    }

    public void setGroup_avatar(String group_avatar) {
        this.group_avatar = group_avatar;
    }
}
