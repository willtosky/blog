package cn.crybird.manage.model;

import lombok.Data;

import java.util.Date;

/**
 * 评论
 */
@Data
public class Comment {

    private Long id;
    //文章id
    private Long articleId;
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
    //回复数量
    private Long replay;
    //点赞数量
    private Long like;

}
