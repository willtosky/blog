package cn.crybird.manage.service;

import cn.crybird.manage.model.Article;
import cn.crybird.manage.model.Type;
import cn.crybird.manage.model.TypeCount;
import com.github.pagehelper.Page;

import java.util.List;

public interface TypeService extends BaseService<Type>{

    int count(Type model);

    List<Type> getTypes();

    List<TypeCount> getTopN(int topN);

    List<TypeCount> getTypeCountList();


}
