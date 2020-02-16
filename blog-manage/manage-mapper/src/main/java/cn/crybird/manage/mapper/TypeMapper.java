package cn.crybird.manage.mapper;

import cn.crybird.manage.model.Article;
import cn.crybird.manage.model.Type;
import cn.crybird.manage.model.TypeCount;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeMapper {
    Type getById(@Param("id") Long id);

    Page<Type> getList(@Param("name") String name);

    int updateByType(@Param("type") Type type);

    int createByType(@Param("type") Type type);

    int removeById(@Param("id") Long id);

    int count(@Param("type") Type type);

    List<Type> getTypes();

    List<TypeCount> getTopN(@Param("topN") int topN);

    List<TypeCount> getTypeCountList();
}
