package com;

import BaseFacilities.MQ.unit.MsgProducer;
import BaseFacilities.MQ.unit.notify.NotifyProducer;
import auxiliary.Test;
import auxiliary.office.excel.utils.ExcelUtils;
import com.alibaba.fastjson.JSON;
import com.service.sys.employee.IEmployee;
import com.service.sys.role.RoleServiceImpl;
import communal.Result;
import communal.util.UUIDGeneratorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sys.UploadContact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private Test test;

    @Autowired
    private MsgProducer msgProducer;

    @Autowired
    @Qualifier("notifyProducer")
    private NotifyProducer notifyProducer;

    @Autowired
    private ExcelUtils excelUtils;

    private static Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    private IEmployee employeeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String indexX() {
        return "index";
    }

    @GetMapping("/index2")
    public String index2() {
        return "index2";
    }

    @GetMapping("/index3")
    public String index3() {
        return "index3";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    public String abc() {
        System.out.println(1);
        return null;
    }

    @ResponseBody
    @GetMapping("/test")
    public String test() {

        String result = null;

        try {
/*

            FastDFSClient client = new FastDFSClient("fdfs_client.conf");
            result = client.uploadFile("G:\\EF2B44C19404C01C143B85C8112617C0.jpg", "jpg");
            System.out.println(result);
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/test123")
    public String test123() {

        Map parameterMap = new HashMap<String, String>();
        parameterMap.put("phone", "18569937167");
        parameterMap.put("time", "2018-10-26 07:30:23");
        parameterMap.put("money1", "10.0");
        parameterMap.put("money2", "11.0");  //返现
        parameterMap.put("money3", "12.0"); //总额
/*
        notifyProducer.sms("13559193463", SMSTemplate.Recharge_SUCCESS_NOGive_Template, parameterMap);    //发送短信
        notifyProducer.appPush();
*/

        msgProducer.sendSMS("13559193463", "SMS_116592288", parameterMap);

/*        try{
            test.aaa(1, 2L, "13559193463", parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return "";
    }

    @GetMapping(value = "/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping(value = "/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping(value = "/test4")
    public String test4() {
        return "test4";
    }

    @GetMapping(value = "/test5")
    public String test5() {
        return "test5";
    }

    @GetMapping(value = "/test6")
    public String test6() {
        return "test6";
    }

    @GetMapping(value = "/test7")
    public String test7() {
        return "test7";
    }

    @PostMapping(value="universalFileUpload")
    @ResponseBody
    public String universalFileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {

        //2.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
        String destFileName = UploadContact.UNIVERSAL_DIR + fileName;

        try {

            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(destFileName);
            //5.把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

        System.out.println(UploadContact.UNIVERSAL_DIR + fileName);

        return JSON.toJSONString(new Result(true, "上传成功!", UploadContact.UNIVERSAL_DIR + fileName));
    }

    /**
     *
     * @param file
     * @return
     */
    @PostMapping(value="fileUpload", produces="application/json")
    @ResponseBody
    public Result fileUpload(@RequestParam(value="file")MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            System.out.println("文件为空!");
        }

        String field = request.getParameter("field");

        //判断扩展名
        {

        }

        //判断文件大小
        {
            Long size = 1 * 1024 * 1024L;
            if (file.getSize() > size) {
                return new Result(false, "该文件大小超出上限!");
            }
        }

        //如果文件夹不存在就新建
        {

        }

        String fileName = file.getOriginalFilename();  //文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        String saveFolder ="D:/web/images/";
        fileName = new Date().getTime() + "_" + UUIDGeneratorUtil.getUUID() + "_" + suffixName;

        try {
            File destFile = new File(saveFolder + fileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
        } catch (FileNotFoundException e) {
            System.out.println("这里是FileNotFoundException");
            e.printStackTrace();
            e.getMessage();
        } catch (IOException e) {
            System.out.println("这里是IOException");
            e.printStackTrace();
            e.getMessage();
        }

        System.out.println(saveFolder + fileName);
        Result result = new Result(true, null,saveFolder + fileName);
        return result;
    }

    @PostMapping(value = "uploadFile")
    public String test2(@RequestParam("picker") MultipartFile[] file, @PathVariable("account") String account,
                        HttpServletRequest request) {

        logger.info("图片开始上传......");

        if (null != file && file.length > 0) {

            for (MultipartFile files : file) {

                //取得当前上传文件的文件名称
                String fileName = account + files.getOriginalFilename();

                //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                //本地上传图片方式
                logger.info(fileName);
            }
        }

        return "test2";
    }

    @GetMapping(value = "/test3")
    public String test3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "test3";

/*
        String[] rowsName = new String[]{"序列", "姓名","手机号码","呢称","所属机构","业务量","日期"};
        String[] rowsName2 = new String[]{"1", "2","3","4","5","6","7"};

        List<Object[]>  dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < 10; i++) {
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = "林少君";
            objs[2] = "135591934693";
            objs[3] = "LoderStar";
            objs[4] = "技术支撑部";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            objs[5] = 100;
            objs[6] = date;
            dataList.add(objs);
        }

        ExcelUtils excelUtils = new ExcelUtils("aaa.xls", response);
        excelUtils.createSheet("员工业绩报表", "员工业绩报表", rowsName, dataList);
        excelUtils.createSheet("员工业绩报表1", "员工业绩报表1", rowsName2, dataList);
        excelUtils.close();
        */
    }
}



