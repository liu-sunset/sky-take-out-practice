package com.sky.service.impl;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    //新增套餐
    @Override
    public void addSetMealService(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.addSetMealMapper(setmeal);
    }

//    根据ID查询套餐
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SetmealVO selectSetmealByIdService(long id) {
        //查询基本的套餐的基本信息
        Setmeal setmeal = setmealMapper.selectSetmealByIdMapper(id);
        //查询套餐中的单品信息
        List<SetmealDish> setmealDishes = setMealDishMapper.selectSetmealDishByIdMapper(id);
        //封装成一个vo
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }
}
