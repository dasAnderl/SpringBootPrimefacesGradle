package com.anderl.base;

import com.anderl.ExternalPropsConfigApplication;
import com.anderl.SpringTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;

/**
 * Created by dasanderl on 17.09.14.
 */
@SpringTest
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
