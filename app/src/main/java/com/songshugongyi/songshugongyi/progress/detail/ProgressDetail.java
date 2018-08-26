package com.songshugongyi.songshugongyi.progress.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.transition.Slide;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.base.BaseDetailActivity;
import com.songshugongyi.songshugongyi.bean.Progress;
import com.songshugongyi.songshugongyi.bean.ProgressImage;
import com.songshugongyi.songshugongyi.databinding.ActivityProgressDetailBinding;
import com.songshugongyi.songshugongyi.progress.create.task.AdapterTask;
import com.songshugongyi.songshugongyi.progress.join.ViewPagerTasks;
import com.songshugongyi.songshugongyi.util.ImageUtils;
import com.songshugongyi.songshugongyi.util.ModelInitPresenter;
import com.songshugongyi.songshugongyi.util.image.ItemEntity;
import com.songshugongyi.songshugongyi.util.image.ListItemAdapter;
import com.yuanopen.commenmodule.UriConfig;

import java.util.ArrayList;
import java.util.List;

import bean.CommentWitnUser;
import comment.AdapterComment;

import static com.yuanopen.commenmodule.utils.Config.ImageHost;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Collect_Progress;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Comment_Progress;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Progress_Comments;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Progress_Detail;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Proogress_Status;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Like_Progress;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Share_Progress;

/**
 * Created by yuanopen on 2018/5/12/012.
 */

public class ProgressDetail extends BaseDetailActivity implements View.OnClickListener{

   private ActivityProgressDetailBinding mBinding;
    private Progress progress;
    private ImageUtils imageUtils;
    private AdapterTask taskAdapter;
     ArrayList<ItemEntity> imgList;
    ListItemAdapter listItemAdapter;
      // 评论适配器
   AdapterComment adapterComment;
    List<CommentWitnUser> commentWitnUsers;
    //
   private Handler handler;

    ModelInitPresenter modelInitPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setEnterTransition(new Slide().setDuration(1000));
//        getWindow().setExitTransition(new Slide().setDuration(1500));
        this.TAG="ProgressDetail";
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_progress_detail);

        // 设置状态栏
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_tab));
        // 接收传过来的数据
        progress= (Progress) getIntent().getSerializableExtra("progress");
        // 初始化
        init();
    }

    private void init() {
        imageUtils=new ImageUtils(this);
        imgList=new ArrayList<>();
        //加载项目图片
        imageUtils.showProgressImage((ImageHost+progress.getImages().get(0).getImage_url()+".jpg").trim(),mBinding.progressMainCover);

        mBinding.tvProgressName.setText(progress.getProgress_name());
        mBinding.tvProgressIntroduction.setText(progress.getProgress_introduction());
        mBinding.tvProgressTime.setText(progress.getProgress_start_time()+"--"+progress.getProgress_end_time());
        taskAdapter=new AdapterTask(ProgressDetail.this,progress.getTasks());
        mBinding.progressTasks.setAdapter(taskAdapter);
       //获取图片uri图片
        getImages();

        listItemAdapter=new ListItemAdapter(this,imgList);
        mBinding.progressImages.setAdapter(listItemAdapter);

        commentWitnUsers=new ArrayList<>();

        adapterComment=new AdapterComment(commentWitnUsers,ProgressDetail.this);
        mBinding.lvProgressContents.setAdapter(adapterComment);

        modelInitPresenter=new ModelInitPresenter(this,this,commentWitnUsers,adapterComment,progress.getProgress_id(), UriConfig.ProgressServlet);

        initBase();
        // 初始化项目的评论，点赞，收藏，分享数
        handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                initComments();
                initCount();
                initStatus();
            }
        });


        mBinding.progressShare.setOnClickListener(this);
        mBinding.progressCollect.setOnClickListener(this);
        mBinding.progressLike.setOnClickListener(this);
        mBinding.tvProgressItemOther.setOnClickListener(this);
        mBinding.btnProgrssMoreComment.setOnClickListener(this);
        mBinding.progressComment.setOnClickListener(this);


    }

    private void initBase() {
        this.btnShare=mBinding.progressShare;
        this.btnCommnet=mBinding.progressComment;
        this.btnLike=mBinding.progressLike;
        this.btnCollect =mBinding.progressCollect;

        this.tvShare=mBinding.tvShareCounts;
        this.tvCommnet=mBinding.tvCommentCounts;
        this.tvLike=mBinding.tvLikeCounts;
        this.tvCollect=mBinding.tvCollectCounts;
    }

    private void initComments() {
     modelInitPresenter.initComments(RequestAction_GetList_Progress_Comments);
//        mBinding.btnProgrssMoreComment.setText("共"+progressComments.size()+"条评论");
    }

    // 初始化项目的评论，点赞，收藏，分享数
    private void initCount() {
        modelInitPresenter.initCount(RequestAction_GetList_Progress_Detail);

    }

    // 初始化项目的评论，点赞，收藏，分享的状态，是否已经点过
    private void initStatus(){
       modelInitPresenter.initStatus(RequestAction_GetList_Proogress_Status);
    }

    public void getImages(){
        ArrayList<String> listuri=new ArrayList<>();
        for (ProgressImage image:progress.getImages()
             ) {
            listuri.add((ImageHost+image.getImage_url()+".jpg").trim());
        }
        imgList.add(new ItemEntity(listuri));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // 点击评论
            case R.id.progress_comment:
                modelInitPresenter.ClickComment(RequestAction_Comment_Progress);
            break;
            case R.id.progress_share:
                 modelInitPresenter.ClickShare(RequestAction_Share_Progress);
                break;
            // 点击点赞
            case R.id.progress_like:
                modelInitPresenter.ClickLike(RequestAction_Like_Progress);
                break;
            // 点击收藏
            case R.id.progress_collect:
                modelInitPresenter.ClickCollect(RequestAction_Collect_Progress);
                break;
            // 跳到项目任务详情页
            case R.id.tv_progress_item_other:
                Intent intent=new Intent(ProgressDetail.this, ViewPagerTasks.class);
                intent.putExtra("progress",progress);
                startActivity(intent);
                break;
            // 跳到项目更多评论
            case R.id.btn_progrss_more_comment:

                break;

        }
    }

}
