package com.songshugongyi.songshugongyi.Loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.user.ActivityEditUserInfo;
import com.songshugongyi.songshugongyi.util.ShowToast;
import com.wang.avi.AVLoadingIndicatorView;
import com.yuanopen.commenmodule.ParamsConfig;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.http.OnSendListener;
import com.yuanopen.commenmodule.http.RequestCommon;
import com.yuanopen.commenmodule.utils.Config;
import com.yuanopen.commenmodule.utils.User;
import com.yuanopen.commenmodule.utils.getCurrentTime;

import java.util.HashMap;
import java.util.Map;

import smsutils.IActivity;
import smsutils.SMSVerification;

import static com.songshugongyi.songshugongyi.util.ShowToast.showToast;
import static com.yuanopen.commenmodule.utils.getUUID.getUUID;

/**
 * Created by yuanopen on 2018/5/6/006.
 */

public class RegisterActivity  extends IActivity {

    private Button btnRegister;
    private Button btnGetCode;
    private EditText etCode;
    private EditText etRegisterPhone;
    private EditText etRegisterPassword;
    private EditText etInsurePassword;
    private Button btnForgetPwd;

    //控制按钮样式是否改变
    //手机号码
    private String phone;
    //验证码
    private String code;

    AVLoadingIndicatorView avi;

    SMSVerification smsVerification;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Slide().setDuration(1000));
        getWindow().setExitTransition(new Slide().setDuration(1000));
        setContentView(R.layout.activity_register);

        // 启动短信验证sdk
        initViews();

    }

    //以下是注册代码
    private void initViews() {

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnGetCode = (Button) findViewById(R.id.btn_getcode);
        etCode = (EditText) findViewById(R.id.register_sure_code);
        etRegisterPhone = (EditText) findViewById(R.id.register_input_username);
        etRegisterPassword = (EditText) findViewById(R.id.register_input_password);
        etInsurePassword = (EditText) findViewById(R.id.register_sure_password);
        avi=findViewById(R.id.avi);
        avi.hide();

        btnGetCode.setClickable(false);
        smsVerification=new SMSVerification(this,this,btnGetCode);
        btnForgetPwd=findViewById(R.id.forget_pwd);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码操作
                phone = etRegisterPhone.getText().toString();
                if (isInputIsNull()) {
                    btnGetCode.setClickable(false);
                    // 检查此手机号是否被注册过
                    Map<String,String> param=new HashMap<String, String>();
                    param.put("what",phone);

                    RequestCommon.sendDataToService(RegisterActivity.this,UriConfig.UserServlet, ParamsConfig.RequestAction_Verify_Phone,param , new OnSendListener<String>() {
                        @Override
                        public void onFail(String msdg) {
                            btnGetCode.setClickable(true);
                            ShowToast.showToast(RegisterActivity.this,"此账号已存在！");
                        }

                        @Override
                        public void onSuccess(String modle) {
                            //填写了手机号码
                            smsVerification.getVerifCode(phone);
                        }
                    });

                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证操作
                code = etCode.getText().toString();
                phone = etRegisterPhone.getText().toString();
                smsVerification.submitViriCode(code,phone);
            }
        });

        btnForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


    }



    // 检查输入是否正确
    private boolean isInputIsNull() {
        avi.hide();

        if ("".equals(etRegisterPhone.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(etRegisterPassword.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(etInsurePassword.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etInsurePassword.getText().toString().equals(etRegisterPassword.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "输入两次密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void verifySuccess() {
        String phone = etRegisterPhone.getText().toString();
        final String pass = etRegisterPassword.getText().toString();
        if (!isInputIsNull())
            return;
        else{
            User user=new User();
            user.setUserId(getUUID());
            user.setUserName(phone);
            user.setUserPassward(pass);
            user.setUser_phone(phone);
            user.setCreateTime(getCurrentTime.getTime());
            user.setCreateTime(getCurrentTime.getTime());

            CreateParam request=new CreateParam(UriConfig.UserServlet,ParamsConfig.RequestAction_Register);
            request.setOnResultListener(new BaseRequest.OnResultListener() {
                @Override
                public void onFail(int code, String msg) {
                    avi.hide();
                    avi.setVisibility(View.GONE);
                    showToast(RegisterActivity.this,"登录失败"+msg);
                }

                @Override
                public void onSuccess(String json) {
                    avi.hide();
                    avi.setVisibility(View.GONE);
                    Config.currentUser= GsonUtils.jsonToModule(json,User.class);
                    Intent intent = new Intent(RegisterActivity.this, ActivityEditUserInfo.class);
                    intent.putExtra("fromType",2);
                    startActivity(intent);
                    finish();
                }
            });

            request.PostRequest(UriConfig.UserServlet,request.getPostUrl(user));
            // 请求服务器
            Log.i("RegisterActivity","请求服务器");

        }
    }
}
