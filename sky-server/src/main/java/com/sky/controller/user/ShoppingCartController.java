package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sub")
    public Result deleteShoppingCartController(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("减少购物车物品数量:{}",shoppingCartDTO);
        shoppingCartService.deleteShoppingCartService(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> selectShoppingCartController(){
        log.info("查看购物车");
        List<ShoppingCart> shoppingCartList = shoppingCartService.selectShoppingCartService();
        return Result.success(shoppingCartList);
    }

    @DeleteMapping("/clean")
    public Result vacantShoppingCartController(){
        log.info("清空购物车");
        shoppingCartService.vacantShoppingCartService();
        return Result.success();
    }
}
