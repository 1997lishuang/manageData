package com.lishuang.display.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lishuang.display.commonutils.ExportWordUtil;
import com.lishuang.display.commonutils.R;
import com.lishuang.display.mapper.RedcaveconvergenceMapper;
import com.lishuang.display.model.ReadData;
import com.lishuang.display.model.Redcaveconvergence;
import com.lishuang.display.service.impl.RedcaveconvergenceServiceImpl;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
@RequestMapping("/Upload")

public class UploadController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    public RedcaveconvergenceServiceImpl redcaveconvergenceService;

     @Autowired
     public RedcaveconvergenceMapper redcaveconvergenceMapper;

    @Autowired
    public UploadController uploadController;
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
            redcaveconvergence.setInstrumentNumber(list.get(i).getInstrumentNumber());
            redcaveconvergence.setMonitorPlace(list.get(i).getMonitorPlace());

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


    @RequestMapping("/UploadWord")
    @ResponseBody
    public R UploadWord(@RequestParam(value = "file",required = false) MultipartFile multipartFile)
    {

        if(multipartFile.isEmpty()){
            //返回选择文件提示
            return R.error();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("src/main/resources/templates");

//        String format = sdf.format(new Date());
        //存放上传文件的文件夹
//        File file = new File(realPath + format);
        File file = new File(realPath);

        if(!file.isDirectory()){
            //递归生成文件夹
            file.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = multipartFile.getOriginalFilename();

//        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());

        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + oldName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            multipartFile.transferTo(newFile);
//            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;

            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return R.ok();
    }



    /*
       word模板下载
     */
    @RequestMapping("downWord")
    @ResponseBody
    @CrossOrigin
    public R downWord(String templatesName) throws FileNotFoundException {


        String substring = templatesName.substring(0, 4);

        String eqName =  substring+ ".docx";

        String path = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(path);

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:templates");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(file.exists()){

            File[] files = file.listFiles();
            if(files != null){
                for(File childFile:files){
                    if(eqName.equals(childFile.getName())){


                        //下载的文件路径
                        String filePath = "src/main/resources/templates/"+eqName;
                        // 开始下载文件
                        InputStream in = null;
                        try {
                            // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
                            response.setContentType("multipart/form-data");
                            // 2.设置文件头：最后一个参数是设置下载文件名

                            response.addHeader("Content-Disposition",
                                    "attachment;filename=" + new String("word模板.docx".getBytes(), "ISO-8859-1"));

                            in = new FileInputStream(new File(filePath));

                            // 3.通过response获取ServletOutputStream对象(out)
                            int b = 0;
                            byte[] buffer = new byte[512];
                            while (b != -1) {
                                b = in.read(buffer);
                                if (b != -1) {
                                    response.getOutputStream().write(buffer, 0, b);// 4.写到输出流(out)中
                                }

                            }
                        } catch (Exception e) {
                        } finally {
                            try {
                                if (in != null) {
                                    in.close();
                                }
                                response.getOutputStream().flush();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }





//                        //下载的文件路径
//                        String filePath = "src/main/resources/templates/"+eqName;
//                        System.out.println(filePath);
//                        //下载后显示的文件名
//                        String fileName = "word模板文件";
//                            //使用流的形式下载文件
//                        try (
//                                InputStream is = new FileInputStream(filePath);
//                                OutputStream os = new BufferedOutputStream(response.getOutputStream());
//                        ) {
//                            byte[] buffer = new byte[is.available()];
//                            is.read(buffer);
//                            response.reset();
//                            response.addHeader("Access-Control-Allow-Origin","*");
//                            response.setContentType("application/octet-stream");
//                            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
//                            response.addHeader("Content-Length", "" + buffer.length);
//                            os.write(buffer);
//                            os.flush();
//                            os.close();
//                            is.close();
//                            return null;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            return R.error();
//                        }

                    }

                }
            }
        }


        return null;

    }
    /*
         循环导出 word 方法
     */


    @RequestMapping("/getWord")
    @ResponseBody
    public void getZbrzWord(@RequestParam String location) {


       switch (location){
           case "永安隧洞":
               uploadController.chooseWord(location);
               break;
           case "圣中水库":
               uploadController.chooseWord(location);
               break;
           case "双桥隧洞":
               uploadController.chooseWord(location);
               break;
           case "千盐隧洞":
               uploadController.chooseWord(location);
               break;
           case "石竹隧洞":
               uploadController.chooseWord(location);
               break;
           case "陈食隧洞":
               uploadController.chooseWord(location);
               break;
           case "王家湾隧洞":
               uploadController.chooseWord(location);
               break;
           default:
               break;
       }



    }

    public void chooseWord(String location){

        Map<String,Object> params = new HashMap<>();
        //word模板地址
        String wordModelPath = "templates/"+location+".docx";
        //数据预处理

        //生成临时文件地址


        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();

        String desktopPath = desktopDir.getAbsolutePath();

//        String filePath = "C:\\Users\\lishuang\\Desktop\\";
        String filePath = desktopPath;


        List<Map<String,Object>> dataList= new ArrayList<>();
        /*
          准备填充数据
         */
        String newLocation = location.substring(0, 2);
        System.out.println("111");
        System.out.println(newLocation);
        QueryWrapper findStation = new QueryWrapper();

        findStation.like("monitor_place",newLocation);

        List<Redcaveconvergence> redCaveList = redcaveconvergenceService.list(findStation);




        //动态填充数据
        for (int i=0;i<redCaveList.size();i++)
        {
            Map<String,Object> data=new HashMap<>();
            data.put("monitor_place",redCaveList.get(i).getMonitorPlace());
            data.put("instrument_number",redCaveList.get(i).getInstrumentNumber());
            data.put("station",redCaveList.get(i).getStation());
            data.put("sum_position_one",redCaveList.get(i).getSumPositionOne());
            data.put("sum_position_two",redCaveList.get(i).getSumPositionTwo());
            data.put("weekly_vary",redCaveList.get(i).getWeeklyVary());
            data.put("rate",redCaveList.get(i).getRate());
            data.put("crown_accumulation",redCaveList.get(i).getCrownAccumulation());
            data.put("remarks",redCaveList.get(i).getRemarks());

            /*
               寻找最值  传递的构造总值
             */

            QueryWrapper newWrapper = new QueryWrapper();
            newWrapper.eq("monitor_place",redCaveList.get(i).getMonitorPlace());
            newWrapper.eq("station",redCaveList.get(i).getStation());

            List<Redcaveconvergence> collects = redcaveconvergenceMapper.selectList(newWrapper);


            Redcaveconvergence maxValue = collects.stream().max(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();

            Redcaveconvergence minValue = collects.stream().min(Comparator.comparing(Redcaveconvergence::getWeeklyVary)).get();
//            System.out.println("最值");
//            System.out.println(maxValue.getWeeklyVary());
//            System.out.println(minValue.getWeeklyVary());


            String key = redCaveList.get(i).getMonitorPlace()+ redCaveList.get(i).getStation();
            String keyMin = key+"min";
//            System.out.println(keyMin);

            params.put(keyMin,minValue.getWeeklyVary());


            String keyMax = key+"max";
//            System.out.println(keyMax);
            params.put(keyMax,maxValue.getWeeklyVary());
            /*
              构造页面的顶拱累计沉降数据
             */
            String Keycrown = key+"c";
//            System.out.println(Keycrown);
            params.put(Keycrown,redCaveList.get(i).getCrownAccumulation());

            dataList.add(data);

        }
        params.put("dataList",dataList);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        if(redCaveList.size()>0){

            params.put("begin",simpleDateFormat.format(redCaveList.get(0).getBeginTime()));


            params.put("end",simpleDateFormat.format(redCaveList.get(0).getEndTime()));
        }

            ExportWordUtil.exportWord(wordModelPath,filePath,"weekly.docx",params,request,response);




        redcaveconvergenceMapper.deleteRedcave();
    }


}