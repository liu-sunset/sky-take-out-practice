package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import com.sky.service.impl.ShoppingCartServiceImpl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    //查找数据库中是否已经存在这条数据
    public List<ShoppingCart> selectShoppingCartByShoppingCartDTO(ShoppingCart shoppingCart);
    //新增数据到购物车
    public void addShoppingCartMapper(ShoppingCart shoppingCart);
    //修改购物车中物品信息
    public void modifyShoppingCartMapper(ShoppingCart shoppingCart);
    //清空购物车
    public void deleteShoppingCartMapper(long userId);
}
