package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    //营业额统计
    @Override
    public TurnoverReportVO turnoverStatService(LocalDate begin, LocalDate end) {
        List<LocalDate> beginList = new ArrayList<>();
        List<Double> moneyList = new ArrayList<>();
        beginList.add(begin);
        while(!begin.equals(end)){
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

    //用户信息统计
    @Override
    public UserReportVO userStatService(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = new ArrayList<>();
        List<Long> allUserList = new ArrayList<>();
        List<Long> newAddUserList = new ArrayList<>();
        localDateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }
        for(LocalDate localDate : localDateList){
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime());
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalDateTime.MAX.toLocalTime());
            Long allUser = userMapper.userAllStatMapper(beginTime, endTime);
            Long newAddUser = userMapper.userNewAddStatMapper(beginTime, endTime);
            allUser = allUser == null ? 0L : allUser;
            newAddUser = newAddUser == null ? 0L : newAddUser;
            allUserList.add(allUser);
            newAddUserList.add(newAddUser);
        }
        String dateList = StringUtils.join(localDateList,",");
        String newUserList = StringUtils.join(newAddUserList,",");
        String totalUserList = StringUtils.join(allUserList,",");
        return UserReportVO.builder()
                .dateList(dateList)
                .newUserList(newUserList)
                .totalUserList(totalUserList)
                .build();
    }

    @Override
    public OrderReportVO orderStatService(LocalDate begin, LocalDate end) {
        List<LocalDate> stringDateList = new ArrayList<>();
        List<Integer> stringOrderCountList = new ArrayList<>();
        List<Integer> stringValidOrderCountList = new ArrayList<>();
        double orderCompletionRate = 0.0;
        int totalOrderCount = 0;
        int validOrderCount = 0;
        stringDateList.add(begin);
        while (begin.equals(end)){
            begin.plusDays(1);
            stringDateList.add(begin);
        }
        for(LocalDate localDate : stringDateList){
            LocalDateTime beginDateTime = LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime());
            LocalDateTime endDateTime = LocalDateTime.of(localDate,LocalDateTime.MAX.toLocalTime());
            Integer allOrder = orderMapper.allOrderStatMapper(beginDateTime,endDateTime);
            Integer allValidOrder = orderMapper.allValidStatMapper(Orders.COMPLETED,beginDateTime,endDateTime);
            allOrder = allOrder == null ? 0 : allOrder;
            allValidOrder = allValidOrder == null ? 0 : allValidOrder;
            stringOrderCountList.add(allOrder);
            stringValidOrderCountList.add(allValidOrder);
        }
        //计算时间段内单量总和
        for(Integer l : stringOrderCountList){
            totalOrderCount += l;
        }
        //计算时间段内有效单量总和
        for(Integer l : stringValidOrderCountList){
            validOrderCount += l;
        }
        //计算有效单率
        if(totalOrderCount!=0){
            orderCompletionRate = (validOrderCount*1.0)/(totalOrderCount*1.0);
        }
        //封装VO返回结果
        String dateList = StringUtils.join(stringDateList,",");
        String orderCountList = StringUtils.join(stringOrderCountList,",");
        String validOrderCountList = StringUtils.join(stringValidOrderCountList,",");
        return OrderReportVO.builder()
                .dateList(dateList)
                .orderCountList(orderCountList)
                .validOrderCountList(validOrderCountList)
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    //销量前十统计
    @Override
    public SalesTop10ReportVO top10StatService(LocalDate begin, LocalDate end) {
        List<String> stringNameList = new ArrayList<>();
        List<Integer> stringNumberList = new ArrayList<>();
        LocalDateTime beginDateTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(end, LocalTime.MAX);
        Map<String, Integer> stringIntegerMap = orderMapper.top10StatMapper(beginDateTime, endDateTime);
        if(stringIntegerMap==null){
           return new SalesTop10ReportVO("","0");
        }
        Set<String> strings = stringIntegerMap.keySet();
        for(String s : strings){
            stringNameList.add(s);
        }
        stringNumberList = new ArrayList<>(stringIntegerMap.values());
        String nameList = StringUtils.join(stringNameList, ",");
        String numberList = StringUtils.join(stringNumberList, ",");
        return SalesTop10ReportVO.builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }
}
