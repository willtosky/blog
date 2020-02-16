package cn.crybird.manage.mapper;

import cn.crybird.manage.model.Comment;
import cn.crybird.manage.model.CommentAndReplay;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CommentMapper {
    Comment getById(@Param("id") Long id);

    Page<Comment> getList(@Param("comment") Comment comment);

    int updateByComment(@Param("comment") Comment comment);

    int updateStatusById(@Param("map") HashMap<String,Object> map);

    int createByComment(@Param("comment") Comment comment);

    int removeById(@Param("articleId") Long articleId);

    int updateStatusByArticleId(@Param("articleId") Long articleId,@Param("status") Integer status);

    List<CommentAndReplay> getCommentAndReplayListByArticleId(@Param("articleId") Long articleId);

    int countById(@Param("id") Long id);

    int incReplay(@Param("commentId") Long commentId);
}
