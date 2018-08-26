package com.songshugongyi.songshugongyi.Loginandregister;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.activity.AtyMain;
import com.wang.avi.AVLoadingIndicatorView;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.ShowPermission;
import com.yuanopen.commenmodule.utils.User;

import java.util.HashMap;
import java.util.Map;

import thirdlogin.ThirdLoginUtils;

import static com.songshugongyi.songshugongyi.util.ShowToast.showToast;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Login;
import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/5/6/006.
 *登录界面
 */

public class LoginAcitivity extends Activity implements View.OnClickListener{

    private String TAG="LoginAcitivity";
    private EditText username;
    private EditText password;
    Button btn_Eye,btnLogin,btnRegister,btnQQLogin,btnWeixinLogin , btnForgetPwd;

    boolean eye =true;
    CheckBox box;
    String name;
    String pass;
    //保存用户名
    SharedPreferences sp ;
    AVLoadingIndicatorView avi;
// 第三方登录
ThirdLoginUtils loginUtils;
    // 指明为何登录 默认为1 ：普通打开应用登录  2：从其他界面登录

    private int fromType=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        //加载启动界面
        ShowPermission.getInstance(this).show();
        init();
    }

    private void init() {
        fromType= getIntent().getIntExtra("fromType",1);
        Log.d(TAG,"fromType"+fromType);
        sp=getSharedPreferences("user", Context.MODE_PRIVATE);

        name=sp.getString("username","null");
        pass=sp.getString("password","null");

        if("null".equals(name))
        username.setText("");
        if("null".equals(pass))
        password.setText("");

        box.setButtonDrawable(R.mipmap.box_unchecked);
        loginUtils=ThirdLoginUtils.getIntance();

    }

    private void thirdLogin() {
        loginUtils.setOnThirdLoginListener(new ThirdLoginUtils.OnThirdLoginListener() {
            @Override
            public void OnFail(String mrg) {
                avi.hide();
                avi.setVisibility(View.GONE);
            }

            @Override
            public void OnSuccess(User user) {
                SharedPreferences.Editor editor = sp.edit();
                avi.hide();
                avi.setVisibility(View.GONE);
                currentUser=user;
                editor.putString("LoginType","third");
                editor.putString("user_id",user.getUserId());
                editor.apply();
                // 判断跳转
                if(fromType==1) {
                    Intent intent = new Intent(LoginAcitivity.this, AtyMain.class);
                    startActivity(intent);
                }
                    finish();
            }
        });
    }

    private void findView() {
       username=findViewById(R.id.login_input_username);
        password=findViewById(R.id.login_input_password);

        btnLogin=findViewById(R.id.btn_login);
        btnRegister=findViewById(R.id.btn_to_register);
        btnQQLogin=findViewById(R.id.btn_qq_login);
        btnWeixinLogin=findViewById(R.id.btn_weixin_login);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnQQLogin.setOnClickListener(this);
        btnWeixinLogin.setOnClickListener(this);

        btn_Eye=findViewById(R.id.passwd_eye_btn);

        box=findViewById(R.id.remember_pwd);

        btn_Eye.setOnClickListener(this);
        box.setOnClickListener(this);

        avi=findViewById(R.id.avi);
        avi.hide();
        btnForgetPwd=findViewById(R.id.forget_pwd);

        btnForgetPwd.setOnClickListener(this);
    }

    public void toRegister() {
        Intent intent = new Intent(LoginAcitivity.this, RegisterActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void Login() {
        name=username.getText().toString();
        pass=password.getText().toString();

        if("".equals(name)||"".equals(pass)){
            showToast(LoginAcitivity.this,"用户名或密码不能为空！");
            return;
        }
        avi.setVisibility(View.VISIBLE);
        avi.show();

        Map<String ,String>params=new HashMap<>();
        params.put("user_phone",name);
        params.put("user_password",pass);

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Login,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                avi.hide();
                avi.setVisibility(View.GONE);
                showToast(LoginAcitivity.this,"登录失败"+msg);
            }

            @Override
            public void onSuccess(String json) {

                SharedPreferences.Editor editor = sp.edit();
                if(box.isChecked()){
                    //记住用户名
                    editor.putString("username",name );
                    editor.putString("password", pass);
                }

                editor.putBoolean("isFirstLogin",false);
                editor.putString("LoginType","phone");
                editor.apply();

                avi.hide();
                avi.setVisibility(View.GONE);
                currentUser= GsonUtils.jsonToModule(json, User.class);
                // 判断跳转
                if(fromType==1) {
                    Intent intent = new Intent(LoginAcitivity.this, AtyMain.class);
                    startActivity(intent);
                }
                finish();

            }
        });

        //请求服务器
        String requestUrl = request.getUrl();
        Log.i("LoginActivity",requestUrl);
        request.request(requestUrl);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_login:
                Log.d(TAG,"Login");
                Login();
                break;
            case R.id.btn_to_register:
                Log.d(TAG,"Register");
                toRegister();
                break;
            case R.id.btn_qq_login:
                thirdLogin();
                avi.setVisibility(View.VISIBLE);
                avi.show();
                Log.d(TAG,"QQ");
                loginUtils.ThirdQQLogin();
                break;
            case R.id.btn_weixin_login:
                loginUtils.ThirdWeiXinLogin();
                Log.d(TAG,"weixin");
                break;
            // 是否记住密码
            case R.id.remember_pwd:
                if(box.isChecked())
                    box.setButtonDrawable(R.mipmap.box_checked);
                else
                    box.setButtonDrawable(R.mipmap.box_unchecked);
                Log.d(TAG,"记住密码");
                break;
            // 眼睛
            case R.id.passwd_eye_btn:
                if(eye){
                    btn_Eye.setBackgroundResource(R.drawable.ic_visibility);
                    password.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    eye=false;
                }else {
                    btn_Eye.setBackgroundResource(R.drawable.ic_visibility_off_);
                    password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    eye=true;
                }
                break;
            case R.id.forget_pwd:
                Intent intent=new Intent(LoginAcitivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                break;

//            case R.id.btn_weixin_login:
//                loginUtils.ThirdWeiXinLogin();
//                break;
        }
    }
}
