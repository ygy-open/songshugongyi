package com.songshugongyi.songshugongyi.progress.getList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.bean.Progress;
import com.songshugongyi.songshugongyi.util.ImageUtils;

import java.util.List;

import static com.yuanopen.commenmodule.utils.Config.ImageHost;

/**
 * Created by yuanopen on 2018/5/10/010.
 */

public class AdapterProgress extends RecyclerView.Adapter<AdapterProgress.RecyclerHolder> implements View.OnClickListener{

    private ImageUtils imageUtils;
    Context context;
    List<Progress>progressList;

    public AdapterProgress(Context context, List<Progress> progressList) {
        this.context = context;
        this.progressList = progressList;
        imageUtils=new ImageUtils(context);
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_propress, parent, false);
        view.setOnClickListener(this);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        imageUtils.showProgressImage((ImageHost+progressList.get(position).getImages().get(0).getImage_url()+".jpg").trim(), holder. cover);
        holder.name.setText(progressList.get(position).getProgress_name());
        holder.introduction.setText(progressList.get(position).getProgress_introduction());
        holder.other.setText(progressList.get(position).getProgress_start_time()+"--"+progressList.get(position).getProgress_end_time());

        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return progressList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
       ImageView cover;
        TextView name;
        TextView introduction;
        TextView other;
        private RecyclerHolder(View view1) {
            super(view1);
             cover=view1.findViewById(R.id.progress_item_cover);
             name=view1.findViewById(R.id.tv_progress_item_name);
            introduction=view1.findViewById(R.id.tv_progress_item_introduction);
            other =view1.findViewById(R.id.tv_progress_item_other);
        }
    }

    private OnItemClickListener mItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
