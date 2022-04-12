package com.zwm.mapper;

import com.zwm.pojo.User;

import java.util.List;

public interface UserMapper {
    public abstract List<User> selectAllUsers();
}
