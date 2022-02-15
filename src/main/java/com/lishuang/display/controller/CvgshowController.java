package com.lishuang.display.controller;


import cn.afterturn.easypoi.word.WordExportUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lishuang.display.commonutils.R;
import com.lishuang.display.mapper.*;
import com.lishuang.display.model.*;
import com.lishuang.display.service.impl.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李爽
 * @since 2022-01-12
 */
@RestController
@CrossOrigin()
@RequestMapping("/cvgShow")
public class CvgshowController {


    @Autowired
    public CvgshowServiceImpl cvgShowService;

    @Autowired
    public CvgshowMapper cvgshowMapper;

    @Autowired
    public MidconvertServiceImpl midconvertService;

    @Autowired
    public MidconvertMapper midconvertMapper;


    @Autowired
    public RedcaveconvergenceServiceImpl redcaveconvergenceService;

    @Autowired

    public InitconfServiceImpl initconfService;
    @Autowired
    public RedcaveconvergenceMapper redcaveconvergenceMapper;


    @Autowired
    public TempstoreMapper tempstoreMapper;

    @Autowired
    public TempstoreServiceImpl tempstoreService;



    @Autowired
    public InitconfMapper initconfMapper;



    /*
          对原始数据进行 转化保存redcaveconvergence的表中
     */

    @RequestMapping("/addCvgShow")
    public R addCvgShow(@RequestBody Cvgshow cvgshow) {
        /*
           将原始数据保存
         */
        boolean save = cvgShowService.save(cvgshow);
        if (save) {
            Date observeTime = cvgshow.getObserveTime();
            String strDateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            String showBeginTime = sdf.format(observeTime);


            String[] split = showBeginTime.split("\\s+");

            String BeginTime = split[0];
            String EndTime = BeginTime;

            Date NowobserveTime = cvgshow.getObserveTime();
            Calendar c = Calendar.getInstance();

            c.setTime(NowobserveTime);

            c.add(Calendar.DAY_OF_MONTH, 0);

            Date tomorrow = c.getTime();

            QueryWrapper<Cvgshow> queryWrapper = new QueryWrapper<>();

            queryWrapper.between("observe_time", observeTime, tomorrow).orderByAsc("observe_time")
                    .eq("monitor_place", cvgshow.getMonitorPlace())
                    .eq("station", cvgshow.getStation())
                    .eq("instrument_number", cvgshow.getInstrumentNumber());


            List<Cvgshow> cvgShowsList = cvgshowMapper.selectList(queryWrapper);

        /*
              得到这一天最新的开始值
         */
            BigDecimal NowShowValue = cvgShowsList.get(cvgShowsList.size() - 1).getShowValue();
            System.out.println(cvgShowsList.get(cvgShowsList.size() - 1).getShowValue());
            BigDecimal NowSteelLen = cvgShowsList.get(cvgShowsList.size() - 1).getSteelLen();
            System.out.println(cvgShowsList.get(cvgShowsList.size() - 1).getSteelLen());

            BigDecimal multiplyValue = new BigDecimal("1000");


//            BigDecimal nowValue = NowShowValue.add(NowSteelLen);
//            double value = nowValue.doubleValue();
            double value = NowSteelLen.multiply(multiplyValue).add(NowShowValue).doubleValue();


        /*
            这里要做修改
            创建初始的仪器配置表
         */
            QueryWrapper queryInitconf = new QueryWrapper();
            queryInitconf.eq("monitor_place", cvgshow.getMonitorPlace());
            queryInitconf.eq("instrument_name", cvgshow.getInstrumentNumber());
            queryInitconf.eq("station", cvgshow.getStation());

            Initconf initconf = initconfService.getOne(queryInitconf);
//            if (initconf == null) {
//                return R.ok();
//            }
            /*
                  2022.1.17 修改

             */

            BigDecimal initSteel = initconf.getInitSteel();
            double iniValue = initSteel.multiply(multiplyValue).add(initconf.getInitShow()).doubleValue();

//            double iniValue = initconf.getInitShow().add(initconf.getInitSteel()).doubleValue();


            double now = iniValue - value;


            String monitorPlace = cvgshow.getMonitorPlace();
            String station = cvgshow.getStation();
            String instrumentNumber = cvgshow.getInstrumentNumber();
            Midconvert midconvert = new Midconvert();


            midconvert.setInstrumentNumber(instrumentNumber);
            midconvert.setStation(station);
            midconvert.setMonitorPlace(monitorPlace);

            BigDecimal controNewValue = new BigDecimal(now);
            midconvert.setConvertValue(controNewValue);

            ZoneId zoneId = ZoneId.systemDefault();

            LocalDateTime localDateTime = LocalDateTime.ofInstant(observeTime.toInstant(), zoneId);
            midconvert.setObserveTime(localDateTime);

        /*
                   获取原始的数据
                   保存到中间表中
        */
            System.out.println("44444");
            boolean flag = midconvertService.save(midconvert);
            if (flag) {
                System.out.println("走了！");
                return R.ok();
            } else {
                return R.error();
            }

        /*
           从中间表中
         */
        } else {
            return R.error();
        }

    }

    /*
       通过 前端传递两个时间
     */
    @RequestMapping("/test")
    public R test(@RequestParam("showBeginTime") String showBeginTime
            , @RequestParam("showEndTime") String showEndTime) throws Exception {



        tempstoreMapper.deleteTempstore();

        //拼接第一个日期
        String[] split = showBeginTime.split("\\s+");
        String begin = split[0] + " " + "00:00:00";
        String beginTow = split[0] + " " + "23:59:59";

        // 拼接第二个日期
        String[] splitOne = showEndTime.split("\\s+");
        String end = splitOne[0] + " " + "00:00:00";
        String endTwo = splitOne[0] + " " + "23:59:59";


        /*
          将两个日期进行 转化
         */
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date nowBegin = format.parse(begin);
        Date nowBeginTwo = format.parse(beginTow);



        Date nowEnd = format.parse(end);
        Date nowEndTwo = format.parse(endTwo);


        /*
            获取前台传入的开始的时间的那一天的最新值
            及一周结束的最新值
         */
        QueryWrapper<Midconvert> queryWrapperBegin = new QueryWrapper<>();
//        queryWrapperBegin.between("observe_time", nowBegin, nowBeginTwo).orderByAsc("observe_time");
        queryWrapperBegin.between("observe_time", nowBegin, nowEndTwo).orderByAsc("observe_time");

        List<Midconvert> midconverts = midconvertMapper.selectList(queryWrapperBegin);



        /*
               查询到在开始时间的所有数据
               怎么将其分类
         */
        Map<String, Map<String, Map<String, List<Midconvert>>>> collectOnes =
                midconverts.stream().collect(
                Collectors.groupingBy(Midconvert::getMonitorPlace, Collectors.groupingBy(
                        Midconvert::getInstrumentNumber,
                        Collectors.groupingBy(Midconvert::getStation))));


        Map<String, Midconvert> groups = new HashMap<String, Midconvert>();
        /*
               根据监测位置 仪器编号 设计编号 分组
         */
        for (Midconvert midconvert : midconverts) {

            for (int i = 0; i < midconverts.size(); i++) {
                LocalDateTime observeTimeOne = midconvert.getObserveTime();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String timeOne = dtf.format(observeTimeOne);

                LocalDateTime observeTime1 = midconverts.get(i).getObserveTime();
                String timeTwo = dtf.format(observeTime1);

                if (midconvert.getStation().equals(midconverts.get(i).getStation()) &&
                        midconvert.getMonitorPlace().equals(midconverts.get(i).getMonitorPlace()) &&
                        midconvert.getInstrumentNumber().equals(midconverts.get(i).getInstrumentNumber())
                        && timeOne.equals(timeTwo)
                ) {
                    String key = midconvert.getInstrumentNumber() + "|" + midconvert.getStation() + "|" + midconvert.getMonitorPlace()+"|"+timeOne;
                    groups.put(key, midconverts.get(i));
                }

            }
        }


        System.out.println("11111");
        for (String key : groups.keySet()) {

            Midconvert midconvert = groups.get(key);

            System.out.println("Key = " + key + ", Value = " + midconvert.toString());
            Tempstore tempstore = new Tempstore();
            tempstore.setStation(midconvert.getStation());
            tempstore.setInstrumentNumber(midconvert.getInstrumentNumber());
            tempstore.setConvertValue(midconvert.getConvertValue());
            tempstore.setMonitorPlace(midconvert.getMonitorPlace());
            LocalDateTime observeTime = midconvert.getObserveTime();

            DateTimeFormatter dtfTwo = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String newDate = dtfTwo.format(observeTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date only_observe_time = simpleDateFormat.parse(newDate);

            tempstore.setOnlyObserveTime(only_observe_time);
            boolean oneflag = tempstoreService.save(tempstore);

            if (oneflag) {
                continue;
            }

        }


        QueryWrapper<Tempstore> q = new QueryWrapper<>();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date beginOne = DateFormat.parse(split[0]);
        Date endOne = DateFormat.parse(splitOne[0]);
        q.between("only_observe_time", beginOne, endOne);
//        q.between("only_observe_time", beginOne, endOne).orderByAsc("only_observe_time");

        List<Tempstore> tempstores = tempstoreMapper.selectList(q);

        QueryWrapper incofs = new QueryWrapper();
        List<Initconf> initconfs = initconfMapper.selectList(incofs);


          /*
              2022.1.17修改
           */

      for( Initconf initconf:initconfs){
          List<Tempstore> collect = new ArrayList<>();
          for (Tempstore tempstore:tempstores){

              Date only = tempstore.getOnlyObserveTime();
              SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
              String onlyTime = simpleDate.format(only);
              if(
                      initconf.getStation().equals(tempstore.getStation())&&
                            initconf.getMonitorPlace().equals(tempstore.getMonitorPlace())&&
                              initconf.getInstrumentName().equals(tempstore.getInstrumentNumber())&&
                   onlyTime.equals(splitOne[0])
              )
              {
                collect.add(tempstore);
              }
          }


          for (Tempstore tempstore:tempstores){
              Date only1 = tempstore.getOnlyObserveTime();


              SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
              String onlyTime1 = simpleDate.format(only1);
              if(initconf.getStation().equals(tempstore.getStation())&&
                      initconf.getMonitorPlace().equals(tempstore.getMonitorPlace())&&
                      initconf.getInstrumentName().equals(tempstore.getInstrumentNumber())&&
                      onlyTime1.equals(split[0]))
              {
                  collect.add(tempstore);
              }
          }



          /*
              2022.1.17修改
           */


          List<Tempstore> tmpStore = new ArrayList<>();
          System.out.println("对象值为：");

          for(int i=0;i<collect.size();i++){
              System.out.println(collect.get(i).toString());
          }
          for(int i=0;i<collect.size();i++){
              /*
                    获取两个数据
               */
              tmpStore.add(collect.get(i));
          }


          Redcaveconvergence redcaveconvergence = new Redcaveconvergence();
          for (int i=0;i<tmpStore.size();i++){
//              System.out.println(tmpStore.get(i));
               redcaveconvergence.setStation(tmpStore.get(i).getStation());
               redcaveconvergence.setSumPositionTwo(tmpStore.get(i).getConvertValue());
               redcaveconvergence.setSumPositionOne(tmpStore.get(tmpStore.size()-1).getConvertValue());

              redcaveconvergence.setInstrumentNumber(tmpStore.get(i).getInstrumentNumber());
              redcaveconvergence.setRemarks("无");

              redcaveconvergence.setMonitorPlace(tmpStore.get(i).getMonitorPlace());
              BigDecimal startValue = tmpStore.get(i).getConvertValue();

              BigDecimal endValue = tmpStore.get(tmpStore.size() - 1).getConvertValue();
              // 周变化量
              BigDecimal weeklyValue = endValue.subtract(startValue);

              redcaveconvergence.setWeeklyVary(weeklyValue);

              /*
             获取时间的变化率 rate
              */
              BigDecimal seven = new BigDecimal("7");

              BigDecimal rate = weeklyValue.divide(seven,20,BigDecimal.ROUND_HALF_UP);

              redcaveconvergence.setRate(rate);

              Date onlyObserveTime = tmpStore.get(i).getOnlyObserveTime();

              Timestamp timeStart = new Timestamp(onlyObserveTime.getTime());


              Date onlyEndTime = tmpStore.get(tmpStore.size() - 1).getOnlyObserveTime();


              Timestamp timeEnd = new Timestamp(onlyEndTime.getTime());



              redcaveconvergence.setBeginTime(timeEnd);

              redcaveconvergence.setEndTime(timeStart);


              /*

                  顶拱累计沉降

               */
              BigDecimal g=  new BigDecimal("5.92");
              redcaveconvergence.setCrownAccumulation(g);

              redcaveconvergenceService.save(redcaveconvergence);

              //
          }

      }


        tempstoreMapper.deleteTempstore();

//        nowBegin nowEnd

        LambdaQueryWrapper<Redcaveconvergence> queryWrapperV = new QueryWrapper<Redcaveconvergence>().lambda();
        queryWrapperV
                .eq(Redcaveconvergence::getBeginTime,nowBegin)
                .and(wrapper->wrapper.eq(Redcaveconvergence::getEndTime,nowBegin));

        boolean remove = redcaveconvergenceService.remove(queryWrapperV);
        LambdaQueryWrapper<Redcaveconvergence> queryWrapperV1 = new QueryWrapper<Redcaveconvergence>().lambda();
        queryWrapperV1
                .eq(Redcaveconvergence::getBeginTime,nowEnd)
                .and(wrapper->wrapper.eq(Redcaveconvergence::getEndTime,nowEnd));

        boolean remove1 = redcaveconvergenceService.remove(queryWrapperV);

        if(remove||remove1){
            return R.ok();
        }
        else{
            return R.error();
        }

    }
}
