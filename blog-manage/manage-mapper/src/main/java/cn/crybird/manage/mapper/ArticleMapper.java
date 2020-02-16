package cn.crybird.manage.mapper;

import cn.crybird.manage.model.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    Article getById(@Param("id") Long id);

    Page<Article> getList(@Param("article") Article article);

    int updateByArticle(@Param("article") Article article);

    int updateStatusById(@Param("map") Map<String,Object> map);

    int createByArticle(@Param("article") Article article);

    int removeById(@Param("id") Long id);

    List<Article> getTopNWithRecommend(@Param("topN") int topN);

    int count(@Param("title") String title);

    int countById(@Param("id") Long id);

    //
    Page<Article> getListForShow(@Param("article") Article article);

    List<Article> getArchives(@Param("years") int years);

    int incBrowseCount(@Param("id") Long id);

    Page<Article> getListByKey(@Param("key") String key);
}
