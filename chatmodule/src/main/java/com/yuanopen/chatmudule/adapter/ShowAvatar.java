package com.yuanopen.chatmudule.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yuanopen.chatmudule.R;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;

/**
 * Created by yuanopen on 2018/7/24/024.
 */

public class ShowAvatar {
    public static void showIavatar(String uri, ImageView iavatar){

        if (uri!=null){
            DisplayImageOptions options = new DisplayImageOptions.Builder()//
                    .showImageOnLoading(R.mipmap.ic_launcher) // 加载中显示的默认图片
                    .showImageOnFail(R.mipmap.ic_launcher) // 设置加载失败的默认图片
                    .cacheInMemory(true) // 内存缓存
                    .cacheOnDisk(true) // sdcard缓存
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
                    .build();//
            ImageLoader.getInstance().displayImage(uri,
                    iavatar , options);
        }else
            iavatar.setImageResource(R.mipmap.ic_launcher);

    }
}
