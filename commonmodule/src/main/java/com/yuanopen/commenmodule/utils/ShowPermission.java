package com.yuanopen.commenmodule.utils;

import android.content.Context;
import android.util.Log;

import com.yuanopen.commenmodule.R;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

import static com.yuanopen.commenmodule.utils.ShowToast.showToast;

/**
 * Created by yuanopen on 2018/8/1/001.
 */

public class ShowPermission {

    String TAG="ShowPermission";
    Context context;
  static   ShowPermission instance;

    public ShowPermission(Context context) {
        this.context = context;
    }

    public static  ShowPermission getInstance(Context context)
    {
        if(instance==null)
            instance=new ShowPermission(context);

        return  instance;
    }

    public void show() {
        HiPermission.create(context)
                .title("Hi 亲爱的小松鼠")
                .style(R.style.PermissionBlueStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.i(TAG, "onClose");
                        showToast(context,"用户关闭权限申请");
                    }

                    @Override
                    public void onFinish() {
//                        showToast("所有权限申请完成");
                    }

                    @Override
                    public void onDeny(String permisson, int position) {
                        Log.i(TAG, "onDeny");
                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
                        Log.i(TAG, "onGuarantee");
                    }
                });


    }
}
