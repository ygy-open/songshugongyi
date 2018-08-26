package com.songshugongyi.songshugongyi.progress.create;

import com.songshugongyi.songshugongyi.bean.Progress;
import com.songshugongyi.songshugongyi.bean.ProgressImage;
import com.songshugongyi.songshugongyi.bean.Task;

import java.util.List;

/**
 * Created by yuanopen on 2018/5/9/009.
 */

public interface  CreateViewUtill {
    abstract Progress getProgress();
    abstract List<Task> getTasks();
    abstract List<ProgressImage> getProgressImageS();
    abstract void AddTask();
    abstract void addTaskToList(Task t);
    abstract void showTaskList();

}
