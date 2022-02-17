package com.lishuang.display.controller;


import com.lishuang.display.commonutils.R;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李爽
 * @since 2022-02-16
 */
@RestController
@RequestMapping("/transfer-slope-data")
public class TransferSlopeDataController {


    public R transferSlopeData(){



        return R.ok();

    }

}
