package com.songshugongyi.songshugongyi.bean;

import java.io.Serializable;

/**
 * Created by yuanopen on 2018/5/8/008.
 * 对应项目的任务
 */

public class Task implements Serializable {
    //任务id
    private  String task_id;
    //任务名称
    private  String task_name;
    //对应项目id
    private  String progress_id;
    //任务人数
    private  int task_people;
    //已报人数
    private  int task_current_people;

    public int getTask_current_people() {
        return task_current_people;
    }

    public void setTask_current_people(int task_current_people) {
        this.task_current_people = task_current_people;
    }

    //任务详情
    private  String task_introduction;

    //创建时间
    private  String create_time;
    //更新时间
    private  String update_time;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(String progress_id) {
        this.progress_id = progress_id;
    }

    public int getTask_people() {
        return task_people;
    }

    public void setTask_people(int task_people) {
        this.task_people = task_people;
    }

    public String getTask_introduction() {
        return task_introduction;
    }

    public void setTask_introduction(String task_introduction) {
        this.task_introduction = task_introduction;
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
