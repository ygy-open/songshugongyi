package com.songshugongyi.songshugongyi.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.songshugongyi.songshugongyi.R;

import java.util.ArrayList;

/**
 * Created by yuanopen on 2018/2/13/013.
 */

class NoScrollGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> imageUrls;
    public NoScrollGridAdapter(Context mContext, ArrayList<String> imageUrls) {
        this.context=mContext;
        this.imageUrls=imageUrls;
    }


    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return imageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_gridview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        // 使用ImageLoader加载网络图
        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .showImageOnLoading(R.drawable.progress_default) // 加载中显示的默认图片
                .showImageOnFail(R.drawable.progress_default) // 设置加载失败的默认图片
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .bitmapConfig(Bitmap.Config.RGB_565)//
                .build();

        ImageLoader.getInstance().displayImage(imageUrls.get(position),
                imageView, options);

        return view;
    }

}
