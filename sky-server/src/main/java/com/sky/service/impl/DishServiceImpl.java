package com.sky.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.util.ElementScanner6;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Transactional(rollbackFor = Exception.class)
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private  DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
//    新增菜品
    @Override
    public void addDishService(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //新增菜品
        dishMapper.addDish(dish);
        //新增菜品的口味
        List<DishFlavor> dishFlavorList = dish.getFlavors();
        if(dishFlavorList!=null&&!dishFlavorList.isEmpty()){
            dishFlavorList.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
            dishFlavorMapper.addDishFlavor(dishFlavorList);
        }
    }

    @Override
    public PageResult selectDishPageService(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page =  dishMapper.selectDishPageMapper(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //删除菜品
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDishService(List<Long> ids) {
        //判断是否已经处在启用状态
        //查询出所有没有启用的菜品ID
        List<Long> longs = dishMapper.selectDishStatusMapper(ids);
        //如果部分菜品已经启用，返回不能删除
        if(longs.size()!=ids.size()){
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }else{
            //如果没有启用，判断是否处在套餐中
            List<Long> longs1 = setMealDishMapper.selectByDishIdMapper(ids);
            if(longs1==null|| longs1.isEmpty()){
                //如果不在套餐中可以删除
                //删除菜品
                dishMapper.deleteDishMapper(ids);
                //删除口味
                dishFlavorMapper.deleteDishFlavorMapper(ids);
            }else {
                //如果处在套餐中返回不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        }
    }

    //菜品的查询回显
    @Override
    public DishVO selectByIdService(long id) {
         //插叙菜品的基本信息
        DishVO dishVO = dishMapper.selectByIdMapper(id);
        //查询菜品对应的口味
        List<DishFlavor> dishFlavorList = dishFlavorMapper.selectByIdMapper(id);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    //修改菜品信息
    @Override
    public void updateDishService(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //修改菜品的基本信息
        dishMapper.updateDishMapper(dish);
        //删除对应的所有口味
        dishFlavorMapper.deleteByIdMapper(dish.getId());
        //重新添加口味
        if(dish.getFlavors()!=null&&!dish.getFlavors().isEmpty()){
            dishFlavorMapper.addDishFlavor(dish.getFlavors());
        }
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.selectByIdMapper(d.getId());
            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    //根据套餐ID查询旗下对应的餐品
    @Override
    public List<Dish> selectDishByCategoryIdService(long categoryId) {
        return dishMapper.selectDishByCategoryIdMapper(categoryId);
    }

    //修改菜品的售卖状态
    @Override
    public void modifyDishStatusService(Integer status, long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.updateDishMapper(dish);
    }
}
