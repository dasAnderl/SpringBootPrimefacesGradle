package com.anderl.controller;

import com.anderl.dao.TestEntityRepository;
import com.anderl.domain.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
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
    TestEntityRepository testEntityRepository;

    @Transactional
    public void saveNewTestEntity() {

        System.out.println("saving new entity");
        TestEntity entity = new TestEntity();
        entity.setName("name"+new Date().toString());
        entity.setAge(new Random().nextInt());
        testEntityRepository.save(entity);
    }

    public String getUserName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();

    }
}
