package com.sky.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(rollbackFor = Exception.class)
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private  DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
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
}
