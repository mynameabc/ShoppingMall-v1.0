package BaseFacilities.MQ.unit.notify;

import com.alibaba.fastjson.JSONObject;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Map;

public class EmailConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();

    public void onMessage(Message message) {

        String json = (String)configuration.asObject(message.getBody());
        Map<String,Object> parameterMap = (Map<String,Object>) JSONObject.parseObject(json);

        System.out.println("--------邮件--------:" + json);
        System.out.println("--------邮件地址--------:" + parameterMap.get("emailAddress"));
        System.out.println("--------邮件标题--------:" + parameterMap.get("title"));
        System.out.println("--------邮件内容--------:" + parameterMap.get(("content")));
    }
}
