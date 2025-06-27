package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    //判断批量删除的菜品中是否有菜品包含在套餐中
    public List<Long> selectByDishIdMapper(List<Long> ids);
    //根据套餐ID查询套餐中所有单品信息
    public List<SetmealDish> selectSetmealDishByIdMapper(long setmealId);
}
