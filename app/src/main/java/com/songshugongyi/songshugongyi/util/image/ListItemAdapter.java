package com.songshugongyi.songshugongyi.util.image;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.songshugongyi.songshugongyi.R;

import java.util.ArrayList;

/**
 * Created by yuanopen on 2018/2/12/012.
 */

public class ListItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ItemEntity> items;

    public ListItemAdapter(Context ctx, ArrayList<ItemEntity> items) {
        this.mContext = ctx;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_list_gridview, null);

            holder.gridview = (GridView) convertView
                    .findViewById(R.id.gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTransitionName("image");
        ItemEntity itemEntity = items.get(position);

        final ArrayList<String> imageUrls = itemEntity.getImageUrls();

        if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
            holder.gridview.setVisibility(View.GONE);
        } else {
         holder.gridview.setAdapter(new NoScrollGridAdapter(mContext,
                   imageUrls));
        }

        // 点击回帖九宫格，查看大图
        holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                imageBrower(position, imageUrls);
            }
        });
        return convertView;
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        toshow(intent);
    }

   private void toshow(Intent intent){
       mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)mContext).toBundle());
   }

    /**
     * listview组件复用，防止“卡顿”
     *
     * @author Administrator
     *
     */
    class ViewHolder {
        private GridView gridview;
    }
}