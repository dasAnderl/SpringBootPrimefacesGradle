package com.anderl.schedule;

import com.anderl.ScheduleConfigTestApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dasanderl on 17.09.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringApplicationConfiguration(classes = ScheduleConfigTestApplication.class)
public class ScheduleTest {

    @Autowired
    Environment env;

    @Test
    public void test() throws Exception {
        Thread.sleep(10000);
    }
}
