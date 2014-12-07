package com.anderl.amqp;

import com.anderl.MessagingTestApplication;
import com.anderl.SpringTest;
import com.anderl.config._Profiles;
import com.anderl.domain.DomainProvider;
import com.anderl.service.MessagingService;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by dasanderl on 17.09.14.
 */
@Profile(_Profiles.MESSAGING)
@ActiveProfiles(_Profiles.MESSAGING)
@SpringTest
@SpringApplicationConfiguration(classes = MessagingTestApplication.class)
public class MessagingTest {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Mocked
    MessagingService messagingService;
    @Value("${amqp.queue.name}")
    private String queueName;
    @Value("${amqp.topic.name}")
    private String topicName;

    /**
     *
     * To run this tests rabbitMqServer must be started
     * and messaging profile must be active
     *
     * start: rabbitmq-server -detached
     * stop: rabbitmqctl stop
     */

    @Test
    public void testQueue() throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(queueName, DomainProvider.getRandomEntity());

        Thread.sleep(500);

        new Verifications() {{
            messagingService.receiveMessage(any);
            times = 1;
        }};
    }

    @Test
    public void testTopic() throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(topicName, "some routingkey", DomainProvider.getRandomEntity());

        Thread.sleep(500);

        new Verifications() {{
            messagingService.receiveMessage(any);
            times = 1;
        }};
    }
}
