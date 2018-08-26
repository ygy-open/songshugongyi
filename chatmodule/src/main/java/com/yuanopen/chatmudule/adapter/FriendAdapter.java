package com.yuanopen.chatmudule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuanopen.chatmudule.R;
import com.yuanopen.chatmudule.bean.UserFriend;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanopen.chatmudule.adapter.ShowAvatar.showIavatar;

/**
 * Created by yuanopen on 2018/7/24/024.
 */

public class FriendAdapter extends BaseAdapter
{
    private Context context;
    private List<UserFriend> list;
    private LayoutInflater layoutInflater;

    public FriendAdapter(Context context, List<UserFriend> list)
    {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return list.size();
    }
    public Object getItem(int position)
    {
        return list.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        View view;
        ViewHolder viewHolder;
        UserFriend friend = list.get(position);
        if(convertView == null)
        {
            view = layoutInflater.inflate(R.layout.item_group, null);
            viewHolder = new ViewHolder();
            viewHolder.iconImage = (CircleImageView) view.findViewById(R.id.iconImage);
            viewHolder.iconName = (TextView) view.findViewById(R.id.iconName);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        showIavatar(friend.getFriend_avatar(),viewHolder.iconImage);
        viewHolder.iconName.setText(friend.getFriend_name());

        return view;
    }
    private class ViewHolder
    {
        CircleImageView iconImage;
        TextView iconName;
    }
}
