package com.songshugongyi.songshugongyi.progress.create.task;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.Task;

import java.util.List;

/**
 * Created by yuanopen on 2018/5/9/009.
 */

public class AdapterTask extends BaseAdapter {
    Context context;
    List<Task>tasks;

    public AdapterTask(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1=View.inflate(context, R.layout.item_task,null);
        TextView tv_task_num=view1.findViewById(R.id.tv_task_num);
        TextView tv_task_name=view1.findViewById(R.id.tv_task_name);
        TextView tv_task_people=view1.findViewById(R.id.tv_task_people);

        tv_task_num.setText("任务"+(i+1));
        tv_task_name.setText(" "+tasks.get(i).getTask_name());
        tv_task_people.setText(" "+tasks.get(i).getTask_people());

        return view1;
    }
}
