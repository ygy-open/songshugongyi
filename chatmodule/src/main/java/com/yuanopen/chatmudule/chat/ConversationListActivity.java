package com.yuanopen.chatmudule.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yuanopen.chatmudule.R;


/**
 * Created by yuanopen on 2018/6/23/023.
 */

public class ConversationListActivity extends FragmentActivity {
  String TAG="ConversationListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversationlist);
    }

}
