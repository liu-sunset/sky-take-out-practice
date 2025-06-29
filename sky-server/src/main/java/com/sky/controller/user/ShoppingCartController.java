package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("user/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public Result addShoppingCartController(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车:{}",shoppingCartDTO);
        shoppingCartService.addShoppingCartService(shoppingCartDTO);
        return Result.success();
    }

    //TODO：减少购物车中物品的数量


    //TODO:查看购物车内容功能测试
    public Result<List<ShoppingCart>> selectShoppingCartController(){
        log.info("查看购物车");
        shoppingCartService.selectShoppingCartService();
        return Result.success();
    }

    //TODO:清空购物车功能测试
    public Result deleteShoppingCartController(){
        log.info("清空购物车");
        shoppingCartService.deleteShoppingCartService();
        return Result.success();
    }

}
