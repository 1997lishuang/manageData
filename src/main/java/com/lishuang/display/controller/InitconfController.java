package com.lishuang.display.controller;


import com.lishuang.display.commonutils.R;
import com.lishuang.display.model.Initconf;
import com.lishuang.display.service.impl.InitconfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李爽
 * @since 2022-01-14
 */
@RestController
@CrossOrigin()
@RequestMapping("/initconf")
public class InitconfController {

    @Autowired
    public InitconfServiceImpl initconfService;

    @RequestMapping("/addInitconf")
    public R addInitconf(@RequestBody Initconf initconf){


        boolean save = initconfService.save(initconf);

        if(!save){
            return R.error();
        }

        return R.ok();
    }

}
