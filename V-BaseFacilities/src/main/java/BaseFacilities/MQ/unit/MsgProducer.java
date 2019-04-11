package BaseFacilities.MQ.unit;

import BaseFacilities.MQ.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {
 
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }
 
    public void sendSMS(String mobile, String template, Map replaceMap) {

        Map parameterMap = new HashMap<String, Object>();
        parameterMap.put("mobile", mobile);
        parameterMap.put("template", template);
        parameterMap.put("replaceMap", replaceMap);

        rabbitTemplate.convertAndSend(
                RabbitConfig.Notify_EXCHANGE,
                RabbitConfig.Notify_KEY_SMS,
                parameterMap,
                new CorrelationData(UUID.randomUUID().toString()));
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        logger.info(" 回调id:" + correlationData.getId());
        if (ack) {
            logger.info("消息成功消费" + ack);
        } else {
            logger.info("消息消费失败:" + cause);
        }

    }
}
