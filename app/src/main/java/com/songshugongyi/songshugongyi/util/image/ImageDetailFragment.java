package com.songshugongyi.songshugongyi.util.image;

/**
 * Created by yuanopen on 2018/2/13/013.
 */

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.songshugongyi.songshugongyi.R;



/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ProgressBar progressBar;
    private PhotoView mAttacher;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url")
                : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_detail_fragment,
                container, false);
        mAttacher = (PhotoView) v.findViewById(R.id.photoView);
       // mAttacher.disenable();
       // mAttacher.isEnabled();
       // mAttacher.setEnabled(true);
        mAttacher.enable();

//        Info info = mAttacher.getInfo();
//// 从普通的 ImageView 中获取 Info
//      //  Info info = PhotoView.getImageViewInfo(mAttacher);
//// 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照 demo 的使用
//        mAttacher.animaFrom(info);
//// 从现在的图片变化到所给定的图片信息，用于图片放大后点击缩小到原来的位置，具体使用可以参照 demo 的使用
//        mAttacher.animaTo(info,new Runnable() {
//            @Override
//            public void run() {
//                //动画完成监听
//            }
//        });

 //      mAttacher.setAnimaDuring(1000);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mAttacher.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             getActivity().finish();
                                         }
                                     }
        );

        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageLoader.getInstance().displayImage(mImageUrl, mAttacher,
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        String message = null;
                        switch (failReason.getType()) {
                            case IO_ERROR:
                                message = "下载错误";
                                break;
                            case DECODING_ERROR:
                                message = "图片无法显示";
                                break;
                            case NETWORK_DENIED:
                                message = "网络有问题，无法下载";
                                break;
                            case OUT_OF_MEMORY:
                                message = "图片太大无法显示";
                                break;
                            case UNKNOWN:
                                message = "未知的错误";
                                break;
                        }
                        Toast.makeText(getActivity(), message,
                                Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
