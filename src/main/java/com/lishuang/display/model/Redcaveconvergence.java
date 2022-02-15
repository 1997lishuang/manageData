package com.lishuang.display.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author 李爽
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Redcaveconvergence extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String monitorPlace;

    private String instrumentNumber;

    private String station;

    private BigDecimal sumPositionOne;

    private BigDecimal sumPositionTwo;

    private BigDecimal weeklyVary;

    private BigDecimal rate;

    private BigDecimal crownAccumulation;

    private String remarks;

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date beginTime;


//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")

    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date endTime;


}
