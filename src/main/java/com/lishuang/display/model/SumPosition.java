package com.lishuang.display.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 李爽
 * @since 2022-02-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SumPosition extends Model {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String transferDirection;

    private LocalDate transferShowTime;

    private String monitorPlace;

    private String instrumentNumber;

    private BigDecimal sumPositionValue;

    private String height;


}
