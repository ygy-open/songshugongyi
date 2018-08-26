package bean;

/**
 * Created by yuanopen on 2018/7/17/017.
 */

public class CommentWitnUser {
    // 用户id
    private  String user_id;
    // 用户名
    private  String user_name;
    // 用户名
    private  String user_avatar;
    // 用户名
    private  String content;
    // 分评论时间
    private String comment_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }
}
