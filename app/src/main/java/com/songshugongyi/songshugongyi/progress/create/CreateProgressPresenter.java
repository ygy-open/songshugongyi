package com.songshugongyi.songshugongyi.progress.create;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.ProgressImage;
import com.songshugongyi.songshugongyi.bean.Task;
import com.wang.avi.AVLoadingIndicatorView;
import com.yuanopen.chatmudule.bean.UserGroup;
import com.yuanopen.commenmodule.UriConfig;
import com.yuanopen.commenmodule.http.BaseRequest;
import com.yuanopen.commenmodule.http.CreateParam;
import com.yuanopen.commenmodule.utils.getCurrentTime;
import com.yuanopen.commenmodule.utils.qiniu.QnUploadHelper;

import java.util.ArrayList;
import java.util.List;

import static com.songshugongyi.songshugongyi.util.ShowToast.showToast;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Create_Group;
import static com.yuanopen.commenmodule.ParamsConfig.RequestAction_Greate_Progress;
import static com.yuanopen.commenmodule.utils.Config.ImageHost;
import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static com.yuanopen.commenmodule.utils.getUUID.getUUID;

/**
 * Created by yuanopen on 2018/5/9/009.
 */

public class CreateProgressPresenter {
  CreateProgress activity_create_progress;

    public CreateProgressPresenter(CreateProgress activity_create_progress) {
        this.activity_create_progress = activity_create_progress;
    }
//任务选择框
    public AlertDialog initDialog(){
        final AlertDialog.Builder buidler=new AlertDialog.Builder(activity_create_progress,AlertDialog.THEME_HOLO_LIGHT);
        View view = View
                .inflate(activity_create_progress, R.layout.dialog_add_tasks, null);
        buidler.setView(view);
        final EditText name=view.findViewById(R.id.input_task_name);
        final EditText people=view.findViewById(R.id.input_task_people);
        final EditText introduction=view.findViewById(R.id.input_task_introduction);
        Button sure=view.findViewById(R.id.add_task_sure);
        Button cancel=view.findViewById(R.id.add_task_cancel);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_str=name.getText().toString();
                String people_str=people.getText().toString();
                String introduction_str=introduction.getText().toString();
                if(isEmpty(name_str,people_str,introduction_str)){
                    Task t=new Task();
                    t.setTask_id(getUUID());

                    t.setTask_name(name_str);
                    name.setText("");

                    t.setTask_people(Integer.parseInt(people_str));
                    people.setText("");

                    t.setTask_introduction(introduction_str);
                    introduction.setText("");

                    t.setCreate_time(getCurrentTime.getTime());
                    t.setUpdate_time(getCurrentTime.getTime());
                    t.setProgress_id(activity_create_progress.getProgress_id());
                    activity_create_progress.addTaskToList(t);

                    showToast(activity_create_progress,"添加成功！");
                    activity_create_progress.showTaskList();
                    activity_create_progress.dialog.cancel();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_create_progress.dialog.cancel();
            }
        });

        return buidler.create();
    }

//判断内容是否为空
    private boolean isEmpty(String name,String people,String introduction){
        if("".equals(name))
        {
            showToast(activity_create_progress,R.string.task_name+"不能为空！");
            return false;
        }else if("".equals(people)){
            showToast(activity_create_progress,R.string.task_people+"不能为空！");
            return false;
        }else if("".equals(introduction)){
            showToast(activity_create_progress,"项目详情不能为空！");
            return false;
        }
        return true;
    }

//选择开始时间和结束时间
    public void SelectData(final TextView editText){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_create_progress);

        View dialogView = View.inflate(activity_create_progress, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int y=datePicker.getYear();
                int m=datePicker.getMonth()+1;
                int d=datePicker.getDayOfMonth();
                editText.setText(" "+y+"-"+m+"-"+d);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
    }

public void Submit(){
    showWait();
    //先提交照片
    activity_create_progress. addImagesToList(getImages());
           uploadImagesToQiniu(activity_create_progress.getProgressImageS());

    CreateParam request=new CreateParam(RequestAction_Greate_Progress);

         request.setOnResultListener(new BaseRequest.OnResultListener() {
             @Override
             public void onFail(int code, String msg) {
                 showToast(activity_create_progress,"项目创建失败！"+msg);
             }

             @Override
             public void onSuccess(String json) {

                 showToast(activity_create_progress,"项目创建成功！");
                 createGroupToService();
                 activity_create_progress.finish();
             }
         });

    //请求服务器

    Log.i("CreateActivity","请求服务器");
    request.PostRequest(UriConfig.ProgressServlet,request.getPostUrl(activity_create_progress.getProgress()));

}

    private void createGroupToService() {
        //转化成json根式
        UserGroup group=new UserGroup();
        group.setGroup_avatar(ImageHost+activity_create_progress.getProgress().getImages().get(0).getImage_url()+".jpg");
        group.setGroup_id(activity_create_progress.getProgress_id());
        group.setGroup_name(activity_create_progress.getProgress().getProgress_name());
        group.setUser_id(currentUser.getUserId());
        group.setGroup_signature(activity_create_progress.getProgress().getProgress_name());

        //向服务发送请求
        CreateParam request=new CreateParam(RequestAction_Create_Group);

        request.setOnResultListener(new BaseRequest.OnResultListener() {
            @Override
            public void onFail(int code, String msg) {

                showToast(activity_create_progress,"创建失败！"+msg);
            }

            @Override
            public void onSuccess(String json) {

            }
        });

        request.PostRequest(UriConfig.UserServlet,request.getPostUrl(group));
        //请求服务器
        Log.i("CreateGroupActivity","请求服务器");

    }


    private void uploadImagesToQiniu(List<ProgressImage> images) {
        int i;
        for ( i= 0; i < images.size(); i++) {
            final int finalI = i;
            QnUploadHelper.uploadPicWithProgress(activity_create_progress.getImagesPath().get(i),
                    images.get(i).getImage_url(), new QnUploadHelper.UploadCallBack() {
                        @Override
                        public void success(String url) {
                            showToast(activity_create_progress,"第"+(finalI +1)+"上传成功");
                        }

                        @Override
                        public void fail(String key, ResponseInfo info) {
                            showToast(activity_create_progress,"第"+(finalI +1)+"上传失败");
                        }

                        @Override
                        public void onProgress(double time) {
                        }
                    });

        }

    }

    //将Image转化为BitMap
    public List<ProgressImage> getImages(){
        List<ProgressImage> images=new ArrayList<>();
        for (String path:activity_create_progress.getImagesPath()
             ) {
            ProgressImage image=new ProgressImage();
            image.setImage_id(getUUID());
            //用户项目id和图片id作为图片名称
            image.setImage_url(activity_create_progress.getProgress_id()+"_"+image.getImage_id());
            image.setProgress_id(activity_create_progress.getProgress_id());
            image.setCreate_time(getCurrentTime.getTime());
            image.setUpdate_time(getCurrentTime.getTime());
            images.add(image);
        }

        return images;
    }


    //
    public  void showWait(){
        final AlertDialog.Builder buidler=new AlertDialog.Builder(activity_create_progress,AlertDialog.THEME_HOLO_LIGHT);
        View view = View
                .inflate(activity_create_progress, R.layout.dialog_wait_for_create_progress, null);
        buidler.setView(view);
        final TextView title=view.findViewById(R.id.create_propress_title);
        title.setText("项目发布中。。。");
        final AVLoadingIndicatorView wait=view.findViewById(R.id.wait);
         buidler.setCancelable(false);
        activity_create_progress.waitdialog= buidler.create();

        activity_create_progress.waitdialog.show();
    }
}
