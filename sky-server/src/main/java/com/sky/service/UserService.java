package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

import java.io.IOException;

public interface UserService {
    //用户登录
    public User userLogin(UserLoginDTO userLoginDTO) throws IOException;
}
