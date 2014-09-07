package com.anderl.service

import com.anderl.Application
import com.anderl.dao.TestEntityRepository
import com.anderl.domain.TestEntity
import com.anderl.hibernateext.Criteria
import com.anderl.hibernateext.HibernateCriterion
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

/**
 * Created by dasanderl on 07.09.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
class HibernateCriterionServiceTest extends groovy.util.GroovyTestCase {

    @Autowired
    HibernateCriterionService hibernateCriterionService

    @Autowired
    TestEntityRepository testEntityRepository

    @Before
    void before() {
        System.out.println("saving new entity");
        TestEntity entity = new TestEntity();
        entity.setName("name1");
        entity.setAge(1);
        testEntityRepository.save(entity);
        entity = new TestEntity();
        entity.setName("name2");
        entity.setAge(2);
        testEntityRepository.save(entity);
        entity = new TestEntity();
        entity.setName("name3");
        entity.setAge(3);
        testEntityRepository.save(entity);
    }

    @Test
    @Transactional
    void testBuildCriteria() {

        def entities = hibernateCriterionService.getEntities(getCriterions());

        entities.each() { println " ${it}" }
    }

    def getCriterions() {
        def idCriterion = new HibernateCriterion<>(Long.class, TestEntity.class, "id", Criteria.eq, new Long(1));
        def nameCriterion = new HibernateCriterion<>(String.class, TestEntity.class, "name", Criteria.eq, "name1");
        def ageCriterion = new HibernateCriterion<>(Integer.class, TestEntity.class, "age", Criteria.eq, 1);
        return [idCriterion, nameCriterion, ageCriterion]
    }
}
