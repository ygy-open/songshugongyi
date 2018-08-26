package com.songshugongyi.songshugongyi.join;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.util.BaseTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanopen on 2018/5/4/004.
 */

public class fragment_join extends Fragment {

    private BaseTabLayout baseTabLayout;
    private List<Fragment> list_fragment;
    List<String>list_name;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_join, container, false);
        findView(view);
        init(view);
        return view;
    }
    private void findView(View v) {
        viewPager=v.findViewById(R.id.join_viewpager);
        tabLayout=v.findViewById(R.id.tl_fragment);
    }
    private void init(View view) {
        list_fragment=new ArrayList<>();
        forum_frag_joining item1= new forum_frag_joining();
        forum_frag_joined item2=new forum_frag_joined();

        list_fragment.add(item2);
        list_fragment.add(item1);

        //页面切换名称
        list_name=new ArrayList<>();
        list_name.add("正在参与");
        list_name.add("参与完成");

        baseTabLayout=new BaseTabLayout(list_fragment,this,view,list_name,viewPager,tabLayout);
        baseTabLayout.init();
    }

}
