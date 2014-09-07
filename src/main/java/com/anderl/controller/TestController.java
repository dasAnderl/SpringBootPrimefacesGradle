package com.anderl.controller;

import com.anderl.dao.TestEntityRepository;
import com.anderl.domain.TestEntity;
import com.anderl.hibernateext.Criteria;
import com.anderl.hibernateext.HibernateCriterion;
import com.anderl.service.HibernateCriterionService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import java.util.Date;
import java.util.List;
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
    HibernateCriterionService hibernateCriterionService;

    HibernateCriterion<Long, TestEntity> idCriterion = new HibernateCriterion<>(Long.class, TestEntity.class, "id", Criteria.eq);
    HibernateCriterion<String, TestEntity> nameCriterion = new HibernateCriterion<>(String.class, TestEntity.class, "name", Criteria.like);
    HibernateCriterion<Integer, TestEntity> ageCriterion = new HibernateCriterion<>(Integer.class, TestEntity.class, "age", Criteria.eq);

    @Transactional
    public void saveNewTestEntity() {

        System.out.println("saving new entity");
        TestEntity entity = new TestEntity();
        entity.setName("name"+new Date().toString());
        entity.setAge(new Random().nextInt());
        testEntityRepository.save(entity);
    }

    @Transactional
    public List<TestEntity> search() {

        System.out.println("searching entities");
        return hibernateCriterionService.getEntities(Lists.newArrayList(idCriterion, nameCriterion, ageCriterion));
    }

    public void setIdCriterion(HibernateCriterion<Long, TestEntity> idCriterion) {
        this.idCriterion = idCriterion;
    }

    public HibernateCriterion<Long, TestEntity> getIdCriterion() {
        return idCriterion;
    }

    public void setNameCriterion(HibernateCriterion<String, TestEntity> nameCriterion) {
        this.nameCriterion = nameCriterion;
    }

    public HibernateCriterion<String, TestEntity> getNameCriterion() {
        return nameCriterion;
    }

    public void setAgeCriterion(HibernateCriterion<Integer, TestEntity> ageCriterion) {
        this.ageCriterion = ageCriterion;
    }

    public HibernateCriterion<Integer, TestEntity> getAgeCriterion() {
        return ageCriterion;
    }
}
