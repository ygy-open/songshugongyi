package com.songshugongyi.songshugongyi.Loginandregister;

import android.content.Intent;
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

/**
 * Created by yuanopen on 2018/7/22/022.
 */

public class SetNewPasswordActivity extends AppCompatActivity {

    private Button btnSure;

    private EditText etRegisterPassword;
    private EditText etInsurePassword;

    String phone;
    String password;
    AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        initViews();
    }

    //以下是注册代码
    private void initViews() {

        btnSure =  findViewById(R.id.btn_sure);
        etRegisterPassword = (EditText) findViewById(R.id.register_input_password);
        etInsurePassword = (EditText) findViewById(R.id.register_sure_password);

        avi=findViewById(R.id.avi);
        avi.hide();

        phone=getIntent().getStringExtra("user_phone");

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sentDataToService();
            }
        });
    }

    private void sentDataToService() {
        avi.show();
        if(!isInputIsNull())
            return;
        if("".equals(phone)){
            ShowToast.showToast(this,"验证失败");
            return;
        }

        password=etRegisterPassword.getText().toString();

        // 检查此手机号是否被注册过
        Map<String,String> param=new HashMap<String, String>();
        param.put("user_phone",phone);
        param.put("user_password",password);

        RequestCommon.sendDataToService(SetNewPasswordActivity.this, UriConfig.UserServlet, ParamsConfig.RequestAction_User_Set_New_Password,param , new OnSendListener<String>() {
            @Override
            public void onFail(String msdg) {
                ShowToast.showToast(SetNewPasswordActivity.this,"设置失败，请重试！");
                avi.hide();
            }

            @Override
            public void onSuccess(String modle) {
                ShowToast.showToast(SetNewPasswordActivity.this,"密码修改成功！");
                Intent intent=new Intent(SetNewPasswordActivity.this,LoginAcitivity.class);
                intent.putExtra("fromType",1);
                startActivity(intent);
                avi.hide();
                finish();
            }
        });
    }

    // 检查输入是否正确
    private boolean isInputIsNull() {
        avi.hide();

        if ("".equals(etRegisterPassword.getText().toString())) {
            Toast.makeText(SetNewPasswordActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(etInsurePassword.getText().toString())) {
            Toast.makeText(SetNewPasswordActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etInsurePassword.getText().toString().equals(etRegisterPassword.getText().toString())) {
            Toast.makeText(SetNewPasswordActivity.this, "输入两次密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
