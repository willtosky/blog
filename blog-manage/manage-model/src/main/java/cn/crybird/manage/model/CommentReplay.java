package cn.crybird.manage.model;

import lombok.Data;

import java.util.Date;

/**
 * 评论回复
 */
@Data
public class CommentReplay {

    private Long id;
    //评论id
    private Long commentId;
    //参与评论用户id
    private Long userId;
    //参与评论用户昵称
    private String nickname;
    private String email;
    //参与评论用户头像
    private String avatar;
    //评论时间
    private Date createTime;
    //评论内容
    private String content;
    //评论状态
    private Integer status;

    //@xxxx id
    private Long linkId;
    //@xxxx nickname
    private String linkName;
    private String linkEmail;

    //点赞数量
    private Long like;

}
