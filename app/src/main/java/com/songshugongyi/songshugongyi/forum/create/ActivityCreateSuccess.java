package com.songshugongyi.songshugongyi.forum.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.songshugongyi.songshugongyi.R;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class ActivityCreateSuccess  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_success);

    }

    public void Finish(View v)
    {
        finish();
    }

    public void Share(View view)
    {

    }

}
