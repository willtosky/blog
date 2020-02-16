package cn.crybird.manage.service;

import cn.crybird.manage.model.Tag;
import cn.crybird.manage.model.TagCount;

import java.util.List;

public interface TagService extends BaseService<Tag> {

    int count(Tag model);

    List<Tag> getListByIds(List<Integer> tagIds);

    List<Tag> getTags();

    List<TagCount> getTopN(int topN);

    List<TagCount> getTagCountList();
}
