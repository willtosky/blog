package cn.crybird.manage.mapper;

import cn.crybird.manage.model.Role;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    Role getById(@Param("id") Long id);

    Page<Role> getList(@Param("name") String name);

    int updateByRole(@Param("role") Role role);

    int createByRole(@Param("role") Role role);

    int removeById(@Param("id") Long id);
}
