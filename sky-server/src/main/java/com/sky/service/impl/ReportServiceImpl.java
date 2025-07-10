package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
    @Autowired
    private WorkspaceService workspaceService;
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
        List<Integer> newAddUserList = new ArrayList<>();
        localDateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }
        for(LocalDate localDate : localDateList){
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime());
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalDateTime.MAX.toLocalTime());
            Long allUser = userMapper.userAllStatMapper(beginTime, endTime);
            Integer newAddUser = userMapper.userNewAddStatMapper(beginTime, endTime);
            allUser = allUser == null ? 0L : allUser;
            newAddUser = newAddUser == null ? 0 : newAddUser;
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

    //导出30天的运营数据
    @Override
    public void exportDateService(HttpServletResponse response) throws IOException {
        //查询数据库获取营业数据
        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);

        BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalDateTime.MAX.toLocalTime()));
        //通过poi将数据写入Excel文件
        InputStream inputstream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        XSSFWorkbook excel = new XSSFWorkbook(inputstream);
        XSSFSheet sheet = excel.getSheet("sheet1");
        sheet.getRow(1).getCell(1).setCellValue("数据时间从:"+begin+"至"+end);
        sheet.getRow(3).getCell(2).setCellValue(businessData.getTurnover());
        sheet.getRow(3).getCell(4).setCellValue(businessData.getOrderCompletionRate());
        sheet.getRow(3).getCell(6).setCellValue(businessData.getNewUsers());
        sheet.getRow(4).getCell(2).setCellValue(businessData.getValidOrderCount());
        sheet.getRow(4).getCell(4).setCellValue(businessData.getUnitPrice());
        //填充明细数据
        for (int i = 0; i < 30; i++) {
            LocalDate date = begin.plusDays(i);
            BusinessDataVO businessData1 = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
            sheet.getRow(7+i).getCell(1).setCellValue(date.toString());
            sheet.getRow(7+i).getCell(2).setCellValue(businessData1.getTurnover());
            sheet.getRow(7+i).getCell(3).setCellValue(businessData1.getValidOrderCount());
            sheet.getRow(7+i).getCell(4).setCellValue(businessData1.getOrderCompletionRate());
            sheet.getRow(7+i).getCell(5).setCellValue(businessData1.getUnitPrice());
            sheet.getRow(7+i).getCell(6).setCellValue(businessData1.getNewUsers());
        }
        //通过输出流将Excel文件返回客户端
        ServletOutputStream outputStream = response.getOutputStream();
        excel.write(outputStream);
        //释放资源
        outputStream.close();
        inputstream.close();
        excel.close();
    }
}
