package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    //处理超时订单的方法
    @Scheduled(cron = "0 0/1 * * * ? ")//每分钟触发一次
    public void processTimeOutOrder(){
        log.info("定时处理超时订单:{}", LocalDateTime.now());
        //查询订单表，查询下单时间已经超过15分钟
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orderList = orderMapper.selectByStatusAndTimeMapper(Orders.PENDING_PAYMENT, localDateTime);
        //遍历集合设置订单的取消时间，状态，取消理由，同时更新数据库
        for(Orders order : orderList){
            order.setCancelReason("订单超时未支付");
            order.setCancelTime(LocalDateTime.now());
            order.setStatus(Orders.CANCELLED);
            orderMapper.modifyOrderMapper(order);
        }
    }

    //处理一直处于派送中的订单
    //    @Scheduled(cron = "0/5 * * * * ? ")//测试用例，每五秒触发一次
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点触发一次，默认1点之后处于打烊状态
    public void processDeliverOrder(){
        log.info("定时处理派送中的订单:{}", LocalDateTime.now());
        //查出前一天一直处理派送中的所有订单
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.selectByStatusAndTimeMapper(Orders.DELIVERY_IN_PROGRESS, localDateTime);
        //设置订单状态，同时更新数据库
        for(Orders orders : ordersList){
            orders.setStatus(Orders.CANCELLED);
            orderMapper.modifyOrderMapper(orders);
        }
    }
}
