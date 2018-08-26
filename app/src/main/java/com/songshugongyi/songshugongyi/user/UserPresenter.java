package com.songshugongyi.songshugongyi.user;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.util.ImageUtils;

import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/5/16/016.
 */

public class UserPresenter {
    Fragment_User Fragment_User;
    ImageUtils imageUtils;
    public UserPresenter(Fragment_User fragment) {
        this.Fragment_User = fragment;
        imageUtils=new ImageUtils(Fragment_User.getContext());
    }

    public void showUserInfo(){
        if(currentUser!=null){
            imageUtils.showIavatar(currentUser.getUserAvatar(),Fragment_User.getUserImage());
            Fragment_User.getUserName().setText(currentUser.getUserName());
            Fragment_User.getUserId().setText(currentUser.getUserId().substring(0,10));
            Fragment_User.getUser_Attention().setText(" "+currentUser.getUser_attention());
            Fragment_User.getUser_Be_Attention().setText(" "+currentUser.getUser_be_attention());
            Fragment_User.getBtn_UserInfo().setText("个人中心");
        }else {
       //如果User为空，根据SharedPreference获取
//            getUser();
            Fragment_User.getUserImage().setImageResource(R.drawable.progress_default);
            Fragment_User.getUserName().setText("未登录");
            Fragment_User.getUserId().setText(" ");
            Fragment_User.getUser_Attention().setText("0");
            Fragment_User.getUser_Be_Attention().setText("0");
            Fragment_User.getBtn_UserInfo().setText("点击登录");
        }
    }


}
