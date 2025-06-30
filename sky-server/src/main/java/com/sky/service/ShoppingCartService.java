package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    //添加购物车
    public void addShoppingCartService(ShoppingCartDTO shoppingCartDTO);
    //查看购物车
    public List<ShoppingCart> selectShoppingCartService();
    //清空购物车
    public void vacantShoppingCartService();
    //减少购物车中物品的数量
    public void deleteShoppingCartService(ShoppingCartDTO shoppingCartDTO);
}
