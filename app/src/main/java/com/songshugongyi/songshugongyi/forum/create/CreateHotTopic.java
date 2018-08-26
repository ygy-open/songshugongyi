package com.songshugongyi.songshugongyi.forum.create;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.HotTopic;
import com.songshugongyi.songshugongyi.databinding.ActivityCreateHotTopicBinding;
import com.songshugongyi.songshugongyi.util.ImageAdapter;
import com.songshugongyi.songshugongyi.util.ImagesPathToBitMap;
import com.songshugongyi.songshugongyi.util.ShowToast;
import com.yuanopen.commenmodule.ParamsConfig;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.utils.Config;
import com.yuanopen.commenmodule.utils.getCurrentTime;
import com.yuanopen.commenmodule.utils.qiniu.UploadImageToQiniu;

import java.util.ArrayList;
import java.util.List;

import static com.yuanopen.commenmodule.utils.getUUID.getUUID;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class CreateHotTopic extends AppCompatActivity {

private String TAG="CreateHotTopic";
    //图片code
    private static final int REQUEST_CODE = 0x00000011;

   private ActivityCreateHotTopicBinding hotTopicBinding;

    private ImageAdapter mAdapter;
    private List<String> imagesPath;

    private HotTopic hotTopic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotTopicBinding = DataBindingUtil. setContentView(this,R.layout.activity_create_hot_topic);
        init();
    }

    private void init() {
        if(Config.currentUser==null){
//            Config.ToLogin(this);
            return;
        }

        hotTopic=new HotTopic();

        mAdapter=new ImageAdapter(this);
        imagesPath=new ArrayList<>();

//        //展现图片
        hotTopicBinding.rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        hotTopicBinding.rvImage.setAdapter(mAdapter);

    }

    public void sendToservice(View view) {
        hotTopic.setHot_topic_id(getUUID());
        hotTopic.setUser_id(Config.currentUser.getUserId());

        if("".equals(hotTopicBinding.content.getText().toString())){
            ShowToast.showToast(this,"还没有输入任何内容哦！");
            return;
        }else
            hotTopic.setContent(hotTopicBinding.content.getText().toString());


        hotTopic.setCreate_time(getCurrentTime.getTime());
        hotTopic.setUpdate_time(getCurrentTime.getTime());

         // 添加图片
        hotTopic.setImages(ImagesPathToBitMap.getImages(hotTopic.getHot_topic_id(),imagesPath));

        CreateParam request=new CreateParam(Config.Host, ParamsConfig.RequestAction_Create_Hot_Topic);

        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {
                Log.d(TAG,"发表热点失败->>"+msg);
                ShowToast.showToast(CreateHotTopic.this,"发表失败,请检查网络是否正常");
            }

            @Override
            public void onSuccess(String json) {
                // 上传图片到七牛云
                Log.d(TAG,"上传图片到七牛云");
                UploadImageToQiniu.uploadImagesToQiniu(CreateHotTopic.this,imagesPath,hotTopic.getImages());
                Log.d(TAG,"发表热点成功");
                HotTopic hotTopicTemp= GsonUtils.jsonToModule(json,HotTopic.class);
                Intent intent=new Intent(CreateHotTopic.this,ActivityCreateSuccess.class);
                startActivity(intent);
                finish();
            }
        });

        Log.d(TAG,"请求创建");
        request.PostRequest(UriConfig.Host,request.getPostUrl(hotTopic));

    }

    //点击添加照片
    public void addPicture(View view) {
        ImageSelectorUtils.openPhoto(CreateHotTopic.this, REQUEST_CODE, false, 12);
    }

    //选择图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片
        if (requestCode == REQUEST_CODE && data != null) {
            imagesPath = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            mAdapter.refresh((ArrayList<String>) imagesPath);

        }
    }
    public void Back(View view)
    {
        finish();
    }

}
