package com.itheima.controller;

import com.itheima.entity.User;
import com.itheima.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;


    /*如果使用Spring Cache缓存数据，key的生成：cacheNames::key
    * #result可以使用方法的返回值
    * #p0可以使用第一个方法形参
    * #a0可以使用第一个方法形参
     */
    @CachePut(cacheNames = "userCache",key = "#user.id")
    @PostMapping
    public User save(@RequestBody User user){
        userMapper.insert(user);
        return user;
    }

    @DeleteMapping
    public void deleteById(Long id){
        userMapper.deleteById(id);
    }

	@DeleteMapping("/delAll")
    public void deleteAll(){
        userMapper.deleteAll();
    }

    @GetMapping
    public User getById(Long id){
        User user = userMapper.getById(id);
        return user;
    }

}
