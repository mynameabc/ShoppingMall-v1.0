package BaseFacilities.MQ.unit.notify;

import com.alibaba.fastjson.JSON;
import notify.ApplicationExchange;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("notifyProducer")
public class NotifyProducer {

    private Logger logger = LoggerFactory.getLogger(NotifyProducer.class);

    static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 短信消息投递到队列
     * @param mobile
     * @param template
     * @param replaceMap
     */
    public void sms(String mobile, String template, Map replaceMap) {

        Map parameterMap_ = new HashMap<String, Object>();
        parameterMap_.put("mobile", mobile);
        parameterMap_.put("template", template);
        parameterMap_.put("replaceMap", replaceMap);

        try {
            amqpTemplate.convertAndSend(ApplicationExchange.NotifyExchange, "SMS", configuration.asByteArray(JSON.toJSONString(parameterMap_)));
        } catch (AmqpException e) {
            logger.error("短信投递失败:" + parameterMap_.get("mobile"));
        }
    }

    /**
     * 邮件消息投递到队列
     * @param emailAddress
     * @param title
     * @param content
     */
    public void email(String emailAddress, String title, String content) {

        Map parameterMap = new HashMap<String, Object>();
        parameterMap.put("emailAddress", emailAddress);
        parameterMap.put("title", title);
        parameterMap.put("content", content);

        try {
            amqpTemplate.convertAndSend(ApplicationExchange.NotifyExchange, "Email", configuration.asByteArray(JSON.toJSONString(parameterMap)));
        } catch (AmqpException e) {
            logger.error("邮件投递失败:");
        }
    }

    /**
     * App推送消息投递到队列
     */
    public void appPush() {

        String s = "...";

        try {
            amqpTemplate.convertAndSend(ApplicationExchange.NotifyExchange, "AppPush", configuration.asByteArray(JSON.toJSONString(s)));
        } catch (AmqpException e) {
            logger.error("App推送投递失败:");
        }
    }
}
