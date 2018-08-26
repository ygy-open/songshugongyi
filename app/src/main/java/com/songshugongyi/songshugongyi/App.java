package com.songshugongyi.songshugongyi;

import android.app.Application;
import android.os.Environment;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yuanopen.commenmodule.utils.qiniu.QnUploadHelper;

import java.io.File;

import io.rong.imkit.RongIM;

/**
 * Created by yuanopen on 2018/5/7/007.
 */

public class App extends Application {

    private static String appKey = "25d079279ae0a";
    private static String appSecret = "75923781249bdd4bfd45063a6f78d9fa";
    private static String AccessKey = "ZvLk_j0IepoBbwFwVojT-YEm-QtJXSSiOSXs0HOw";
    private static String SecretKey = "JLEAdKwaEu-18XymOnOEPyyDec2chddIElTLjceD";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化短信验证
//        MobSDK.init(this, appKey, appSecret);
         MobSDK.init(this);
        //初始化七牛云
        QnUploadHelper.init(AccessKey, SecretKey);
        //初始化图片下载器
        intiImageLoader();
        //初始化融云
        RongIM.init(this);

    }

    // 图片选择配置
    private void intiImageLoader() {
        File  cacheDir = new File(Environment.getExternalStorageDirectory()+ "/songshugongyi/image/");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.progress_default) //
                .showImageOnFail(R.drawable.progress_default) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//

        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
//                .discCacheSize(50 * 1024 * 1024)//
//                .discCacheFileCount(100)// 缓存一百张图片
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .writeDebugLogs()//
                .build();//

        ImageLoader.getInstance().init(config);
    }

   }
