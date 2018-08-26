package com.songshugongyi.songshugongyi.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songshugongyi.songshugongyi.R;

/**
 * Created by yuanopen on 2018/5/4/004.
 */

public class forum_frag_rank extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_live_list, container, false);

        return view;
    }
}
