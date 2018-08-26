package com.songshugongyi.songshugongyi.forum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.HotTopic;
import com.songshugongyi.songshugongyi.util.ShowToast;
import com.songshugongyi.songshugongyi.util.image.ItemEntity;
import com.songshugongyi.songshugongyi.util.image.ListItemAdapter;
import com.songshugongyi.songshugongyi.widget.NoScrollGridView;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.bean.Image;
import com.yuanopen.commenmodule.utils.Config;
import com.yuanopen.sharemodule.utils.GetProgressDetail;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import bean.ModelDetailCount;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.songshugongyi.songshugongyi.forum.SendAttentionToService.sendAttentionToService;
import static com.songshugongyi.songshugongyi.util.ImageUtils.showIavatar;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_GetList_Hot_Topic_Detail;
import static com.yuanopen.commenmodule.utils.Config.ImageHost;
import static com.yuanopen.commenmodule.utils.Config.currentUser;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class AdapterHotTopic extends RecyclerView.Adapter<AdapterHotTopic.RecyclerHolder> implements View.OnClickListener{
    private String TAG="AdapterHotTopic";
    Context context;
    List<HotTopic>hotTopicList;

    ArrayList<ItemEntity> imgList;
    ListItemAdapter listItemAdapter;
    int type=1;

    public AdapterHotTopic(Context context, List<HotTopic> hotTopicList,int type) {
        this.context = context;
        this.hotTopicList = hotTopicList;
        this.type=type;

    }

    @Override
    public AdapterHotTopic.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot_topic, parent, false);
        view.setOnClickListener(this);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterHotTopic.RecyclerHolder viewHolder, int position) {

        final HotTopic hotTopic=hotTopicList.get(position);
        Log.d(TAG,"数据请求"+hotTopicList.size());
        viewHolder.user_name.setText(hotTopic.getUser().getUserName());
        viewHolder.create_time.setText(hotTopic.getCreate_time());
        viewHolder.content.setText(hotTopic.getContent());
        showIavatar(hotTopicList.get(position).getUser().getUserAvatar(),viewHolder.user_avatar);

        if(type==1){
            if (hotTopic.getIs_attented())
                viewHolder.attention.setText("已关注");
            else
                viewHolder.attention.setText("+关注");
        }else
            viewHolder.attention.setVisibility(View.GONE);

        viewHolder.attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Config.currentUser!=null)
                {
                if(hotTopic.getIs_attented())
                    ShowToast.showToast(context,"你已关注");
                else{
                    sendAttentionToService(context,hotTopic.getUser_id(),currentUser.getUserId());
                    viewHolder.attention.setText("已关注");
                }
                }

            }
        });

        initCount(viewHolder,hotTopic.getHot_topic_id());
        getImages(hotTopic,viewHolder.scrollGridView);

       viewHolder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return hotTopicList.size();
    }
    /** 创建 ViewHolder */
    class RecyclerHolder extends RecyclerView.ViewHolder{
        CircleImageView user_avatar;
        TextView user_name;
        TextView create_time;
        Button attention;
        TextView content;
        NoScrollGridView scrollGridView;
        TextView tvShareCounts,tvCommentCounts,tvLikeCounts,tvCollectCounts;

        public RecyclerHolder(View view) {
            super(view);
            user_name = view.findViewById(R.id.user_name);
            user_avatar = view.findViewById(R.id.user_avatar);
            create_time=view.findViewById(R.id.create_time);
            content =  view.findViewById(R.id.tv_hot_topic_content);
            scrollGridView= view.findViewById(R.id.hot_topic_images);
            attention =  view.findViewById(R.id.btn_attention);
            tvShareCounts=view.findViewById(R.id.tv_share_counts);
            tvCommentCounts=view.findViewById(R.id.tv_comment_counts);
            tvLikeCounts=view.findViewById(R.id.tv_like_counts);
            tvCollectCounts=view.findViewById(R.id.tv_collect_counts);
        }
    }

    public void getImages(HotTopic hotTopic,NoScrollGridView gridView){
        ArrayList<String> listuri=new ArrayList<>();
        imgList=new ArrayList<>();
        for (Image image:hotTopic.getImages()
                ) {
            listuri.add((ImageHost+image.getImage_url()+".jpg").trim());
        }
        imgList.add(new ItemEntity(listuri));

        listItemAdapter=new ListItemAdapter(context,imgList);
        gridView.setAdapter(listItemAdapter);
    }


    private OnItemClickListener mItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    // 初始化项目的评论，点赞，收藏，分享数
    private void initCount(final AdapterHotTopic.RecyclerHolder viewHolder,String model_id) {

        Subscriber<ModelDetailCount> subscriber=new Subscriber<ModelDetailCount>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(ModelDetailCount progressDetailCount) {

                if(progressDetailCount!=null)
                {
                    Log.d(TAG,"初始化Counts数据成功");
                    viewHolder.tvShareCounts.setText( "("+progressDetailCount.getShare_counts()+")");
                    viewHolder.tvCommentCounts.setText("("+progressDetailCount.getComment_counts()+")");
                    viewHolder.tvLikeCounts.setText("("+progressDetailCount.getLike_counts()+")");
                    viewHolder.tvCollectCounts.setText( "("+progressDetailCount.getCollect_counts()+")");


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

        GetProgressDetail.getDetailCounts(UriConfig.HotTopicServlet,RequestAction_GetList_Hot_Topic_Detail,model_id,subscriber);

    }
}
