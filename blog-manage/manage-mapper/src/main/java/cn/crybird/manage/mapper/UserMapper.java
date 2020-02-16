package cn.crybird.manage.mapper;

import cn.crybird.manage.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface UserMapper {
    /**
     * 根据用户主键查询用户信息
     * @param id 用户信息的主键
     * @return Users 用户信息
     */
    User getById(@Param("id") Long id);

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User getByUserName(@Param("username") String username);

    /**
     * 根据邮箱查找用户信息
     * @param email
     * @return
     */
    User getByEmail(@Param("email") String email);

    /**
     * 根据手机号查找用户信息
     * @param phone
     * @return
     */
    User getByPhone(@Param("phone") String phone);

    /**
     * 创建新用户
     * @param user
     * @return
     */
    int createUser(@Param("user") User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(@Param("user") User user);

    /**
     * 更改用户账号状态
     * @param map
     * @return
     */
    int updateUserStatus(@Param("map") HashMap<String,Object> map);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int removeUser(@Param("id") Long id);
}
