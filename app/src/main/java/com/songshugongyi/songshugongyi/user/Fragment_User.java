package com.songshugongyi.songshugongyi.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.songshugongyi.songshugongyi.Loginandregister.LoginAcitivity;
import com.songshugongyi.songshugongyi.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanopen.commenmodule.utils.Config.IsUserEditChange;
import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/5/4/004.
 */

public class Fragment_User extends Fragment {

    private  static  Fragment_User instance;
    public static  Fragment_User getInstance(){
        return instance;
    }

    private String TAG="Fragment_User";

    //用户头像
    private CircleImageView UserImage;
    //用户昵称
    private TextView UserName,UserId,User_Attention,User_Be_Attention;

    //信息中心，收藏项目，我的积分,我的好友，关于我们，意见反馈，设置
    public   userEdit MyNews,MyCollectProgress,MyJoin,UserFeedBack,AboutUs;
    //点击进入个人中心
    Button Btn_UserInfo;
    //presenter
    UserPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user, container, false);
       findAllViews(view);
        //展示信息
        presenter=new UserPresenter(this);
        instance=this;
        presenter.showUserInfo();
        setIconKey();
        return view;
    }

    private void findAllViews(View view) {
        UserImage=view.findViewById(R.id.user_image);
        UserName=view.findViewById(R.id.user_name);
        UserId=view.findViewById(R.id.user_id);
        User_Attention=view.findViewById(R.id.user_attention);
        User_Be_Attention=view.findViewById(R.id.user_be_attention);

        MyNews=view.findViewById(R.id.user_news);
        MyCollectProgress=view.findViewById(R.id.user_likes);
        MyJoin=view.findViewById(R.id.user_joins);

        AboutUs=view.findViewById(R.id.about_us);
        UserFeedBack=view.findViewById(R.id.user_feedback);
  //点击个人主页
        Btn_UserInfo=view.findViewById(R.id.btn_to_userinfo);

        Btn_UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(currentUser==null){
                    intent=new Intent(getContext(),LoginAcitivity.class);
                    intent.putExtra("fromType",2);
                }else{
                    intent=new Intent(getContext(),ActivityEditUserInfo.class);
                    intent.putExtra("fromType",1);
                }

                startActivity(intent);
            }
        });

        MyNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),ActivityVerifyMessage.class);
                startActivity(intent);
            }
        });

    }

    private void setIconKey() {

        MyNews.set(R.drawable.ic_user_news,"通知消息");
        MyCollectProgress.set(R.drawable.ic_user_collect,"喜欢");
        MyJoin.set(R.drawable.ic_join_icon,"参与");
        UserFeedBack.set(R.drawable.ic_user_feedback,"意见反馈");
        AboutUs.set(R.drawable.ic_about_us, "关于我们");


    }

    public CircleImageView getUserImage() {
        return UserImage;
    }
    public TextView getUserName() {
        return UserName;
    }
    public TextView getUserId() {
        return UserId;
    }
    public TextView getUser_Attention() {
        return User_Attention;
    }
    public TextView getUser_Be_Attention() {
        return User_Be_Attention;
    }
    public Button getBtn_UserInfo() {
        return Btn_UserInfo;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"---->onResume");
        if (!IsUserEditChange){
            presenter.showUserInfo();
            IsUserEditChange=false;
        }
    }


    public void makeNull(){
        presenter.showUserInfo();
    }

}
