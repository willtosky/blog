package cn.crybird.manage.mapper;

import cn.crybird.manage.model.CommentReplay;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CommentReplayMapper {
    CommentReplay getById(@Param("id") Long id);

    Page<CommentReplay> getList(@Param("commentReplay") CommentReplay commentReplay);

    int updateByCommentReplay(@Param("commentReplay") CommentReplay commentReplay);

    int updateStatusById(@Param("map") HashMap<String,Object> map);

    int createByCommentReplay(@Param("commentReplay") CommentReplay commentReplay);

    int removeById(@Param("id") Long id);

    int removeByArticleId(@Param("articleId") Long articleId);
}
