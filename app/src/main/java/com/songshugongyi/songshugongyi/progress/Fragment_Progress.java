package com.songshugongyi.songshugongyi.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

public class Fragment_Progress extends Fragment {

    private String TAG="Fragment_Progress";
    private BaseTabLayout baseTabLayout;
    private List<Fragment> list_fragment;
    List<String>list_name;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        findView(view);
        init(view);

        return view;
    }

    private void findView(View v) {
        viewPager=v.findViewById(R.id.progress_viewpager);
        tabLayout=v.findViewById(R.id.tl_fragment);
    }

    private void init(View view) {

        list_fragment=new ArrayList<>();
        Progress_Frag_Entry item1=new Progress_Frag_Entry();
        Progress_Frag_Complete item2= new Progress_Frag_Complete();

        list_fragment.add(item1);
        list_fragment.add(item2);

        //页面切换名称
        list_name=new ArrayList<>();
        list_name.add("报名");
        list_name.add("完成");

        baseTabLayout=new BaseTabLayout(list_fragment,this,view,list_name,viewPager,tabLayout);

        baseTabLayout.init();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"---->onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"---->onResume");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG,"---->onViewStateRestored");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"---->onSaveInstanceState");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"---->onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"---->onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"---->onDestroy");
    }
}
