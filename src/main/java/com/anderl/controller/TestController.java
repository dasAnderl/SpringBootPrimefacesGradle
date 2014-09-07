package com.anderl.controller;

import com.anderl.dao.TestEntityRepository;
import com.anderl.domain.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.Random;

/**
 * Created by dasanderl on 07.09.14.
 */
@Component
@Scope("view")
@ManagedBean//only for autocompletion in xhtml. annotation not working
public class TestController {

    @Autowired
    TestEntityRepository testEntityDao;

    @Transactional
    public void saveNewTestEntity() {

        System.out.println("saving new entity");
        TestEntity entity = new TestEntity();
        entity.setName("name"+new Date().toString());
        entity.setAge(new Random().nextInt());
        testEntityDao.save(entity);
    }
}
