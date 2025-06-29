package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    //新增套餐
    public void addSetMealService(SetmealDTO setmealDTO);
    //根据ID查询套餐
    public SetmealVO selectSetmealByIdService(long id);
    //套餐的分页查询
    public PageResult selectSetmealPageService(SetmealPageQueryDTO setmealPageQueryDTO);
    //批量删除套餐
    public void deleteSetmealService(List<Long> ids);
    //根据ID修改套餐
    public void modifySetmealService(SetmealDTO setmealDTO);
//     条件查询
    public List<Setmeal> list(Setmeal setmeal);
//    根据id查询菜品选项
    public List<DishItemVO> getDishItemById(Long id);
    //修改套餐的状态
    public void modifySetmealStatusService(Integer status,long id);
}
