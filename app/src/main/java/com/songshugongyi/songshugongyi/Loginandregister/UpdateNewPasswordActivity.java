package com.songshugongyi.songshugongyi.Loginandregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.util.ShowToast;
import com.wang.avi.AVLoadingIndicatorView;
import com.yuanopen.commenmodule.ParamsConfig;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.OnSendListener;
import com.yuanopen.commenmodule.http.RequestCommon;

import java.util.HashMap;
import java.util.Map;

import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/7/22/022.
 */

public class UpdateNewPasswordActivity extends AppCompatActivity {

    private EditText etOldPassword;
    private EditText etNewPassword;
    private Button btnSure,btn_Cancle;

    String password;
    AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_new_password);
        initViews();
    }

    //以下是注册代码
    private void initViews() {

        btnSure =  findViewById(R.id.btn_sure);
        btn_Cancle=findViewById(R.id.btn_cancel);
        etOldPassword = (EditText) findViewById(R.id.input_old_password);
        etNewPassword = (EditText) findViewById(R.id.input_new_password);

        avi=findViewById(R.id.avi);
        avi.hide();

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentDataToService();
            }
        });
        btn_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sentDataToService() {

        if(!isInputIsNull())
            return;
        else{
            avi.show();
            password=etNewPassword.getText().toString();
            // 检查此手机号是否被注册过
            final Map<String,String> param=new HashMap<String, String>();
            param.put("user_phone", currentUser.getUser_phone());
            param.put("user_password",password);

            RequestCommon.sendDataToService(UpdateNewPasswordActivity.this, UriConfig.UserServlet,ParamsConfig.RequestAction_User_Set_New_Password,param , new OnSendListener<String>() {
                @Override
                public void onFail(String msdg) {
                    ShowToast.showToast(UpdateNewPasswordActivity.this,"设置失败，请重试！");
                    avi.hide();
                }

                @Override
                public void onSuccess(String modle) {
                    ShowToast.showToast(UpdateNewPasswordActivity.this,"密码修改成功！");
                    avi.hide();
                    currentUser.setUserPassward(password);
                    finish();
                }
            });
        }

    }

    // 检查输入是否正确
    private boolean isInputIsNull() {
        avi.hide();
        if ("".equals(etOldPassword.getText().toString())) {
            Toast.makeText(UpdateNewPasswordActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(etNewPassword.getText().toString())) {
            Toast.makeText(UpdateNewPasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etOldPassword.getText().toString().equals(etNewPassword.getText().toString())) {
            Toast.makeText(UpdateNewPasswordActivity.this, "新密码与旧密码一样", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!etOldPassword.getText().toString().equals(currentUser.getUserPassward())) {
            Toast.makeText(UpdateNewPasswordActivity.this, "请输入正确的原始密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
