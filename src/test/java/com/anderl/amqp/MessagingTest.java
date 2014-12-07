package com.anderl.amqp;

import com.anderl.MessagingTestApplication;
import com.anderl.config._Profiles;
import com.anderl.domain.DomainProvider;
import com.anderl.service.MessagingService;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
     * <p>
     * start: rabbitmq-server -detached
     * stop: rabbitmqctl stop
     */

    @BeforeClass
    public static void ensureRabbitMqRunning() throws Exception {

        if (!isRabbitMqRunning()) {
            System.out.println("starting rabbit mq");
            Process p = Runtime.getRuntime().exec("rabbitmq-server -detached");
            p.waitFor();
            if (!isRabbitMqRunning()) {
                throw new AssertionError("Could not start rabbit mq");
            }
            startedRabbitMq = true;
            return;
        }
    }

    @AfterClass
    public static void stopRabbitMqg() throws Exception {
        if (startedRabbitMq) {
            System.out.println("stopping rabbit mq");
            Process p = Runtime.getRuntime().exec("rabbitmqctl stop");
            p.waitFor();
        }
    }

    private static boolean isRabbitMqRunning() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("rabbitmqctl status");
        p.waitFor();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Error: unable to connect to node")) {
                return false;
            }
        }
        return true;
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
