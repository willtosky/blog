package cn.crybird.manage.service.impl;

import cn.crybird.manage.mapper.CommentMapper;
import cn.crybird.manage.model.Comment;
import cn.crybird.manage.model.CommentAndReplay;
import cn.crybird.manage.service.CommentService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment getById(Long id) {
        return commentMapper.getById(id);
    }

    @Override
    public Page<Comment> getList(Comment model, Map<String, Object> map) {
        return commentMapper.getList(model);
    }

    @Override
    public int update(Comment model) {
        return commentMapper.updateByComment(model);
    }

    @Override
    public int updateStatusById(Long id, Integer status) {
        return 0;
    }

    @Override
    public int create(Comment model) throws Exception {
        try{
            return commentMapper.createByComment(model);
        }catch (Exception e ){
            return -1;
        }
    }

    @Override
    public int removeById(Long id) {
        return commentMapper.removeById(id);
    }

    @Override
    public List<CommentAndReplay> getCommentAndReplayListByArticleId(Long articleId) {
        return commentMapper.getCommentAndReplayListByArticleId(articleId);
    }

    @Override
    public int countById(Long id) {
        return commentMapper.countById(id);
    }
}
