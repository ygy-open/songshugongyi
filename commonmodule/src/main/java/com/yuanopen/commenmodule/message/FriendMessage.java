package com.yuanopen.commenmodule.message;

/**
 * Created by yuanopen on 2018/7/26/026.
 */
public class FriendMessage {
    // 1:申请添加  2:同意添加  3:拒绝添加
    private   int type;
    // 添加者
    private String user_id;
    // 添加者Name
    private String user_name;
    // 添加者头像
    private String user_avatar;
    // 目标者
    private String target_id;
    // 添加者Name
    private String target_name;
    // 内容
    private String content;

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
