package com.anderl.domain;

import com.anderl.DomainTestApplication;
import com.anderl.dao.TestEntityRepository;
import com.google.common.collect.Lists;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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
public class TestEntityTest {

    private static int age = 1217;
    private static String name = "name";
    private static int nestedAge = 199999;
    private static String nestedName = "nestedName";
    @Autowired
    TestEntityRepository testEntityRepository;
    @Autowired
    EntityManager entityManager;
    TestEntity testEntity = TestEntityBuilder.aTestEntity()
            .withAge(age)
            .withName(name)
            .withNestedEntitiesBatch10(
                    Lists.newArrayList(
                            NestedEntityBuilder.aNestedEntity()
                                    .withNestedAge(nestedAge)
                                    .withNestedName(nestedName)
                                    .build(),
                            NestedEntityBuilder.aNestedEntity()
                                    .withNestedAge(nestedAge)
                                    .withNestedName(nestedName)
                                    .build()
                    )
            )
            .withNestedEntitiesNoBatch(
                    Lists.newArrayList(
                            NestedEntityBuilder.aNestedEntity()
                                    .withNestedAge(nestedAge)
                                    .withNestedName(nestedName)
                                    .build(),
                            NestedEntityBuilder.aNestedEntity()
                                    .withNestedAge(nestedAge)
                                    .withNestedName(nestedName)
                                    .build()
                    )
            ).build();

    @Before
    public void test1Setup() throws Exception {
        testEntityRepository.save(testEntity);
        entityManager.unwrap(Session.class).flush();
        entityManager.unwrap(Session.class).evict(testEntity);

    }

    @Test
    @Transactional
    public void test2Verfiy() {

        TestEntity entity = (TestEntity) entityManager.unwrap(Session.class).createCriteria(TestEntity.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();

        for (NestedEntity nestedEntity : entity.getNestedEntitiesNoBatch()) {
            System.out.println(nestedEntity.getNestedAge());
        }

        for (NestedEntity nestedEntity : entity.getNestedEntitiesBatch10()) {
            System.out.println(nestedEntity.getNestedAge());
        }

//        entity.getNestedEntitiesNoBatch().forEach(nested -> System.out.println(nested.getNestedName()));
//
//        entity.getNestedEntitiesBatch10().forEach(nested -> System.out.println(nested.getNestedName()));


    }
}