package com.lishuang.display.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.word.WordExportUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lishuang.display.commonutils.R;
import com.lishuang.display.mapper.RedcaveconvergenceMapper;
import com.lishuang.display.model.ReadData;
import com.lishuang.display.model.Redcaveconvergence;
import com.lishuang.display.service.impl.RedcaveconvergenceServiceImpl;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李爽
 * @since 2022-01-08
 */
@RestController
@CrossOrigin()
@RequestMapping("/redcaveconvergence")
public class RedcaveconvergenceController {

    @Autowired
    public RedcaveconvergenceServiceImpl redcaveconvergenceService;


    @Autowired
    public RedcaveconvergenceMapper redcaveconvergenceMapper;

    @RequestMapping("/findAllRedcav")
    private R getAllRedcav() throws Exception {
        /*
        从数据库中查找 Cvgshow表中查找到 原始的数据 将其用相应的公式转化后 保存到 redcaveconvergence表中
        */

        List<Redcaveconvergence> list = redcaveconvergenceService.list();
        Map<String,Object> map = new HashMap<>();
        for(int i= 0;i<list.size();i++){
            if(list.get(i).getInstrumentNumber().equals("L12")&&
                    list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                    list.get(i).getStation().equals("K4+520")){
                map.put("l12k5201",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12k5202",format);
                map.put("l12k520wvary",list.get(i).getWeeklyVary());
                map.put("l12k520rate",list.get(i).getRate());
                map.put("l12k520sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K45201",minValue.getWeeklyVary());
                map.put("K45202",maxValue.getWeeklyVary());

            }
            else if(list.get(i).getInstrumentNumber().equals("L13")&&
                    list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                    list.get(i).getStation().equals("K4+520")){
                map.put("l13k5201",list.get(i).getSumPositionOne());
                map.put("l13k5202",list.get(i).getSumPositionTwo());
                map.put("l13k520wvary",list.get(i).getWeeklyVary());
                map.put("l13k520rate",list.get(i).getRate());
//                    map.put("l13k520sum",list.get(i).getCrownAccumulation());

            }else if(list.get(i).getInstrumentNumber().equals("L23")&&
                    list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                    list.get(i).getStation().equals("K4+520")){

                map.put("l23k5201",list.get(i).getSumPositionOne());
                map.put("l23k5202",list.get(i).getSumPositionTwo());
                map.put("l23k520wvary",list.get(i).getWeeklyVary());
                map.put("l23k520rate",list.get(i).getRate());
//                    map.put("l23k520sum",list.get(i).getCrownAccumulation());

            }else if(list.get(i).getInstrumentNumber().equals("L12")&&
                    list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                    list.get(i).getStation().equals("K4+500")){

                    map.put("l12k5001",list.get(i).getSumPositionOne());
                    map.put("l12k5002",list.get(i).getSumPositionTwo());
                    map.put("l12k500wvary",list.get(i).getWeeklyVary());
                    map.put("l12k500rate",list.get(i).getRate());
                    map.put("l12k500sum",list.get(i).getCrownAccumulation());

                QueryWrapper wrapper500 = new QueryWrapper();
                wrapper500.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper500.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper500);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                System.out.println("11");
                map.put("K45001",minValue.getWeeklyVary());
                map.put("K45002",maxValue.getWeeklyVary());



            }else if(list.get(i).getInstrumentNumber().equals("L13")&&
                    list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                    list.get(i).getStation().equals("K4+500")){
                    map.put("l13k5001",list.get(i).getSumPositionOne());
                    map.put("l13k5002",list.get(i).getSumPositionTwo());
                    map.put("l13k500wvary",list.get(i).getWeeklyVary());
                    map.put("l13k500rate",list.get(i).getRate());

            }else if(list.get(i).getInstrumentNumber().equals("L23")&&
                    list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                    list.get(i).getStation().equals("K4+500")){

                    map.put("l23k5001",list.get(i).getSumPositionOne());
                    map.put("l23k5002",list.get(i).getSumPositionTwo());
                    map.put("l23k500wvary",list.get(i).getWeeklyVary());
                    map.put("l23k500rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+470")
            ){
                    map.put("l12k4701",list.get(i).getSumPositionOne());
                    map.put("l12k4702",list.get(i).getSumPositionTwo());
                    map.put("l12k470wvary",list.get(i).getWeeklyVary());
                    map.put("l12k470rate",list.get(i).getRate());
                    map.put("l12k470sum",list.get(i).getRate());


                QueryWrapper wrapper470 = new QueryWrapper();
                wrapper470.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper470.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper470);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K44701",minValue.getWeeklyVary());
                map.put("K44702",maxValue.getWeeklyVary());


            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+470")
            ){

                    map.put("l13k4701",list.get(i).getSumPositionOne());
                    map.put("l13k4702",list.get(i).getSumPositionTwo());
                    map.put("l13k470wvary",list.get(i).getWeeklyVary());
                    map.put("l13k470rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+470")
            ){
                map.put("l23k4701",list.get(i).getSumPositionOne());
                map.put("l23k4702",list.get(i).getSumPositionTwo());
                map.put("l23k470wvary",list.get(i).getWeeklyVary());
                map.put("l23k470rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+440")
            ){


                QueryWrapper wrapper440 = new QueryWrapper();
                wrapper440.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper440.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper440);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K44401",minValue.getWeeklyVary());
                map.put("K44402",maxValue.getWeeklyVary());

                map.put("l12k4401",list.get(i).getSumPositionOne());
                map.put("l12k4402",list.get(i).getSumPositionTwo());
                map.put("l12k440wvary",list.get(i).getWeeklyVary());
                map.put("l12k440rate",list.get(i).getRate());
                map.put("l12k440sum",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+440")
            ){
                map.put("l13k4401",list.get(i).getSumPositionOne());
                map.put("l13k4402",list.get(i).getSumPositionTwo());
                map.put("l13k440wvary",list.get(i).getWeeklyVary());
                map.put("l13k440rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+440")
            ){
                map.put("l23k4401",list.get(i).getSumPositionOne());
                map.put("l23k4402",list.get(i).getSumPositionTwo());
                map.put("l23k440wvary",list.get(i).getWeeklyVary());
                map.put("l23k440rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+380")
            ){
                map.put("l12k3801",list.get(i).getSumPositionOne());
                map.put("l12k3802",list.get(i).getSumPositionTwo());
                map.put("l12k380wvary",list.get(i).getWeeklyVary());
                map.put("l12k380rate",list.get(i).getRate());
                map.put("l12k380sum",list.get(i).getRate());



                QueryWrapper wrapper380 = new QueryWrapper();
                wrapper380.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper380.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper380);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K43801",minValue.getWeeklyVary());
                map.put("K43802",maxValue.getWeeklyVary());


            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+380")
            ){

                map.put("l13k3801",list.get(i).getSumPositionOne());
                map.put("l13k3802",list.get(i).getSumPositionTwo());
                map.put("l13k380wvary",list.get(i).getWeeklyVary());
                map.put("l13k380rate",list.get(i).getRate());


            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+380")
            ){
                map.put("l23k3801",list.get(i).getSumPositionOne());
                map.put("l23k3802",list.get(i).getSumPositionTwo());
                map.put("l23k380wvary",list.get(i).getWeeklyVary());
                map.put("l23k380rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+315")
            ){
                map.put("l12k3151",list.get(i).getSumPositionOne());
                map.put("l12k3152",list.get(i).getSumPositionTwo());
                map.put("l12k315wvary",list.get(i).getWeeklyVary());
                map.put("l12k315rate",list.get(i).getRate());
                map.put("l12k315sum",list.get(i).getRate());

                QueryWrapper wrapper315 = new QueryWrapper();
                wrapper315.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper315.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper315);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K43151",minValue.getWeeklyVary());
                map.put("K43152",maxValue.getWeeklyVary());


            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+315")
            ){
                map.put("l13k3151",list.get(i).getSumPositionOne());
                map.put("l13k3152",list.get(i).getSumPositionTwo());
                map.put("l13k315wvary",list.get(i).getWeeklyVary());
                map.put("l13k315rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+315")
            ){
                map.put("l23k3151",list.get(i).getSumPositionOne());
                map.put("l23k3152",list.get(i).getSumPositionTwo());
                map.put("l23k315wvary",list.get(i).getWeeklyVary());
                map.put("l23k315rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+270")
            ){
                QueryWrapper wrapper270 = new QueryWrapper();
                wrapper270.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper270.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper270);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K42701",minValue.getWeeklyVary());
                map.put("K42702",maxValue.getWeeklyVary());



                map.put("l12k2701",list.get(i).getSumPositionOne());
                map.put("l12k2702",list.get(i).getSumPositionTwo());
                map.put("l12k270wvary",list.get(i).getWeeklyVary());
                map.put("l12k270rate",list.get(i).getRate());
                map.put("l12k270sum",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+270")
            ){
                map.put("l13k2701",list.get(i).getSumPositionOne());
                map.put("l13k2702",list.get(i).getSumPositionTwo());
                map.put("l13k270wvary",list.get(i).getWeeklyVary());
                map.put("l13k270rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K4+270")
            ){
                map.put("l23k2701",list.get(i).getSumPositionOne());
                map.put("l23k2702",list.get(i).getSumPositionTwo());
                map.put("l23k270wvary",list.get(i).getWeeklyVary());
                map.put("l23k270rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+990")
            ){
                map.put("l12k9901",list.get(i).getSumPositionOne());
                map.put("l12k9902",list.get(i).getSumPositionTwo());
                map.put("l12k990wvary",list.get(i).getWeeklyVary());
                map.put("l12k990rate",list.get(i).getRate());
                map.put("l12k990sum",list.get(i).getRate());

                QueryWrapper wrapper990 = new QueryWrapper();
                wrapper990.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper990.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper990);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K49901",minValue.getWeeklyVary());
                map.put("K49902",maxValue.getWeeklyVary());




            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+990")
            ){
                map.put("l13k9901",list.get(i).getSumPositionOne());
                map.put("l13k9902",list.get(i).getSumPositionTwo());
                map.put("l13k990wvary",list.get(i).getWeeklyVary());
                map.put("l13k990rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+990")
            ){
                map.put("l23k9901",list.get(i).getSumPositionOne());
                map.put("l23k9902",list.get(i).getSumPositionTwo());
                map.put("l23k990wvary",list.get(i).getWeeklyVary());
                map.put("l23k990rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+940")
            ){
                map.put("l12k9401",list.get(i).getSumPositionOne());
                map.put("l12k9402",list.get(i).getSumPositionTwo());
                map.put("l12k940wvary",list.get(i).getWeeklyVary());
                map.put("l12k940rate",list.get(i).getRate());
                map.put("l12k940sum",list.get(i).getRate());

                QueryWrapper wrapper940 = new QueryWrapper();
                wrapper940.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper940.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper940);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K49401",minValue.getWeeklyVary());
                map.put("K49402",maxValue.getWeeklyVary());



            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+940")
            ){
                map.put("l13k9401",list.get(i).getSumPositionOne());
                map.put("l13k9402",list.get(i).getSumPositionTwo());
                map.put("l13k940wvary",list.get(i).getWeeklyVary());
                map.put("l13k940rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+940")
            ){
                map.put("l23k9401",list.get(i).getSumPositionOne());
                map.put("l23k9402",list.get(i).getSumPositionTwo());
                map.put("l23k940wvary",list.get(i).getWeeklyVary());
                map.put("l23k940rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+890")
            ){
                map.put("l12k8901",list.get(i).getSumPositionOne());
                map.put("l12k8902",list.get(i).getSumPositionTwo());
                map.put("l12k890wvary",list.get(i).getWeeklyVary());
                map.put("l12k890rate",list.get(i).getRate());
                map.put("l12k890sum",list.get(i).getRate());

                QueryWrapper wrapper890 = new QueryWrapper();
                wrapper890.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper890.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper890);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K48901",minValue.getWeeklyVary());
                map.put("K48902",maxValue.getWeeklyVary());



            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+890")
            ){
                map.put("l13k8901",list.get(i).getSumPositionOne());
                map.put("l13k8902",list.get(i).getSumPositionTwo());
                map.put("l13k890wvary",list.get(i).getWeeklyVary());
                map.put("l13k890rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+890")
            ){
                map.put("l23k8901",list.get(i).getSumPositionOne());
                map.put("l23k8902",list.get(i).getSumPositionTwo());
                map.put("l23k890wvary",list.get(i).getWeeklyVary());
                map.put("l23k890rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+840")
            ){

                map.put("l12k8401",list.get(i).getSumPositionOne());
                map.put("l12k8402",list.get(i).getSumPositionTwo());
                map.put("l12k840wvary",list.get(i).getWeeklyVary());
                map.put("l12k840rate",list.get(i).getRate());
                map.put("l12k840sum",list.get(i).getRate());

                QueryWrapper wrapper840 = new QueryWrapper();
                wrapper840.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper840.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper840);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K48401",minValue.getWeeklyVary());
                map.put("K48402",maxValue.getWeeklyVary());


            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+840")
            ){
                map.put("l13k8401",list.get(i).getSumPositionOne());
                map.put("l13k8402",list.get(i).getSumPositionTwo());
                map.put("l13k840wvary",list.get(i).getWeeklyVary());
                map.put("l13k840rate",list.get(i).getRate());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K2+840")
            ){
                map.put("l23k8401",list.get(i).getSumPositionOne());
                map.put("l23k8402",list.get(i).getSumPositionTwo());
                map.put("l23k840wvary",list.get(i).getWeeklyVary());
                map.put("l23k840rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K3+040")
            ){
                map.put("l12k0401",list.get(i).getSumPositionOne());
                map.put("l12k0402",list.get(i).getSumPositionTwo());
                map.put("l12k040wvary",list.get(i).getWeeklyVary());
                map.put("l12k040rate",list.get(i).getRate());
                map.put("l12k040sum",list.get(i).getRate());


                QueryWrapper wrapper040 = new QueryWrapper();
                wrapper040.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper040.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper040);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("K40401",minValue.getWeeklyVary());
                map.put("K40402",maxValue.getWeeklyVary());

            }else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K3+040")
            ){
                map.put("l13k0401",list.get(i).getSumPositionOne());
                map.put("l13k0402",list.get(i).getSumPositionTwo());
                map.put("l13k040wvary",list.get(i).getWeeklyVary());
                map.put("l13k040rate",list.get(i).getRate());
            }else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
                            list.get(i).getStation().equals("K3+040")
            ){
                map.put("l23k0401",list.get(i).getSumPositionOne());
                map.put("l23k0402",list.get(i).getSumPositionTwo());
                map.put("l23k040wvary",list.get(i).getWeeklyVary());
                map.put("l23k040rate",list.get(i).getRate());
            }

//            else if(
//                    list.get(i).getInstrumentNumber().equals("L12")&&
//                            list.get(i).getMonitorPlace().equals("红岩隧洞")&&
//                            list.get(i).getStation().equals("HY1#K0+000")
//            ){
//                map.put("l12hy00001",list.get(i).getSumPositionOne());
//                map.put("l12hy00002",list.get(i).getSumPositionTwo());
//                map.put("l12hy0000wvary",list.get(i).getWeeklyVary());
//                map.put("l12hy0000rate",list.get(i).getRate());
//                map.put("l12hy0000sum",list.get(i).getRate());
//            }

            else if(list.get(i).getInstrumentNumber().equals("L12")&&
                    list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                    list.get(i).getStation().equals("HY1#K0+000")){
                map.put("l12HY00001",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY00002",format);
                map.put("l12HY0000wvary",list.get(i).getWeeklyVary());
                map.put("l12k520rate",list.get(i).getRate());
                map.put("l12HY0000sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY0000min",minValue.getWeeklyVary());
                map.put("l12HY0000max",maxValue.getWeeklyVary());

            }
            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+000")
            ){
                map.put("l13HY00001",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY00002",format);
                map.put("l13HY0000wvary",list.get(i).getWeeklyVary());
                map.put("l13HY0000rate",list.get(i).getRate());
                map.put("l13HY0000sum",list.get(i).getCrownAccumulation());


            }
            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+000")

            ){

                map.put("l23HY00001",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY00002",format);
                map.put("l23HY0000wvary",list.get(i).getWeeklyVary());
                map.put("l23k520rate",list.get(i).getRate());
                map.put("l23HY0000sum",list.get(i).getCrownAccumulation());


            }
            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+050")
            ){
                map.put("l13HY00501",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY00502",format);
                map.put("l13HY0050wvary",list.get(i).getWeeklyVary());
                map.put("l13HY0050rate",list.get(i).getRate());
                map.put("l13HY0050sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l13HY0050min",minValue.getWeeklyVary());
                map.put("l13HY0050max",maxValue.getWeeklyVary());
            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+050")
            ){
                map.put("l12HY00501",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY00502",format);
                map.put("l12HY0050wvary",list.get(i).getWeeklyVary());
                map.put("l13HY0050rate",list.get(i).getRate());
                map.put("l12HY0050sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+050")
            ){
                map.put("l23HY00501",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY00502",format);
                map.put("l23HY0050wvary",list.get(i).getWeeklyVary());
                map.put("l13HY0050rate",list.get(i).getRate());
                map.put("l23HY0050sum",list.get(i).getCrownAccumulation());

            }


            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+100")
            ){
                map.put("l12HY01001",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY01002",format);
                map.put("l12HY0100wvary",list.get(i).getWeeklyVary());
                map.put("l12HY0100rate",list.get(i).getRate());
                map.put("l12HY0100sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY0100min",minValue.getWeeklyVary());
                map.put("l12HY0100max",maxValue.getWeeklyVary());
            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+100")
            ){
                map.put("l13HY01001",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY01002",format);
                map.put("l13HY0100wvary",list.get(i).getWeeklyVary());
                map.put("l13HY0100rate",list.get(i).getRate());
                map.put("l13HY0100sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+100")
            ){
                map.put("l23HY01001",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY01002",format);
                map.put("l23HY0100wvary",list.get(i).getWeeklyVary());
                map.put("l23HY0100rate",list.get(i).getRate());
                map.put("l23HY0100sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+150")
            ){
                map.put("l12HY01501",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY01502",format);
                map.put("l12HY0150wvary",list.get(i).getWeeklyVary());
                map.put("l12HY0150rate",list.get(i).getRate());
                map.put("l12HY0150sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY0150min",minValue.getWeeklyVary());
                map.put("l12HY0150max",maxValue.getWeeklyVary());
            }


            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+150")
            ){
                map.put("l13HY01501",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY01502",format);
                map.put("l13HY0150wvary",list.get(i).getWeeklyVary());
                map.put("l13HY0150rate",list.get(i).getRate());
                map.put("l13HY0150sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩1#支洞")&&
                            list.get(i).getStation().equals("HY1#K0+150")
            ){
                map.put("l23HY01501",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY01502",format);
                map.put("l23HY0150wvary",list.get(i).getWeeklyVary());
                map.put("l23HY0150rate",list.get(i).getRate());
                map.put("l23HY0150sum",list.get(i).getCrownAccumulation());

            }



            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+010")
            ){
                map.put("l12HY200101",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY200102",format);
                map.put("l12HY20010wvary",list.get(i).getWeeklyVary());
                map.put("l12HY20010rate",list.get(i).getRate());
                map.put("l12HY20010sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY20010min",minValue.getWeeklyVary());
                map.put("l12HY20010max",maxValue.getWeeklyVary());
            }


            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+010")
            ){
                map.put("l13HY200101",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY200102",format);
                map.put("l13HY20010wvary",list.get(i).getWeeklyVary());
                map.put("l13HY20010rate",list.get(i).getRate());
                map.put("l13HY20010sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+010")
            ){
                map.put("l23HY200101",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY200102",format);
                map.put("l23HY20010wvary",list.get(i).getWeeklyVary());
                map.put("l23HY20010rate",list.get(i).getRate());
                map.put("l23HY20010sum",list.get(i).getCrownAccumulation());

            }


            //
            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+040")
            ){
                map.put("l12HY200401",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY2004012",format);
                map.put("l12HY200401wvary",list.get(i).getWeeklyVary());
                map.put("l12HY200401rate",list.get(i).getRate());
                map.put("l12HY200401sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY200401min",minValue.getWeeklyVary());
                map.put("l12HY200401max",maxValue.getWeeklyVary());
            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+040")
            ){
                map.put("l13HY2004011",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY2004012",format);
                map.put("l13HY200401wvary",list.get(i).getWeeklyVary());
                map.put("l13HY200401rate",list.get(i).getRate());
                map.put("l13HY200401sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+040")
            ){
                map.put("l23HY2004011",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY2004012",format);
                map.put("l23HY200401wvary",list.get(i).getWeeklyVary());
                map.put("l23HY200401rate",list.get(i).getRate());
                map.put("l23HY200401sum",list.get(i).getCrownAccumulation());

            }
            //

            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+055")
            ){
                map.put("l12HY200551",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY200552",format);
                map.put("l12HY20055wvary",list.get(i).getWeeklyVary());
                map.put("l12HY20055rate",list.get(i).getRate());
                map.put("l12HY20055sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY20055min",minValue.getWeeklyVary());
                map.put("l12HY20055max",maxValue.getWeeklyVary());
            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+055")
            ){
                map.put("l13HY200551",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY200552",format);
                map.put("l13HY20055wvary",list.get(i).getWeeklyVary());
                map.put("l13HY20055rate",list.get(i).getRate());
                map.put("l13HY20055sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+055")
            ){
                map.put("l23HY200551",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY200552",format);
                map.put("l23HY20055wvary",list.get(i).getWeeklyVary());
                map.put("l23HY20055rate",list.get(i).getRate());
                map.put("l23HY20055sum",list.get(i).getCrownAccumulation());

            }

            //
            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+085")
            ){
                map.put("l12HY200851",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY200852",format);
                map.put("l12HY20085wvary",list.get(i).getWeeklyVary());
                map.put("l12HY20085rate",list.get(i).getRate());
                map.put("l12HY20085sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY20085min",minValue.getWeeklyVary());
                map.put("l12HY20085max",maxValue.getWeeklyVary());
            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+085")
            ){
                map.put("l13HY200851",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY200852",format);
                map.put("l13HY20085wvary",list.get(i).getWeeklyVary());
                map.put("l13HY20085rate",list.get(i).getRate());
                map.put("l13HY20085sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+085")
            ){
                map.put("l23HY200851",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY200852",format);
                map.put("l23HY20085wvary",list.get(i).getWeeklyVary());
                map.put("l23HY20085rate",list.get(i).getRate());
                map.put("l23HY20085sum",list.get(i).getCrownAccumulation());

            }

            //


            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+115")
            ){
                map.put("l12HY201151",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY201152",format);
                map.put("l12HY20115wvary",list.get(i).getWeeklyVary());
                map.put("l12HY20115rate",list.get(i).getRate());
                map.put("l12HY20115sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY20115min",minValue.getWeeklyVary());
                map.put("l12HY20115max",maxValue.getWeeklyVary());
            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+115")
            ){
                map.put("l13HY201151",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY201152",format);
                map.put("l13HY20115wvary",list.get(i).getWeeklyVary());
                map.put("l13HY20115rate",list.get(i).getRate());
                map.put("l13HY20115sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+115")
            ){
                map.put("l23HY201151",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY201152",format);
                map.put("l23HY20115wvary",list.get(i).getWeeklyVary());
                map.put("l23HY20115rate",list.get(i).getRate());
                map.put("l23HY20115sum",list.get(i).getCrownAccumulation());

            }

            //
            else if(
                    list.get(i).getInstrumentNumber().equals("L12")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+145")
            ){
                map.put("l12HY201451",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l12HY201452",format);
                map.put("l12HY20145wvary",list.get(i).getWeeklyVary());
                map.put("l12HY20145rate",list.get(i).getRate());
                map.put("l12HY20145sum",list.get(i).getCrownAccumulation());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("monitor_place",list.get(i).getMonitorPlace());
                wrapper.eq("station",list.get(i).getStation());

                List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(wrapper);


                Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

                Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
                System.out.println("最值");
                System.out.println(maxValue.getWeeklyVary());
                System.out.println(minValue.getWeeklyVary());

                map.put("l12HY20145min",minValue.getWeeklyVary());
                map.put("l12HY20145max",maxValue.getWeeklyVary());
            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L13")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+145")
            ){
                map.put("l13HY201451",list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l13HY201452",format);
                map.put("l13HY20145wvary",list.get(i).getWeeklyVary());
                map.put("l13HY20145rate",list.get(i).getRate());
                map.put("l13HY20145sum",list.get(i).getCrownAccumulation());

            }

            else if(
                    list.get(i).getInstrumentNumber().equals("L23")&&
                            list.get(i).getMonitorPlace().equals("红岩2#支洞")&&
                            list.get(i).getStation().equals("HY2#K0+145")
            ) {
                map.put("l23HY201451", list.get(i).getSumPositionOne());
                DecimalFormat df = new DecimalFormat("0.00");
                String format = df.format(list.get(i).getSumPositionTwo());
                map.put("l23HY201452", format);
                map.put("l23HY20145wvary", list.get(i).getWeeklyVary());
                map.put("l23HY20145rate", list.get(i).getRate());
                map.put("l23HY20145sum", list.get(i).getCrownAccumulation());

            }




        }

        XWPFDocument xwpfDocument = WordExportUtil.exportWord07("templates/template2.docx",map);


        /*
          2.12 修改
         */
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();

        String desktop = desktopDir.getAbsolutePath();


        String desktopPath =  desktop+"\\test.docx";


        /*

         */
//        OutputStream fileoutputStream  = new FileOutputStream("C:\\Users\\lishuang\\Desktop\\test.docx");
        OutputStream fileoutputStream  = new FileOutputStream(desktopPath);
        xwpfDocument.write(fileoutputStream);
        fileoutputStream.flush();
        fileoutputStream.close();

//        redcaveconvergenceMapper.deleteRedcave();

        System.out.println(" work is done");
        return R.ok();
    }

    @RequestMapping("/addConvergence")
    private R addConvergence(@RequestBody Redcaveconvergence redcaveconvergence){


        System.out.println(redcaveconvergence.toString());


        boolean save = redcaveconvergenceService.save(redcaveconvergence);

        if(save){
            return R.ok();
        }
        return R.ok();
    }


    @RequestMapping("login")
    public R login() {
        return R.ok().data("token","admin").data("code",20000);
    }
    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").data("code",20000);
    }



    @RequestMapping("/UpFile")
    public R UploadFile(@RequestParam(value = "file",required = false) MultipartFile multipartFile) throws Exception {
        ImportParams params = new ImportParams();
        //设置表头占行
        params.setHeadRows(1);
        //sheet位置 从0开始
        params.setStartSheetIndex(0);
        List<ReadData> list = ExcelImportUtil.importExcel(multipartFile.getInputStream(),
                ReadData.class, params);
        list.forEach(System.out::println);
        Redcaveconvergence redcaveconvergence = null;
        for(int i=0;i<list.size();i++){


            redcaveconvergence= new Redcaveconvergence();

            redcaveconvergence.setMonitorPlace(list.get(i).getMonitorPlace());
            redcaveconvergence.setInstrumentNumber(list.get(i).getInstrumentNumber());
            redcaveconvergence.setSumPositionOne(list.get(i).getSumPositionOne());
            redcaveconvergence.setSumPositionTwo(list.get(i).getSumPositionTwo());
            redcaveconvergence.setStation(list.get(i).getStation());

            redcaveconvergence.setWeeklyVary(list.get(i).getWeeklyVary());
            redcaveconvergence.setRate(list.get(i).getRate());
            redcaveconvergence.setRemarks(list.get(i).getRemarks());
            redcaveconvergence.setCrownAccumulation(list.get(i).getCrownAccumulation());

            redcaveconvergence.setBeginTime(list.get(i).getBegin_time());
            redcaveconvergence.setEndTime(list.get(i).getEnd_time());


            redcaveconvergenceService.save(redcaveconvergence);

        }
        return R.ok();
    }

}
