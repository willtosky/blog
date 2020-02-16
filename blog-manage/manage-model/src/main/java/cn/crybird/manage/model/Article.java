package cn.crybird.manage.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * 文章信息
 */
@Data
public class Article {
    Long id;
    //文章作者id
    Long userId;
    //原创/转载/翻译标志
    Integer mark;
    //类型id
    Long typeId;
    //类型
    String type;
    //标签ids
    String tagIds;
    //标签
    String tags;
    //标题
    String title;
    //摘要
    String summary;
    //图片完整路径或者URL
    String imagePath;
    //markdown格式文本
    String mdPath;
    //推荐文章标志
    Integer recommendFlag;
    //评论开启标志
    Integer commentFlag;
    //赞赏开启标志
    Integer praiseFlag;
    //转载声明开启标志
    Integer reproducedFlag;
    //浏览次数
    Long browseCount;
    //评论数量
    Long commentCount;
    //赞赏人数
    Long praiseCount;
    //捐献
    Double alms;
    //文章当前状态
    Integer status;
    //创建时间
    Date createTime;
    //markdown最近一次编辑时间
    Date editedTime;
    //发布时间
    Date publishTime;
}
