package com.anderl.base;

import com.anderl.ExternalPropsConfigApplication;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
@SpringApplicationConfiguration(classes = ExternalPropsConfigApplication.class)
public class ExternalPropsTest {

    @Autowired
    Environment env;

    @Test
    public void test() throws Exception {
        String property = env.getProperty("test.property");
        Assert.assertThat("no prop for key: test.property found", property, Matchers.not(Matchers.isEmptyOrNullString()));
    }
}
