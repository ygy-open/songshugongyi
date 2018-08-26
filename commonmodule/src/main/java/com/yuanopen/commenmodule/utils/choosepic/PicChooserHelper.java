package com.yuanopen.commenmodule.utils.choosepic;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.qiniu.android.http.ResponseInfo;
import com.yalantis.ucrop.UCrop;
import com.yuanopen.commenmodule.utils.qiniu.QnUploadHelper;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.yuanopen.commenmodule.utils.Config.currentUser;


/**
 * Created by Administrator.
 */

public class PicChooserHelper {

    private Activity mActivity;
    private Fragment mFragment;
    private static final int FROM_CAMERA = 2;
    private static final int FROM_ALBUM = 1;
    private static final int CROP = 0;

    private Uri mCameraFileUri;
    private PicType mPicType;

    public static enum PicType {
        Avatar, Cover
    }

    public PicChooserHelper(Activity activity, PicType picType) {
        mActivity = activity;
        mPicType = picType;
    }

    public PicChooserHelper(Fragment fragment, PicType picType) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
        mPicType = picType;

    }

    public void showPicChooserDialog() {
        PicChooseDialog dialog = new PicChooseDialog(mActivity);
        dialog.setOnDialogClickListener(new PicChooseDialog.OnDialogClickListener() {
            @Override
            public void onCamera() {
                //拍照
                takePicFromCamera();
            }

            @Override
            public void onAlbum() {
                //相册
                takePicFromAlbum();
            }
        });
        dialog.show();
    }

    private void takePicFromAlbum() {
        Intent picIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        picIntent.setType("image/*");
        if (mFragment == null) {
            mActivity.startActivityForResult(picIntent, FROM_ALBUM);
        } else {
            mFragment.startActivityForResult(picIntent, FROM_ALBUM);
        }
    }

    private void takePicFromCamera() {
        mCameraFileUri = createAlbumUri();


        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < 24) {
            //小于7.0的版本
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, mCameraFileUri);
            if (mFragment == null) {
                mActivity.startActivityForResult(intentCamera, FROM_CAMERA);
            } else {
                mFragment.startActivityForResult(intentCamera, FROM_CAMERA);
            }
        } else {
            //大于7.0的版本
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, mCameraFileUri.getPath());
            Uri uri = getImageContentUri(mCameraFileUri);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            if (mFragment == null) {
                mActivity.startActivityForResult(intentCamera, FROM_CAMERA);
            } else {
                mFragment.startActivityForResult(intentCamera, FROM_CAMERA);
            }
        }

    }

    private Uri createAlbumUri() {
        String dirPath = Environment.getExternalStorageDirectory() + "/" + mActivity.getApplication().getApplicationInfo().packageName;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String id = "";
        if (currentUser != null) {
            id = currentUser.getUserId();
        }
        String fileName = id + ".jpg";
        File picFile = new File(dirPath, fileName);
        if (picFile.exists()) {
            picFile.delete();
        }

        return Uri.fromFile(picFile);

    }


    /**
     * 转换 content:// uri
     */
    public Uri getImageContentUri(Uri uri) {
        String filePath = uri.getPath();
        Cursor cursor = mActivity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            return mActivity.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data,int i) {
        if (requestCode == FROM_CAMERA) {
            //从相册选择返回。
            if (resultCode == RESULT_OK) {
                startCrop(mCameraFileUri);
            }
        } else if (requestCode == FROM_ALBUM) {
            //从相册选择返回。
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                startCrop(uri);
            }
        } else if (requestCode == CROP) {
            //裁剪结束
            if (resultCode == RESULT_OK) {
                //上传到服务器保存起来
                //七牛上传
                // i==1 说明是更好头像
                if(i==1)
                uploadTo7Niu(cropUri.getPath());
                else  if(i==2){
                    // i==2 是选择创建群组头像
                    mOnChooserResultListener.onSuccess(cropUri.getPath());
                }
            }
        }//裁剪成功后调用
        else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            //设置裁剪完成后的图片显示
//                mIv.setImageURI(resultUri);
//            Log.d(TAG,"UCrop:"+new File(resultUri.getPath()).getAbsoluteFile().length());
            if(i==1)
                uploadTo7Niu(cropUri.getPath());
            else  if(i==2){
                // i==2 是选择创建群组头像
                mOnChooserResultListener.onSuccess(cropUri.getPath());
            }
            //出错时进入该分支
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private Uri cropUri = null;


    private void startCrop(Uri uri) {

        UCrop.Options options = new UCrop.Options();

        options.setCompressionQuality(100);

        cropUri = createCropUri();
        UCrop.of(uri, cropUri) //定义路径
                .withAspectRatio(16, 16) //定义裁剪比例 4:3 ， 16:9
                .withMaxResultSize(400, 400) //定义裁剪图片宽高最大值
                .withOptions(options)
                .start(mActivity);

//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.putExtra("crop", "true");
//        if(mPicType == PicType.Avatar) {
//            intent.putExtra("aspectX", 300);
//            intent.putExtra("aspectY", 300);
//            intent.putExtra("outputX", 300);
//            intent.putExtra("outputY", 300);
//        }else if(mPicType == PicType.Cover){
//            intent.putExtra("aspectX", 500);
//            intent.putExtra("aspectY", 300);
//            intent.putExtra("outputX", 500);
//            intent.putExtra("outputY", 300);
//        }
//        intent.putExtra("return-data", false);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentapiVersion < 24) {
//            //小于7.0的版本
//            intent.setDataAndType(uri, "image/*");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
//            if (mFragment == null) {
//                mActivity.startActivityForResult(intent, CROP);
//            } else {
//                mFragment.startActivityForResult(intent, CROP);
//            }
//        } else {
//            //大于7.0的版本
//            {
//                String scheme = uri.getScheme();
//                if (scheme.equals("content")) {
//                    Uri contentUri = uri;
//                    intent.setDataAndType(contentUri, "image/*");
//                } else {
//                    Uri contentUri = getImageContentUri(uri);
//                    intent.setDataAndType(contentUri, "image/*");
//                }
//            }

//            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
//            if (mFragment == null) {
//                mActivity.startActivityForResult(intent, CROP);
//            } else {
//                mFragment.startActivityForResult(intent, CROP);
//            }
//        }
    }

    private Uri createCropUri() {
        String dirPath = Environment.getExternalStorageDirectory() + "/songshugongyi/image/user";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String id = "";
        if (currentUser != null) {
            id = currentUser.getUserId();
        }

        String fileName = id + "_crop.jpg";
        File picFile = new File(dirPath, fileName);
        if (picFile.exists()) {
            picFile.delete();
        }
        try {
            picFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(picFile);
    }

    private void uploadTo7Niu(String path) {
        String id = "";
        if (currentUser != null) {
            id = currentUser.getUserId();
        }
        String name = id + "_" + System.currentTimeMillis() + "_avatar";
//        String name = id  + "_avatar";
        QnUploadHelper.uploadPic(path, name, new QnUploadHelper.UploadCallBack() {
            @Override
            public void success(String url) {
                //上传成功
                if (mOnChooserResultListener != null) {
                    mOnChooserResultListener.onSuccess(url);
                }
            }

            @Override
            public void fail(String key, ResponseInfo info) {
                //上传失败！
                if (mOnChooserResultListener != null) {
                    mOnChooserResultListener.onFail(info.error);
                }
            }

            @Override
            public void onProgress(double time) {

            }
        });
    }

    public void uploadGroupTo7Niu(String group_id,String path) {

        String name = "group_"+group_id + "_" + System.currentTimeMillis() + "_avatar";

        QnUploadHelper.uploadPic(path, name, new QnUploadHelper.UploadCallBack() {
            @Override
            public void success(String url) {
                //上传成功
                if (mOnChooserResultListener != null) {
                    mOnChooserResultListener.onSuccess(url);
                }
            }

            @Override
            public void fail(String key, ResponseInfo info) {
                //上传失败！
                if (mOnChooserResultListener != null) {
                    mOnChooserResultListener.onFail(info.error);
                }
            }

            @Override
            public void onProgress(double time) {

            }
        });
    }

    public interface OnChooseResultListener {
        void onSuccess(String url);

        void onFail(String msg);
    }

    private OnChooseResultListener mOnChooserResultListener;



    public void setOnChooseResultListener(OnChooseResultListener l) {
        mOnChooserResultListener = l;
    }
}
