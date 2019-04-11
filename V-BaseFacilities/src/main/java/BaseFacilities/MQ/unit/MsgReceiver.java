package BaseFacilities.MQ.unit;

import BaseFacilities.MQ.RabbitConfig;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sys.GlobalConstants;

import java.io.IOException;
import java.util.Map;

@Component
public class MsgReceiver {
 
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.Notify_QUEUE_SMS, concurrency = "1")
    public void handler(Channel channel, Message message, Map parameterMap) {

        String mobile = parameterMap.get("mobile").toString();           //手机号码
        String template = parameterMap.get("template").toString();       //模板
        String replaceMap = parameterMap.get("replaceMap").toString();   //替换参数的JSON

        logger.info("--------手机号--------:" + mobile);
        logger.info("--------短信模板--------:" + template);
        logger.info("--------短信内容替换JSON--------:" + replaceMap);

        logger.info("\n" + "--------短信内容替换JSON--------:" + "\n" + "你好!");


        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receiver success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
