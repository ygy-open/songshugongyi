package com.songshugongyi.songshugongyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.songshugongyi.songshugongyi.Loginandregister.LoginAcitivity;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.forum.create.CreateHotTopic;
import com.songshugongyi.songshugongyi.forum.fragment_forum;
import com.songshugongyi.songshugongyi.progress.Fragment_Progress;
import com.songshugongyi.songshugongyi.progress.create.CreateProgress;
import com.songshugongyi.songshugongyi.receive.MyReceiveMessageListener;
import com.songshugongyi.songshugongyi.user.Fragment_User;
import com.songshugongyi.songshugongyi.widget.CustomViewPager;
import com.yuanopen.chatmudule.ChatConfig;
import com.yuanopen.chatmudule.bean.RongyunToken;
import com.yuanopen.chatmudule.chat.FragmentConvercationList;
import com.yuanopen.chatmudule.chat.ToCList;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Get_New_RongYun_Token;
import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static io.rong.imkit.RongIM.setOnReceiveMessageListener;


/**
     *   Copyright  2018,yuanopen
 *    fun  实现首页之间的切换
     */

    public class AtyMain extends AppCompatActivity {

    private String TAG="AtyMain";
    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private List<TabLayout.Tab> tabList;
    private TextView name;
    private List<String>list;
    private Button btnShowAdd,btnShowPeople;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main1);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_tab));

        initView();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(){
        setOnReceiveMessageListener(new MyReceiveMessageListener(this));

        viewPager=(CustomViewPager)findViewById(R.id.vp_main);
        tabLayout=(TabLayout)findViewById(R.id.tl_main);
        name= (TextView) findViewById(R.id.fragment_item_name);
        btnShowAdd = (Button) findViewById(R.id.is_show_add);

        btnShowAdd.setVisibility(View.VISIBLE);
        btnShowAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(currentUser==null){
                    intent= new Intent(AtyMain.this, LoginAcitivity.class);
                    intent.putExtra("fromtype",2);
                }else
                    intent= new Intent(AtyMain.this, CreateProgress.class);
                startActivity(intent);
            }
        });


        btnShowPeople=(Button) findViewById(R.id.is_show_friend);
        btnShowPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToCList.getIntance(AtyMain.this).toAddressBook();
            }
        });

        tabList=new ArrayList<>();
        list=new ArrayList<>();
        final AdapterMainViewPager adapter=new AdapterMainViewPager(getSupportFragmentManager());

        Fragment_Progress progress=new Fragment_Progress();
        FragmentConvercationList fragmentConvercationList=new FragmentConvercationList();
        fragment_forum forum=new fragment_forum();
        Fragment_User user=new Fragment_User();

        adapter.addFragment(progress);
        adapter.addFragment(fragmentConvercationList);
        adapter.addFragment(forum);
        adapter.addFragment(user);


        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));
        tabList.add(tabLayout.getTabAt(3));

        tabList.get(0).setCustomView(getTabView("项目",R.drawable.ic_progress_icon));
        tabList.get(1).setCustomView(getTabView("圈子",R.drawable.ic_join_icon));
        tabList.get(2).setCustomView(getTabView("论坛",R.drawable.ic_forum_icon));
        tabList.get(3).setCustomView(getTabView("我的",R.drawable.ic_user_icon));

        list.add("项目");
        list.add("圈子");
        list.add("论坛");
        list.add("我的");
        viewPager.setScanScroll(false);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

                name.setText(list.get(position));
                if(position==0)
                {
                    btnShowAdd.setVisibility(View.VISIBLE);
                    btnShowPeople.setVisibility(View.INVISIBLE);

                    btnShowAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent;
                            if(currentUser==null){
                                intent= new Intent(AtyMain.this, LoginAcitivity.class);
                                intent.putExtra("fromtype",2);
                            }else
                                intent= new Intent(AtyMain.this, CreateProgress.class);
                            startActivity(intent);
                        }
                    });

                }else if(position==1){
                    btnShowAdd.setVisibility(View.INVISIBLE);
                    btnShowPeople.setVisibility(View.VISIBLE);
                }else if(position==2){
                    btnShowAdd.setVisibility(View.VISIBLE);
                    btnShowPeople.setVisibility(View.INVISIBLE);

                    btnShowAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent;
                            if(currentUser==null){
                                intent= new Intent(AtyMain.this, LoginAcitivity.class);
                                intent.putExtra("fromtype",2);
                            }else
                             intent= new Intent(AtyMain.this, CreateHotTopic.class);
                            startActivity(intent);
                        }
                    });
                }
                    else{
                    btnShowAdd.setVisibility(View.INVISIBLE);
                    btnShowPeople.setVisibility(View.INVISIBLE);
                }

                tabList.get(position).getCustomView() .post(new Runnable() {

                    @Override
                    public void run() {
                        float pivotX =  tabList.get(position).getCustomView().getWidth() / 2;
                        float pivotY =  tabList.get(position).getCustomView().getHeight() / 2;
                        Animation animation = new RotateAnimation(0, 360, pivotX, pivotY);
                        //设置动画持续时间
                        animation.setDuration(1000);
                        //通过View的startAnimation方法将动画立即应用到View上
                        tabList.get(position).getCustomView().startAnimation(animation);
                    }
                });

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public View getTabView(String strNmae,int position){
        //首先为子tab布置一个布局
        View v = LayoutInflater.from(this).inflate(R.layout.item_tab,null);
        TextView tv = (TextView) v.findViewById(R.id.tab_Dial_Tv);
        ImageView iv = (ImageView) v.findViewById(R.id.tab_Dial_Img);
        tv.setText(strNmae);
        iv.setImageResource(position);
        return v;
    }

    private void connect(String token) {
        //test
        /**
         * IMKit SDK调用第二步
         *
         * 建立与服务器的连接
         *
         */

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
                RegetToken();
            }

            @Override
            public void onSuccess(String userId) {
                Log.d(TAG, "——onSuccess—-" + userId);
                FragmentConvercationList.getInstance().getFragment().onRestoreUI();
//                RongIM.getInstance().refreshGroupInfoCache();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

                Log.d(TAG, "——onError—-" + errorCode);
            }
        });
    }


    private void  RegetToken(){

        Map<String,String> params=new HashMap<>();
        params.put("user_id",currentUser.getUserId());
        params.put("user_name",currentUser.getUserName());
        params.put("user_avatar",currentUser.getUserAvatar());

        CreateParam request=new CreateParam(UriConfig.UserServlet,RequestAction_Get_New_RongYun_Token,params);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {

            }

            @Override
            public void onSuccess(String json) {
                RongyunToken token= GsonUtils.jsonToModule(json,RongyunToken.class);
                connect(token.getToken());

            }
        });
        request.request(request.getUrl());
    }

    @Override
    public void onResume() {
        super.onResume();
//        // 初始化当前用户
        if(currentUser!=null){
            ChatConfig.USER_ID=currentUser.getUserId();
            ChatConfig.USER_NAME=currentUser.getUserName();
            ChatConfig.USER_AVATAR=currentUser.getUserAvatar();
            ChatConfig.USER_TOKEN=currentUser.getUserToken();
            Log.d(TAG,RongIM.getInstance().getCurrentConnectionStatus().getValue()+" ");
            if(RongIM.getInstance().getCurrentConnectionStatus().getValue()!=0)
            connect(ChatConfig.USER_TOKEN);
        }else{
            Log.d(TAG,RongIM.getInstance().getCurrentConnectionStatus().getValue()+" ");
            if(RongIM.getInstance().getCurrentConnectionStatus().getValue()==0)
            RongIM.getInstance().logout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(RongIM.getInstance().getCurrentConnectionStatus().getValue()==0)
            RongIM.getInstance().logout();
    }


    // 退出程序提醒
//

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showTips();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showTips() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("退出程序").setMessage("是否退出程序")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create();

        alertDialog.show();
    }
}
