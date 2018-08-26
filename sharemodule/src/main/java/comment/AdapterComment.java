package comment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuanopen.sharemodule.R;

import java.util.List;

import bean.CommentWitnUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class AdapterComment extends BaseAdapter {

    List<CommentWitnUser>commentWitnUserList;
    Context mContext;

    public AdapterComment(List<CommentWitnUser> commentWitnUserList, Context mContext) {
        this.commentWitnUserList = commentWitnUserList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return commentWitnUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentWitnUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1=View.inflate(mContext, R.layout.item_comment,null);
        CircleImageView userAvatar=view1.findViewById(R.id.user_avatar);
        TextView userName=view1.findViewById(R.id.user_name);
        TextView content=view1.findViewById(R.id.content);

        showIavatar(commentWitnUserList.get(i).getUser_avatar(),userAvatar);
        userName.setText(" "+commentWitnUserList.get(i).getUser_name());
        content.setText(""+commentWitnUserList.get(i).getContent());
        return view1;
    }

    public void showIavatar(String uri, ImageView iavatar){

        if (uri!=null){
            DisplayImageOptions options = new DisplayImageOptions.Builder()//
                    .showImageOnLoading(R.drawable.user) // 加载中显示的默认图片
                    .showImageOnFail(R.drawable.user) // 设置加载失败的默认图片
                    .cacheInMemory(true) // 内存缓存
                    .cacheOnDisk(true) // sdcard缓存
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
                    .build();//
            ImageLoader.getInstance().displayImage(uri,
                    iavatar , options);
        }else
            iavatar.setImageResource(R.drawable.user);

    }
}
