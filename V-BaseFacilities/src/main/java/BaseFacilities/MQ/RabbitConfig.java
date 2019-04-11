package BaseFacilities.MQ;

import BaseFacilities.MQ.unit.MsgReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtual_host;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisher_confirms;

    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean publisher_returns;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtual_host);
        /** 如果要进行消息回调，则这里必须要设置为true */
        connectionFactory.setPublisherConfirms(publisher_confirms);
        connectionFactory.setPublisherReturns(publisher_returns);
        return connectionFactory;
    }

    @Bean
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     */

    public static final String Notify_EXCHANGE      = "notifyExchange";     //通知交换器

    public static final String Notify_QUEUE_SMS     = "smsQueue";           //短信队列名称
    public static final String Notify_QUEUE_EMAIL   = "emailQueue";         //邮件队列名称
    public static final String Notify_QUEUE_AppPush = "appPushQueue";       //app推送队列名称

    public static final String Notify_KEY_SMS       = "SMS";                //短信路由Key
    public static final String Notify_KEY_Email     = "Email";              //邮件路由Key
    public static final String Notify_KEY_AppPush   = "AppPush";            //App推送路由Key

    /**
     * 短信队列
     * @return
     */
    @Bean
    public Queue getSmsQueue() {
        return new Queue(Notify_QUEUE_SMS, true, false, false);
    }

    /**
     * 邮件队列
     * @return
     */
    @Bean
    public Queue getEmailQueue() {
        return new Queue(Notify_QUEUE_EMAIL, true, false, false);
    }

    /**
     * app推送队列
     * @return
     */
    @Bean
    public Queue getAppPushQueue() {
        return new Queue(Notify_QUEUE_AppPush, true, false, false);
    }

    /**
     * 定义notifyExchange
     */
    @Bean
    public DirectExchange notifyEXCHANGE() {
        return new DirectExchange(Notify_EXCHANGE, true, false);
    }

    /**
     * 绑定smsQueue
     * @return
     */
    @Bean
    public Binding bindingSMSQueue() {
        return BindingBuilder.bind(getSmsQueue()).to(notifyEXCHANGE()).with(Notify_KEY_SMS);
    }

    /**
     * 绑定emailQueue
     * @return
     */
    @Bean
    public Binding bindingEmailQueue() {
        return BindingBuilder.bind(getEmailQueue()).to(notifyEXCHANGE()).with(Notify_KEY_Email);
    }

    /**
     * 绑定appPushQueue
     * @return
     */
    @Bean
    public Binding bindingAppPushQueue() {
        return BindingBuilder.bind(getAppPushQueue()).to(notifyEXCHANGE()).with(Notify_KEY_AppPush);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     */

}
