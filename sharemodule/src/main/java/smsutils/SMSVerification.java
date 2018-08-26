package smsutils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getSupportedCountries;
import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;

/**
 * Created by yuanopen on 2018/7/13/013.
 */

public class SMSVerification {

    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i = 60;

    Context mContent;
    IActivity mActivity;
    Button btnGetCode;
    private Handler handler;
    public SMSVerification(Context mContent, IActivity mActivity, Button btnGetCode) {
        this.mContent = mContent;
        this.mActivity = mActivity;
        this.btnGetCode = btnGetCode;

        init();
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    private void init(){

    handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    Log.i("aaa",""+"可以进行注册");
//                    ShowToast.showToast(mContent,"验证成功");
                    Toast.makeText(mContent, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    mActivity.verifySuccess();
                    break;
                case 1:
                    //获取验证码成功
                    Toast.makeText(mContent, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //返回支持发送验证码的国家列表
//                    Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    //返回支持发送验证码的国家列表
//                    Toast.makeText(mContent, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    }

    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Message msg = new Message();
                    msg.arg1 = 0;
                    msg.obj = data;
                    handler.sendMessage(msg);
                    Log.i("aaa", "提交验证码成功");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Message msg = new Message();
                    //获取验证码成功
                    msg.arg1 = 1;
                    msg.obj = "获取验证码成功";
                    handler.sendMessage(msg);
                    Log.i("aaa", "获取验证码成功");
                    // Log.d(TAG, "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    Message msg = new Message();
                    //返回支持发送验证码的国家列表
                    Log.i("aaa", "返回支持发送验证码的国家列表");
                    msg.arg1 = 2;
                    msg.obj = "返回支持发送验证码的国家列表";
                    handler.sendMessage(msg);
//                          Log.d(TAG, "返回支持发送验证码的国家列表");
                }
            } else {
                Message msg = new Message();
                //返回支持发送验证码的国家列表
                msg.arg1 = 3;
                msg.obj = "验证失败";
                handler.sendMessage(msg);
                Toast.makeText(mContent,"验证码错误", Toast.LENGTH_SHORT).show();
            }
        }

    };




public  void submitViriCode(String code,String phone){
    if (code.equals("")) {
        Toast.makeText(mContent, "验证码不能为空", Toast.LENGTH_SHORT).show();
    } else {
        submitVerificationCode("86", phone, code);
    }
}
    public void  getVerifCode(String phone){
        if (isMobileNO(phone)) {
            //如果手机号码无误，则发送验证请求
            btnGetCode.setClickable(true);
            changeBtnGetCode();
            getSupportedCountries();
            getVerificationCode("86", phone);
        } else {
            //手机号格式有误
            Toast.makeText(mContent, "手机号格式错误，请检查", Toast.LENGTH_SHORT).show();
        }    }

    /*
* 改变按钮样式
* */
    private void changeBtnGetCode() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (mContent == null) {
                            break;
                        }
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnGetCode.setText("获取验证码(" + i + ")");
                                btnGetCode.setClickable(false);
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;

                if (mContent != null) {

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnGetCode.setText("获取验证码");
                            btnGetCode.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    private boolean isMobileNO(String phone) {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)) return false;
        else return phone.matches(telRegex);
    }

}
