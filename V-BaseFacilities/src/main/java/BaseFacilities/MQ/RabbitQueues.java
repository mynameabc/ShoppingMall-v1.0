package BaseFacilities.MQ;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueues {

    public static final String QUEUE_A = "QUEUE_A";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";
}
