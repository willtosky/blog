package cn.crybird.manage.model.log;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GlobalLog {

    private Long id;
    private Date invokeTime;
    private String method;
    private String args;

}
