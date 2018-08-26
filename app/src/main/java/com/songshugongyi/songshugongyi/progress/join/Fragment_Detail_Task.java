package com.songshugongyi.songshugongyi.progress.join;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.Task;
import com.songshugongyi.songshugongyi.databinding.FragmentDetailProgressTasksBinding;

/**
 * Created by yuanopen on 2018/5/18/018.
 */

public class Fragment_Detail_Task extends Fragment {

   private Task task;
    FragmentDetailProgressTasksBinding tasksBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        tasksBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_detail_progress_tasks,container,false);
        init();
        return tasksBinding.getRoot();
    }

    private void init() {
        tasksBinding.taskPeoples.setText(task.getTask_people()+" ");
        tasksBinding.taskPrinciple.setText(task.getTask_current_people()+" ");
        tasksBinding.taskInstroduction.setText(task.getTask_introduction());
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
