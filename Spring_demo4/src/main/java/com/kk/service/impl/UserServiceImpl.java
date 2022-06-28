package com.kk.service.impl;

import com.kk.mapper.UserMapper;
import com.kk.pojo.User;
import com.kk.pojo.UserExample;
import com.kk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteUser(Integer uid) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIsNull();
        return userMapper.deleteByExample(userExample);
    }

    @Override
    public int alterUser(User user) {
        return userMapper.updateByExample(user, null);
    }

    @Override
    public List<User> findAllUsers() {
        return userMapper.selectByExample(null);
    }
}
