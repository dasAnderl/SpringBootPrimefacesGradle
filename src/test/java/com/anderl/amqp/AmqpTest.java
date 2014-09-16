package com.anderl.amqp;

import com.anderl.AmqpTestApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dasanderl on 17.09.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AmqpTestApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AmqpTest {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Value("${amqp.queue.name}")
    private String queueName;
    @Value("${amqp.topic.name}")
    private String topicName;

    /**
     * To run this test rabbitMqServer must be started
     */
    @Test
    public void test() {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ!");
    }
}
