package com.songshugongyi.songshugongyi.bean;

/**
 * Created by yuanopen on 2018/7/19/019.
 */
public class Attention {
    // 被关注者id
    private String attented_user_id;
    // 关注者id
    private String user_id;
    // 创建时间
    private  String create_time;
    // 更新时间
    private  String update_time;

    public String getAttented_user_id() {
        return attented_user_id;
    }

    public void setAttented_user_id(String attented_user_id) {
        this.attented_user_id = attented_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
