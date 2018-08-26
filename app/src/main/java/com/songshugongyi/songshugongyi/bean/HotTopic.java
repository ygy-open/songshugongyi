package com.songshugongyi.songshugongyi.bean;

import com.yuanopen.commenmodule.utils.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class HotTopic implements Serializable {

    //热点id
    private  String hot_topic_id;
    // 热点内容
    private String content;
    //热点图片
    private List<com.yuanopen.commenmodule.bean.Image> images;
    //创建时间
    private  String create_time;
    //更新时间
    private  String update_time;
    // 用户id
    private String user_id;
    // 记录是被被当前用户关注
    private boolean is_attented;

    public boolean getIs_attented() {
        return is_attented;
    }

    public void setIs_attented(boolean is_attented) {
        this.is_attented = is_attented;
    }

    //
    private User user;

    public String getHot_topic_id() {
        return hot_topic_id;
    }

    public void setHot_topic_id(String hot_topic_id) {
        this.hot_topic_id = hot_topic_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<com.yuanopen.commenmodule.bean.Image> getImages() {
        return images;
    }

    public void setImages(List<com.yuanopen.commenmodule.bean.Image> images) {
        this.images = images;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
