package com.yuanopen.commenmodule.utils.qiniu;

import android.content.Context;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.yuanopen.commenmodule.bean.Image;

import java.util.List;

import static com.yuanopen.commenmodule.utils.ShowToast.showToast;
import static com.yuanopen.commenmodule.utils.qiniu.QnUploadHelper.uploadPicWithProgress;

/**
 * Created by yuanopen on 2018/7/19/019.
 */

public class UploadImageToQiniu {

    private static  String TAG="UploadImageToQiniu";
    public static void uploadImagesToQiniu(final Context context, List<String> imagePath, List<Image> images) {
        int i;
        Log.d(TAG,"需要上传"+imagePath.size());
        for ( i= 0; i < imagePath.size(); i++) {
            final int finalI = i;
            uploadPicWithProgress(imagePath.get(i),
                    images.get(i).getImage_url(),
                    new QnUploadHelper.UploadCallBack() {
                        @Override
                        public void success(String url) {
                            showToast(context,"第"+(finalI +1)+"上传成功");
                            Log.d(TAG,"第"+(finalI +1)+"上传成功");
                        }

                        @Override
                        public void fail(String key, ResponseInfo info) {
                            showToast(context,"第"+(finalI +1)+"上传失败");
                            Log.d(TAG,"第"+(finalI +1)+"上传失败");
                        }
                        @Override
                        public void onProgress(double time) {

                        }
                    });
        }

    }
}
