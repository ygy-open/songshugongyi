package com.songshugongyi.songshugongyi.forum.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.base.BaseDetailActivity;
import com.songshugongyi.songshugongyi.bean.HotTopic;
import com.songshugongyi.songshugongyi.databinding.ActivityHotTopicDetailBinding;
import com.songshugongyi.songshugongyi.util.ImageUtils;
import com.songshugongyi.songshugongyi.util.ModelInitPresenter;
import com.songshugongyi.songshugongyi.util.image.ItemEntity;
import com.songshugongyi.songshugongyi.util.image.ListItemAdapter;
import com.songshugongyi.songshugongyi.widget.NoScrollGridView;
import com.yuanopen.commenmodule.UriConfig;

import java.util.ArrayList;
import java.util.List;

import bean.CommentWitnUser;
import comment.AdapterComment;

import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Collect_Hot_Topic;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Comment_Hot_Topic;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Hot_Topic_Comments;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Hot_Topic_Detail;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Hot_Topic_Status;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Like_Hot_Topic;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Share_Hot_Topic;
import static com.yuanopen.commenmodule.utils.Config.ImageHost;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class HotTopicDetail extends BaseDetailActivity implements View.OnClickListener{

    ActivityHotTopicDetailBinding hotTopicDetailBinding;
    HotTopic hotTopic;

    ArrayList<ItemEntity> imgList;
    ListItemAdapter listItemAdapter;

    // 用户标记是否显示关注
    int type=1;
    AdapterComment adapterComment;
    List<CommentWitnUser> commentWitnUsers;

    private Handler handler;

    ModelInitPresenter modelInitPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotTopicDetailBinding= DataBindingUtil.setContentView(this, R.layout.activity_hot_topic_detail);
        TAG="HotTopicDetail";
        // 设置状态栏
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_tab));
        // 接收传过来的数据
        hotTopic= (HotTopic) getIntent().getSerializableExtra("hot_topic");
        type=getIntent().getIntExtra("type",1);
        // 初始化
        init();
    }

    private void init() {
        ImageUtils.showIavatar(hotTopic.getUser().getUserAvatar(),hotTopicDetailBinding.userAvatar);
        hotTopicDetailBinding.userName.setText(hotTopic.getUser().getUserName());
        hotTopicDetailBinding.createTime.setText(hotTopic.getCreate_time());
        hotTopicDetailBinding.tvHotTopicContent.setText(hotTopic.getContent());

        getImages(hotTopicDetailBinding.hotTopicImages);
       if(type==1){
           if (hotTopic.getIs_attented())
               hotTopicDetailBinding.btnAttention.setText("已关注");
           else
               hotTopicDetailBinding.btnAttention.setText("+关注");
       }else
           hotTopicDetailBinding.btnAttention.setVisibility(View.GONE);

        hotTopicDetailBinding.hotTopicShare.setOnClickListener(this);
        hotTopicDetailBinding.hotTopicComment.setOnClickListener(this);
        hotTopicDetailBinding.hotTopicLike.setOnClickListener(this);
        hotTopicDetailBinding.hotTopicCollect.setOnClickListener(this);

        commentWitnUsers=new ArrayList<>();
        adapterComment=new AdapterComment(commentWitnUsers,HotTopicDetail.this);
        hotTopicDetailBinding.lvHotTopicContents.setAdapter(adapterComment);

        modelInitPresenter=new ModelInitPresenter(this,this,commentWitnUsers,adapterComment,hotTopic.getHot_topic_id(), UriConfig.HotTopicServlet);
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

    }
    private void initBase() {
        this.btnShare=hotTopicDetailBinding.hotTopicShare;
        this.btnCommnet=hotTopicDetailBinding.hotTopicComment;
        this.btnLike=hotTopicDetailBinding.hotTopicLike;
        this.btnCollect =hotTopicDetailBinding.hotTopicCollect;

        this.tvShare=hotTopicDetailBinding.tvShareCounts;
        this.tvCommnet=hotTopicDetailBinding.tvCommentCounts;
        this.tvLike=hotTopicDetailBinding.tvLikeCounts;
        this.tvCollect=hotTopicDetailBinding.tvCollectCounts;
    }

    // 初始化项目的评论，点赞，收藏，分享数
    private void initCount() {

                modelInitPresenter.initCount(RequestAction_GetList_Hot_Topic_Detail);

    }

    // 初始化项目的评论，点赞，收藏，分享的状态，是否已经点过
    private void initStatus(){

                    modelInitPresenter.initStatus(RequestAction_GetList_Hot_Topic_Status);
    }

    public void getImages(NoScrollGridView gridView){
        ArrayList<String> listUri=new ArrayList<>();
        imgList=new ArrayList<>();
        for (com.yuanopen.commenmodule.bean.Image image:hotTopic.getImages()
                ) {
            listUri.add((ImageHost+image.getImage_url()+".jpg").trim());
        }
        imgList.add(new ItemEntity(listUri));

        listItemAdapter=new ListItemAdapter(this,imgList);
        gridView.setAdapter(listItemAdapter);

    }

    // 初始化评论
    private void initComments() {
        modelInitPresenter.initComments(RequestAction_GetList_Hot_Topic_Comments);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // 点击评论
            case R.id.hot_topic_comment:
                modelInitPresenter. ClickComment(RequestAction_Comment_Hot_Topic);
                break;
            // 点击分享
            case R.id.hot_topic_share:
                modelInitPresenter.ClickShare(RequestAction_Share_Hot_Topic);
                break;
            // 点击点赞
            case R.id.hot_topic_like:
                modelInitPresenter.ClickLike(RequestAction_Like_Hot_Topic);
                break;
            // 点击收藏
            case R.id.hot_topic_collect:
                modelInitPresenter.ClickCollect(RequestAction_Collect_Hot_Topic);
                break;

        }
    }

}
