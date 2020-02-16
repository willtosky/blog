package cn.crybird.manage.service.impl;

import cn.crybird.manage.mapper.TypeMapper;
import cn.crybird.manage.model.Article;
import cn.crybird.manage.model.Type;
import cn.crybird.manage.model.TypeCount;
import cn.crybird.manage.service.TypeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type getById(Long id) {
        return typeMapper.getById(id);
    }

    @Override
    public Page<Type> getList(Type model, Map<String, Object> map) {
        return typeMapper.getList(model.getName());
    }

    @Override
    public int update(Type model) {
        return typeMapper.updateByType(model);
    }

    @Override
    public int updateStatusById(Long id, Integer status) {
        return 0;
    }

    @Override
    public int create(Type model) throws Exception {
        try{
            return typeMapper.createByType(model);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int removeById(Long id) {
        return typeMapper.removeById(id);
    }

    @Override
    public int count(Type model) {
        return typeMapper.count(model);
    }

    @Override
    public List<Type> getTypes() {
        return typeMapper.getTypes();
    }

    @Override
    public List<TypeCount> getTopN(int topN) {
        return typeMapper.getTopN(topN);
    }

    @Override
    public List<TypeCount> getTypeCountList() {
        return typeMapper.getTypeCountList();
    }
}
