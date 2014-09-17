package com.anderl.controller;

import com.anderl.dao.EntityRepository;
import com.anderl.domain.DomainProvider;
import com.anderl.domain.Entity;
import com.anderl.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;

/**
 * Created by dasanderl on 07.09.14.
 */
@Component
@Scope("view")
@ManagedBean//only for autocompletion in xhtml. annotation not working
public class TestController {

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    TestService testService;

    @Transactional
    public void saveTestEntity() {

        System.out.println("saving new entity");
        Entity entity = DomainProvider.getRandomEntity();
        entityRepository.save(entity);
    }

    public String getUserName() {
        return testService.getUserNameFromService();

    }
}
