package com.yuanopen.commenmodule.message;

/**
 * Created by yuanopen on 2018/7/31/031.
 */
public class GroupMessage {

    // 1:申请加入  2:同意加入  3:拒绝加入
    private   int type;
    // 添加者
    private String user_id;
    // 添加者Name
    private String user_name;
    // 目标者
    private String target_id;
    // 添加者Name
    private String target_name;
    // 群组id
    private String group_id;
    // 群组名称
    private String group_name;
    // 添加者图片
    private String user_avatar;
    // 内容
    private String content;
    // 其他


    public GroupMessage() {
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
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

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
