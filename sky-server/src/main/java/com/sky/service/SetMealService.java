package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetMealService {
    //新增套餐
    public void addSetMealService(SetmealDTO setmealDTO);
    //根据ID查询套餐
    public SetmealVO selectSetmealByIdService(long id);
    //套餐的分页查询
    public PageResult selectSetmealPageService(SetmealPageQueryDTO setmealPageQueryDTO);
}
