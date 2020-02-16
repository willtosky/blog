package cn.crybird.manage.service.impl;

import cn.crybird.manage.mapper.TagMapper;
import cn.crybird.manage.model.Tag;
import cn.crybird.manage.model.TagCount;
import cn.crybird.manage.service.TagService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Tag getById(Long id) {
        return tagMapper.getById(id);
    }

    @Override
    public Page<Tag> getList(Tag model, Map<String, Object> map) {
        return tagMapper.getList(model.getName());
    }

    @Override
    public int update(Tag model) {
        return tagMapper.updateByTag(model);
    }

    @Override
    public int updateStatusById(Long id, Integer status) {
        return 0;
    }

    @Override
    public int create(Tag model) throws Exception {
        try{
            return tagMapper.createByTag(model);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int removeById(Long id) {
        return tagMapper.removeById(id);
    }

    @Override
    public int count(Tag model) {
        return tagMapper.count(model);
    }

    @Override
    public List<Tag> getListByIds(List<Integer> tagIds) {
        return tagMapper.getListByIds(tagIds);
    }

    @Override
    public List<Tag> getTags() {
        return tagMapper.getTags();
    }

    @Override
    public List<TagCount> getTopN(int topN) {
        return tagMapper.getTopN(topN);
    }

    @Override
    public List<TagCount> getTagCountList() {
        return tagMapper.getTagCountList();
    }
}
