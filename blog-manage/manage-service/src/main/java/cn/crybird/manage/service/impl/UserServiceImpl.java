package cn.crybird.manage.service.impl;

import cn.crybird.manage.mapper.UserMapper;
import cn.crybird.manage.model.User;
import cn.crybird.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUserName(username);
    }

    @Override
    public User getByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    @Override
    public int createUser(User user) {
        try{
            return userMapper.createUser(user);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int updateUserStatus(Long id, Integer status) {
        HashMap<String, Object> hash = new HashMap<String,Object>(2);
        hash.put("id", id);
        hash.put("status", status);
        return userMapper.updateUserStatus(hash);
    }

    @Override
    public int removeUser(Long id) {
        return userMapper.removeUser(id);
    }

}
