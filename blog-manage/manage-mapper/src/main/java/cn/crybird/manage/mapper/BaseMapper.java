package cn.crybird.manage.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface BaseMapper<T> {

    T getById(Long id);

    Page<T> getList(T model);

    int update(T model);

    int updateStatusById(HashMap<String,Object> map);

    int create(T model);

    int removeById(Long id);

}
