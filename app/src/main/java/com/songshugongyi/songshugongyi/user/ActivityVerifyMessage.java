package com.songshugongyi.songshugongyi.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.songshugongyi.songshugongyi.R;
import com.yuanopen.commenmodule.http.GsonUtils;
import com.yuanopen.commenmodule.message.DBMessage;
import com.yuanopen.commenmodule.message.FriendMessage;
import com.yuanopen.commenmodule.message.GroupMessage;
import com.yuanopen.commenmodule.utils.DbManager;

import java.util.ArrayList;
import java.util.List;

import static com.songshugongyi.songshugongyi.util.ShowToast.showToast;
import static com.yuanopen.commenmodule.utils.Config.currentUser;
import static com.yuanopen.commenmodule.utils.SendMessage.sendAddGroupMessage;
import static com.yuanopen.commenmodule.utils.SendMessage.sendMessage;

/**
 * Created by yuanopen on 2018/7/30/030.
 */

public class ActivityVerifyMessage extends AppCompatActivity {

    private String TAG="ActivityVerifyMessage";
    ListView lvVerifyMessage;
    List<DBMessage>dbMessageList;
    AdapterVerifyMessage adapterVerifyMessage;
    DbManager db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_message);
        init();
    }

    private void init() {
        lvVerifyMessage=findViewById(R.id.lv_verify_messages);
        dbMessageList=new ArrayList<>();
        db=DbManager.getInstance(this);
        dbMessageList.addAll(db.getData());
        Log.d(TAG,dbMessageList.size()+"");
        adapterVerifyMessage=new AdapterVerifyMessage(this,dbMessageList);
        lvVerifyMessage.setAdapter(adapterVerifyMessage);
        this.registerForContextMenu(lvVerifyMessage);

        lvVerifyMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showToast(ActivityVerifyMessage.this,"点击"+i+"个");
                Log.d(TAG,"点击"+i+"个");
                showChooseDialog(dbMessageList.get(i));
            }
        });
    }

    AlertDialog dialog;
    private void showChooseDialog(final DBMessage dbMessage){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("是否同意申请？");
        builder.setPositiveButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dbMessage.getType()==1)
                sendAgreeMessage(dbMessage,3);
                else
                    sendAgreeGroupMessage(dbMessage,3);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dbMessage.getType()==1)
                    sendAgreeMessage(dbMessage,2);
                else
                    sendAgreeGroupMessage(dbMessage,2);
                dialog.dismiss();
            }
        });

        dialog=builder.create();

        dialog.show();
    }
    private void   sendAgreeMessage( DBMessage dbMessage,int type){

        FriendMessage friendMessage= GsonUtils.jsonToModule(dbMessage.getExtra(),FriendMessage.class);
        friendMessage.setTarget_id(friendMessage.getUser_id());
        friendMessage.setTarget_name(friendMessage.getUser_name());
//        friendMessage.setContent("同意");
        friendMessage.setType(type);
        friendMessage.setUser_id(currentUser.getUserId());
        friendMessage.setUser_name(currentUser.getUserName());
        friendMessage.setUser_avatar(currentUser.getUserAvatar());

        sendMessage(this,friendMessage);
    }

    private void   sendAgreeGroupMessage( DBMessage dbMessage,int type){

        GroupMessage groupMessage=GsonUtils.jsonToModule(dbMessage.getExtra(),GroupMessage.class);
        groupMessage.setTarget_id(groupMessage.getUser_id());
        groupMessage.setTarget_name(groupMessage.getUser_name());

        groupMessage.setContent("同意");
        groupMessage.setType(type);
        groupMessage.setUser_id(currentUser.getUserId());
        groupMessage.setUser_name(currentUser.getUserName());
        groupMessage.setUser_avatar(currentUser.getUserAvatar());

        sendAddGroupMessage(this,groupMessage);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 3, Menu.NONE, "删除");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Log.i("id",item.getItemId()+"");
        switch (item.getItemId()) {

            case 3:
                db.delete(dbMessageList.get(menuInfo.position).getTarget_id());
                dbMessageList.remove(menuInfo.position);
                adapterVerifyMessage.notifyDataSetChanged();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public void Back(View v){
        finish();
    }
}
