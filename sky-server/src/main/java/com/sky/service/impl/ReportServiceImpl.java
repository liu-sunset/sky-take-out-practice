package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    //营业额统计
    @Override
    public TurnoverReportVO turnoverStatService(LocalDate begin, LocalDate end) {
        List<LocalDate> beginList = new ArrayList<>();
        List<Double> moneyList = new ArrayList<>();
        beginList.add(begin);
        while(begin.equals(end)){
            begin = begin.plusDays(1);
            beginList.add(begin);
        }
        //根据状态，下单时间查询符合的订单
        for(LocalDate localDate : beginList){
            LocalDateTime todayBegin = LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime());
            LocalDateTime todayEnd = LocalDateTime.of(localDate, LocalDateTime.MAX.toLocalTime());
            Double turnover = orderMapper.turnoverStatMapper(Orders.COMPLETED, todayBegin, todayEnd);
            //空指针判断
            turnover = turnover==null ? 0.00 : turnover;
            moneyList.add(turnover);
        }
        String dateList = StringUtils.join(beginList, ",");
        String turnoverList = StringUtils.join(moneyList, ",");
        return TurnoverReportVO.builder()
                .dateList(dateList)
                .turnoverList(turnoverList)
                .build();
    }
}
