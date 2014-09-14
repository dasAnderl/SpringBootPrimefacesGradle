package com.anderl.domain;

import com.anderl.DomainTestApplication;
import com.anderl.dao.TestEntityRepository;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DomainTestApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityTest {

    private static int age = 1217;
    private static String name = "name";
    private static int nestedAge = 199999;
    private static String nestedName = "nestedName";
    @Autowired
    TestEntityRepository testEntityRepository;
    @Autowired
    EntityManager entityManager;
    Entity entity = EntityProvider.getRandomEntity();

    @Before
    public void test1Setup() throws Exception {
        testEntityRepository.save(entity);
        entityManager.unwrap(Session.class).flush();
        entityManager.unwrap(Session.class).evict(entity);

    }

    @Test
    @Transactional
    public void test2Verfiy() {

        Entity entity = testEntityRepository.findAll().iterator().next();

        for (NestedEntity nestedEntity : entity.getNestedEntitiesNoBatch()) {
            System.out.println(nestedEntity.getNestedAge());
        }

        for (NestedEntity nestedEntity : entity.getNestedEntitiesBatch10()) {
            System.out.println(nestedEntity.getNestedAge());
        }
    }
}