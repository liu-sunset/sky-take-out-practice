package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    //新增菜品
    @PostMapping
    public Result addDishController(@RequestBody DishDTO dishDTO){
        log.info("新增菜品");
        dishService.addDishService(dishDTO);
        return Result.success();
    }

    //菜品的分页查询
    @GetMapping("/page")
    public Result<PageResult> selectDishPageController(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询参数：{}",dishPageQueryDTO);
        PageResult pageResult = dishService.selectDishPageService(dishPageQueryDTO);
        return Result.success(pageResult);
    }
}
