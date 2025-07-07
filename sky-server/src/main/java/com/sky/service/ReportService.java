package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    //营业额统计
    public TurnoverReportVO turnoverStatService(LocalDate begin,LocalDate end);
    //用户信息统计
    public UserReportVO userStatService(LocalDate begin,LocalDate end);
    //订单信息统计
    public OrderReportVO orderStatService(LocalDate begin,LocalDate end);
}
