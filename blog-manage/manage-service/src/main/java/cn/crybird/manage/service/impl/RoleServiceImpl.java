package cn.crybird.manage.service.impl;

import cn.crybird.manage.mapper.RoleMapper;
import cn.crybird.manage.model.Role;
import cn.crybird.manage.service.RoleService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getById(Long id) {
        return null;
    }

    @Override
    public Page<Role> getList(Role model, Map<String, Object> map) {
        return null;
    }

    @Override
    public int update(Role model) {
        return 0;
    }

    @Override
    public int updateStatusById(Long id, Integer status) {
        return 0;
    }

    @Override
    public int create(Role model) throws Exception {
        return 0;
    }

    @Override
    public int removeById(Long id) {
        return 0;
    }
}
