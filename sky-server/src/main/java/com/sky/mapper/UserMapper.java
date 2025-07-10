package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    //根据openid查询用户
    public User selectByOpenId(String openid);
    //添加用户
    public void addUserMapper(User user);
    //查询指定日期时间段内的所有用户人数
    public long userAllStatMapper(LocalDateTime begin,LocalDateTime end);
    //统计指定日期时间段内新增加的用户数量
    public Integer userNewAddStatMapper(LocalDateTime begin,LocalDateTime end);
}
