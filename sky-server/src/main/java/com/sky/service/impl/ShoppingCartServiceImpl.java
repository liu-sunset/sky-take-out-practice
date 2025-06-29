package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;
    //添加购物车
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addShoppingCartService(ShoppingCartDTO shoppingCartDTO) {
        //封装一个购物车对象
        ShoppingCart shoppingCart = new ShoppingCart().builder()
                .dishId(shoppingCartDTO.getDishId())
                .dishFlavor(shoppingCartDTO.getDishFlavor())
                .setmealId(shoppingCartDTO.getSetmealId())
                .userId(BaseContext.getCurrentId())
                .build();
        //查找数据库中是否已经存在这条数据
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectShoppingCartByShoppingCartDTO(shoppingCart);
        //如果数据库中没有数据,插入数据
        if(shoppingCartList==null||shoppingCartList.isEmpty()){
            //如果添加购物车的是套餐
            if(shoppingCart.getSetmealId()!=null){
                Setmeal setmeal = setmealMapper.selectSetmealByIdMapper(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getName());
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setCreateTime(LocalDateTime.now());
            }else {
                DishVO dish = dishMapper.selectByIdMapper(shoppingCartDTO.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getName());
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setCreateTime(LocalDateTime.now());
            }
            shoppingCartMapper.addShoppingCartMapper(shoppingCart);
        }else{
            ShoppingCart shoppingCart1 = shoppingCartList.get(0);
            shoppingCart1.setNumber(shoppingCart1.getNumber()+1);
            shoppingCartMapper.modifyShoppingCartMapper(shoppingCart1);
        }
    }
}
