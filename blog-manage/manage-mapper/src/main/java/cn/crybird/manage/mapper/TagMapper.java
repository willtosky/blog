package cn.crybird.manage.mapper;

import cn.crybird.manage.model.Tag;
import cn.crybird.manage.model.TagCount;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    Tag getById(@Param("id") Long id);

    Page<Tag> getList(@Param("name") String name);

    int updateByTag(@Param("tag") Tag tag);

    int createByTag(@Param("tag") Tag tag);

    int removeById(@Param("id") Long id);

    List<Tag> getListByIds(@Param("ids") List<Integer> ids);

    int count(@Param("tag") Tag tag);

    List<Tag> getTags();

    List<TagCount> getTopN(@Param("topN") int topN);

    List<TagCount> getTagCountList();
}
