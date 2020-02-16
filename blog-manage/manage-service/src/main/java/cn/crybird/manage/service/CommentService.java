package cn.crybird.manage.service;

import cn.crybird.manage.model.Comment;
import cn.crybird.manage.model.CommentAndReplay;

import java.util.List;

public interface CommentService extends BaseService<Comment>{

    List<CommentAndReplay> getCommentAndReplayListByArticleId(Long articleId);

    int countById(Long Id);
}
