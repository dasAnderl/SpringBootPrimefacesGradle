package com.anderl.amqp;

import com.anderl.MessagingTestApplication;
import com.anderl.config._Profiles;
import com.anderl.domain.DomainProvider;
import com.anderl.service.MessagingService;
import com.anderl.utils.RabbitMqHelper;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringApplicationConfiguration(classes = MessagingTestApplication.class)
public class MessagingTest {

    private static boolean startedRabbitMq = false;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Mocked
    MessagingService messagingService;
    @Value("${amqp.queue.name}")
    private String queueName;
    @Value("${amqp.topic.name}")
    private String topicName;

    /**
     * To run this tests rabbitMqServer must be started
     * and messaging profile must be active
     */

    @BeforeClass
    public static void ensureRabbitMqRunning() throws Exception {

        startedRabbitMq = RabbitMqHelper.startRabbitMqIfNotRrunning();
    }

    @AfterClass
    public static void stopRabbitMqg() throws Exception {
        if (startedRabbitMq) {
            RabbitMqHelper.stopRabbitMg();
        }
    }

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
}
