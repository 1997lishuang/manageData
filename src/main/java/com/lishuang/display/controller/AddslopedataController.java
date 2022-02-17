package com.lishuang.display.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lishuang.display.commonutils.R;
import com.lishuang.display.model.Addslopedata;
import com.lishuang.display.service.impl.AddslopedataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李爽
 * @since 2022-02-16
 */
@RestController
@RequestMapping("/addslopedata")
@CrossOrigin
public class AddslopedataController {
    @Autowired
    public AddslopedataServiceImpl addslopedataService;


      /*
            add  surface sideSlope deformation monitor

            增加德感边坡
     */

    @RequestMapping("/addDeGanSlopeData")
    public R addSideSlopeData(@RequestBody Addslopedata addslopedata){

        boolean save = addslopedataService.save(addslopedata);

        if(save){
            return R.ok();
        }else {
            return R.error();
        }

    }

}
