package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    //添加订单信息
    public void addOrderShoppingCartMapper(Orders orders);
    //通过订单状态和下单时间查询订单
    public List<Orders> selectByStatusAndTimeMapper(Integer status, LocalDateTime orderTime);
    //修改订单信息
    public void modifyOrderMapper(Orders orders);
}
