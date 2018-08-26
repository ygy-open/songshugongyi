package bean;

/**
 * Created by yuanopen on 2018/7/20/020.
 */

public class ModelStatus {
    // 项目id;
    private  String model_id;
    // 是否评论
    private boolean comment;
    // 是否评论
    private boolean share;
    // 是否评论数
    private boolean like;
    // 是否评论
    private boolean collect;

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
