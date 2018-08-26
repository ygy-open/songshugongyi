package com.songshugongyi.songshugongyi.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.songshugongyi.songshugongyi.Loginandregister.LoginAcitivity;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.activity.AtyMain;
import com.songshugongyi.songshugongyi.databinding.ActivityEditUserinfoBinding;

import io.rong.imkit.RongIM;

import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/5/16/016.
 */

public class ActivityEditUserInfo extends Activity implements View.OnClickListener{

    private  String TAG="ActivityEditUserInfo";
    ActivityEditUserinfoBinding mBinding;
    UserEditPresenter presenter;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_edit_userinfo);
        //状态栏设置
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_tab));

        presenter=new UserEditPresenter(this,mBinding);
        type=getIntent().getIntExtra("fromType",1);
        presenter.init();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击头像
            case R.id.user_image:
                presenter.choosePic();
                break;
            case R.id.user_name:
                presenter.showEditNickNameDialog();
                break;
            case R.id.user_signature:
                presenter.showEditSignatureDialog();
                break;
            case R.id.user_age:
                presenter. showEditUserAgeDialog();
                break;
            case R.id.user_sex:
                presenter.showEditGenderDialog();
                break;
            case R.id.user_phone:
                break;
            //点击返回
            case R.id.btn_back:
                onBackPressed();
                break;
            //点击保存
            case R.id.btn_save:
                presenter.SaveToDB();
                break;
            //修改密码
            case R.id.update_password:
                presenter. showEditUserPasswordDialog();
                break;
            //退出登录
            case R.id.login_out:
                // 退出融云
                RongIM.getInstance().logout();
                //记住用户名
                //保存用户名
                SharedPreferences sp ;
                sp=getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username","null" );
                editor.putString("password", "null");
                editor.commit();
                currentUser=null;
                Fragment_User.getInstance().makeNull();
                Intent intent=new Intent(this, LoginAcitivity.class);
                intent.putExtra("fromType",2);
                startActivity(intent);
                finish();

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (presenter.mPicChooserHelper != null) {
            presenter.mPicChooserHelper.onActivityResult(requestCode, resultCode, data,1);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    AlertDialog dialog;

    @Override
    public void onBackPressed() {

        Log.d(TAG,"isChange"+presenter.isChange);
        if(presenter.isChange)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("信息有更变，请保存");
            builder.setNegativeButton("保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    presenter.SaveToDB();

                    dialog.dismiss();
                    Finish();
                }
            });

            builder.setPositiveButton("不保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Finish();
                    dialog.dismiss();
                }
            });

            dialog=builder.create();
            dialog.show();
        }else
        {
            Finish();
        }



    }

    void Finish(){
        if(type==1)
            finish();
        else
        {
            Intent intent=new Intent(this, AtyMain.class);
            startActivity(intent);
            finish();
        }
    }

    }
