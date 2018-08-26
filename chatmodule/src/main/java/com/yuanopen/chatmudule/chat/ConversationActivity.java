package com.yuanopen.chatmudule.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yuanopen.chatmudule.R;
import com.yuanopen.chatmudule.add.ShowUserInfoAcivity;
import com.yuanopen.chatmudule.group.ShowGroupInfoAcivity;
import com.yuanopen.commenmodule.utils.ShowToast;

import java.util.Locale;

import io.rong.imlib.model.Conversation;

/**
 * Created by yuanopen on 2018/5/6/006.
 */

public class ConversationActivity extends AppCompatActivity {

    private static final String TAG = ConversationActivity.class.getSimpleName();
    private TextView Title;
    private String user_id;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        init();
        }


    /**
     * 初始化
     */
    private void init() {
         intent=getIntent();
        Title=findViewById(R.id.conversation_title);
        Title.setText(""+intent.getData().getQueryParameter("title"));
        user_id=intent.getData().getQueryParameter("targetId");
//      user_id=intent.getData().getQueryParameter("")
        Log.i("targetId:",intent.getData().getQueryParameter("targetId"));
        Log.i("title:",intent.getData().getQueryParameter("title"));

    }

    /**
     *createed by yuanopen
     * @param intent
     * @return 1:单聊  2：群聊  0：null
     */
    private  int getConversationType(Intent intent){
        Conversation.ConversationType mConversationType = Conversation.ConversationType
                .valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        return  mConversationType.getValue();
    }

    public void Back(View view)
    {
        finish();
    }

    public void toInfo(View v){
        if(getConversationType(intent)==1){
            Intent friend=new Intent(this, ShowUserInfoAcivity.class);
            friend.putExtra("user_id",user_id);
            startActivity(friend);
        }else if(getConversationType(intent)==2){
            Intent intent=new Intent(this, ShowGroupInfoAcivity.class);
            intent.putExtra("group_id",user_id);
            startActivity(intent);

        }else{
            ShowToast.showToast(this,"跳转失败！");
        }
    }

}