package com.controller.orderPayment;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public abstract class BaseOrderPaymentController {

    private static Logger logger = LoggerFactory.getLogger(BaseOrderPaymentController.class);

    /**
     * 提交订单
     * @param parameterJson
     * @return
     */
    @ResponseBody
    @PostMapping(value = "orderPayment")
    public JSONObject payment(String parameterJson) {

        logger.info(
                System.getProperty("line.separator") +
                        "商户编号 : {}" + System.getProperty("line.separator") +
                        "下单时间 : {}" + System.getProperty("line.separator") +
                        "订单金额 : {}" + System.getProperty("line.separator") +
                        "订单编号 : {}",
                "20000004",
                "2019-03-03 13:57:12",
                "5000",
                "P01201903030125191350000");

        JSONObject parameterMap = null;
        //折腾参数
        {

        }

        String sign = null;
        //签名
        {
            sign = getSign(parameterMap);
        }

        String result = null;
        //发送请求, 并返回结果
        {
            result = sendRequest(parameterMap);
        }

        //转换结果为boolean型
        {

        }

        //判断结果
        {
            if (true) {

                //新增一条记录

                //如果添加没问题, 就返回true, 否则返回false
                return (true) ?
                        (SUCCESS("成功!")) :
                        (FAIL("失败!"));
            } else {
                return FAIL("失败!");
            }
        }
    }

    protected abstract boolean convert();

    //签名
    protected abstract String getSign(JSONObject parameterMap);

    //发送请求
    protected abstract String sendRequest(JSONObject parameterMap);

    private JSONObject SUCCESS() {
        JSONObject result = new JSONObject();
        result.put("success", "true");
        result.put("msg", "");
        return result;
    }

    private JSONObject SUCCESS(String msg) {
        JSONObject result = new JSONObject();
        result.put("success", "true");
        result.put("msg", (StringUtils.isEmpty(msg)) ? ("") : (msg));
        return result;
    }

    private JSONObject FAIL() {
        JSONObject result = new JSONObject();
        result.put("success", "false");
        result.put("msg", "");
        return result;
    }

    private JSONObject FAIL(String msg) {
        JSONObject result = new JSONObject();
        result.put("success", "false");
        result.put("msg", (StringUtils.isEmpty(msg)) ? ("") : (msg));
        return result;
    }
}
