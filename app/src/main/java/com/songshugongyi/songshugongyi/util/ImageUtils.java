package com.songshugongyi.songshugongyi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.ProgressImage;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.songshugongyi.songshugongyi.util.ShowToast.showToast;

/**
 * Created by yuanopen on 2018/5/9/009.
 */

public class ImageUtils {
    private String url;
    Context context;
    List<ProgressImage> images;
    List<String>image_path;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public ImageUtils(Context context, List<ProgressImage> images, List<String>image_path) {
        this.context = context;
        this.images = images;
        this.image_path=image_path;
    }
  public  void upload(){
      for (int i = 0; i < images.size(); i++) {
          Bitmap bitmap = BitmapFactory.decodeFile(image_path.get(i));
          upload(BitmapAndStringUtils.BitmapToString(bitmap),images.get(i).getImage_url(),i+1);
      }


 }
    public void upload(String photo_base64,String name,final int id) {
        // 将bitmap转为string，并使用BASE64加密
//        String photo = BitmapAndStringUtils.BitmapToString(bitmap);
//        // 获取到图片的名字
//        String name = photoPath.substring(photoPath.lastIndexOf("/")).substring(1);
        // new一个请求参数
        RequestParams params = new RequestParams();
        // 将图片和名字添加到参数中
        params.put("photo", photo_base64);
        params.put("name", name);
        AsyncHttpClient client = new AsyncHttpClient();
        // 调用AsyncHttpClient的post方法
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
             showToast(context,"第"+id+"图片上传成功");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast(context,"第"+id+"图片上传失败");
            }
        });
    }

    public Bitmap getBitmap( String btmStr){
                  return  BitmapAndStringUtils.String2Bitmap(btmStr);
    }
//    public  void downloadFile(String uri){
//        AsyncHttpClient client = new AsyncHttpClient();
//        // 指定文件类型
//        String[] fileTypes = new String[] { "image/png", "image/jpeg" };
//        // 获取二进制数据如图片和其他文件
//        client.get(uri, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                try {
//                    Log.i("aaa","json"+response);
//
//                    JSONObject data=response.getJSONObject("data");
//                    String btmStr=data.getString("userImageContent");
//
//                    Bitmap bitmap=Utils.toRoundBitmap(Utils.String2Bitmap(btmStr));
//
//                    imageButton.setImageBitmap(bitmap);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onProgress(long bytesWritten, long totalSize) {
//                super.onProgress(bytesWritten,totalSize);
//            }
//        });
//    }
public static  void showIavatar(String uri, ImageView iavatar){

    if (uri!=null){
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .showImageOnLoading(R.drawable.progress_default) // 加载中显示的默认图片
                .showImageOnFail(R.drawable.progress_default) // 设置加载失败的默认图片
                .cacheInMemory(true) // 内存缓存
                .cacheOnDisk(true) // sdcard缓存
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
                .build();//
        ImageLoader.getInstance().displayImage(uri,
                iavatar , options);
    }else
        iavatar.setImageResource(R.drawable.progress_default);

}
    public void showProgressImage(String uri, ImageView iavatar){

        if (uri!=null){
            DisplayImageOptions options = new DisplayImageOptions.Builder()//
                    .showImageOnLoading(R.drawable.progress_default) // 加载中显示的默认图片
                    .showImageOnFail(R.drawable.progress_default) // 设置加载失败的默认图片
                    .cacheInMemory(true) // 内存缓存
                    .cacheOnDisk(true) // sdcard缓存
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
                    .build();//
            ImageLoader.getInstance().displayImage(uri,
                    iavatar , options);
        }else
            iavatar.setImageResource(R.drawable.progress_default);

    }

    public  void showImage(String uri,ImageView imageView){
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .showImageOnLoading(R.drawable.progress_default) // 加载中显示的默认图片
                .showImageOnFail(R.drawable.progress_default) // 设置加载失败的默认图片
                .cacheInMemory(true) // 内存缓存
                .cacheOnDisk(true) // sdcard缓存
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
                .build();//

        ImageLoader.getInstance().displayImage(uri, imageView, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                }
        );
    }
}
