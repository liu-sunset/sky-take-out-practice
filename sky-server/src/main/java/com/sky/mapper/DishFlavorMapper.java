package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    //新增菜品的口味
    public void addDishFlavor(List<DishFlavor> dishFlavorList);
    //删除菜品对应的口味
    public void deleteDishFlavorMapper(List<Long> ids);
    // 根据ID查询菜品对应的口味信息
    public List<DishFlavor> selectByIdMapper(long id);
    //根据菜品Id删除对应的口味
    public void deleteByIdMapper(long id);
}
