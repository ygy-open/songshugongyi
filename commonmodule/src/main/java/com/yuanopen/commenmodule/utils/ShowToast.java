package com.yuanopen.commenmodule.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yuanopen on 2018/5/7/007.
 */

public class ShowToast {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }


}
