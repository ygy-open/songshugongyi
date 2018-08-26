package com.songshugongyi.songshugongyi.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yuanopen on 2018/5/8/008.
 * 压缩图片
 */

public class BitmapUtils {
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            //首先获取原图高度和宽度的一半
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            //循环，如果halfHeight和halfWidth同时大于最小宽度和最小高度时，inSampleSize乘2，
            //最后得到的宽或者高都是最接近最小宽度或者最小高度的
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 根据Resources压缩图片
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options);
        return src;
    }

    /**
     * 根据地址压缩图片
     *
     * @param pathName
     * @param reqWidth 最小宽度
     * @param reqHeight 最小高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFd(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 若要对图片进行压缩，必须先设置Option的inJustDecodeBounds为true才能进行Option的修改
        options.inJustDecodeBounds = true;
        // 第一次decodeFile是获取到options.outHeight和options.outWidth
        BitmapFactory.decodeFile(pathName, options);
        // options.inSampleSize是图片的压缩比，例如原来大小是100*100，options.inSampleSize为1，则不变，
        // options.inSampleSize为2，则压缩成50*50
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 重新设置options.inJustDecodeBounds为false，不能修改option
        options.inJustDecodeBounds = false;
        // 根据options重新加载图片
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return src;
    }
}

