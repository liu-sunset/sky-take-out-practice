package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //根据openid查询用户
    public User selectByOpenId(String openid);
    //添加用户
    public void addUserMapper(User user);
}
