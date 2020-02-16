package cn.crybird.manage.model;

import lombok.Data;

/**
 * 文章标签
 */
@Data
public class Tag {

    private Long id;
    private Long userId;
    private String name;

}
