package com.yuanopen.commenmodule.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yuanopen.commenmodule.message.DBMessage;

import java.util.ArrayList;
import java.util.List;

import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/5/26/026.
 */

public class DbManager {

    private String TAG="DbManager";
   static  DbManager instance;
    public  static  DbManager getInstance(Context context)
    {
        if(instance==null)
        instance=new DbManager(context);

        return  instance;
    }
    SQLiteDatabase db;
    Context context;

    public DbManager(Context context) {
        this.context = context;
    }

    private   void createDb(){
        db=context.openOrCreateDatabase("data", Context.MODE_PRIVATE,null);
        db.execSQL("create table IF NOT EXISTS message (_id integer primary key autoincrement ,user_id text,target_id text not null," +
                "extra text not null,type integer not null)");
    }


    public List<DBMessage> getData(){
        List<DBMessage>list=new ArrayList<>();
        createDb();
        Cursor c=db.rawQuery("select * from  message where user_id='"+currentUser.getUserId()+"'" ,null);

        if(c.getCount()>=1){
            while (c.moveToNext()){
                DBMessage message =new DBMessage();
                message.setId(c.getInt(c.getColumnIndex("_id")));
                message.setUser_id(c.getString(c.getColumnIndex("user_id")));
                message.setTarget_id(c.getString(c.getColumnIndex("target_id")));
                message.setType(c.getInt(c.getColumnIndex("type")));
                message.setExtra(c.getString(c.getColumnIndex("extra")));
                Log.d(TAG,message.getExtra());
                list.add(message);
            }
        }
        return list;
    }

    public void insertToDb(String user_id,String target_id,String extra,int type){
        createDb();

//实例化常量值
        ContentValues cValue = new ContentValues();

        cValue.put("user_id", user_id);
        cValue.put("target_id",target_id);
        cValue.put("type",type);
        cValue.put("extra",extra);
//调用insert()方法插入数据
        db.insert("message",null,cValue);

    }

    public void delete(String target_id) {
        createDb();
        db.execSQL("delete from message where target_id='"+target_id+"'" );

    }

    public void deleteAll() {
        createDb();
        db.execSQL("delete from message" );
    }
}
