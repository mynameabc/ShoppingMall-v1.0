package BaseFacilities.MQ.unit.notify;

import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class AppPushConcumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(AppPushConcumer.class);

    static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();

    public void onMessage(Message message) {

        String json = (String)configuration.asObject(message.getBody());

        System.out.println("--------AppPush--------:" + json);
    }
}
