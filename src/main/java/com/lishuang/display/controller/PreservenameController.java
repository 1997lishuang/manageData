package com.lishuang.display.controller;


import com.lishuang.display.commonutils.R;
import com.lishuang.display.model.Preservename;
import com.lishuang.display.service.impl.PreservenameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李爽
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/preservename")
@CrossOrigin
public class PreservenameController {


   @Autowired
   public PreservenameServiceImpl preservenameService;


    @RequestMapping("/findTunnelName")
    @ResponseBody
    public R findTunnelName(){

        List<Preservename> tunnalLists = preservenameService.list();

        if(tunnalLists.size()>0){
            return R.ok().data("tunnelNameList",tunnalLists);
        }

        return R.error();
    }





}
