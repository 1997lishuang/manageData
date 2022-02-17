package com.lishuang.display.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lishuang.display.commonutils.ExportWordUtil;
import com.lishuang.display.commonutils.R;
import com.lishuang.display.mapper.RedcaveconvergenceMapper;
import com.lishuang.display.model.Preservename;
import com.lishuang.display.model.ReadData;
import com.lishuang.display.model.Redcaveconvergence;
import com.lishuang.display.service.impl.PreservenameServiceImpl;
import com.lishuang.display.service.impl.RedcaveconvergenceServiceImpl;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
@RequestMapping("/Upload")

public class UploadController {

    @Resource
    private ResourceLoader resourceLoader;


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    public RedcaveconvergenceServiceImpl redcaveconvergenceService;

    @Autowired
    public RedcaveconvergenceMapper redcaveconvergenceMapper;

    @Autowired
    public PreservenameServiceImpl preservenameService;

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

        System.out.println(oldName);
        String[] split = oldName.split("\\.");
        String tunnelName = split[0];



        Preservename preservename = new Preservename();
        preservename.setTunnelName(tunnelName);

        boolean save = preservenameService.save(preservename);

        if(save){
            try {
                //构建真实的文件路径
                File newFile = new File(file.getAbsolutePath() + File.separator + oldName);
                //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
                multipartFile.transferTo(newFile);
//            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return R.ok();
        }

        else {
            return R.error();
        }


//        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());


    }



//    /*
//       word模板下载
//     */
//    @RequestMapping("downWord")
//    @ResponseBody
//    public void downWord(String templatesName) {
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        try {
//
//            request.setCharacterEncoding("utf-8");
//
////            fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
//
////获取文件路径
//            File file = null;
//            try {
//                file = ResourceUtils.getFile("classpath:templates");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            String path = file.getAbsolutePath();
//            String filePath = path + "\\" + templatesName + ".docx";
//
//
//            filePath = filePath == null ? "" : filePath;
//
//
//            File f = new File(filePath);
//            System.out.println(filePath);
//            if (!f.exists()) {
//                try {
//                    response.sendError(404, "File not found!");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            BufferedInputStream br = null;
//            try {
//                br = new BufferedInputStream(new FileInputStream(f));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            byte[] buf = new byte[1024];
//            int len = 0;
//            response.reset(); // 非常重要
//            response.setContentType("application/msword");
////            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            OutputStream out = null;
//            try {
//                out = response.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("222");
//            while (true){
//                try {
//                    if (!((len = br.read(buf)) > 0)) break;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    out.write(buf, 0, len);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                br.close();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//
//
//        } catch (UnsupportedEncodingException e) {
//
//            e.printStackTrace();
//
//        }
//
//    }


    /*
         循环导出 word 方法
     */


    @RequestMapping("/getWord")
    @ResponseBody
    public void getZbrzWord(@RequestParam String location) {

        List<Preservename> list = preservenameService.list();

       for(int i=0;i<list.size();i++){
                if(list.get(i).getTunnelName().equals(location)){
                    uploadController.chooseWord(location);
                }
       }
//        switch (location){
//           case "永安隧洞":
//               uploadController.chooseWord(location);
//               break;
//           case "圣中水库":
//               uploadController.chooseWord(location);
//               break;
//           case "双桥隧洞":
//               uploadController.chooseWord(location);
//               break;
//           case "千盐隧洞":
//               uploadController.chooseWord(location);
//               break;
//           case "石竹隧洞":
//               uploadController.chooseWord(location);
//               break;
//           case "陈食隧洞":
//               uploadController.chooseWord(location);
//               break;
//           case "王家湾隧洞":
//               uploadController.chooseWord(location);
//               break;
//           default:
//               break;
//       }


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




//        redcaveconvergenceMapper.deleteRedcave();
    }






    @GetMapping("/downWord")
    @ResponseBody
    public void downloadTemplate(String templatesName) {

//
//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:templates");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        String path = file.getAbsolutePath();
//        String filePath = path + "\\" + templatesName + ".docx";



        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = templatesName+".docx";
            String path = "templates/"+templatesName+".docx";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);

            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                    servletOutputStream = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }




}






