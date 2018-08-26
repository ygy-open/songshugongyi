package com.yuanopen.chatmudule.chat.chatrequest.model;

import java.io.Serializable;

/**
 * Created by yuanopen on 2018/6/29/029.
 */

public class Group implements Serializable{

    private String userId;
    private String groupId;
    private String groupName;

    public Group(String userId, String groupId, String groupName) {
        this.userId = userId;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
