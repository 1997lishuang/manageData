package com.lishuang.display.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@ExcelTarget("")
public class ReadData implements Serializable {

    @Excel(name = "监测部位")
    private String monitorPlace;

    @Excel(name = "设计编号")
    private String instrumentNumber;

    @Excel(name = "桩号")
    private String station;

    @Excel(name = "累计收敛值一(mm)")
    private BigDecimal sumPositionOne;

    @Excel(name = "累计收敛值二(mm)")
    private BigDecimal sumPositionTwo;

    @Excel(name = "周变化量(mm)")
    private BigDecimal weeklyVary;

    @Excel(name = "周变化率(mm)")
    private BigDecimal rate;

    @Excel(name = "顶拱累计沉降(mm)")
    private BigDecimal crownAccumulation;

    @Excel(name = "备注")
    private String remarks;

    @Excel(name = "观察起始日期")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    public Date begin_time;

    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @Excel(name = "观测结束日期")
    public Date end_time;

}
