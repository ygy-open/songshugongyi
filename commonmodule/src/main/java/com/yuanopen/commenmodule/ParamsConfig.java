package com.yuanopen.commenmodule;

/**
 * Created by yuanopen on 2018/7/18/018.
 */

public class ParamsConfig {
    public static final String RequestParamKey_Action = "action";
    // 用户注册
    public static final String RequestAction_Register = "register";
    // 用户第三方注册
    public static final String RequestAction_The_Third_Register = "third_register";
    // 用户注册验证手机号是否存在
    public static final String RequestAction_Verify_Phone = "verify_phone";
    // 用户注册验证手userId是否存在
    public static final String RequestAction_Verify_Phone_User_id= "verify_user_id";

    public static final String RequestAction_User_Set_New_Password = "set_new_password";
    // 用户更新信息
    public static final String RequestAction_UserEdit = "useredit";
    // 用户登录
    public static final String RequestAction_Login = "login";
    // 用户第三方登录
    public static final String RequestAction_The_Third_Login = "third_login";
    // 获取用户的群组
    public static final String RequestAction_Get_User_Group = "get_user_group";
    // 获取用户的好友
    public static final String RequestAction_Get_User_Friend = "get_user_friend";

    // 获取用户的好友
    public static final String RequestAction_Get_New_RongYun_Token = "get_rongyun_token";
    // 根据id获取群组信息
    public static final String RequestAction_Get_GroupInfo_By_Id = "get_group_by_id";
    // 根据id获取好友信息
    public static final String RequestAction_Get_FriendInfo_By_Id = "get_friend_by_id";

    // 根据输入内容获取群组信息
    public static final String RequestAction_Search_Group_By_Name = "search_group_by_name";
    // 根据username获取好友信息
    public static final String RequestAction_Search_User_By_User_Nmae = "search_user_by_name";

    public static final String RequestAction_Upload = "upload";
    public static final String RequestAction_Send_Image_To_Client = "send";

    // 创建项目
    public static final String RequestAction_Greate_Progress = "create_progress";
    // 获取项目列表
    public static final String RequestAction_GetList_Progress_Comments = "getList_progress_comments";
    // 获取项目的评论详情
    public static final String RequestAction_GetList_Progress = "getList_progresses";
    // 获取项目评论，转发，点赞，收藏数
    public static final String RequestAction_GetList_Progress_Detail = "collect_progress_detail";
    // 获取项目评论，转发，点赞，收藏与当前用户状态
    public static final String RequestAction_GetList_Proogress_Status = "get_progress_status";
    // 评论项目记录
    public static final String RequestAction_Comment_Progress = "comment_progress";
    // 分享项目记录
    public static final String RequestAction_Share_Progress = "share_progress";
    // 点赞项目记录
    public static final String RequestAction_Like_Progress = "like_progress";
    // 收藏项目记录
    public static final String RequestAction_Collect_Progress = "collect_progress";
    // 报名加入项目任务
    public static final String RequestAction_Join_Progress_Task = "join_progress_task";
    // 报名加入项目任务
    public static final String RequestAction_Is_Join_Progress = "is_join_progress";

    // 热点
    // 创建热点
    public static final String RequestAction_Create_Hot_Topic = "create_hot_topic";
    // 获取热点列表
    public static final String RequestAction_GetList_Hot_Topic= "get_list_hot_topic";
    // 获取热点评论，转发，点赞，收藏数
    public static final String RequestAction_GetList_Hot_Topic_Detail = "collect_hot_topic_detail";
    // 获取项目的评论详情
     public static final String RequestAction_GetList_Hot_Topic_Status = "get_hot_topic_status";

    public static final String RequestAction_GetList_Hot_Topic_Comments = "getList_hot_topic_comments";
    // 评论热点记录
    public static final String RequestAction_Comment_Hot_Topic = "comment_hot_topic";
    // 分享热点记录
    public static final String RequestAction_Share_Hot_Topic = "share_hot_topic";
    // 点赞热点记录
    public static final String RequestAction_Like_Hot_Topic = "like_hot_topic";
    // 收藏热点记录
    public static final String RequestAction_Collect_Hot_Topic= "collect_hot_topic";

    // 关注
    public static final String RequestAction_Hot_Topic_Attention = "create_attention";

    // 创建群组
    public static final String RequestAction_Create_Group = "create_group";

    // 添加好友
    public static final String RequestAction_Add_Friend = "add_friend";

    // 加入群组
    public static final String RequestAction_Join_Group = "join_group";
    // 退出群组
    public static final String RequestAction_Quit_Group = "quit_group";


    public static final String RequestParamKey_Model_Id = "model_id";
    public static final String RequestParamKey_Table_Name = "table_name";

}
