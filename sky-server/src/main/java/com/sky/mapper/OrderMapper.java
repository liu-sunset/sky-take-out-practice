package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.service.OrderService;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    //添加订单信息
    public void addOrderShoppingCartMapper(Orders orders);
    //通过订单状态和下单时间查询订单
    public List<Orders> selectByStatusAndTimeMapper(Integer status, LocalDateTime orderTime);
    //修改订单信息
    public void modifyOrderMapper(Orders orders);
    //根据订单号查询订单
    public Orders getByNumber(String orderNumber);
    //根据订单ID查询订单
    public Orders selectOrderByIdMapper(long id);
    //统计营业额
    public Double turnoverStatMapper(Integer status, LocalDateTime begin,LocalDateTime end);
    //统计某个时间段内所有的订单的数量
    public Integer allOrderStatMapper(LocalDateTime begin,LocalDateTime end);
    //统计某个时间段内有效的订单的数量
    public Integer allValidStatMapper(Integer status,LocalDateTime begin,LocalDateTime end);
    //统计销量前十
    @MapKey("name")
    public Map<String,Integer> top10StatMapper(LocalDateTime begin,LocalDateTime end);
}
