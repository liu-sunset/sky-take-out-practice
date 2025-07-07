package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;


@RestController
@Slf4j
@RequestMapping("admin/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/turnoverStatistics")
    public Result turnoverStatController(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("营业额统计,开始日期：{},结束日期：{}", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.turnoverStatService(begin, end);
        return Result.success(turnoverReportVO);
    }

    @GetMapping("/userStatistics")
    public Result userStatController(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("用户信息统计,开始时间：{},结束时间：{}", begin, end);
        UserReportVO userReportVO = reportService.userStatService(begin, end);
        return Result.success(userReportVO);
    }

    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderStatController(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("订单信息统计,开始时间：{},结束时间：{}", begin, end);
        OrderReportVO orderReportVO = reportService.orderStatService(begin, end);
        return Result.success(orderReportVO);
    }
}