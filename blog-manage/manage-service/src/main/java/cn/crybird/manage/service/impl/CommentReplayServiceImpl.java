package cn.crybird.manage.service.impl;

import cn.crybird.manage.mapper.CommentMapper;
import cn.crybird.manage.mapper.CommentReplayMapper;
import cn.crybird.manage.model.CommentReplay;
import cn.crybird.manage.service.CommentReplayService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentReplayServiceImpl implements CommentReplayService {

    @Autowired
    private CommentReplayMapper commentReplayMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentReplay getById(Long id) {
        return commentReplayMapper.getById(id);
    }

    @Override
    public Page<CommentReplay> getList(CommentReplay model, Map<String, Object> map) {
        return commentReplayMapper.getList(model);
    }

    @Override
    public int update(CommentReplay model) {
        return commentReplayMapper.updateByCommentReplay(model);
    }

    @Override
    public int updateStatusById(Long id, Integer status) {
        return 0;
    }

    @Override
    public int create(CommentReplay model) throws Exception {
        try{
            int result = commentReplayMapper.createByCommentReplay(model);

            /**
             * 评论回复数量+1
             */
            commentMapper.incReplay(model.getCommentId());

            return result;
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int removeById(Long id) {
        return commentReplayMapper.removeById(id);
    }
}
