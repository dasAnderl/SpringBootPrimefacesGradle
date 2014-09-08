package com.anderl.service

import com.anderl.Application
import com.anderl.dao.TestEntityRepository
import com.anderl.domain.TestEntity
import com.anderl.hibernate.ext.Criteria
import com.anderl.hibernate.ext.HibernateCriterion
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat

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

    String name1 = "name1"
    String name2 = "name2"
    String name3 = "name3"
    int age1 = 1
    int age2 = 2
    int age3 = 3

    TestEntity te1 = new TestEntity(name: name1, age: age1)
    TestEntity te2 = new TestEntity(name: name2, age: age2)
    TestEntity te3 = new TestEntity(name: name3, age: age3)

    HibernateCriterion<Long, TestEntity> idCriterion = new HibernateCriterion<>(Long.class, TestEntity.class, "id", Criteria.eq, new Long(1));
    HibernateCriterion<String, TestEntity> nameCriterion = new HibernateCriterion<>(String.class, TestEntity.class, "name", Criteria.eq, name1);
    HibernateCriterion<Integer, TestEntity> ageCriterion = new HibernateCriterion<>(Integer.class, TestEntity.class, "age", Criteria.eq, age1);

    HibernateCriterion<String, TestEntity> nameContainsCriterion = new HibernateCriterion<>(String.class, TestEntity.class, "name", Criteria.like, "name");

    @Before
    void before() {
        System.out.println("saving new entities");
        testEntityRepository.save([te1, te2, te3]);
    }

    @Test
    @Transactional
    void testBuildCriteria() {

        def entities = hibernateCriterionService.getEntities([idCriterion, nameCriterion, ageCriterion]);
        assertThat(entities, notNullValue())
        assertThat(entities.size(), is(1))

        def entity = entities.get(0)
        assertThat(entity.name, is(name1))
        assertThat entity.age, is(age1)
        assertThat(entity.id, is(new Long(1)))

        entities = hibernateCriterionService.getEntities([nameContainsCriterion]);
        assertThat(entities, notNullValue())
        assertThat(entities.size(), is(3))
    }
}
