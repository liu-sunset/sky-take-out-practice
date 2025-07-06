package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
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
}
