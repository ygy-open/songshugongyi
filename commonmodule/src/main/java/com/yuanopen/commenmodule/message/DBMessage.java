package com.yuanopen.commenmodule.message;

/**
 * Created by yuanopen on 2018/7/30/030.
 */

public class DBMessage {
//    create table IF NOT EXISTS message (_id integer primary key autoincrement ,
// user_id text,target_id text not null," +
//            "name  not null ,image_url text not null,type integer not null");

    private int id;
    private String user_id;
    private String target_id;
    private String extra;
    private int type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
