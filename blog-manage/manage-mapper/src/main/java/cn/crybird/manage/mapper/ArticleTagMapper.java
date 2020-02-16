package cn.crybird.manage.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleTagMapper {

    int removeByArticleId(@Param("articleId")Long articleId);

    int removeByTagId(@Param("tagId")Long tagId);

    int removeByArticleIdAndTagId(@Param("articleId")Long articleId,@Param("tagId")Long tagId);

    int createByArticleIdAndTagIds(@Param("articleId") Long articleId,@Param("tagIds") List<Integer> tagIds);
}
