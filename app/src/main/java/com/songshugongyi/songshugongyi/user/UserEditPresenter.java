package com.songshugongyi.songshugongyi.user;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.songshugongyi.songshugongyi.Loginandregister.UpdateNewPasswordActivity;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.databinding.ActivityEditUserinfoBinding;
import com.songshugongyi.songshugongyi.util.ImageUtils;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.Config;
import com.yuanopen.commenmodule.utils.User;
import com.yuanopen.commenmodule.utils.choosepic.PicChooserHelper;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_UserEdit;
import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static com.yuanopen.commenmodule.utils.ShowToast.showToast;

/**
 * Created by yuanopen on 2018/5/17/017.
 */

public class UserEditPresenter {
    ActivityEditUserInfo activityEditUserInfo;
    ActivityEditUserinfoBinding mBinding;
    PicChooserHelper mPicChooserHelper;
    //记录是否有修改
    public boolean isChange;
    ImageUtils utils;
    public UserEditPresenter(ActivityEditUserInfo activityEditUserInfo, ActivityEditUserinfoBinding mBinding) {
        this.activityEditUserInfo = activityEditUserInfo;
        this.mBinding = mBinding;
        isChange=false;
    }


    public void init() {
        utils=new ImageUtils(activityEditUserInfo);
        utils.showIavatar(currentUser.getUserAvatar(),mBinding.userImage);
        mBinding.userName.set(R.drawable.ic_user_icon,"昵称",currentUser.getUserName());
        if("".equals(currentUser.getUser_signature()))
        mBinding.userSignature.set(R.drawable.ic_user_icon,"签名","无");
        else
            mBinding.userSignature.set(R.drawable.ic_user_icon,"签名",currentUser.getUser_signature());

        mBinding.userAge.set(R.drawable.ic_user_icon,"年龄",currentUser.getUserAge()+"");
        mBinding.userSex.set(R.drawable.ic_user_icon,"性别",(currentUser.getUserSex()==1)?"男":"女");
        mBinding.userPhone.set(R.drawable.ic_user_icon,"手机",currentUser.getUser_phone());

        //添加点击事件
        mBinding.userImage.setOnClickListener(activityEditUserInfo);
        mBinding.userName.setOnClickListener(activityEditUserInfo);
        mBinding.userAge.setOnClickListener(activityEditUserInfo);
        mBinding.userSex.setOnClickListener(activityEditUserInfo);
        mBinding.userPhone.setOnClickListener(activityEditUserInfo);
        mBinding.userSignature.setOnClickListener(activityEditUserInfo);

        mBinding.btnBack.setOnClickListener(activityEditUserInfo);
        mBinding.btnSave.setOnClickListener(activityEditUserInfo);
        mBinding.loginOut.setOnClickListener(activityEditUserInfo);
        mBinding.updatePassword.setOnClickListener(activityEditUserInfo);

    }

    //选择图片
    public void choosePic() {

        if (mPicChooserHelper == null) {
            mPicChooserHelper = new PicChooserHelper(activityEditUserInfo,PicChooserHelper.PicType.Avatar);
            mPicChooserHelper.setOnChooseResultListener(new PicChooserHelper.OnChooseResultListener() {
                @Override
                public void onSuccess(String url) {
                    currentUser.setUserAvatar(url);
                    utils.showIavatar(url,mBinding.userImage);
                    isChange=true;
                }

                @Override
                public void onFail(String msg) {
                    Toast.makeText(activityEditUserInfo, "更换头像失败：" + msg, Toast.LENGTH_SHORT).show();
                }
            });
        }

        mPicChooserHelper.showPicChooserDialog();
    }


    public void showEditGenderDialog() {
        EditGenderDialog dialog = new EditGenderDialog(activityEditUserInfo);
        dialog.setOnChangeGenderListener(new EditGenderDialog.OnChangeGenderListener() {
            @Override
            public void onChangeGender(boolean isMale) {
                int gender = isMale ? 1: 0;
                if(gender==1)
                    mBinding.userSex.set("男");
                else
                    mBinding.userSex.set("女");
                if(currentUser.getUserSex()!=gender)
                    isChange=true;

                currentUser.setUserSex(gender);

            }
        });

        dialog.show(currentUser.getUserSex()==0);
    }

    public void showEditNickNameDialog() {
        EditStrProfileDialog dialog = new EditStrProfileDialog(activityEditUserInfo);
        dialog.setOnOKListener(new EditStrProfileDialog.OnOKListener() {
            @Override
            public void onOk(String title, final String content) {
                mBinding.userName.set(content);
                if(currentUser.getUserName()!=content)
                    isChange=true;
                currentUser.setUserName(content);

            }
        });
        dialog.show("昵称", R.drawable.ic_user_icon,currentUser.getUserName());
    }

    public void showEditSignatureDialog() {
        EditStrProfileDialog dialog = new EditStrProfileDialog(activityEditUserInfo);
        dialog.setOnOKListener(new EditStrProfileDialog.OnOKListener() {
            @Override
            public void onOk(String title, final String content) {
                mBinding.userSignature.set(content);
                if(!"".equals(content)){
                    if(currentUser.getUser_signature()!=content)
                        isChange=true;
                    currentUser.setUser_signature(content);
                }
            }
        });
        dialog.show("签名", R.drawable.ic_user_icon,currentUser.getUser_signature());
    }

    public void showEditUserAgeDialog() {
        EditStrProfileDialog dialog = new EditStrProfileDialog(activityEditUserInfo);
        dialog.setOnOKListener(new EditStrProfileDialog.OnOKListener() {
            @Override
            public void onOk(String title, final String content) {
                mBinding.userAge.set(content);
                if(currentUser.getUserAge()!=Integer.parseInt(content))
                    isChange=true;

            }
        });
        dialog.show("年龄", R.drawable.ic_user_icon,currentUser.getUserAge()+"");
    }

    public void showEditUserPasswordDialog() {
        Intent intent=new Intent(activityEditUserInfo, UpdateNewPasswordActivity.class);
        activityEditUserInfo.startActivity(intent);
    }


    public void SaveToDB() {
        updateCurrentUser();
        //转化成json根式
        Gson gson=new Gson();
        String data=gson.toJson(currentUser);
        System.out.println("json: "+data);
        //向服务发送请求

        CreateParam request=new CreateParam(RequestAction_UserEdit);

        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                showToast(activityEditUserInfo,"保存失败！"+msg);
            }

            @Override
            public void onSuccess(String json) {
                currentUser= GsonUtils.jsonToModule(json, User.class);
                Config.IsUserEditChange=true;
                showToast(activityEditUserInfo,"保存成功！");
                activityEditUserInfo.Finish();
            }
        });

        request.PostRequest(UriConfig.UserServlet,request.getPostUrl(currentUser));
        //请求服务器
        Log.i("ActivityEditUserInfo","请求服务器");

    }


    public void  updateCurrentUser(){
        currentUser.setUserName(mBinding.userName.getValue());

        int  gender = mBinding.userSex.getValue() == "男"?1:0;
        currentUser.setUserSex(gender);

        currentUser.setUser_phone(mBinding.userPhone.getValue());
        currentUser.setUserAge(Integer.parseInt( mBinding.userAge.getValue()));
    }

}
