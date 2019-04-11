package BaseFacilities.MQ.unit.notify;

import com.alibaba.fastjson.JSONObject;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Map;

/*
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
*/

public class SMSConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(SMSConsumer.class);

    static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();

    public void onMessage(Message message) {

        String json = (String)configuration.asObject(message.getBody());            //反序列化
        Map<String,Object> map = JSONObject.parseObject(json); //JSON转Map

        String mobile = map.get("mobile").toString();           //手机号码
        String template = map.get("template").toString();       //模板
        String replaceMap = map.get("replaceMap").toString();   //替换参数的JSON
/*
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = SMSUtil.sendSMS(mobile, template, replaceMap);      //发送短信
        } catch (ClientException e) {
            logger.error("短信发送失败:" + sendSmsResponse.getCode() + "-" + sendSmsResponse.getMessage() + "-" + e.toString());
        }

        if (!sendSmsResponse.getCode().equals("OK")) {
            logger.error("短信发送失败:" + sendSmsResponse.getCode() + "-" + sendSmsResponse.getMessage());
        }
*/
        System.out.println("--------短信--------:" + json);
        System.out.println("--------手机号--------:" + map.get("mobile"));
        System.out.println("--------短信模板--------:" + map.get("template"));
        System.out.println("--------短信内容替换JSON--------:" + map.get("replaceMap"));
    }
}
