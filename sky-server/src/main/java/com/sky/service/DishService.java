package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    //新增菜品
    public void addDishService(DishDTO dishDTO);
    //菜品的分页查询
    public PageResult selectDishPageService(DishPageQueryDTO dishPageQueryDTO);
}
