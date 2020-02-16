package cn.crybird.manage.service;

import cn.crybird.manage.model.User;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    User getByPhone(String phone);

    int createUser(User user);

    int updateUser(User user);

    int updateUserStatus(Long id,Integer status);

    int removeUser(Long id);
}
