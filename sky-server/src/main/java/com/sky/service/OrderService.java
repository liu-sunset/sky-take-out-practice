package com.sky.service;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    //用户提交的订单信息
    public OrderSubmitVO addOrderShoppingCartService(OrdersSubmitDTO ordersSubmitDTO);
    //支付成功，修改订单状态
    void paySuccess(String outTradeNo);
}
