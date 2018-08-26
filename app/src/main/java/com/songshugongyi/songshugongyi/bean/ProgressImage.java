package com.songshugongyi.songshugongyi.bean;

import java.io.Serializable;

/**
 * Created by yuanopen on 2018/5/8/008.
 * 项目对应image
 */

public class ProgressImage implements Serializable {
    //id
    private  String image_id;
    //对应项目id
    private  String progress_id;
    //图片地址
    private  String image_url;
    //创建时间
    private  String create_time;
    //更新时间
    private  String update_time;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(String progress_id) {
        this.progress_id = progress_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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
