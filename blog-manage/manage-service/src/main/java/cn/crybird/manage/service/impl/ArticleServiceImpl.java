package cn.crybird.manage.service.impl;

import cn.crybird.manage.mapper.ArticleMapper;
import cn.crybird.manage.mapper.ArticleTagMapper;
import cn.crybird.manage.mapper.CommentMapper;
import cn.crybird.manage.mapper.CommentReplayMapper;
import cn.crybird.manage.model.Article;
import cn.crybird.manage.service.ArticleService;
import cn.crybird.manage.util.FileSaver;
import cn.crybird.manage.util.FileTaker;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentReplayMapper commentReplayMapper;

    @Autowired
    private FileTaker taker;

    @Autowired
    private FileSaver saver;

    @Override
    public Article getById(Long id) {
        return articleMapper.getById(id);
    }

    public String getMarkdownContent(String path) throws IOException {
        return taker.takeMdFile(path);
    }

    @Override
    public Page<Article> getList(Article model, Map<String, Object> map) {
        return articleMapper.getList(model);
    }

    @Override
    @Transactional
    public int update(Article model) {
        Article oldArticle = articleMapper.getById(model.getId());
        if (!oldArticle.getTagIds().equals(model.getTagIds())) {
            articleTagMapper.removeByArticleId(model.getId());
            String[] tagIdsStr = model.getTagIds().split(",");
            List<Integer> tagIds = new ArrayList<>(tagIdsStr.length);
            for (String id : tagIdsStr) {
                id = Strings.trimToNull(id);
                if (id != null && StringUtils.isNumber(id)) {
                    tagIds.add(Integer.valueOf(id));
                }
            }
            if (tagIds.size() > 0) {
                articleTagMapper.createByArticleIdAndTagIds(model.getId(), tagIds);
            }
        }


        String path = model.getTitle() + System.currentTimeMillis() + ".md";
        String content = model.getMdPath();
        model.setMdPath(path);
        try {
            if (articleMapper.updateByArticle(model) > 0) {
                saver.saveMdFile(path, content);
                return 1;
            }else{
                return -1;
            }
        } catch (IOException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return -1;
        }finally {
            model.setMdPath(content);
        }
    }

    @Override
    @Transactional
    public int updateStatusById(Long id, Integer status) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("status", status);
        return articleMapper.updateStatusById(map);
    }

    @Override
    @Transactional
    public int create(Article model) throws Exception {
        try {
            String[] tagIdsStr = model.getTagIds().split(",");
            List<Integer> tagIds = new ArrayList<>(tagIdsStr.length);
            for (String id : tagIdsStr) {
                id = Strings.trimToNull(id);
                if (id != null && StringUtils.isNumber(id)) {
                    tagIds.add(Integer.valueOf(id));
                }
            }
            String path = model.getTitle() + System.currentTimeMillis() + ".md";
            String content = model.getMdPath();
            model.setMdPath(path);
            articleMapper.createByArticle(model);
            if (model.getId() == null) {
                throw new Exception("文章创建失败");
            }
            if (tagIds.size() > 0) {
                articleTagMapper.createByArticleIdAndTagIds(model.getId(), tagIds);
            }
            saver.saveMdFile(path, content);
            return 1;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    @Transactional
    public int removeById(Long id) {
        articleTagMapper.removeByArticleId(id);
        commentReplayMapper.removeByArticleId(id);
        commentMapper.removeById(id);
        return articleMapper.removeById(id);
    }

    @Override
    public List<Article> getTopNWithRecommend(int topN) {
        return articleMapper.getTopNWithRecommend(topN);
    }

    @Override
    public Page<Article> getListForShow(Article article) {
        return articleMapper.getListForShow(article);
    }

    @Override
    public int count(String title) {
        return articleMapper.count(title);
    }

    @Override
    public List<Article> getArchives(int years) {
        return articleMapper.getArchives(years);
    }

    @Override
    public int countById(Long id) {
        return articleMapper.countById(id);
    }

    @Override
    public int incBrowseCount(Long id) {
        return articleMapper.incBrowseCount(id);
    }

    @Override
    public Page<Article> getListByKey(String key) {
        return articleMapper.getListByKey(key);
    }
}
