package com.yuanopen.commenmodule.utils;

import java.util.UUID;

/**
 * Created by yuanopen on 2018/5/9/009.
 */

public class getUUID {
    public static String getUUID(){
        return UUID.randomUUID().toString().substring(24);
    }
}
