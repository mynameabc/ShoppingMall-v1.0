package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RabbitMQController {

/*    @Autowired
    private NotifyProducer2 notifyProducer2;*/

    @ResponseBody
    @GetMapping("/t1")
    public String t1() {

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

//        notifyProducer2.sms("13559193463","你好, 这是测试队列!", parameterMap);
        return "";
    }
}
