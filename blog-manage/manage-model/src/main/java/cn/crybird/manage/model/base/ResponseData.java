package cn.crybird.manage.model.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 自定义响应类
 * @param <T>
 */
@ToString
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class ResponseData<T> {

    private String msg;
    private List<String> msgs;
    private String code;
    private boolean success;
    private T data;

}
