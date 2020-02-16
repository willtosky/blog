package cn.crybird.manage.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

/**
 * 用户
 */
@Data
public class User {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String nickname;
    private Integer sex;
    private Date birthday;
    private Integer status;
    private String roleName;
    private Integer updateCount;

}
