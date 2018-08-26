package com.songshugongyi.songshugongyi.receive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.songshugongyi.songshugongyi.R;
import com.songshugongyi.songshugongyi.activity.AtyMain;
import com.songshugongyi.songshugongyi.user.ActivityVerifyMessage;
import com.yuanopen.commenmodule.notify.NotifyUtil;
import com.yuanopen.commenmodule.utils.DbManager;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ContactNotificationMessage;

/**
 * Created by yuanopen on 2018/7/30/030.
 */

public class MyReceiveMessageListener  implements RongIMClient.OnReceiveMessageListener  {

    private String TAG="MessageListener";
    Context mContent;

    public MyReceiveMessageListener(Context mContent) {
        this.mContent = mContent;
    }

    @Override
    public boolean onReceived(Message message, int i) {

        MessageContent messageContent = message.getContent();


        if (messageContent instanceof ContactNotificationMessage) {
            ContactNotificationMessage contactNotificationMessage= (ContactNotificationMessage) messageContent;

            Log.d(TAG, contactNotificationMessage.getOperation());
            Log.d(TAG,contactNotificationMessage.getMessage()+" user_id:"+contactNotificationMessage.getSourceUserId()+" "+
                    contactNotificationMessage+"  target_id:"+contactNotificationMessage.getTargetUserId()+ " "+message.getTargetId()
            +"fdfd:"+contactNotificationMessage.getExtra());

            Intent intent =new Intent (mContent, ActivityVerifyMessage.class);
            PendingIntent pendingIntent =PendingIntent.getActivity(mContent, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            DbManager.getInstance(mContent).insertToDb(contactNotificationMessage.getTargetUserId(),contactNotificationMessage.getSourceUserId(),
                contactNotificationMessage.getExtra(),Integer.parseInt(contactNotificationMessage.getOperation()));

            Notify(mContent,pendingIntent);
          //  showNotification(mContent,1,"测试","内容");
            return  false;
        }

        return true;
    }

    private  void Notify(Context mContext,  PendingIntent pIntent){
        Log.d(TAG,  "Notify");
        int smallIcon = R.drawable.default_avatar;
        String ticker = "您有一条新通知";
        String title = "双十一大优惠！！！";
        String content = "仿真皮肤充气娃娃，女朋友带回家！";
        //实例化工具类，并且调用接口
        NotifyUtil notify1 = new NotifyUtil(mContext, 1);
        notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, true, false);
    }

    private void showNotification(Context context, int id, String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.default_avatar);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        // 需要VIBRATE权限
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setPriority(Notification.PRIORITY_DEFAULT);

        // Creates an explicit intent for an Activity in your app
        //自定义打开的界面
        Intent resultIntent = new Intent(context, AtyMain.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }


}
