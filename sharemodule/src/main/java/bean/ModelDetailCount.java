package bean;

/**
 * Created by yuanopen on 2018/7/15/015.
 */
public class ModelDetailCount {
    // 项目id;
    private  String progress_id;
    // 评论数
    private int comment_counts;
    // 评论数
    private int share_counts;
    // 评论数
    private int like_counts;
    // 评论数
    private int collect_counts;

    public String getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(String progress_id) {
        this.progress_id = progress_id;
    }

    public int getComment_counts() {
        return comment_counts;
    }

    public void setComment_counts(int comment_counts) {
        this.comment_counts = comment_counts;
    }

    public int getShare_counts() {
        return share_counts;
    }

    public void setShare_counts(int share_counts) {
        this.share_counts = share_counts;
    }

    public int getLike_counts() {
        return like_counts;
    }

    public void setLike_counts(int like_counts) {
        this.like_counts = like_counts;
    }

    public int getCollect_counts() {
        return collect_counts;
    }

    public void setCollect_counts(int collect_counts) {
        this.collect_counts = collect_counts;
    }
}
