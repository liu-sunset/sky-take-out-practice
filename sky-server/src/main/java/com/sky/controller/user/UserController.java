package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import com.sun.tools.javac.comp.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public Result<UserLoginVO> userLoginController(@RequestBody UserLoginDTO userLoginDTO) throws IOException {
        //微信登录
        User user = userService.userLogin(userLoginDTO);
        //生成jwt令牌
        HashMap<String, Object> claim = new HashMap<>();
        claim.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claim);
        //组装UserLoginVO
        UserLoginVO userLoginVO = new UserLoginVO()
                .builder()
                .id(user.getId())
                .token(token)
                .openid(user.getOpenid())
                .build();
        
        log.info("用户登录成功，信息：{}",userLoginVO);
        //组装uservo并返回
        return Result.success(userLoginVO);
    }
}
