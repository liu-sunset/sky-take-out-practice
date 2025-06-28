package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    //新增套餐
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSetMealService(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.addSetMealMapper(setmeal);
        setmealDTO.getSetmealDishes().forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
        setMealDishMapper.addSetmealDishMapper(setmealDTO.getSetmealDishes());
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

    //套餐的分页查询
    @Override
    public PageResult selectSetmealPageService(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setmealMapper.selectSetmealPageMapper(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //批量删除套餐
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSetmealService(List<Long> ids) {
        //批量删除Setmeal表中信息
        setmealMapper.deleteSetmealMapper(ids);
        //批量删除Setmeal_Dish表中信息
        setMealDishMapper.deleteSetmealDishMapper(ids);
    }

    //根据ID修改套餐
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifySetmealService(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //修改套餐基本信息
        setmealMapper.modifySetmealMapper(setmeal);
        //修改套餐中餐品信息
        List<Long> id = new ArrayList<>();
        id.add(setmeal.getId());
        setMealDishMapper.deleteSetmealDishMapper(id);
        setMealDishMapper.addSetmealDishMapper(setmealDTO.getSetmealDishes());
    }
}
