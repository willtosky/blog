package cn.crybird.manage.service;

import cn.crybird.manage.model.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleService extends BaseService<Article> {

    List<Article> getTopNWithRecommend(int topN);

    Page<Article> getListForShow(Article article);

    int count(String title);

    List<Article> getArchives(int years);

    int countById(Long id);

    int incBrowseCount(Long id);

    Page<Article> getListByKey(String key);

}
