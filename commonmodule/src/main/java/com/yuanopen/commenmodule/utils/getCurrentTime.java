package com.yuanopen.commenmodule.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuanopen on 2018/5/7/007.
 */
public class getCurrentTime {
    public static String getTime(){
        Date date=new Date();
        SimpleDateFormat dfs= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dfs.format(date);
    }
}
