package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.openxmlformats.schemas.drawingml.x2006.main.STTextAutonumberScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminShopController")//设置bean对象的名称
@RequestMapping("/admin/shop")
public class ShopStatusController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺的营业状态为：{}", status);
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success(status == 1 ? "营业中" : "打烊中");
    }

    @GetMapping("/status")
    public Result<Integer> getstatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("当前店铺的状态是：{}",status);
        return Result.success(status);
    }
}
