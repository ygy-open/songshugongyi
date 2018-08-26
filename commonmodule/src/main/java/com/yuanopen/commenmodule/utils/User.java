package com.yuanopen.commenmodule.utils;

import java.io.Serializable;

/**
 * Created by yuanopen on 2018/5/4/004.
 */

public class User implements Serializable {
    private String UserId;
    //用户id
    private String UserName;
    //用户名
    private String user_phone;
    private String UserPassward;
    //用户密码
    private String UserToken;
    //融云的taken
    private int UserAge;
    //用户年龄
    private int UserSex;
    //用户性别
    private String UserAvatar;
    //用户头像地址
    private int UserIntegral;
    //用户签名
    private String user_signature;
    //用户积分
    private int user_attention;
    //关注
    private int user_be_attention;
    //被关注
    private String CreateTime;
    //注册时间
    private String UpdateTime;
    //信息更新时间


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUserPassward() {
        return UserPassward;
    }

    public void setUserPassward(String userPassward) {
        UserPassward = userPassward;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public int getUserAge() {
        return UserAge;
    }

    public void setUserAge(int userAge) {
        UserAge = userAge;
    }

    public int getUserSex() {
        return UserSex;
    }

    public void setUserSex(int userSex) {
        UserSex = userSex;
    }

    public String getUserAvatar() {
        return UserAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        UserAvatar = userAvatar;
    }
    public String getUser_signature() {

        return user_signature;
    }

    public void setUser_signature(String user_signature) {
        this.user_signature = user_signature;
    }
        public int getUserIntegral() {
        return UserIntegral;
    }

    public void setUserIntegral(int userIntegral) {
        UserIntegral = userIntegral;
    }

    public int getUser_attention() {
        return user_attention;
    }

    public void setUser_attention(int user_attention) {
        this.user_attention = user_attention;
    }

    public int getUser_be_attention() {
        return user_be_attention;
    }

    public void setUser_be_attention(int user_be_attention) {
        this.user_be_attention = user_be_attention;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
