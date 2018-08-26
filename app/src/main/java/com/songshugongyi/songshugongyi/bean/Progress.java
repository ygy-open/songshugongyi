package com.songshugongyi.songshugongyi.bean;

import com.yuanopen.commenmodule.utils.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanopen on 2018/5/8/008.
 */

public class Progress implements Serializable{
    //项目id
    private  String progress_id;
    //项目名称
    private  String progress_name;
    //项目详情
    private  String progress_introduction;
    //项目当前报名人数
    private  int progress_current_people;
    //项目开始时间
    private  String progress_start_time;
    //项目结束时间
    private  String progress_end_time;
    //项目任务
    private List<Task>tasks;
    //项目任务
    private List<ProgressImage>images;
    //创建时间
    private  String create_time;
    //更新时间
    private  String update_time;
    //类型1:正在报名  2：完成项目

    private int progress_type;
    private User progress_user;
    private String progress_user_id;

    public String getProgress_user_id() {
        return progress_user_id;
    }

    public void setProgress_user_id(String progress_user_id) {
        this.progress_user_id = progress_user_id;
    }
    public User getProgress_user() {
        return progress_user;
    }

    public void setProgress_user(User progress_user) {
        this.progress_user = progress_user;
    }

    public int getProgress_type() {
        return progress_type;
    }

    public void setProgress_type(int progress_type) {
        this.progress_type = progress_type;
    }

    public String getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(String progress_id) {
        this.progress_id = progress_id;
    }

    public String getProgress_name() {
        return progress_name;
    }

    public void setProgress_name(String progress_name) {
        this.progress_name = progress_name;
    }

    public String getProgress_introduction() {
        return progress_introduction;
    }

    public void setProgress_introduction(String progress_introduction) {
        this.progress_introduction = progress_introduction;
    }

    public int getProgress_current_people() {
        return progress_current_people;
    }

    public void setProgress_current_people(int progress_current_people) {
        this.progress_current_people = progress_current_people;
    }

    public String getProgress_start_time() {
        return progress_start_time;
    }

    public void setProgress_start_time(String progress_start_time) {
        this.progress_start_time = progress_start_time;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<ProgressImage> getImages() {
        return images;
    }

    public void setImages(List<ProgressImage> images) {
        this.images = images;
    }

    public String getProgress_end_time() {
        return progress_end_time;
    }

    public void setProgress_end_time(String progress_end_time) {
        this.progress_end_time = progress_end_time;
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
