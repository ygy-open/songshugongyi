package com.songshugongyi.songshugongyi.progress.create;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.jaeger.library.StatusBarUtil;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.Progress;
import com.songshugongyi.songshugongyi.bean.ProgressImage;
import com.songshugongyi.songshugongyi.bean.Task;
import com.songshugongyi.songshugongyi.databinding.ActivityCreateProgressBinding;
import com.songshugongyi.songshugongyi.progress.create.task.AdapterTask;
import com.songshugongyi.songshugongyi.util.ImageAdapter;
import com.yuanopen.commenmodule.utils.getCurrentTime;

import java.util.ArrayList;
import java.util.List;

import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static com.songshugongyi.songshugongyi.util.ShowToast.showToast;
import static com.yuanopen.commenmodule.utils.getUUID.getUUID;

/**
 * Created by yuanopen on 2018/5/8/008.
 */

public class CreateProgress extends Activity implements CreateViewUtill{

    //图片code
    private static final int REQUEST_CODE = 0x00000011;
   ActivityCreateProgressBinding mBinding;
    private Progress progress;
    private  List<Task>tasks;
    private  List<ProgressImage>images;
    public AlertDialog dialog,waitdialog;
    CreateProgressPresenter presenter;
    private AdapterTask adapter;

    private ImageAdapter mAdapter;
    private List<String>imagesPath;

    private  String progress_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    private void init() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_create_progress);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_tab));

        progress =new Progress();
        tasks=new ArrayList<>();
        images=new ArrayList<>();
        presenter=new CreateProgressPresenter(this);

        dialog=presenter.initDialog();
        adapter=new AdapterTask(this,tasks);
        mBinding.listviewProgressesTask.setAdapter(adapter);
        //初始化项目id
        progress_id=getUUID();

        imagesPath=new ArrayList<>();
        //展现图片
        mBinding.rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        mBinding.rvImage.setAdapter(mAdapter);

    }

    @Override
    public Progress getProgress() {
        progress.setProgress_id(progress_id);
        progress.setProgress_name(mBinding.inputProgressName.getText().toString());
        progress.setProgress_introduction(mBinding.inputProgressIntroduction.getText().toString());
        progress.setProgress_current_people(0);
        progress.setProgress_start_time(mBinding.inputProgressStartTime.getText().toString());
        progress.setProgress_end_time(mBinding.inputProgressEndTime.getText().toString());
        progress.setProgress_name(mBinding.inputProgressName.getText().toString());
        progress.setProgress_user(currentUser);
        progress.setTasks(getTasks());
        progress.setImages(getProgressImageS());
        progress.setCreate_time(getCurrentTime.getTime());
        progress.setUpdate_time(getCurrentTime.getTime());
        progress.setProgress_type(1);
        return progress;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<ProgressImage> getProgressImageS() {
        return images;
    }

    public void addImagesToList(List<ProgressImage>imageList) {
        images.addAll(imageList);
    }

    @Override
    public void AddTask() {
        dialog.show();
    }

    public String getProgress_id() {
        return progress_id;
    }
    @Override
    public void addTaskToList(Task task) {
        tasks.add(task);
    }

    @Override
    public void showTaskList() {
        if(mBinding.listviewProgressesTask.getVisibility()==View.GONE)
            mBinding.listviewProgressesTask.setVisibility(View.VISIBLE);
           adapter.notifyDataSetChanged();
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

//点击添加照片
    public void addPicture(View v) {
        ImageSelectorUtils.openPhoto(CreateProgress.this, REQUEST_CODE, false, 12);
    }
 //点击添加任务
    public void addTask(View v){
        AddTask();
    }
    //添加开始时间
    public void addStartTime(View v){
       presenter.SelectData(mBinding.inputProgressStartTime);
    }
    //添加结束时间
    public void addEndTime(View v){
        presenter.SelectData(mBinding.inputProgressEndTime);
    }

    public List<String> getImagesPath() {
        return imagesPath;
    }

    public void Submit(View v){
        showToast(this,"点击提交");
        presenter.Submit();
    }
}
