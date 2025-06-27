package com.sky.service;


import com.aliyuncs.endpoint.LocalConfigGlobalEndpointResolver;
import com.sky.dto.SetmealDTO;
import com.sky.vo.SetmealVO;

public interface SetMealService {
    //新增套餐
    public void addSetMealService(SetmealDTO setmealDTO);
    //根据ID查询套餐
    public SetmealVO selectSetmealByIdService(long id);
}
