package com.songshugongyi.songshugongyi.util;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.activity.AdapterMainViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanopen on 2018/5/4/004.
 */

public class BaseTabLayout  {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AdapterMainViewPager adapterMainViewPager;
    private List<TabLayout.Tab> tabList;

    private List<Fragment> fragment_items;
    private List<String>list_items_name;
    private Fragment fragment;
    private View view;

    public BaseTabLayout( List<Fragment> fragment_items,Fragment fragment, View view,List<String>list_items_name,ViewPager viewPager,TabLayout tabLayout) {

        this.fragment_items = fragment_items;
        this.fragment=fragment;
        this.list_items_name=list_items_name;
        this.view= view;
        this.   viewPager=viewPager;
        this. tabLayout=tabLayout;
        adapterMainViewPager=new AdapterMainViewPager(fragment.getFragmentManager());

        tabList=new ArrayList<>();

    }

    public void init(){

       tabLayout.setTabTextColors(R.color.menu_item_background,R.color.themeColor);
                //添加
        for (Fragment f:fragment_items
             ) {
            adapterMainViewPager.addFragment(f);
        }

        viewPager.setAdapter(adapterMainViewPager);
        viewPager.setOffscreenPageLimit(fragment_items.size());
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < fragment_items.size(); i++) {
            tabList.add(tabLayout.getTabAt(i));
            tabList.get(i).setText(list_items_name.get(i));

        }
        //缓存
        viewPager.setOffscreenPageLimit(tabList.size());
        tabLayout.setSelectedTabIndicatorColor(view.getResources().getColor(R.color.colorPrimary));
    }

}
