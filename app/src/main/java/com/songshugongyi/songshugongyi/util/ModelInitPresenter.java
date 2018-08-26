package com.songshugongyi.songshugongyi.util;

import android.content.Context;
import android.util.Log;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.base.BaseDetailActivity;
import com.yuanopen.commenmodule.http.OnSendListener;
import com.yuanopen.sharemodule.utils.GetKindsModel;
import com.yuanopen.sharemodule.utils.GetModelStatus;
import com.yuanopen.sharemodule.utils.GetProgressDetail;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Collect;
import bean.Comment;
import bean.CommentWitnUser;
import bean.Like;
import bean.ModelDetailCount;
import bean.ModelStatus;
import bean.Share;
import collect.CollectUtils;
import comment.AdapterComment;
import comment.CommentDialog;
import comment.CommentUtils;
import like.LikeUtils;
import share.ShareUtils;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.yuanopen.commenmodule.ParamsConfig.RequestParamKey_Model_Id;
import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/7/20/020.
 */

public class ModelInitPresenter {



    BaseDetailActivity baseDetailActivity;
    Context context;
    List<CommentWitnUser>commentWitnUsers ;
    AdapterComment adapterComment;
     // model_id
    String modle_id;
    // 用户获取评论，点赞，收藏，分享不同对象的
    GetKindsModel getKindsModel;
    //记录热点评论，点赞，收藏，分享数
    ModelDetailCount modelDetailCount;
    // 记录评论，点赞，收藏，分享的状态
    ModelStatus status ;

    String user_id="null";
    String host;

    public ModelInitPresenter(BaseDetailActivity baseDetailActivity,Context context, List<CommentWitnUser> commentWitnUsers,
                              AdapterComment adapterComment, String modle_id ,String host) {
        this.baseDetailActivity=baseDetailActivity;
        this.context =context;
        this.commentWitnUsers = commentWitnUsers;
        this.adapterComment = adapterComment;
        this.modle_id = modle_id;
        this.host=host;
        this.modelDetailCount = new ModelDetailCount();
        this.status=new ModelStatus();
        if(currentUser==null)
            getKindsModel=new GetKindsModel(modle_id,"null");
            else{
            getKindsModel=new GetKindsModel(modle_id, currentUser.getUserId());
            user_id=currentUser.getUserId();
        }



    }


    // 初始化评论
    public void initComments(String anction) {

        Subscriber<List<CommentWitnUser>> subscriber=new Subscriber<List<CommentWitnUser>>() {

            @Override
            public void onSubscribe(Subscription s) {
            }
            @Override
            public void onNext(List<CommentWitnUser> temp) {
                Log.d(TAG,"初始化评论数据成功"+commentWitnUsers.size()+"条");
                commentWitnUsers.clear();
                commentWitnUsers.addAll(temp);
                adapterComment.notifyDataSetChanged();
                Log.d(TAG,"初始化评论数据成功"+adapterComment.getCount());
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG,"初始化评论数据失败");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"初始化评论数据完成");
            }
        };

        Map<String ,String> params=new HashMap<>();
        params.put(RequestParamKey_Model_Id,modle_id);

        CommentUtils<List<CommentWitnUser>> listCommentUtils=new CommentUtils<>(context);
        listCommentUtils.GetComentFormService(host,anction,subscriber,params);

    }

    // 初始化项目的评论，点赞，收藏，分享的状态，是否已经点过
    public void initStatus(String action){
        Subscriber<ModelStatus> subscriber=new Subscriber<ModelStatus>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(ModelStatus status) {

                if(status!=null)
                {
                    Log.d(TAG,"初始化Counts数据成功");
                    if(status.isShare())
                        baseDetailActivity.btnShare.setBackground(context.getDrawable(R.drawable.ic_shared));

                    if(status.isComment())
                        baseDetailActivity.btnCommnet.setBackground(context.getDrawable(R.drawable.ic_commented));

                    if(status.isLike())
                        baseDetailActivity.btnLike.setBackground(context.getDrawable(R.drawable.ic_liked));

                    if(status.isCollect())
                        baseDetailActivity.btnCollect.setBackground(context.getDrawable(R.drawable.ic_collectted));

                }
                else
                    ShowToast.showToast(context,"初始化出现问题");
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG,"初始化Counts数据失败");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"初始化Counts数据完成");
            }
        };

        GetModelStatus.getModelStatus(host,action,modle_id,user_id,subscriber);
    }

    // 初始化项目的评论，点赞，收藏，分享数
    public void initCount(String action) {

        Subscriber<ModelDetailCount> subscriber=new Subscriber<ModelDetailCount>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(ModelDetailCount progressDetailCount) {

                if(progressDetailCount!=null)
                {
                    modelDetailCount=progressDetailCount;
                    Log.d(TAG,"初始化Counts数据成功");
                    baseDetailActivity.tvShare.setText( "("+progressDetailCount.getShare_counts()+")");
                    baseDetailActivity.tvCommnet.setText( "("+progressDetailCount.getComment_counts()+")");
                    baseDetailActivity.tvLike.setText("("+progressDetailCount.getLike_counts()+")");
                    baseDetailActivity.tvCollect.setText("("+progressDetailCount.getCollect_counts()+")");   }
                else
                    ShowToast.showToast(context,"初始化出现问题");
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG,"初始化Counts数据失败");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"初始化Counts数据完成");
            }
        };

        GetProgressDetail.getDetailCounts(host,action,modle_id,subscriber);

    }

    // 点击评论的时间
    public void ClickComment(String action) {
        if(currentUser==null)
           return;
        else{
            CommentDialog.showCommentDialog(context,host, action,
                    getKindsModel.getComment(" "), new OnSendListener<Comment>() {
                        @Override
                        public void onFail(String msdg) {

                        }

                        @Override
                        public void onSuccess(Comment comment) {
                            CommentWitnUser witnUser=new CommentWitnUser();
                            witnUser.setUser_id(currentUser.getUserId());
                            witnUser.setContent(comment.getContent());
                            witnUser.setUser_avatar(currentUser.getUserAvatar());
                            witnUser.setUser_name(currentUser.getUserName());
                            commentWitnUsers.add(witnUser);
                            adapterComment.notifyDataSetChanged();

                            baseDetailActivity.tvCollect.setText( "("+(modelDetailCount.getComment_counts()+1)+")");
                            baseDetailActivity.btnCommnet.setBackground(context.getDrawable(R.drawable.ic_commented));
                        }
                    });
        }
    }

    // 点击评论事件
    public void ClickShare(String action){
        if(currentUser==null)
           return;
        else {
            ShareUtils.getIntance(context).showShare(host,action, getKindsModel.getShare(" "), new OnSendListener<Share>() {
                @Override
                public void onFail(String msdg) {

                }

                @Override
                public  void onSuccess(Share modle) {
                    baseDetailActivity.btnShare.setBackground(context.getDrawable(R.drawable.ic_shared));
                    baseDetailActivity.tvShare.setText( "("+(modelDetailCount.getShare_counts()+1)+")");
                }
            });
        }
    }

    // 点击评论事件
    public void ClickLike(String action){
        if(!status.isCollect()){
            if(currentUser==null)
               return;
            else{
                LikeUtils.getIntance(context).sendLikeToService(host,action, getKindsModel.getLike(), new OnSendListener<Like>() {
                    @Override
                    public void onFail(String msdg) {

                    }
                    @Override
                    public void onSuccess(Like modle) {
                        baseDetailActivity.btnLike.setBackground(context.getDrawable(R.drawable.ic_liked));
                        baseDetailActivity.tvLike.setText( "("+(modelDetailCount.getLike_counts()+1)+")");
                    }
                });
            }

        }

        else
            ShowToast.showToast(context,"已点赞过了");

    }

    // 点击评论事件
    public void ClickCollect(String action){
        if(!status.isCollect()){
            if(currentUser==null)
               return;
            else {
                CollectUtils.getIntance(context).sendCollectToService(host,action, getKindsModel.getCollect(), new OnSendListener<Collect>() {
                    @Override
                    public void onFail(String msdg) {

                    }

                    @Override
                    public void onSuccess(Collect modle) {
                        baseDetailActivity.tvCollect.setText( "("+(modelDetailCount.getCollect_counts()+1)+")");
                        baseDetailActivity.btnCollect.setBackground(context.getDrawable(R.drawable.ic_collectted));
                    }
                });
            }
        }

        else
            ShowToast.showToast(context,"已点赞过了");

    }

}
