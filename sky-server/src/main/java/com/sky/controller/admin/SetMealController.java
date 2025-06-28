package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @PostMapping
    public Result<Object> addSetMealController(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐信息：{}",setmealDTO);
        setMealService.addSetMealService(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> selectSetmealByIdController(@PathVariable long id){
        log.info("根据ID查询套餐，id:{}",id);
        SetmealVO setmealVO = setMealService.selectSetmealByIdService(id);
        return Result.success(setmealVO);
    }

    @GetMapping("/page")
    public Result<PageResult> selectSetmealPageController(SetmealPageQueryDTO setmealPageQueryDTO){
            log.info("套餐的分页查询：{}",setmealPageQueryDTO);
        PageResult pageResult = setMealService.selectSetmealPageService(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
}
