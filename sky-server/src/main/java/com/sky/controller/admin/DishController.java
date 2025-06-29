package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    //新增菜品
    @PostMapping
    public Result addDishController(@RequestBody DishDTO dishDTO){
        log.info("新增菜品");
        dishService.addDishService(dishDTO);
        //清理缓存数据
        clearCache("dish_"+dishDTO.getCategoryId());
        return Result.success();
    }

    //菜品的分页查询
    @GetMapping("/page")
    public Result<PageResult> selectDishPageController(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询参数：{}",dishPageQueryDTO);
        PageResult pageResult = dishService.selectDishPageService(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    //菜品的批量删除
    @DeleteMapping
    public Result deleteDishController(@RequestParam List<Long> ids){
        log.info("菜品批量删除的ID：{}",ids);
        dishService.deleteDishService(ids);
        clearCache("dish_*");
        return Result.success();
    }

    //菜品的查询回显
    @GetMapping("/{id}")
    public Result<DishVO> selectByIdController(@PathVariable long id){
        log.info("菜品的查询回显id：{}",id);
        DishVO dishVO = dishService.selectByIdService(id);
        return Result.success(dishVO);
    }

    //修改菜品信息
    @PutMapping
    public Result updateDishController(@RequestBody DishDTO dishDTO){
        log.info("修改菜品信息:{}",dishDTO);
        dishService.updateDishService(dishDTO);
        clearCache("dish_*");
        return Result.success();
    }

    @GetMapping("/list")
    public Result selectDishByCategoryIdController(long categoryId){
        log.info("根据套餐ID查询旗下的餐品，套餐ID是：{}",categoryId);
        List<Dish> dishList = dishService.selectDishByCategoryIdService(categoryId);
        return Result.success(dishList);
    }

    @PostMapping("status/{status}")
    public Result modifyDishStatusController(@PathVariable Integer status,long id){
        log.info("修改菜品ID为{}的售卖状态为{}",id,status==1 ? "起售" : "停售");
        dishService.modifyDishStatusService(status,id);
        //清理所有的缓存
        clearCache("dish_*");
        return Result.success();
    }


    //清理缓存数据
    private void clearCache(String pattern){
        //删除reids中所有dish_开头的key的数据
        Set keys = redisTemplate.keys(pattern);
        assert keys != null;
        redisTemplate.delete(keys);//删除方法不支持通配符
    }
}
