package com.anderl.domain;

import com.anderl.DomainTestApplication;
import com.anderl.SpringTest;
import com.anderl.dao.EntityRepository;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@SpringTest
@SpringApplicationConfiguration(classes = DomainTestApplication.class)
@Transactional
public class EntityTest {

    @Autowired
    EntityRepository entityRepository;
    @Autowired
    EntityManager entityManager;
    Entity entity = DomainProvider.getRandomEntity();

    @Before
    public void save() throws Exception {
        entityRepository.save(entity);
        entityManager.unwrap(Session.class).flush();
        entityManager.unwrap(Session.class).evict(entity);

    }

    @Test
    public void load() {

        Entity entity = entityRepository.findAll().iterator().next();

        //check audting
        assertThat(entity.getCreatedBy(), notNullValue());
        assertThat(entity.getLastModifiedBy(), notNullValue());
        assertThat(entity.getCreatedDate(), notNullValue());
        assertThat(entity.getLastModifiedDate(), notNullValue());

        //check fields
        assertThat(entity.getName(), notNullValue());
        assertThat(entity.getAge(), notNullValue());
        assertThat(entity.getNestedEntitiesNoBatch(), iterableWithSize(greaterThan(0)));
        assertThat(entity.getNestedEntitiesBatch10(), iterableWithSize(greaterThan(0)));
        assertThat(entity.getNestedEntitiesNoBatch().get(0).getNestedName(), notNullValue());
        assertThat(entity.getNestedEntitiesNoBatch().get(0).getNestedName(), notNullValue());
        assertThat(entity.getNestedEntitiesBatch10().get(0).getNestedAge(), notNullValue());
        assertThat(entity.getNestedEntitiesBatch10().get(0).getNestedAge(), notNullValue());
    }

    //only for remembering this cool new spring feature
    @Test
    public void testNewSpringTransactionFeature() {
        //given
        long nrOfEntities = entityRepository.count();

        //when
        entityRepository.save(DomainProvider.getRandomEntity());

        //then
        Assert.assertThat("number is not same", nrOfEntities, is(entityRepository.count()));

        //when
        TestTransaction.flagForCommit();
        TestTransaction.end();

        //then
        Assert.assertThat("wrong entity count(should be one more)", nrOfEntities, is(entityRepository.count()));
    }
}