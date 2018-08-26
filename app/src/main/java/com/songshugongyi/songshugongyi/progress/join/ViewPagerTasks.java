package com.songshugongyi.songshugongyi.progress.join;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.activity.AdapterMainViewPager;
import com.songshugongyi.songshugongyi.bean.Progress;
import com.songshugongyi.songshugongyi.bean.Task;
import com.songshugongyi.songshugongyi.widget.CustomViewPager;
import com.yuanopen.commenmodule.ParamsConfig;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.utils.ShowToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

import static com.yuanopen.commenmodule.utils.Config.currentUser;


/**
     *   Copyright  2018,yuanopen
     */

    public class ViewPagerTasks extends AppCompatActivity {
  private String TAG="ViewPagerTasks";

    private CustomViewPager viewPager;

    private TextView name;
    private List<Task>tasks;
    private Button btnJoin;
    Progress progress;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_progress_task);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_tab));

        initData();
        initView();

    }

    private void initData() {
        tasks=new ArrayList<>();
        progress= (Progress) getIntent().getSerializableExtra("progress");
        if(progress!=null)
            Log.d(TAG,"项目获取成功"+progress.getProgress_id());
        tasks=progress.getTasks();

    }

    private void initView(){

        viewPager=(CustomViewPager)findViewById(R.id.vp_tasks);
        name= (TextView) findViewById(R.id.fragment_item_name);
        name.setText(tasks.get(0).getTask_name());
        btnJoin= (Button) findViewById(R.id.btn_join);
        isJoin();

        final AdapterMainViewPager adapter=new AdapterMainViewPager(getSupportFragmentManager());

        for (int i = 0; i < tasks.size(); i++) {
            Fragment_Detail_Task item= new Fragment_Detail_Task();
            item.setTask(tasks.get(i));
            adapter.addFragment(item);
        }

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(tasks.size());


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
               name.setText(tasks.get(position).getTask_name());

                    btnJoin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnJoin.setClickable(false);
                            apply(tasks.get(position).getTask_id(),tasks.get(position).getProgress_id());
                        }
                    });
                }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    // 点击报名
    private void apply(String task_id,String progress_id){
        Map<String,String>param=new HashMap<>();
        param.put("task_id",task_id);
        param.put("user_id",currentUser.getUserId());
        param.put("progress_id",progress_id);
        CreateParam request = new CreateParam(UriConfig.ProgressServlet, ParamsConfig.RequestAction_Join_Progress_Task,param);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                btnJoin.setClickable(true);
                ShowToast.showToast(ViewPagerTasks.this,"报名失败，请稍后再试！");
            }

            @Override
            public void onSuccess(String json) {
                showToGroup();
            }
        });

        String url = request.getUrl();
        System.out.println(TAG+"--->"+url);
        request.request(url);
    }

    // 向服务器发送请求是否已经报名
    private void isJoin(){
        Map<String,String>param=new HashMap<>();
        param.put("user_id",currentUser.getUserId());
        param.put("progress_id",progress.getProgress_id());
        CreateParam request = new CreateParam(UriConfig.ProgressServlet, ParamsConfig.RequestAction_Is_Join_Progress,param);
        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                btnJoin.setClickable(true);

            }

            @Override
            public void onSuccess(String json) {
                btnJoin.setClickable(false);
                btnJoin.setText("已报名");
            }
        });

        String url = request.getUrl();
        System.out.println(TAG+"--->"+url);
        request.request(url);
    }

    private void showToGroup() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("报名成功").setMessage("已自动将你加入该项目群聊，现在就去认识一下大家吧！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RongIM.getInstance().startGroupChat(ViewPagerTasks.this, progress.getProgress_id(),progress.getProgress_name());
                    }
                }).create();

        alertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
