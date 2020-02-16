package cn.crybird.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TypeCount {

    private Long id;
    private int count;
    private Type type;
    //private String name;

}
