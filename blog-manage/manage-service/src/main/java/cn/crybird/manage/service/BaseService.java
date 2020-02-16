package cn.crybird.manage.service;

import com.github.pagehelper.Page;

import java.util.Map;

public interface BaseService<T> {
    T getById(Long id);

    Page<T> getList(T model,Map<String,Object> map);

    int update(T model);

    int updateStatusById(Long id, Integer status);

    int create(T model) throws Exception;

    int removeById(Long id);

}
