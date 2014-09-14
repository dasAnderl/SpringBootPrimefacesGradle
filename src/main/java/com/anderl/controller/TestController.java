package com.anderl.controller;

import com.anderl.dao.TestEntityRepository;
import com.anderl.domain.Entity;
import com.anderl.domain.EntityBuilder;
import com.anderl.domain.NestedEntityBuilder;
import com.anderl.service.TestService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import java.util.Random;

/**
 * Created by dasanderl on 07.09.14.
 */
@Component
@Scope("view")
@ManagedBean//only for autocompletion in xhtml. annotation not working
public class TestController {

    @Autowired
    TestEntityRepository testEntityRepository;

    @Autowired
    TestService testService;

    @Transactional
    public void saveNewTestEntity() {

        System.out.println("saving new entity");
        Entity entity = EntityBuilder.aTestEntity()
                .withAge(new Random().nextInt())
                .withName(new Random().nextInt(1) + "")
                .withNestedEntitiesBatch10(
                        Lists.newArrayList(
                                NestedEntityBuilder.aNestedEntity()
                                        .withNestedAge(new Random().nextInt())
                                        .withNestedName(new Random().nextInt(1) + "")
                                        .build(),
                                NestedEntityBuilder.aNestedEntity()
                                        .withNestedAge(new Random().nextInt())
                                        .withNestedName(new Random().nextInt(1) + "")
                                        .build()
                        )
                )
                .withNestedEntitiesNoBatch(
                        Lists.newArrayList(
                                NestedEntityBuilder.aNestedEntity()
                                        .withNestedAge(new Random().nextInt())
                                        .withNestedName(new Random().nextInt(1) + "")
                                        .build(),
                                NestedEntityBuilder.aNestedEntity()
                                        .withNestedAge(new Random().nextInt())
                                        .withNestedName(new Random().nextInt(1) + "")
                                        .build()
                        )
                ).build();
        testEntityRepository.save(entity);
    }

    public String getUserName() {
        return testService.getUserNameFromService();

    }
}
