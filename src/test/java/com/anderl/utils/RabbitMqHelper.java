package com.anderl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dasanderl on 08.12.14.
 */
public class RabbitMqHelper {


    private RabbitMqHelper() {
    }

    public static boolean startRabbitMqIfNoRrunning() throws Exception {

        if (!isRabbitMqRunning()) {
            System.out.println("starting rabbit mq");
            Process p = Runtime.getRuntime().exec("rabbitmq-server -detached");
            p.waitFor();
            if (!isRabbitMqRunning()) {
                throw new Exception("Could not start rabbit mq");
            }
            return true;
        }
        return false;
    }

    public static void stopRabbitMg() throws Exception {
        System.out.println("stopping rabbit mq");
        Process p = Runtime.getRuntime().exec("rabbitmqctl stop");
        p.waitFor();
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
}
