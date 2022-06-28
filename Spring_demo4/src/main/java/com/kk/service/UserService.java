package com.kk.service;

import com.kk.pojo.Account;
import com.kk.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 添加用户
     */
    public abstract int addUser(User user);

    /**
     * 删除用户
     */
    public abstract int deleteUser(Integer uid);

    /**
     * 修改用户
     */
    public abstract int alterUser(User user);

    /**
     * 查询全部用户
     */
    public abstract List<User> findAllUsers();
}
