package com.anderl.service;

import com.anderl.config._Profiles;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by dasanderl on 17.09.14.
 */
@Profile(_Profiles.MESSAGING)
@Service
public class MessagingService {

    public void receiveMessage(Object message) {
        System.out.printf("Amqp msg received < %s >", message);
    }
}
