package com.songshugongyi.songshugongyi.Loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.util.ShowToast;
import com.wang.avi.AVLoadingIndicatorView;

import smsutils.IActivity;
import smsutils.SMSVerification;

/**
 * Created by yuanopen on 2018/7/21/021.
 */

public class ForgetPasswordActivity extends IActivity {

    private String TAG="ForgetPasswordActivity";

    private Button btnVerify;
    private Button btnGetCode;
    private EditText etCode;
    private EditText etRegisterPhone;

    //控制按钮样式是否改变
    //手机号码
    private String phone;
    //验证码
    private String code;
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i = 60;
    AVLoadingIndicatorView avi;

    SMSVerification smsVerification;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initViews();
    }

    //以下是注册代码
    private void initViews() {

        btnVerify =  findViewById(R.id.btn_verify);
        btnGetCode = findViewById(R.id.btn_getcode);
        etCode =  findViewById(R.id.register_sure_code);
        etRegisterPhone = findViewById(R.id.register_input_username);

        avi=findViewById(R.id.avi);
        avi.hide();

        btnGetCode.setClickable(false);
        smsVerification=new SMSVerification(this,this,btnGetCode);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码操作
                phone = etRegisterPhone.getText().toString();
                if (!"".equals(phone)) {
                            //填写了手机号码
                            smsVerification.getVerifCode(phone);
                }else
                    ShowToast.showToast(ForgetPasswordActivity.this,"请输入手机号");
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证操作
                code = etCode.getText().toString();
                phone = etRegisterPhone.getText().toString();
                smsVerification.submitViriCode(code,phone);
            }
        });
    }

    @Override
    protected void verifySuccess() {
        Intent intent=new Intent(ForgetPasswordActivity.this,SetNewPasswordActivity.class);
        intent.putExtra("user_phone",phone);
        startActivity(intent);
        finish();
    }
}
