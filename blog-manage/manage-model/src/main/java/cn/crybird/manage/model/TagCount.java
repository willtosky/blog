package cn.crybird.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TagCount {

    private Long id;
    private int count;
    private Tag tag;

}
