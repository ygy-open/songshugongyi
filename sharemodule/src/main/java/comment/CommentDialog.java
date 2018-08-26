package comment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuanopen.commenmodule.http.OnSendListener;
import com.yuanopen.commenmodule.utils.ShowToast;
import com.yuanopen.commenmodule.utils.getCurrentTime;
import com.yuanopen.sharemodule.R;

import bean.Comment;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class CommentDialog {

    public static void showCommentDialog(final Context context, final String host, final String action, final Comment comment, final OnSendListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View
                .inflate(context, R.layout.comment_dialog, null);
        builder.setView(view);
        builder.setCancelable(true);

        final EditText input_edt= (EditText) view
                .findViewById(R.id.dialog_edit);//输入内容
        Button btn_cancel=(Button)view
                .findViewById(R.id.btn_cancel);//取消按钮
        Button btn_comfirm=(Button)view
                .findViewById(R.id.btn_comfirm);//确定按钮
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=input_edt.getText().toString();

                if("".equals(content))
                    ShowToast.showToast(context,"评论内容不能为空！");
                else{

                    comment.setContent(content);
                    comment.setComment_time(getCurrentTime.getTime());

                    CommentUtils.getIntance(context).sendComentToService(host,action,comment,listener);
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
    }
}
