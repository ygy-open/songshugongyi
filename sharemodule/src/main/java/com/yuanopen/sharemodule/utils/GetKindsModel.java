package com.yuanopen.sharemodule.utils;

import com.yuanopen.commenmodule.utils.getCurrentTime;

import bean.Collect;
import bean.Comment;
import bean.Like;
import bean.Share;

/**
 * Created by yuanopen on 2018/7/19/019.
 */

public class GetKindsModel {

    String model_id;
    String user_id;

    public GetKindsModel(String model_id, String user_id) {
        this.model_id = model_id;
        this.user_id = user_id;
    }

    private    Share mShare;
    private  Like mLike;
    private  Comment mComment;
    private  Collect mLollect;

    // mShare
  public    Share  getShare(String platform){
      if(mShare==null)
      {
          mShare=new Share();
          mShare.setUser_id(user_id);
          mShare.setModel_id(model_id);

      }
      mShare.setShare_platform(platform);
      mShare.setShare_time(getCurrentTime.getTime());
      return mShare;
  }

    // mLike
    public    Like  getLike(){
        if(mLike==null)
        {
            mLike=new Like();
            mLike.setUser_id(user_id);
            mLike.setModel_id(model_id);

        }
        mLike.setLike_time(getCurrentTime.getTime());
        return mLike;
    }

    // mCollect
    public    Collect  getCollect(){
        if(mLollect==null)
        {
            mLollect=new Collect();
            mLollect.setUser_id(user_id);
            mLollect.setModel_id(model_id);

        }
        mLollect.setCollect_time(getCurrentTime.getTime());
        return mLollect;
    }

    // mCollect
    public    Comment  getComment(String content){
        if(mComment==null)
        {
            mComment=new Comment();
            mComment.setUser_id(user_id);
            mComment.setModel_id(model_id);
        }
        mComment.setContent(content);
        mComment.setComment_time(getCurrentTime.getTime());
        return mComment;
    }

}
