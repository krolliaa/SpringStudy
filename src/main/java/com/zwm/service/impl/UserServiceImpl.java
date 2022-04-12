package com.zwm.service.impl;

import com.zwm.mapper.UserMapper;
import com.zwm.pojo.User;
import com.zwm.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> selectAllUsers() throws IOException {
        return userMapper.selectAllUsers();
    }
}
