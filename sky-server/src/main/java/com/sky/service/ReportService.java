package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {
    //营业额统计
    public TurnoverReportVO turnoverStatService(LocalDate begin,LocalDate end);
}
