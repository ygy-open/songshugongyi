package com.yuanopen.chatmudule.group;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.yuanopen.chatmudule.R;
import com.yuanopen.chatmudule.bean.UserGroup;
import com.yuanopen.chatmudule.databinding.ActivityCreateGroupBinding;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.utils.ShowToast;
import com.yuanopen.commenmodule.utils.choosepic.PicChooserHelper;
import com.yuanopen.commenmodule.utils.qiniu.QnUploadHelper;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Create_Group;
import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static com.yuanopen.commenmodule.utils.ShowToast.showToast;
import static com.yuanopen.commenmodule.utils.getUUID.getUUID;

/**
 * Created by yuanopen on 2018/7/25/025.
 */

public class CreateGroupActivity extends AppCompatActivity {

    ActivityCreateGroupBinding createGroupBinding;
    PicChooserHelper mPicChooserHelper;
    String imagePath;
    UserGroup group;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createGroupBinding= DataBindingUtil.setContentView(this,R.layout.activity_create_group);
        init();
    }

    private void init() {
    }

    public void SaveToDB(View v) {

        createGroupBinding.btnCreate.setClickable(false);
         group=new UserGroup();
        group.setGroup_id(getUUID());
        group.setGroup_name(createGroupBinding.loginInputGroupname.getText().toString());
        group.setUser_id(currentUser.getUserId());
        group.setGroup_signature(createGroupBinding.groupSignature.getText().toString());

        // 先上传图片
        UploadImageToQiniu();
    }

    private void sendGroupToService() {
       //转化成json根式


        //向服务发送请求
        CreateParam request=new CreateParam(RequestAction_Create_Group);

        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                createGroupBinding.btnCreate.setClickable(false);
                showToast(CreateGroupActivity.this,"创建失败！"+msg);
            }

            @Override
            public void onSuccess(String json) {

                showToast(CreateGroupActivity.this,"创建成功！");
                CreateGroupActivity.this.finish();
            }
        });

        request.PostRequest(UriConfig.UserServlet,request.getPostUrl(group));
        //请求服务器
        Log.i("CreateGroupActivity","请求服务器");

    }

    //选择图片
    public void choosePic(View v) {

        if (mPicChooserHelper == null) {
            mPicChooserHelper = new PicChooserHelper(this, PicChooserHelper.PicType.Avatar);
            mPicChooserHelper.setOnChooseResultListener(new PicChooserHelper.OnChooseResultListener() {
                @Override
                public void onSuccess(String url) {
                    imagePath=url;
                    Bitmap bitmap = BitmapFactory.decodeFile(url);
                    createGroupBinding.groupImage.setImageBitmap(bitmap);
                }

                @Override
                public void onFail(String msg) {
                    Toast.makeText(CreateGroupActivity.this, "选择头像失败：" + msg, Toast.LENGTH_SHORT).show();
                }
            });
        }

        mPicChooserHelper.showPicChooserDialog();
    }

    private void UploadImageToQiniu(){
        String name = "group_"+group.getGroup_id() + "_" + System.currentTimeMillis() + "_avatar";

        QnUploadHelper.uploadPic(imagePath, name, new QnUploadHelper.UploadCallBack() {
            @Override
            public void success(String url) {
              // 图片上传成功后 保存信息到服务器
                group.setGroup_avatar(url);
                sendGroupToService();
            }

            @Override
            public void fail(String key, ResponseInfo info) {
                ShowToast.showToast(CreateGroupActivity.this,"请选择图片");
                createGroupBinding.btnCreate.setClickable(false);
            }

            @Override
            public void onProgress(double time) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mPicChooserHelper != null) {
            mPicChooserHelper.onActivityResult(requestCode, resultCode, data,2);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
