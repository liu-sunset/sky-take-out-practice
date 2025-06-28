package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import java.util.List;

public interface DishService {
    //新增菜品
    public void addDishService(DishDTO dishDTO);
    //菜品的分页查询
    public PageResult selectDishPageService(DishPageQueryDTO dishPageQueryDTO);
    //菜品的批量删除
    public void deleteDishService(List<Long> ids);
    //菜品的查询回显
    public DishVO selectByIdService(long id);
    //修改菜品信息
    public void updateDishService(DishDTO dishDTO);
    //根据分类id查询菜品
    List<DishVO> listWithFlavor(Dish dish);
    //根据套餐ID查询旗下的餐品
    public List<Dish> selectDishByCategoryIdService(long categoryId);
}
