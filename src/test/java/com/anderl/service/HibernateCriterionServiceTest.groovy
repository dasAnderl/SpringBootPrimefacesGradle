package com.anderl.service

import com.anderl.Application
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by dasanderl on 07.09.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
class HibernateCriterionServiceTest extends groovy.util.GroovyTestCase {
    @Test
    void testBuildCriteria() {
        println 'bhbjhb'
    }
}
