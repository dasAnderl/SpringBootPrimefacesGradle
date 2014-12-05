package com.anderl.amqp;

import com.anderl.MessagingTestApplication;
import com.anderl.config._Profiles;
import com.anderl.service.MessagingService;
import mockit.Mocked;
import mockit.Tested;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dasanderl on 17.09.14.
 */
@Profile(_Profiles.MESSAGING)
@ActiveProfiles(_Profiles.MESSAGING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MessagingTestApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessagingTest {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Tested
    MessageListenerAdapter messageListenerAdapter;
    @Mocked
    MessagingService messagingService;
    @Value("${amqp.queue.name}")
    private String queueName;
    @Value("${amqp.topic.name}")
    private String topicName;

    /**
     * To run this test rabbitMqServer must be started
     *
     * start: rabbitmq-server -detached
     * stop: rabbitmqctl stop
     */
    @Test
    public void test() throws Exception {
        System.out.println("Sending message...");
//        rabbitTemplate.convertAndSend(queueName, DomainProvider.getRandomEntity());
//
//        Thread.sleep(500);
//
//        new Verifications() {{
//            messagingService.receiveMessage(any);
//            times = 1;
//        }};
    }
}
