package com.zwm.service;

import com.zwm.pojo.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public abstract List<User> selectAllUsers() throws IOException;
}
