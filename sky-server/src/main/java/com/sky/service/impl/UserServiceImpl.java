package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    private static final String URL = "https://api.weixin.qq.com/sns/jscode2session";
    //用户登录
    @Override
    public User userLogin(UserLoginDTO userLoginDTO) throws IOException {
        //调用微信登录接口返回openid
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",userLoginDTO.getCode());
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doPost(URL, map);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid = jsonObject.getString("openid");
        //判断openid是否是空，如果是空抛出异常
        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断数据库中是否已经存在此用户
        User user = userMapper.selectByOpenId(openid);
        //如果不存在就重新添加到数据库
        //封装一个user只添加openid和创建时间
        if(user==null){
            User userNew = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.addUserMapper(userNew);
        }
        //返回user
        return user;
    }
}
